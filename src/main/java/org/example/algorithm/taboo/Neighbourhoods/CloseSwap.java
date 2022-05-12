package org.example.algorithm.taboo.Neighbourhoods;

import org.example.algorithm.taboo.Neighbourhoods.Moves.Move;
import org.example.algorithm.taboo.Neighbourhoods.Moves.SwapMove;
import org.example.data.Result;
import org.example.data.TspData;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class CloseSwap implements Neighbourhood {
    @Override
    public ArrayList<Map.Entry<Result, Move>> getNeighbourhoodSymmetric(Result result) {
        return getNeighbourhoodAsymmetric(result);
    }

    @Override
    public ArrayList<Map.Entry<Result, Move>> getNeighbourhoodAsymmetric(Result result) {
        ArrayList<Map.Entry<Result, Move>> neighbours = new ArrayList<>();
        int n = result.problem.getSize();
        for (int i = 0; i < result.problem.getSize(); i++) {

            //zrobiłem pewne zmiany w Result, teraz taki konstruktor kopiuje
            Result newResult = new Result(result);

            swap(newResult, i, (i+1)%n);

            newResult.objFuncResult = newResult.calcObjectiveFunction();

            //utworzenie obiektu reprezentującego dokonany ruch
            SwapMove move = new SwapMove(i, (i+1)%n, false);

            //utworzenie pary (nowy rezultat, ruch) i dodanie do listy sąsiadów
            neighbours.add(new AbstractMap.SimpleEntry<Result, Move>(newResult, move));

        }

        return neighbours;
    }

    @Override
    public Neighbourhood copy() {
        return new CloseSwap();
    }

    private void swap(Result result, int i, int j) {
        int tmp = result.way[i];
        result.way[i] = result.way[j];
        result.way[j] = tmp;
    }

}