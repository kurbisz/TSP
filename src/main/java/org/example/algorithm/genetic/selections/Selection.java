package org.example.algorithm.genetic.selections;

import org.example.data.Pair;

import java.util.List;

public interface Selection {

    List<Pair> getParents(List<Pair> population);

}
