package org.example.algorithm;

import org.example.algorithm.Neighbourhoods.Moves.Move;
import org.example.algorithm.Neighbourhoods.Neighbourhood;
import org.example.data.Result;
import org.example.data.TspData;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Queue;

public class TabooSearch extends Algorithm {

    final int tabooListSize = 7;
    final int maxIterations = 1000;

    Result result;
    Neighbourhood neighbourhood;
    boolean aspirationCriteria;
    TabooList tabooList;
    int iterations = 0;

    public TabooSearch(TspData tspData) {
        super(tspData);
        tabooList = new TabooList(tabooListSize);
    }

    public void setParameters(Result startingResult, Neighbourhood neighbourhood, boolean aspirationCriteria) {
        this.result = startingResult;
        result.calcObjectiveFunction();
        this.neighbourhood = neighbourhood;
        this.aspirationCriteria = aspirationCriteria;
    }

    @Override
    public Result calculate() {
        ArrayList<Entry<Result, Move>> neighbours;
        do {
            if (tspData.isSymmetric()) neighbours = neighbourhood.getNeighbourhoodSymmetric(result);
            else neighbours = neighbourhood.getNeighbourhoodAsymmetric(result);
            if (!chooseBestNeighbour(neighbours)) return result;
        } while ( !stopFunction());
        return result;
    }

    private boolean stopFunction() {
        if(iterations >= maxIterations) return true;
        iterations++;
        return false;
    }

    private boolean chooseBestNeighbour(ArrayList<Entry<Result, Move>> neighbours) {
        Result candidate = null;
        //wiem, że dziwnie to wygląda, ale wydaje mi się, że musi to być mniej więcej w ten sposób
        //jak nie ma kryterium aspiracji, to mogłoby się zdarzyć, że pierwszy sąsiad akurat ma najlepszą funkcję
        //celu, ale jest niedozwolony, bo jest w tablicy tabu. I wtedy dalsza pętla nie obrałaby nic
        if(aspirationCriteria) candidate = neighbours.get(0).getKey();
        else {
            for (Entry<Result, Move> entry : neighbours) {
                if ( !tabooList.containsMove(entry.getValue())) candidate = entry.getKey();
            }
        }
        //jeśli tutaj nadal candidate jest nullem, to ozn., że mamy aspiration = false i
        //wszystkie ruchy są na taboo - przerywam algorytm
        if(candidate == null) return false;

        for(Entry<Result, Move> neighbour : neighbours) {
            Result res = neighbour.getKey();
            Move move = neighbour.getValue();
            if(res.objFuncResult < candidate.objFuncResult){
                if(tabooList.containsMove(move)){
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
