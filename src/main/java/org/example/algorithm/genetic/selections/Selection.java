package org.example.algorithm.genetic.selections;

import org.example.algorithm.genetic.GeneticResult;
import org.example.data.Pair;

import java.util.List;

public interface Selection {
    /**
     * Funkcja zwracająca listę par rodziców
     * @param population - populacja, z której mają zostać wybrane rodzice
     * @return - lista par rodziców
     */
    List<Pair> getParents(List<GeneticResult> population);

    Selection copy();
}
