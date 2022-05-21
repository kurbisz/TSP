package org.example.algorithm.genetic.startPopulations;

import junit.framework.TestCase;
import org.example.FileLoader;
import org.example.algorithm.genetic.GeneticResult;
import org.example.data.TspData;
import org.junit.Before;

import java.io.File;
import java.util.List;

import static org.example.RunClass.loader;

public class RandomPopulationTest extends TestCase {

    TspData data;
    int size = 10;

    @Before
    public void setUp() {
        loader = new FileLoader("metro3.tsp");
        loader.load();
        data = loader.getTspData();
    }

    public void testGetNewPopulation() {
        RandomPopulation randomPopulation = new RandomPopulation(size);
        List<GeneticResult> population = randomPopulation.getNewPopulation(data);
        assertEquals(size, population.size());
        for (GeneticResult result : population) {
            System.out.print(population.indexOf(result) + ": ");
            System.out.println(result.toString());
        }


    }
}