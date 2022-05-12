package org.example.algorithm.taboo;

import org.example.algorithm.Algorithm;
import org.example.algorithm.KRandom;
import org.example.algorithm.taboo.ExploreFunctions.Blank;
import org.example.algorithm.taboo.ExploreFunctions.ExploreFunction;
import org.example.algorithm.taboo.Neighbourhoods.Invert;
import org.example.algorithm.taboo.Neighbourhoods.Moves.Move;
import org.example.algorithm.taboo.Neighbourhoods.Neighbourhood;
import org.example.stopFunctions.IterationsStop;
import org.example.stopFunctions.StopFunction;
import org.example.algorithm.taboo.tabooList.BasicTabooList;
import org.example.algorithm.taboo.tabooList.TabooList;
import org.example.data.Result;
import org.example.data.TabooSearchResult;
import org.example.data.TspData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TabooSearch2 extends Algorithm {

    Result mainResult; //aktualny najlepszy wynik
    boolean aspirationCriteria = false; //czy uzywamy kryterium aspiracji
    TabooList tabooListTemplate; //szablon listy tabu
    Neighbourhood neighbourhoodTemplate; //szablon sasiedztwa
    StopFunction stopFunctionTemplate; //szablon funkcji stopu
    ExploreFunction exploreFunctionTemplate = null; //szablon funkcji eksploracji
    LongTermList longTermList = null; //lista dlugoterminowa
    int threadCount = 1;

    class SingleSimplePass implements Runnable{
        TabooSearchResult resultTS;
        Result bestResult;
        boolean aspirationCriteria;
        Neighbourhood neighbourhood;
        StopFunction stopFunction;
        ExploreFunction exploreFunction;

        SingleSimplePass(Result startingResult,
                         boolean aspirationCriteria,
                         TabooList tabooList,
                         Neighbourhood neighbourhood,
                         StopFunction stopFunction,
                         LongTermList longTermList,
                         ExploreFunction exploreFunction){
            this.resultTS = new TabooSearchResult(tabooList, startingResult, longTermList);
            this.bestResult = startingResult;
            this.aspirationCriteria = aspirationCriteria;
            this.neighbourhood = neighbourhood;
            this.stopFunction = stopFunction;
            this.exploreFunction = exploreFunction;
//            printHashCodes();
        }

        @Override
        public void run() {

            ArrayList<Map.Entry<Result, Move>> neighbours;

//            for(int i = 0; i< resultTS.result.problem.getSize(); i++){
//                System.out.println(resultTS.result.way[i]);
//            }

            do {
//                generowanie sąsiedztwa
                if (tspData.isSymmetric()) neighbours = neighbourhood.getNeighbourhoodSymmetric(resultTS.result);
                else neighbours = neighbourhood.getNeighbourhoodAsymmetric(resultTS.result);
//                neighbours = neighbourhood.getNeighbourhoodAsymmetric(resultTS.result);

//                for (Map.Entry<Result, Move> neighbour : neighbours) {
//                    neighbour.getKey().calcObjectiveFunction();
//                    System.out.println("Neighbour: " + neighbour.getKey().objFuncResult);
//                }

                if (!chooseBestNeighbour(neighbours)) return;
                if (exploreFunction != null && exploreFunction.shouldExplore(resultTS.result))
                    exploreFunction.explore(resultTS.result);
            } while ( !stopFunction.check());
        }

        /**
         * Picks the best neighbour from the list of neighbours,
         * updates the taboo list, long term list and the best result (if necessary).
         * @param neighbours
         * @return false if every neighbour is tabooed
         */
        private boolean chooseBestNeighbour(ArrayList<Map.Entry<Result, Move>> neighbours ){
            Map.Entry<Result, Move> candidate = null;    //kandydat na nowe rozwiązanie w obecnym sąsiedztwie

            //jak nie ma aspiracji, to wywalam wszystko to, co jest na taboo
            if(!aspirationCriteria) {
                //explicit użyję iteratora, żeby móc bezpiecznie usuwać elementy z listy w trakcie pętli
                for(Iterator<Map.Entry<Result, Move>> it = neighbours.iterator(); it.hasNext(); ) {
                    Map.Entry<Result, Move> entry = it.next();
                    if ( resultTS.tabooList.contains(entry.getValue())) {
                        it.remove();
                    }
                }
            }

            if(neighbours.size() == 0) return false;
            else candidate = neighbours.get(0);

            //jeśli nie ma aspiracji, to na tym etapie mamy tylko dozwolone rozwiązania
            for(Map.Entry<Result, Move> neighbour : neighbours) {
                //tutaj nie jestem do końca pewien, jak to ma wyglądać
                //w sensie "jeśli jest kryterium aspiracji, to jeśli rozwiązanie jest lepsze, a zakazane, to i tak je bierzemy"
                //ale lepsze od najlepszego dotychczas znalezionego przez TS, czy w tym sąsiedztwie?
                if(neighbour.getKey().objFuncResult < candidate.getKey().objFuncResult){
                    if(!aspirationCriteria){
                        candidate = neighbour;
                    } else {
                        if(!resultTS.tabooList.contains(neighbour.getValue())){
                            candidate = neighbour;
                        } else {
                            if(neighbour.getKey().objFuncResult < bestResult.objFuncResult){
                                candidate = neighbour;
                            }
                        }
                    }
                }
            }

            resultTS.result = candidate.getKey();
//            System.out.println("New best result: " + bestResult.objFuncResult);
            if(candidate.getKey().objFuncResult< bestResult.objFuncResult)
                bestResult = candidate.getKey();

            resultTS.tabooList.add(candidate.getValue());

            //nie ruszałem nic, poza przeniesieniem na nowe oznaczenia
            if (resultTS.longTermList != null && resultTS.longTermList.addCount(candidate.getValue())) {
                boolean b = resultTS.longTermList.isBetter(candidate.getKey().objFuncResult);
                resultTS.longTermList.reset(candidate.getKey(), resultTS.tabooList, b);
                // Jesli rozwiazanie po n krokach nie jest lepsze to wroc do ekstremum
                if(!b) {
                    if(resultTS.longTermList.kick) {
                        resultTS.result = new KRandom(tspData).calculate();
                    }
                    else {
                        resultTS.result = resultTS.longTermList.result;
                        resultTS.tabooList = resultTS.longTermList.list;
                    }
                }
            }
            return true;
        }

        void printHashCodes(){
            System.out.println("Hash codes:");
            System.out.println("Staring result: " + bestResult.hashCode());
            System.out.println("Taboo list: " + resultTS.tabooList.hashCode());
            System.out.println("Neigbourhood: " + neighbourhood.hashCode());
            if (longTermList != null) System.out.println("Long term list: " + resultTS.longTermList.hashCode());
            System.out.println("Stop function: " + stopFunction.hashCode());
        }
    }

    /**
     * Constructor for simplest TabooSearch with default values
     * @param tspData - data for TSP
     */
    public TabooSearch2(TspData tspData, Result startingResult){
        super(tspData);
        mainResult = startingResult;
        mainResult.objFuncResult = mainResult.calcObjectiveFunction();
//        mainResult = new Result(startingResult.problem);
        aspirationCriteria = false;
        tabooListTemplate = new BasicTabooList(7);
        neighbourhoodTemplate = new Invert();
        stopFunctionTemplate = new IterationsStop(10);
        exploreFunctionTemplate = new Blank();
        longTermList = null;
    }

    /**
     * Constructor for TabooSearch with custom values
     * @param tspData - data for TSP
     * @param aspirationCriteria - use aspiration criteria
     * @param tabooListTemplate - template for taboo list
     * @param neighbourhoodTemplate - template for neighbourhood
     * @param stopFunctionTemplate - template for stop function
     * @param longTermList - list of long term solutions (will be shared across threads)
     */
    public TabooSearch2(TspData tspData,
                 Result startingResult,
                 boolean aspirationCriteria,
                 TabooList tabooListTemplate,
                 Neighbourhood neighbourhoodTemplate,
                 StopFunction stopFunctionTemplate,
                 LongTermList longTermList,
                 ExploreFunction exploreFunctionTemplate,
                 int threadCount){
        super(tspData);
        mainResult = startingResult;
        mainResult.objFuncResult = mainResult.calcObjectiveFunction();
        this.aspirationCriteria = aspirationCriteria;
        this.tabooListTemplate = tabooListTemplate;
        this.neighbourhoodTemplate = neighbourhoodTemplate;
        this.stopFunctionTemplate = stopFunctionTemplate;
        this.longTermList = longTermList;
        if(exploreFunctionTemplate == null) this.exploreFunctionTemplate = new Blank();
        else this.exploreFunctionTemplate = exploreFunctionTemplate;
        this.threadCount = threadCount;
    }

    @Override
    public Result calculate() {
//        printHashCodes();
        if (threadCount <= 1){

            SingleSimplePass pass = new SingleSimplePass(mainResult.clone(),
                    aspirationCriteria,
                    tabooListTemplate.cloneTabooList(),
                    neighbourhoodTemplate.copy(),
                    stopFunctionTemplate.copy(),
                    longTermList,
                    exploreFunctionTemplate.copy());

            Thread t = new Thread(pass);
            t.start();
            try {
                t.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return pass.bestResult;
        } else{
            ExecutorService pool = Executors.newFixedThreadPool(threadCount);
            SingleSimplePass[] passes = new SingleSimplePass[threadCount];
            Random r = new Random();
            int change_count = tspData.getSize()/8;

            for (int i = 0; i < threadCount; i++){
//                KRandom r = new KRandom(tspData);
                Result startingResult = mainResult.clone();
                for (int j = 0; j<change_count; j++){
                    int ind_1 = r.nextInt(tspData.getSize());
                    int ind_2;
                    do{
                        ind_2 = r.nextInt(tspData.getSize());
                    } while(ind_1 == ind_2);
                    //losowy swap na wyniku początkowym
                    int temp = startingResult.way[ind_1];
                    startingResult.way[ind_1] = startingResult.way[ind_2];
                    startingResult.way[ind_2] = temp;
                }
                startingResult.objFuncResult = startingResult.calcObjectiveFunction();
//                System.out.println("Starting result: " + startingResult.objFuncResult);
                passes[i] = new SingleSimplePass(startingResult,
                        aspirationCriteria,
                        tabooListTemplate.cloneTabooList(),
                        neighbourhoodTemplate.copy(),
                        stopFunctionTemplate.copy(),
                        longTermList,
                        exploreFunctionTemplate.copy());
                pool.execute(passes[i]);
            }
            pool.shutdown();
            try {
                pool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            for (int i = 1; i < threadCount; i++){
//                System.out.println("Thread " + i + ": " + passes[i].bestResult.objFuncResult);
                if (passes[i].bestResult.objFuncResult < mainResult.objFuncResult){
                    mainResult = passes[i].bestResult;
                }
            }
            return mainResult;
        }
    }

    void printHashCodes() {
        System.out.println("Main function hash codes:");
        System.out.println("Staring result: " + mainResult.hashCode());
        System.out.println("Taboo list: " + tabooListTemplate.hashCode());
        System.out.println("Neigbourhood: " + neighbourhoodTemplate.hashCode());
        if (longTermList != null) System.out.println("Long term list: " + longTermList.hashCode());
        System.out.println("Stop function: " + stopFunctionTemplate.hashCode());
    }

}
