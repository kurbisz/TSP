package org.example.algorithm.genetic;

import org.example.algorithm.genetic.crossovers.NeighboursCrossover;
import org.example.algorithm.genetic.crossovers.PartialCrossover;
import org.example.algorithm.genetic.crossovers.PartlyOrderCrossover;
import org.example.algorithm.genetic.mutations.InvertMutation;
import org.example.algorithm.genetic.mutations.NearestNeighbourMutation;
import org.example.algorithm.genetic.mutations.SwapMutation;
import org.example.data.Pair;
import org.example.data.Result;
import org.example.data.TspData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class IslandsGeneticAlgorithm extends GeneticAlgorithm {

	protected int iterations;

	public IslandsGeneticAlgorithm(TspData tspData, int threads, int iterations) {
		super(tspData);
		this.threadCount = threads;
		this.iterations = iterations;
	}

	@Override
	public Result calculate() {
		if(threadCount < 2) {
			return null;
		}
		int n = tspData.getSize();
		IslandGeneticThread[] threads = new IslandGeneticThread[threadCount];
		for(int i = 0; i < threadCount; i++){
			threads[i] = new IslandGeneticThread();
			customizeThread(threads[i], i, n);
		}
		for(int i = 0; i < threadCount; i++){
			if(i>=2) threads[i].addReceiver(threads[i-2]);
			if(i>=1) threads[i].addReceiver(threads[i-1]);
			if(i<threadCount-1) threads[i].addReceiver(threads[i+1]);
			if(i<threadCount-2) threads[i].addReceiver(threads[i+2]);
		}
		for(int j = 0; j < threadCount; j++) {
			if(threads[j].getBestResult()!=null)System.out.println(j + ": " + threads[j].getBestResult().calcObjectiveFunction());
		}
		ExecutorService pool = Executors.newFixedThreadPool(threadCount);
		for (IslandGeneticThread pass : threads) {
			pool.execute(pass);
		}
		pool.shutdown();
		try {
			boolean succeeded = pool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		GeneticResult best = null;
		for(SingleGeneticThread pass : threads){
			GeneticResult thisBest = pass.getBestResult();
			if(best == null || best.objFuncResult > thisBest.objFuncResult){
				best = thisBest;
			}
		}
		return best;
	}

	private void customizeThread(IslandGeneticThread thread, int i, int n) {
		if(i%9 == 0) thread.mutation = new NearestNeighbourMutation(0.1, n/4, 3*n/4);
		else if(i%9 == 1) thread.mutation = new SwapMutation(0.8, 1);
		else if(i%9 == 2) thread.mutation = new InvertMutation(0.1, 1);
		else if(i%9 == 3) thread.mutation = new NearestNeighbourMutation(0.5, n/10, 9*n/10);
		else if(i%9 == 4) thread.mutation = new SwapMutation(0.5, 3);
		else if(i%9 == 5) thread.mutation = new InvertMutation(0.5, 2);
		else if(i%9 == 6) thread.mutation = new NearestNeighbourMutation(0.05, n/3, 2*n/3);
		else if(i%9 == 7) thread.mutation = new SwapMutation(0.2, 5);
		else if(i%9 == 8) thread.mutation = new InvertMutation(0.2, 3);
	}

	class IslandGeneticThread extends SingleGeneticThread {

		List<IslandGeneticThread> receivers = new ArrayList<>();

		public IslandGeneticThread() {
			super();
		}

		@Override
		public void run() {
			for(int i = 0; i < iterations; i++) {
				stopFunction = stopFunctionTemplate.copy();
				do {
//					for(GeneticResult re : currentPopulation) System.out.println(re.toString());
					if (evaluator != null)
						currentPopulation = evaluator.evaluatePopulation(currentPopulation, oldPopulation);
					List<Pair> parents = selection.getParents(currentPopulation);
//					for(Pair p : parents) System.out.println(p.getParent1() + "      " + p.getParent2());
					currentPopulation = crossover.getNewPopulation(parents);
					GeneticResult bestRes = returnBest();
					if (bestResult == null || bestRes.objFuncResult < bestResult.objFuncResult) {
						bestResult = bestRes.clone();
					}
//					System.out.println("Size: " + currentPopulation.size());
//					System.out.println("Best: " + bestRes + " (score: " + bestRes.objFuncResult + ")");
//					System.out.println("Best score: " + bestRes.objFuncResult + " / " + bestResult.objFuncResult);
					filler.fillPopulation(currentPopulation, oldPopulation);
					if (mutation != null)
						currentPopulation = mutation.getMutatedPopulation(currentPopulation);
					if(longTermEditor != null && longTermChecker != null) {
						if(longTermChecker.check(currentPopulation, bestResult)) {
							currentPopulation = longTermEditor.getChanged(currentPopulation);
						}
					}
					oldPopulation = currentPopulation;
				} while (!stopFunction.check());
				GeneticResult bestRes = returnBest();
				if (bestResult == null || bestRes.objFuncResult < bestResult.objFuncResult) {
					bestResult = bestRes.clone();
				}
				try {
					// Avoid situation that 1 thread has finished and others have not
					Thread.sleep(1000);
					addBest();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Best score: " + bestResult.objFuncResult);
			}
//			for(GeneticResult re : currentPopulation) System.out.println(re.toString());
		}

		public void addBest() {
			for(IslandGeneticThread islandGeneticThread : receivers) {
				GeneticResult res = islandGeneticThread.getBestResult();
				if(res!=null) currentPopulation.add(res);
			}
		}

		public void addReceiver(IslandGeneticThread islandGeneticThread) {
			receivers.add(islandGeneticThread);
		}

	}

}
