package org.example.GAAnalyses;

import org.example.algorithm.genetic.Fillers.AgeFiller;
import org.example.algorithm.genetic.Fillers.BestAgeFiller;
import org.example.algorithm.genetic.Fillers.BestFiller;
import org.example.algorithm.genetic.Fillers.RandomFiller;
import org.example.algorithm.genetic.GeneticAlgorithm;
import org.example.algorithm.genetic.crossovers.NeighboursCrossover;
import org.example.algorithm.genetic.crossovers.PartialCrossover;
import org.example.algorithm.genetic.crossovers.PartlyOrderCrossover;
import org.example.algorithm.genetic.longterm.BestLongTermEditor;
import org.example.algorithm.genetic.longterm.RandomLongTermEditor;
import org.example.algorithm.genetic.longterm.SimpleLongTermChecker;
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

public class ResComp {

	private static int repeats = 5;

	public static void compareCrossovers(String fileName, TspData[] dataArray, int[] best, int dataSize) throws IOException {
		FileWriter fileWriter = createNewFile(fileName);
		writeToFile(fileWriter, "Problem;BestResult;Partial;PartlyOrder;Neighbours\n", true);
		for(int i = 0; i < dataSize; i++) {
			writeToFile(fileWriter, dataArray[i].getName() + ";" + best[i], true);
			int n = dataArray[i].getSize();
			int res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setCrossoverTemplate(new PartialCrossover(1 * n / 3, 2 * n / 3, false));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setCrossoverTemplate(new PartlyOrderCrossover(n/10, false));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setCrossoverTemplate(new NeighboursCrossover(1));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			writeToFile(fileWriter, "\n", true);
		}
		fileWriter.close();
	}

	public static void compareFillers(String fileName, TspData[] dataArray, int[] best, int dataSize) throws IOException {
		FileWriter fileWriter = createNewFile(fileName);
		writeToFile(fileWriter, "Problem;BestResult;Random;Best\n", true);
		for(int i = 0; i < dataSize; i++) {
			writeToFile(fileWriter, dataArray[i].getName() + ";" + best[i], true);
			int n = dataArray[i].getSize();
			int res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setFillerTemplate(new RandomFiller());
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setFillerTemplate(new BestFiller(n/5, 100));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			writeToFile(fileWriter, "\n", true);
		}
		fileWriter.close();
	}

	/**
	 * Stop function: 5 minutes and only 1 iteration (not taken avg of 5 iterations as it is in others)
	 * @param fileName
	 * @param dataArray
	 * @param best
	 * @param dataSize
	 * @throws IOException
	 */
	public static void compareLongTerm(String fileName, TspData[] dataArray, int[] best, int dataSize) throws IOException {
		FileWriter fileWriter = createNewFile(fileName);
		writeToFile(fileWriter, "Problem;BestResult;None;Random;Best\n", true);
		for(int i = 10; i < dataSize; i++) {
			writeToFile(fileWriter, dataArray[i].getName() + ";" + best[i], true);
			int n = dataArray[i].getSize();
			int res;
			GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
			geneticAlgorithm.setStopFunctionTemplate(new TimeStop(150000000000L));
			geneticAlgorithm.setLongTermCheckerTemplate(null);
			geneticAlgorithm.setLongTermEditorTemplate(null);
			res=geneticAlgorithm.calculate().objFuncResult;
			writeToFile(fileWriter, ";" + res, true);

			geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
			geneticAlgorithm.setStopFunctionTemplate(new TimeStop(150000000000L));
			geneticAlgorithm.setLongTermCheckerTemplate(new SimpleLongTermChecker());
			geneticAlgorithm.setLongTermEditorTemplate(new RandomLongTermEditor());
			res=geneticAlgorithm.calculate().objFuncResult;
			writeToFile(fileWriter, ";" + res, true);

			geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
			geneticAlgorithm.setStopFunctionTemplate(new TimeStop(150000000000L));
			geneticAlgorithm.setLongTermCheckerTemplate(new SimpleLongTermChecker());
			geneticAlgorithm.setLongTermEditorTemplate(new BestLongTermEditor());
			res=geneticAlgorithm.calculate().objFuncResult;
			writeToFile(fileWriter, ";" + res, true);

			writeToFile(fileWriter, "\n", true);
		}
		fileWriter.close();
	}

	public static void compareMutations(String fileName, TspData[] dataArray, int[] best, int dataSize) throws IOException {
		FileWriter fileWriter = createNewFile(fileName);
		writeToFile(fileWriter, "Problem;BestResult;Invert;Swap;NearestNeighbour\n", true);
		for(int i = 0; i < dataSize; i++) {
			writeToFile(fileWriter, dataArray[i].getName() + ";" + best[i], true);
			int n = dataArray[i].getSize();
			int res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setMutationTemplate(new InvertMutation());
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setMutationTemplate(new SwapMutation());
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setMutationTemplate(new NearestNeighbourMutation(0.8, n/8, 7*n/8));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			writeToFile(fileWriter, "\n", true);
		}
		fileWriter.close();
	}

	public static void compareSelections(String fileName, TspData[] dataArray, int[] best, int dataSize) throws IOException {
		FileWriter fileWriter = createNewFile(fileName);
		writeToFile(fileWriter, "Problem;BestResult;Random;SimpleBest;BestBest\n", true);
		for(int i = 0; i < dataSize; i++) {
			writeToFile(fileWriter, dataArray[i].getName() + ";" + best[i], true);
			int n = dataArray[i].getSize();
			int res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setSelectionTemplate(new RandomSelection());
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setSelectionTemplate(new SimpleBestSelection(n/2, n/3));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setSelectionTemplate(new BestWithBestSelection(n/2));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			writeToFile(fileWriter, "\n", true);
		}
		fileWriter.close();
	}

	public static void compareStartPopulations(String fileName, TspData[] dataArray, int[] best, int dataSize) throws IOException {
		FileWriter fileWriter = createNewFile(fileName);
		writeToFile(fileWriter, "Problem;BestResult;Random;RandomTwoOpt;HammingRandom\n", true);
		for(int i = 0; i < dataSize; i++) {
			writeToFile(fileWriter, dataArray[i].getName() + ";" + best[i], true);
			int n = dataArray[i].getSize();
			int res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setStartPopulation(new RandomPopulation());
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setStartPopulation(new RandomAndTwoOptPopulation());
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setStartPopulation(new HammingRandomPopulation());
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			writeToFile(fileWriter, "\n", true);
		}
		fileWriter.close();
	}

	public static void comparePopulationSize(String fileName, TspData[] dataArray, int[] best, int dataSize) throws IOException {
		FileWriter fileWriter = createNewFile(fileName);
		writeToFile(fileWriter, "Problem;BestResult;20;50;100;200;500;1000\n", true);
		for(int i = 0; i < dataSize; i++) {
			writeToFile(fileWriter, dataArray[i].getName() + ";" + best[i], true);
			int n = dataArray[i].getSize();
			int res = 0;
			int populationSize = 20;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setStartPopulation(new RandomPopulation(populationSize));
				geneticAlgorithm.setSelectionTemplate(new SimpleBestSelection(populationSize/2, populationSize/3));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			populationSize = 50;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setStartPopulation(new RandomPopulation(populationSize));
				geneticAlgorithm.setSelectionTemplate(new SimpleBestSelection(populationSize/2, populationSize/3));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			populationSize = 100;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setStartPopulation(new RandomPopulation(populationSize));
				geneticAlgorithm.setSelectionTemplate(new SimpleBestSelection(populationSize/2, populationSize/3));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			populationSize = 200;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setStartPopulation(new RandomPopulation(populationSize));
				geneticAlgorithm.setSelectionTemplate(new SimpleBestSelection(populationSize/2, populationSize/3));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			populationSize = 500;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setStartPopulation(new RandomPopulation(populationSize));
				geneticAlgorithm.setSelectionTemplate(new SimpleBestSelection(populationSize/2, populationSize/3));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			populationSize = 1000;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setStartPopulation(new RandomPopulation(populationSize));
				geneticAlgorithm.setSelectionTemplate(new SimpleBestSelection(populationSize/2, populationSize/3));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			writeToFile(fileWriter, "\n", true);
		}
		fileWriter.close();
	}

	public static void compareAge(String fileName, TspData[] dataArray, int[] best, int dataSize) throws IOException {
		FileWriter fileWriter = createNewFile(fileName);
		writeToFile(fileWriter, "Problem;BestResult;Best; Age; Age + best\n", true);
		for(int i = 0; i < dataSize; i++) {
			writeToFile(fileWriter, dataArray[i].getName() + ";" + best[i], true);
			int n = dataArray[i].getSize();
			int res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setFillerTemplate(new BestFiller(n/5, 100));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setFillerTemplate(new AgeFiller(35));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setFillerTemplate(new BestAgeFiller(n/5, 35));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			writeToFile(fileWriter, "\n", true);
		}
		fileWriter.close();
	}

	public static void compareCriticalAge(String fileName, TspData[] dataArray, int[] best, int dataSize) throws IOException {
		FileWriter fileWriter = createNewFile(fileName);
		writeToFile(fileWriter, "Problem;BestResult;Best; Age; Age + best\n", true);
		for(int i = 0; i < dataSize; i++) {
			System.out.println(dataArray[i].getName());
			writeToFile(fileWriter, dataArray[i].getName() + ";" + best[i], true);
			int n = dataArray[i].getSize();
			int res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setFillerTemplate(new AgeFiller(10));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setFillerTemplate(new AgeFiller(30));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setFillerTemplate(new AgeFiller(60));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			res = 0;
			for(int j = 0; j < repeats; j++) {
				GeneticAlgorithm geneticAlgorithm = getDefaultGeneticAlgorithm(dataArray[i]);
				geneticAlgorithm.setFillerTemplate(new AgeFiller(90));
				res+=geneticAlgorithm.calculate().objFuncResult;
			}
			res /= repeats;
			writeToFile(fileWriter, ";" + res, true);

			writeToFile(fileWriter, "\n", true);
		}
		fileWriter.close();
	}

	private static GeneticAlgorithm getDefaultGeneticAlgorithm(TspData tspData) {
		int n = tspData.getSize();
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(tspData);
		geneticAlgorithm.setCrossoverTemplate(new PartialCrossover(1*n/3, 2*n/3, false));
		geneticAlgorithm.setFillerTemplate(new RandomFiller());
		geneticAlgorithm.setLongTermEditorTemplate(null);
		geneticAlgorithm.setLongTermCheckerTemplate(null);
		geneticAlgorithm.setMutationTemplate(new SwapMutation(0.8, 1));
		geneticAlgorithm.setSelectionTemplate(new SimpleBestSelection(n/2, n/3));
		geneticAlgorithm.setStartPopulation(new RandomPopulation(100));
		geneticAlgorithm.setThreadCount(1);
		geneticAlgorithm.setStopFunctionTemplate(new TimeStop(1200000000L));
		return geneticAlgorithm;
	}

	private static FileWriter createNewFile(String fileName) throws IOException {
		File file = new File(fileName);
		file.delete();
		file.createNewFile();
		return new FileWriter(file, true);
	}
	private static void writeToFile(FileWriter fileWriter, String str, boolean flush) throws IOException {
		fileWriter.write(str);
		if(flush) {
			fileWriter.flush();
		}
	}

}
