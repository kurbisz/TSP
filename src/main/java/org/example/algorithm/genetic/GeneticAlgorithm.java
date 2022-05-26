package org.example.algorithm.genetic;

import org.example.algorithm.Algorithm;
import org.example.algorithm.genetic.Evaluations.Evaluator;
import org.example.algorithm.genetic.Fillers.Filler;
import org.example.algorithm.genetic.Fillers.RandomFiller;
import org.example.algorithm.genetic.crossovers.Crossover;
import org.example.algorithm.genetic.crossovers.PartialCrossover;
import org.example.algorithm.genetic.mutations.Mutation;
import org.example.algorithm.genetic.selections.RandomSelection;
import org.example.algorithm.genetic.selections.Selection;
import org.example.algorithm.genetic.selections.SimpleBestSelection;
import org.example.algorithm.genetic.startPopulations.PopulationGenerator;
import org.example.algorithm.genetic.startPopulations.RandomPopulation;
import org.example.data.Pair;
import org.example.data.Result;
import org.example.data.TspData;
import org.example.stopFunctions.StopFunction;
import org.example.stopFunctions.TimeStop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class GeneticAlgorithm extends Algorithm {

	PopulationGenerator populationGeneratorTemplate;
	Selection selectionTemplate;
	Mutation mutationTemplate = null;
	Crossover crossoverTemplate;
	Evaluator evaluatorTemplate = null;
	Filler fillerTemplate;
	StopFunction stopFunctionTemplate = null;
	int threadCount = 1;

	public GeneticAlgorithm(TspData tspData) {
		super(tspData);
		// Initialize variables with default classes
		int n = tspData.getSize();
		populationGeneratorTemplate = new RandomPopulation(1000);
		selectionTemplate = new SimpleBestSelection();
		crossoverTemplate = new PartialCrossover(n/3, 2*n/3, true);
		fillerTemplate = new RandomFiller();
		stopFunctionTemplate = new TimeStop(60000000000L); // 60s na wykonanie
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public void setStartPopulation(PopulationGenerator populationGenerator){
		this.populationGeneratorTemplate = populationGenerator;
	}

	public void setSelection(Selection selection){
		this.selectionTemplate = selection;
	}

	public void setMutationTemplate(Mutation mutationTemplate){
		this.mutationTemplate = mutationTemplate;
	}

	public void setCrossoverTemplate(Crossover crossoverTemplate){
		this.crossoverTemplate = crossoverTemplate;
	}

	public void setEvaluatorTemplate(Evaluator evaluatorTemplate){
		this.evaluatorTemplate = evaluatorTemplate;
	}

	public void setStopFunctionTemplate(StopFunction stopFunctionTemplate) {
		this.stopFunctionTemplate = stopFunctionTemplate;
	}

	@Override
	public Result calculate() {

		ExecutorService pool = Executors.newFixedThreadPool(threadCount);
		ArrayList<SingleGeneticThread> passes = new ArrayList<>();
		for(int i = 0; i < threadCount; i++){
			passes.add(new SingleGeneticThread());
		}
		for(SingleGeneticThread pass : passes){
			pool.execute(pass);
		}
		pool.shutdown();
		try {
			boolean succeeded = pool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		GeneticResult best = null;
		for(SingleGeneticThread pass : passes){
			GeneticResult thisBest = pass.bestResult;
			if(best == null || best.objFuncResult > thisBest.objFuncResult){
				best = thisBest;
			}
		}
		return best;
	}

	class SingleGeneticThread implements Runnable {

		GeneticResult bestResult;

		PopulationGenerator populationGenerator;
		Selection selection;
		Mutation mutation;
		Crossover crossover;
		Evaluator evaluator;
		Filler filler;
		StopFunction stopFunction;

		List<GeneticResult> currentPopulation;

		SingleGeneticThread(){
			populationGenerator = populationGeneratorTemplate.copy();
			selection = selectionTemplate.copy();
			if(mutationTemplate != null)
				mutation = mutationTemplate.copy();
			crossover = crossoverTemplate.copy();
			if(evaluator != null)
				evaluator = evaluatorTemplate.copy();
			filler = fillerTemplate.copy();
			stopFunction = stopFunctionTemplate.copy();
		}

		// Main code of genetic algorithm
		@Override
		public void run() {
			currentPopulation = populationGenerator.getNewPopulation(tspData);
			List<GeneticResult> oldPopulation = null;
			do{
//				for(GeneticResult re : currentPopulation) System.out.println(re.toString());
				if(evaluator != null)
					currentPopulation = evaluator.evaluatePopulation(currentPopulation, oldPopulation);
				List<Pair> parents = selection.getParents(currentPopulation);
//				for(Pair p : parents) System.out.println(p.getParent1() + "      " + p.getParent2());
				currentPopulation = crossover.getNewPopulation(parents);
				bestResult = returnBest();
//				System.out.println("Size: " + currentPopulation.size());
				System.out.println("Best: " + bestResult + " (score: " + bestResult.objFuncResult + ")");
				filler.fillPopulation(currentPopulation, oldPopulation);
				if(mutation != null)
					currentPopulation = mutation.getMutatedPopulation(currentPopulation);
				oldPopulation = currentPopulation;
			} while(!stopFunction.check());
			bestResult = returnBest();
//			for(GeneticResult re : currentPopulation) System.out.println(re.toString());
		}

		GeneticResult returnBest(){
			int bestObjFunc = Integer.MAX_VALUE;
			GeneticResult bestResult = null;
			for(GeneticResult result : currentPopulation){
				if(result.objFuncResult < bestObjFunc){
					bestObjFunc = result.objFuncResult;
					bestResult = result;
				}
			}
			return bestResult;
		}
	}

}

/**
 * Schemat tworzenia populacji startowej:
 *  - klasa, która zwraca tablicę/listę zawierającą początkową populację
 *  - może wykorzystajmy już istniejące sąsiedztwa + ewentualnie coś nowego
 *
 * Schemat ewaluacji:
 *  -
 */