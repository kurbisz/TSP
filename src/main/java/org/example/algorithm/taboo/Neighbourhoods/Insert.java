package org.example.algorithm.taboo.Neighbourhoods;

import org.example.algorithm.taboo.Neighbourhoods.Moves.InvertMoveWithoutSymm;
import org.example.algorithm.taboo.Neighbourhoods.Moves.Move;
import org.example.data.Result;
import org.example.data.TspData;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class Insert implements Neighbourhood {
    @Override
    public ArrayList<Map.Entry<Result, Move>> getNeighbourhoodSymmetric(Result result) {
        return getNeighbourhoodSymmetric(result);
    }

    @Override
    public ArrayList<Map.Entry<Result, Move>> getNeighbourhoodAsymmetric(Result result) {
        ArrayList<Map.Entry<Result, Move>> neighbours = new ArrayList<>();
        int n = result.problem.getSize();
        for (int i = 0; i < result.problem.getSize(); i++) {
            for (int j = i+1; j < result.problem.getSize(); j++) {
                if(i==j) continue;
                //zrobiłem pewne zmiany w Result, teraz taki konstruktor kopiuje
                Result newResult = new Result(result);

                // wprowadzenie zmiany w newResult
                insert(newResult, i, j);

                newResult.objFuncResult = newResult.calcObjectiveFunction();

                //utworzenie obiektu reprezentującego dokonany ruch
                InvertMoveWithoutSymm move = new InvertMoveWithoutSymm(i, j);

                //utworzenie pary (nowy rezultat, ruch) i dodanie do listy sąsiadów
                neighbours.add(new AbstractMap.SimpleEntry<Result, Move>(newResult, move));
            }
        }

        return neighbours;
    }

    @Override
    public Neighbourhood copy() {
        return new Insert();
    }

    private void insert(Result result, int i, int j) {
        int tmp = result.way[i];
        for(int k = i; k < j; k++) {
            result.way[k] = result.way[k+1];
        }
        result.way[j] = tmp;
    }

}