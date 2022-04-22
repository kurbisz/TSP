package org.example.algorithm.taboo.Neighbourhoods;

import org.example.algorithm.taboo.Neighbourhoods.Moves.InvertMove;
import org.example.algorithm.taboo.Neighbourhoods.Moves.Move;
import org.example.data.Result;
import org.example.data.TspData;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class Invert implements Neighbourhood {
    @Override
    public ArrayList<Map.Entry<Result, Move>> getNeighbourhoodSymmetric(Result result) {
        ArrayList<Map.Entry<Result, Move>> neighbours = new ArrayList<>();
        int n = result.problem.getSize();
        for (int i = 0; i < result.problem.getSize(); i++) {
            for (int j = i+1; j < result.problem.getSize(); j++) {
                /**
                 //TODO:
                 * Tutaj spójrz jak możesz, było i!=j i zarówno j i i szły od 0 ale w sumie invert 5,2 to to samo,
                 * co invert 2,5, więc uznałem, że sensowniej będzie tak
                 * Ale może czegoś nie widzę
                 */
                //zrobiłem pewne zmiany w Result, teraz taki konstruktor kopiuje
                Result newResult = new Result(result);

                // wprowadzenie zmiany w newResult
                reverse(newResult, i, j);
                int p = i-1;
                if(p < 0) p += n;
                int q = j+1;
                if(q >= n) q -= n;
                if(p == j) continue;
                TspData tspData = newResult.problem;
                int way = newResult.objFuncResult;
                way -= tspData.getDistance(result.way[p], result.way[i]);
                way -= tspData.getDistance(result.way[j], result.way[q]);
                way += tspData.getDistance(result.way[p], result.way[j]);
                way += tspData.getDistance(result.way[i], result.way[q]);
                //ręcznie modyfikuję wynik funkcji celu
                newResult.objFuncResult = way;

                //utworzenie obiektu reprezentującego dokonany ruch
                InvertMove move = new InvertMove(i, j, true);

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
                /**
                 //TODO:
                 * Tutaj spójrz jak możesz, było i!=j i zarówno j i i szły od 0 ale w sumie invert 5,2 to to samo,
                 * co invert 2,5, więc uznałem, że sensowniej będzie tak
                 * Ale może czegoś nie widzę
                 */
                //zrobiłem pewne zmiany w Result, teraz taki konstruktor kopiuje
                Result newResult = new Result(result);

                // wprowadzenie zmiany w newResult
                reverse(newResult, i, j);

                newResult.objFuncResult = newResult.calcObjectiveFunction();

                //utworzenie obiektu reprezentującego dokonany ruch
                InvertMove move = new InvertMove(i, j, false);

                //utworzenie pary (nowy rezultat, ruch) i dodanie do listy sąsiadów
                neighbours.add(new AbstractMap.SimpleEntry<Result, Move>(newResult, move));


//                Result newResult2 = new Result(result);
//                reverse(newResult2, j, i);
//                newResult.objFuncResult = newResult.calcObjectiveFunction();
//                InvertMove move2 = new InvertMove(j, i, );
//                neighbours.add(new AbstractMap.SimpleEntry<Result, Move>(newResult2, move2));
            }
        }

        return neighbours;
    }

    private void reverse(Result result, int i, int j) {
        int n = result.problem.getSize();
        int k = 0;
        int am = j-i;
        if(am < 0) am+=n;
        while(k < am/2+1) {
            int from = k+i;
            if(from >= n) from -= n;
            int to = j-k;
            if(to < 0) to += n;
            int tmp = result.way[from];
            result.way[from] = result.way[to];
            result.way[to] = tmp;
            k++;
        }
    }

}