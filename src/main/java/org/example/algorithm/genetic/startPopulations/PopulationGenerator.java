package org.example.algorithm.genetic.startPopulations;

import org.example.algorithm.genetic.GeneticResult;
import java.util.List;

public interface PopulationGenerator {

    List<GeneticResult> getNewPopulation();

}
