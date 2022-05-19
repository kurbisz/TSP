package org.example.algorithm.genetic.startPopulations;

import org.example.algorithm.KRandom;
import org.example.algorithm.genetic.GeneticResult;
import org.example.data.TspData;

import java.util.ArrayList;
import java.util.List;

public class RandomPopulation implements PopulationGenerator {

    @Override
    public List<GeneticResult> getNewPopulation(TspData tspData) {
        ArrayList<GeneticResult> population = new ArrayList<>();

        KRandom kRandom = new KRandom(tspData);




        return population;
    }
}
}
