package org.example.algorithm.genetic.startPopulations;

import org.example.algorithm.genetic.GeneticResult;
import org.example.data.TspData;

import java.util.List;

public interface PopulationGenerator {

    List<GeneticResult> getNewPopulation(TspData tspData);

}
