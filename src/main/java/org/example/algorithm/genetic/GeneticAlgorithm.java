package org.example.algorithm.genetic;

import org.example.algorithm.Algorithm;
import org.example.algorithm.genetic.Evaluations.Evaluator;
import org.example.algorithm.genetic.Fillers.Filler;
import org.example.algorithm.genetic.Fillers.RandomFiller;
import org.example.algorithm.genetic.crossovers.Crossover;
import org.example.algorithm.genetic.crossovers.PartialCrossover;
import org.example.algorithm.genetic.longterm.LongTermChecker;
import org.example.algorithm.genetic.longterm.LongTermEditor;
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
	StopFunction stopFunctionTemplate;
	LongTermChecker longTermCheckerTemplate = null;
	LongTermEditor longTermEditorTemplate = null;
	int threadCount = 1;

	public GeneticAlgorithm(TspData tspData) {
		super(tspData);
		// Initialize variables with default classes
		int n = tspData.getSize();
		populationGeneratorTemplate = new RandomPopulation(100);
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

	public void setFillerTemplate(Filler fillerTemplate) {
		this.fillerTemplate = fillerTemplate;
	}

	public void setPopulationGeneratorTemplate(PopulationGenerator populationGeneratorTemplate) {
		this.populationGeneratorTemplate = populationGeneratorTemplate;
	}

	public void setSelectionTemplate(Selection selectionTemplate) {
		this.selectionTemplate = selectionTemplate;
	}

	public void setLongTermCheckerTemplate(LongTermChecker longTermCheckerTemplate) {
		this.longTermCheckerTemplate = longTermCheckerTemplate;
	}

	public void setLongTermEditorTemplate(LongTermEditor longTermEditorTemplate) {
		this.longTermEditorTemplate = longTermEditorTemplate;
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
			GeneticResult thisBest = pass.getBestResult();
			if(best == null || best.objFuncResult > thisBest.objFuncResult){
				best = thisBest;
			}
		}
		return best;
	}

	protected class SingleGeneticThread implements Runnable {

		GeneticResult bestResult;

		PopulationGenerator populationGenerator;
		Selection selection;
		Mutation mutation;
		Crossover crossover;
		Evaluator evaluator;
		Filler filler;
		StopFunction stopFunction;
		LongTermChecker longTermChecker;
		LongTermEditor longTermEditor;

		List<GeneticResult> currentPopulation;
		List<GeneticResult> oldPopulation;

		public SingleGeneticThread(){
			populationGenerator = populationGeneratorTemplate.copy();
			selection = selectionTemplate.copy();
			if(mutationTemplate != null)
				mutation = mutationTemplate.copy();
			crossover = crossoverTemplate.copy();
			if(evaluatorTemplate != null)
				evaluator = evaluatorTemplate.copy();
			filler = fillerTemplate.copy();
			stopFunction = stopFunctionTemplate.copy();
			if(longTermCheckerTemplate != null)
				longTermChecker = longTermCheckerTemplate.copy();
			if(longTermEditorTemplate != null)
				longTermEditor = longTermEditorTemplate.copy();

			currentPopulation = populationGenerator.getNewPopulation(tspData);
			oldPopulation = currentPopulation;
		}

		// Main code of genetic algorithm
		@Override
		public void run() {
			do{
//				for(GeneticResult re : currentPopulation) System.out.println(re.toString());
				if(evaluator != null)
					currentPopulation = evaluator.evaluatePopulation(currentPopulation, oldPopulation);
				List<Pair> parents = selection.getParents(currentPopulation);
//				for(Pair p : parents) System.out.println(p.getParent1() + "      " + p.getParent2());
				currentPopulation = crossover.getNewPopulation(parents);
				GeneticResult bestRes = returnBest();
				if(bestResult == null || bestRes.objFuncResult < bestResult.objFuncResult) {
					bestResult = bestRes.clone();
				}
//				System.out.println("Size: " + currentPopulation.size());
//				System.out.println("Best: " + bestRes + " (score: " + bestRes.objFuncResult + ")");
//				System.out.println("Best score: " + bestRes.objFuncResult + " / " + bestResult.objFuncResult);
				filler.fillPopulation(currentPopulation, oldPopulation);
				if(mutation != null)
					currentPopulation = mutation.getMutatedPopulation(currentPopulation);
				if(longTermEditor != null && longTermChecker != null) {
					if(longTermChecker.check(currentPopulation, bestResult)) {
						currentPopulation = longTermEditor.getChanged(currentPopulation);
					}
				}
				oldPopulation = currentPopulation;
			} while(!stopFunction.check());
			GeneticResult bestRes = returnBest();
			if(bestResult == null || bestRes.objFuncResult < bestResult.objFuncResult) {
				bestResult = bestRes.clone();
			}
//			for(GeneticResult re : currentPopulation) System.out.println(re.toString());
		}

		public GeneticResult getBestResult() {
			return bestResult;
		}

		protected GeneticResult returnBest(){
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