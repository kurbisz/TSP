package org.example.algorithm.genetic.selections;

import junit.framework.TestCase;
import org.example.FileLoader;
import org.example.algorithm.genetic.GeneticResult;
import org.example.data.Pair;
import org.example.data.TspData;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static org.example.RunClass.loader;

public class RandomSelectionTest extends TestCase {

    TspData data;
    int size = 10;
    ArrayList<GeneticResult> population;


    @Before
    public void setUp() {
        loader = new FileLoader("metro3.tsp");
        loader.load();
        data = loader.getTspData();
        population = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            population.add(new GeneticResult(data));
        }
    }

    public void testGetParents() {
        RandomSelection randomSelection = new RandomSelection();
        List<Pair> parents = randomSelection.getParents(population);
        assertEquals(size/2, parents.size());
        for (Pair pair : parents) {
            System.out.print(parents.indexOf(pair) + ": ");
            System.out.println( population.indexOf(pair.getParent1()) + " " + population.indexOf(pair.getParent2()));
        }
    }
}