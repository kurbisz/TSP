package org.example.algorithm.genetic.startPopulations;

import org.example.algorithm.genetic.GeneticResult;
import org.example.data.TspData;

import java.util.List;

public interface PopulationGenerator {

    /**
     * Funckja zwracająca listę z osobnikami startowymi
     * @param tspData - dane problemu, dla którego ma zostać wygenerowana populacja
     * @return - lista osobników startowych
     */
    List<GeneticResult> getNewPopulation(TspData tspData);

    PopulationGenerator copy();
}
