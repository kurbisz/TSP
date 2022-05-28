package org.example.GAAnalyses;

import org.example.algorithm.genetic.Fillers.BestFiller;
import org.example.algorithm.genetic.Fillers.RandomFiller;
import org.example.algorithm.genetic.GeneticAlgorithm;
import org.example.algorithm.genetic.crossovers.NeighboursCrossover;
import org.example.algorithm.genetic.crossovers.PartialCrossover;
import org.example.algorithm.genetic.crossovers.PartlyOrderCrossover;
import org.example.algorithm.genetic.mutations.SwapMutation;
import org.example.algorithm.genetic.selections.SimpleBestSelection;
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

	private static GeneticAlgorithm getDefaultGeneticAlgorithm(TspData tspData) {
		int n = tspData.getSize();
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(tspData);
		geneticAlgorithm.setCrossoverTemplate(new PartialCrossover(1 * n / 3, 2 * n / 3, false));
		geneticAlgorithm.setFillerTemplate(new RandomFiller());
		geneticAlgorithm.setLongTermEditorTemplate(null);
		geneticAlgorithm.setLongTermCheckerTemplate(null);
		geneticAlgorithm.setMutationTemplate(new SwapMutation(0.8, 1));
		geneticAlgorithm.setSelectionTemplate(new SimpleBestSelection());
		geneticAlgorithm.setStartPopulation(new RandomPopulation(100));
		geneticAlgorithm.setThreadCount(1);
		geneticAlgorithm.setStopFunctionTemplate(new TimeStop(12000000000L));
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
