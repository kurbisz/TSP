package org.example.algorithm.taboo.Neighbourhoods;

import org.example.algorithm.taboo.Neighbourhoods.Moves.InvertMove;
import org.example.algorithm.taboo.Neighbourhoods.Moves.Move;
import org.example.algorithm.taboo.Neighbourhoods.Moves.SwapMove;
import org.example.data.Result;
import org.example.data.TspData;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class Swap implements Neighbourhood {
    @Override
    public ArrayList<Map.Entry<Result, Move>> getNeighbourhoodSymmetric(Result result) {
        int n = result.problem.getSize();
        ArrayList<Map.Entry<Result, Move>> neighbours = new ArrayList<>();
        for (int i = 0; i < result.problem.getSize(); i++) {
            for (int j = i+1; j < result.problem.getSize(); j++) {

                //zrobiłem pewne zmiany w Result, teraz taki konstruktor kopiuje
                Result newResult = new Result(result);

                // wprowadzenie zmiany w newResult
                swap(newResult, i, j);
                int p = i-1;
                if(p < 0) p += n;
                int q = j+1;
                if(q >= n) q -= n;
                if(p == j) continue;
                TspData tspData = newResult.problem;
                int way = newResult.objFuncResult;
                // 1 2 3 4 5 -> 1 5 3 4 2
                // Stad trzeba usunac 1->2, 2->3, 4->5 5->1
                // i trzeba dodac 1->5, 5->3, 4->2, 2->1
                way -= tspData.getDistance(result.way[p], result.way[i]);
                way -= tspData.getDistance(result.way[i], result.way[i+1]);
                way -= tspData.getDistance(result.way[j], result.way[q]);
                way -= tspData.getDistance(result.way[j-1], result.way[j]);
                way += tspData.getDistance(result.way[p], result.way[j]);
                way += tspData.getDistance(result.way[j], result.way[i+1]);
                way += tspData.getDistance(result.way[i], result.way[q]);
                way += tspData.getDistance(result.way[j-1], result.way[i]);
                //ręcznie modyfikuję wynik funkcji celu
                newResult.objFuncResult = way;

                //utworzenie obiektu reprezentującego dokonany ruch
                SwapMove move = new SwapMove(i, j, true);

                //utworzenie pary (nowy rezultat, ruch) i dodanie do listy sąsiadów
                neighbours.add(new AbstractMap.SimpleEntry<Result, Move>(newResult, move));
            }
        }

        return neighbours;
    }

    @Override
    public ArrayList<Map.Entry<Result, Move>> getNeighbourhoodAsymmetric(Result result) {
        ArrayList<Map.Entry<Result, Move>> neighbours = new ArrayList<>();
        int n = result.problem.getSize();
        for (int i = 0; i < result.problem.getSize(); i++) {
            for (int j = 0; j < result.problem.getSize(); j++) {

                //zrobiłem pewne zmiany w Result, teraz taki konstruktor kopiuje
                Result newResult = new Result(result);

                swap(result, i, j);

                newResult.objFuncResult = newResult.calcObjectiveFunction();

                //utworzenie obiektu reprezentującego dokonany ruch
                SwapMove move = new SwapMove(i, j, false);

                //utworzenie pary (nowy rezultat, ruch) i dodanie do listy sąsiadów
                neighbours.add(new AbstractMap.SimpleEntry<Result, Move>(newResult, move));

            }
        }

        return neighbours;
    }

    @Override
    public Neighbourhood copy() {
        return new Swap();
    }

    private void swap(Result result, int i, int j) {
        int tmp = result.way[i];
        result.way[i] = result.way[j];
        result.way[j] = tmp;
    }

}