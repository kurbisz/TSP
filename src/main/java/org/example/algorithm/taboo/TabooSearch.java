package org.example.algorithm.taboo;

import org.example.algorithm.Algorithm;
import org.example.algorithm.taboo.Neighbourhoods.Moves.Move;
import org.example.algorithm.taboo.Neighbourhoods.Neighbourhood;
import org.example.algorithm.taboo.stopFunctions.StopFunction;
import org.example.algorithm.taboo.stopFunctions.TimeStop;
import org.example.algorithm.taboo.tabooList.InvertTabooList;
import org.example.algorithm.taboo.tabooList.TabooList;
import org.example.data.Result;
import org.example.data.TspData;

import java.util.ArrayList;
import java.util.Map.Entry;

public class TabooSearch extends Algorithm {

    final int tabooListSize = 7;

    Result result;
    Neighbourhood neighbourhood;
    boolean aspirationCriteria;
    TabooList tabooList;
    StopFunction stopFunction;

    int threads = 0;

    public TabooSearch(TspData tspData) {
        super(tspData);
    }

    public void setParameters(Result startingResult,
                              Neighbourhood neighbourhood,
                              boolean aspirationCriteria,
                              TabooList tabooList,
                              StopFunction stopFunction) {
        this.result = startingResult;
        result.calcObjectiveFunction();
        this.neighbourhood = neighbourhood;
        this.aspirationCriteria = aspirationCriteria;
        this.tabooList = tabooList;
        this.stopFunction = stopFunction;
    }

    public void setParameters(Result startingResult,
                              Neighbourhood neighbourhood,
                              boolean aspirationCriteria) {
        setParameters(startingResult, neighbourhood, aspirationCriteria,
                new InvertTabooList(tabooListSize, tspData.getSize()),
                new TimeStop(10000000000L));
    }

    /**
     * Set taboo search to sync/async mode (bigger than 1 means async)
     * @param threads new amount of used threads
     */
    public void setAsync(int threads) {
        this.threads = threads;
    }

    @Override
    public Result calculate() {
        if(threads <= 1) {
            return calculateSingleResult(result);
        }
        else {
            final Result bestResults[] = new Result[threads];
            Thread thread[] = new Thread[threads];
            for(int i = 0; i < threads; i++) {
                final int k = i;
                thread[i] = new Thread() {
                    @Override
                    public void run() {
                        bestResults[k] = calculate();
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

    private Result calculateSingleResult(Result res) {
        ArrayList<Entry<Result, Move>> neighbours;
        do {
            if (tspData.isSymmetric()) neighbours = neighbourhood.getNeighbourhoodSymmetric(res);
            else neighbours = neighbourhood.getNeighbourhoodAsymmetric(res);
            if (!chooseBestNeighbour(neighbours)) return res;
        } while ( !stopFunction());
        return res;
    }

    private boolean stopFunction() {
        return stopFunction.check();
    }

    private boolean chooseBestNeighbour(ArrayList<Entry<Result, Move>> neighbours) {
        Result candidate = null;
        //wiem, że dziwnie to wygląda, ale wydaje mi się, że musi to być mniej więcej w ten sposób
        //jak nie ma kryterium aspiracji, to mogłoby się zdarzyć, że pierwszy sąsiad akurat ma najlepszą funkcję
        //celu, ale jest niedozwolony, bo jest w tablicy tabu. I wtedy dalsza pętla nie obrałaby nic
        if(aspirationCriteria) candidate = neighbours.get(0).getKey();
        else {
            for (Entry<Result, Move> entry : neighbours) {
                if ( !tabooList.contains(entry.getValue())) candidate = entry.getKey();
            }
        }
        //jeśli tutaj nadal candidate jest nullem, to ozn., że mamy aspiration = false i
        //wszystkie ruchy są na taboo - przerywam algorytm
        if(candidate == null) return false;

        for(Entry<Result, Move> neighbour : neighbours) {
            Result res = neighbour.getKey();
            Move move = neighbour.getValue();
            if(res.objFuncResult < candidate.objFuncResult){
                if(tabooList.contains(move)){
                    if( aspirationCriteria && res.objFuncResult < result.objFuncResult) {
                        candidate = res;
                    }
                } else {
                    candidate = res;
                }
            }
        }
        result = candidate;
        tabooList.add(neighbours.get(0).getValue());
        return true;
    }

}
