package org.example.GAAnalyses;

import org.example.algorithm.genetic.Fillers.BestFiller;
import org.example.algorithm.genetic.Fillers.RandomFiller;
import org.example.algorithm.genetic.GeneticAlgorithm;
import org.example.algorithm.genetic.IslandsGeneticAlgorithm;
import org.example.algorithm.genetic.crossovers.Crossover;
import org.example.algorithm.genetic.crossovers.NeighboursCrossover;
import org.example.algorithm.genetic.crossovers.PartialCrossover;
import org.example.algorithm.genetic.crossovers.PartlyOrderCrossover;
import org.example.algorithm.genetic.mutations.InvertMutation;
import org.example.algorithm.genetic.mutations.NearestNeighbourMutation;
import org.example.algorithm.genetic.mutations.SwapMutation;
import org.example.algorithm.genetic.selections.BestWithBestSelection;
import org.example.algorithm.genetic.selections.RandomSelection;
import org.example.algorithm.genetic.selections.SimpleBestSelection;
import org.example.algorithm.genetic.startPopulations.HammingRandomPopulation;
import org.example.algorithm.genetic.startPopulations.RandomAndTwoOptPopulation;
import org.example.algorithm.genetic.startPopulations.RandomPopulation;
import org.example.data.TspData;
import org.example.stopFunctions.TimeStop;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandResComp {

	static Random r = new Random();
	private static int repeats = 5;
	private static String name;

	public static void genRand(String fileName, TspData[] dataArray, int[] best, int dataSize, int rand) throws IOException {
		FileWriter fileWriter = createNewFile(fileName);
		writeToFile(fileWriter, "Problem;Crossover,Filler,Mutation,Selection,StartPopulation;AllResults\n",true);
		for(int c = 0; c < 3; c++) {
			for(int f = 0; f < 2; f++) {
				for(int m = 0; m < 3; m++) {
					for(int sel = 0; sel < 3; sel++) {
						for(int st = 0; st < 3; st++) {
							for(int nr = 0; nr < rand; nr++) {
								Long seed = r.nextLong();
								r.setSeed(seed);
								getGeneticAlgorithm(-1, c, f, m, sel, st, dataArray[0]);
								writeToFile(fileWriter, name, true);
								for(int i = 0; i < dataSize; i++) {
									int res = 0;
									r.setSeed(seed);
									GeneticAlgorithm geneticAlgorithm = getGeneticAlgorithm(-1, c, f, m, sel, st, dataArray[i]);
									for (int j = 0; j < repeats; j++) {
										res+=geneticAlgorithm.calculate().objFuncResult;
									}
									res /= repeats;
									writeToFile(fileWriter, ";" + res, true);
								}
								writeToFile(fileWriter, "\n", true);
							}
						}
					}
				}
			}
		}
		fileWriter.close();
	}

	private static GeneticAlgorithm getGeneticAlgorithm(int threads, int cross, int fill, int mut, int sel, int start, TspData tspData) {
		name = "";
		int n = tspData.getSize();
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(tspData);
		if (threads > 1) geneticAlgorithm = new IslandsGeneticAlgorithm(tspData, threads, 50);

		// Crossover
		if (cross == 0) {
			RandomPair rp = new RandomPair(n);
			boolean bool = r.nextBoolean();
			geneticAlgorithm.setCrossoverTemplate(new PartialCrossover(rp.from, rp.to, bool));
			name += "PC," + rounded(rp.from/n) + "," + rounded(rp.to/n) + "," + bool;
		} else if (cross == 1) {
			int k = r.nextInt(n);
			boolean bool = r.nextBoolean();
			geneticAlgorithm.setCrossoverTemplate(new PartlyOrderCrossover(k, bool));
			name += "POC," + rounded(k/n) + "," + bool;
		} else {
			int k = r.nextInt(n/2);
			geneticAlgorithm.setCrossoverTemplate(new NeighboursCrossover(k));
			name += "NC," + rounded(k/n);
		}
		name += ";";
		// Filler
		if(fill == 0) {
			geneticAlgorithm.setFillerTemplate(new RandomFiller());
			name += "RF";
		}
		else {
			int k1 = r.nextInt(n), k2 = r.nextInt(2*n);
			geneticAlgorithm.setFillerTemplate(new BestFiller(k1, k2));
			name += "BF," + rounded(k1/n) + "," + rounded(k2/n);
		}
		name += ";";
		// Mutation
		if(mut == 0) {
			double k = r.nextDouble();
			int k2 = r.nextInt(n/2);
			geneticAlgorithm.setMutationTemplate(new InvertMutation(k, k2));
			name += "IM," + rounded(k) + "," + rounded(k2/n);
		}
		else if(mut == 1) {
			RandomPair rp = new RandomPair(n);
			double k = r.nextDouble();
			geneticAlgorithm.setMutationTemplate(new NearestNeighbourMutation(k, rp.from, rp.to));
			name += "NNM," + rounded(k) + "," + rounded(rp.from/n) + "," + rounded(rp.to/n);
		}
		else {
			double k = r.nextDouble();
			int k2 = r.nextInt(n);
			geneticAlgorithm.setMutationTemplate(new SwapMutation(k, k2));
			name += "SM," + rounded(k) + "," + rounded(k2/n);
		}
		name += ";";
		int populationSize = 50 + r.nextInt(450);
		if(r.nextDouble() < 0.5) populationSize = 20 + r.nextInt(80);
		// Selection
		if(sel == 0) {
			int k = r.nextInt(populationSize);
			geneticAlgorithm.setSelectionTemplate(new RandomSelection(k));
			name += "RS," + populationSize + "," + rounded(k/populationSize);
		}
		else if(sel == 1) {
			RandomPair rp = new RandomPair(populationSize);
			geneticAlgorithm.setSelectionTemplate(new SimpleBestSelection(rp.to, rp.from));
			name += "SBS," + populationSize + "," + rounded(rp.from/populationSize) + "," + rounded(rp.to/populationSize);
		}
		else {
			int k = (int) (r.nextDouble()*populationSize);
			geneticAlgorithm.setSelectionTemplate(new BestWithBestSelection(k));
			name += "BWBS," + populationSize + "," + rounded(k/populationSize);
		}
		name += ";";
		// Start population
		if(start == 0) {
			geneticAlgorithm.setStartPopulation(new RandomPopulation(populationSize));
			name += "RP," + populationSize;
		}
		else if(start == 1) {
			int k = r.nextInt(20);
			geneticAlgorithm.setStartPopulation(new RandomAndTwoOptPopulation(populationSize, k));
			name += "RTOP," + populationSize + "," + k;
		}
		else {
			geneticAlgorithm.setStartPopulation(new HammingRandomPopulation(populationSize, 0.9));
			name += "HRP," + populationSize;
		}

		geneticAlgorithm.setStopFunctionTemplate(new TimeStop(12000000000L));
		return geneticAlgorithm;
	}

	private static FileWriter createNewFile(String fileName) throws IOException {
		File file = new File(fileName);
		if(!file.exists())
			file.createNewFile();
		return new FileWriter(file, true);
	}
	private static void writeToFile(FileWriter fileWriter, String str, boolean flush) throws IOException {
		fileWriter.write(str);
		if(flush) {
			fileWriter.flush();
		}
	}

	private static String rounded(double d) {
		return String.format("%,.3f", d);
	}

	static class RandomPair {
		public int from, to;
		public RandomPair(int n) {
			from = (int) (n * r.nextDouble());
			to = (int) (n * r.nextDouble());
			if(from == to) {
				if(from!=0) from--;
				else to++;
			}
			else if(from < to) {
				int tmp = from;
				from = to;
				to = tmp;
			}
		}
	}

}
