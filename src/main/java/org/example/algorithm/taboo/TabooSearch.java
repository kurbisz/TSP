package org.example.algorithm.taboo;

import org.example.algorithm.Algorithm;
import org.example.algorithm.KRandom;
import org.example.algorithm.taboo.Neighbourhoods.Moves.Move;
import org.example.algorithm.taboo.Neighbourhoods.Neighbourhood;
import org.example.algorithm.taboo.stopFunctions.StopFunction;
import org.example.algorithm.taboo.stopFunctions.TimeStop;
import org.example.algorithm.taboo.tabooList.InvertTabooList;
import org.example.algorithm.taboo.tabooList.TabooList;
import org.example.data.Result;
import org.example.data.TabooSearchResult;
import org.example.data.TspData;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Klasa implementująca algorytm Tabu Search.
 */
public class TabooSearch extends Algorithm {

    final int tabooListSize = 7;

    Result result;  //aktualny najlepszy wynik, a na początku działania wynik startowy
    //parametry algorytmu
    Neighbourhood neighbourhood = null;
    boolean aspirationCriteria = false;
    TabooList tabooList = null;
    StopFunction stopFunction = null;
    LongTermList longTermList = null;

    int threads = 0;

    public TabooSearch(TspData tspData) {
        super(tspData);
    }

    /**
     * funkcja pozwalająca ustawić wszystkie parametry algorytmu
     * @param startingResult - rozwiązanie startowe
     * @param neighbourhood - używane sąsiedztwo
     * @param aspirationCriteria - czy używać kryterium aspiracji
     * @param tabooList - używana lista tabu
     * @param stopFunction - używana stop function
     * @param longTermList - używana lista długoterminowa (może być null)
     */
    public void setParameters(Result startingResult,
                              Neighbourhood neighbourhood,
                              boolean aspirationCriteria,
                              TabooList tabooList,
                              StopFunction stopFunction,
                              LongTermList longTermList) {
        this.result = startingResult;
        result.calcObjectiveFunction();
        this.neighbourhood = neighbourhood;
        this.aspirationCriteria = aspirationCriteria;
        this.tabooList = tabooList;
        this.stopFunction = stopFunction;
        this.longTermList = longTermList;
    }

    public void setParameters(Result startingResult, Neighbourhood neighbourhood, boolean aspirationCriteria) {
        setParameters(startingResult, neighbourhood, aspirationCriteria,
                new InvertTabooList(tabooListSize, tspData.getSize()),
                new TimeStop(10000000000L), null);
    }

    /**
     * Set taboo search to sync/async mode (bigger than 1 means async)
     * @param threads new amount of used threads
     */
    public void setAsync(int threads) {
        this.threads = threads;
    }
/*
    @Override
    public Result calculate() {
        if(threads <= 1) {
            return calculateSingleResult(result);
        }
        else {
            final Result[] bestResults = new Result[threads];
            Thread[] thread = new Thread[threads];
            for(int i = 0; i < threads; i++) {
                final int k = i;

                thread[i] = new Thread() {
                    @Override
                    public void run() {
                        Result r = new Result(tspData);
                        for (int j = 0; j < tspData.getSize(); j++) {
                            int ra = ThreadLocalRandom.current().nextInt(tspData.getSize());
                            int tmp = r.way[ra];
                            r.way[ra] = r.way[j];
                            r.way[j] = tmp;
                        }
                        bestResults[k] = calculateSingleResult(r);
                    }
                };
                thread[i].start();
            }

            for(int i = 0; i < threads; i++) {
                try {
                    thread[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int min = bestResults[0].calcObjectiveFunction();
            int minIndex = 0;
            for(int i = 1; i < threads; i++) {
                int func = bestResults[i].calcObjectiveFunction();
                if(func < min) {
                    min = func;
                    minIndex = i;
                }
            }
            return bestResults[minIndex];
        }
    }
*/

    /**
     * Wewnętrzna klasa implementująca Runnable - szkielet do wątku wykonującego pojedynczego TS
     */
    class SimpleSinglePass implements Runnable{

        Result res;

        SimpleSinglePass(Result startingResult) {
            this.res = startingResult;
        }

        @Override
        public void run() {
            ArrayList<Entry<Result, Move>> neighbours;
            TabooSearchResult tabooSearchResult = new TabooSearchResult(tabooList.clone(), res.clone(), longTermList.clone());
            do {
                //generowanie sąsiedztwa
                if (tspData.isSymmetric()) neighbours = neighbourhood.getNeighbourhoodSymmetric(res);
                else neighbours = neighbourhood.getNeighbourhoodAsymmetric(res);
                if (!chooseBestNeighbour(tabooSearchResult, neighbours)) return;
            } while ( !stopFunction());
            res = tabooSearchResult.result;
        }
    }

    @Override
    public Result calculate() {
        //przy jednym wątku normalnie jest liczone na podstawie rozwiązania startowego
        if(threads <= 1) {
            SimpleSinglePass pass = new SimpleSinglePass(result);
            Thread t = new Thread(pass);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return pass.res;
        } else {    //przy wielu wątkach liczone są oddzielne taboosearch dla rozwiązań startowych z KRandom
            SimpleSinglePass[] passes = new SimpleSinglePass[threads];
            ExecutorService executor = Executors.newFixedThreadPool(threads);
            for(int i = 0; i < threads; i++) {
                KRandom r = new KRandom(tspData);
                passes[i] = new SimpleSinglePass(r.calculate());
                executor.execute(passes[i]);
            }
            try{
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Result best = passes[0].res;
            for(int i = 1; i < threads; i++) {
                if (passes[i].res.calcObjectiveFunction() < best.calcObjectiveFunction()) {
                    best = passes[i].res;
                }
            }
            return best;
        }
    }

    private boolean stopFunction() {
        return stopFunction.check();
    }

    private boolean chooseBestNeighbour(TabooSearchResult tsr, ArrayList<Entry<Result, Move>> neighbours) {
        Result candidate = null;
        //wiem, że dziwnie to wygląda, ale wydaje mi się, że musi to być mniej więcej w ten sposób
        //jak nie ma kryterium aspiracji, to mogłoby się zdarzyć, że pierwszy sąsiad akurat ma najlepszą funkcję
        //celu, ale jest niedozwolony, bo jest w tablicy tabu. I wtedy dalsza pętla nie obrałaby nic

        if(aspirationCriteria) candidate = neighbours.get(0).getKey();
        else {
            for (Entry<Result, Move> entry : neighbours) {
                if ( !tsr.tabooList.contains(entry.getValue())) candidate = entry.getKey();
            }
        }
        //jeśli tutaj nadal candidate jest nullem, to ozn., że mamy aspiration = false i
        //wszystkie ruchy są na taboo - przerywam algorytm
        if(candidate == null) return false;

        /**
         * W sumie jeszcze jakimś wyjściem byłoby obranie
         * candidate = tsr.result;
         * może i to nie jest takie głupie
         */

        for(Entry<Result, Move> neighbour : neighbours) {
            Result res = neighbour.getKey();
            Move move = neighbour.getValue();
            if(res.objFuncResult < candidate.objFuncResult){
                if(tsr.tabooList.contains(move)){
                    if(aspirationCriteria && res.objFuncResult < tsr.result.objFuncResult) {
                        candidate = res;
                    }
                } else {
                    candidate = neighbour.getKey();
                }
            }
        }
        tsr.result = candidate;
        Move m = neighbours.get(0).getValue();
        tsr.tabooList.add(m);
        if (tsr.longTermList != null && tsr.longTermList.addCount(m)) {
            boolean b = tsr.longTermList.isBetter(candidate.calcObjectiveFunction());
            tsr.longTermList.reset(candidate, tsr.tabooList, b);
            // Jesli rozwiazanie po n krokach nie jest lepsze to wroc do ekstremum
            if(!b) {
                tsr.result = tsr.longTermList.result;
                tsr.tabooList = tsr.longTermList.list;
            }
        }
        return true;
    }

}
