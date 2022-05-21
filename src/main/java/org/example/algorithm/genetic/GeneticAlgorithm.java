package org.example.algorithm.genetic;

import org.example.algorithm.Algorithm;
import org.example.algorithm.genetic.Evaluations.Evaluator;
import org.example.algorithm.genetic.Fillers.Filler;
import org.example.algorithm.genetic.crossovers.Crossover;
import org.example.algorithm.genetic.mutations.Mutation;
import org.example.algorithm.genetic.selections.Selection;
import org.example.algorithm.genetic.startPopulations.PopulationGenerator;
import org.example.data.Pair;
import org.example.data.Result;
import org.example.data.TspData;
import org.example.stopFunctions.StopFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class GeneticAlgorithm extends Algorithm {

	PopulationGenerator populationGeneratorTemplate;
	Selection selectionTemplate = null;
	Mutation mutationTemplate = null;
	Crossover crossoverTemplate = null;
	Evaluator evaluatorTemplate = null;
	Filler fillerTemplate = null;
	StopFunction stopFunctionTemplate = null;
	int threadCount = 1;

	public GeneticAlgorithm(TspData tspData) {
		super(tspData);
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
			GeneticResult thisBest = pass.returnBest();
			if(best == null || best.objFuncResult > thisBest.objFuncResult){
				best = thisBest;
			}
		}
		return best;
	}

	class SingleGeneticThread implements Runnable {

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
			mutation = mutationTemplate.copy();
			crossover = crossoverTemplate.copy();
			evaluator = evaluatorTemplate.copy();
			filler = fillerTemplate.copy();
			stopFunction = stopFunctionTemplate.copy();
		}

		@Override
		public void run() {
			currentPopulation = populationGenerator.getNewPopulation(tspData);
			List<GeneticResult> oldPopulation = null;
			do{
				if(evaluator != null)
					currentPopulation = evaluator.evaluatePopulation(currentPopulation, oldPopulation);
				List<Pair> parents = selection.getParents(currentPopulation);
				currentPopulation = crossover.getNewPopulation(parents);
				filler.fillPopulation(currentPopulation, oldPopulation);
				if( mutation != null)
					currentPopulation = mutation.getMutatedPopulation(currentPopulation);
				oldPopulation = currentPopulation;
			} while(!stopFunction.check());
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