package org.example.algorithm.Neighbourhoods;

import org.example.algorithm.Neighbourhoods.Moves.Move;
import org.example.data.Result;

import java.util.ArrayList;
import java.util.Map;

public interface Neighbourhood {

    ArrayList<Map.Entry<Result, Move>> getNeighbourhoodSymmetric(Result result);
    ArrayList<Map.Entry<Result, Move>> getNeighbourhoodAsymmetric(Result result);
}
