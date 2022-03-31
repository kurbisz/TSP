package org.example.analyses;

import org.example.Main;
import org.example.algorithm.KRandom;
import org.example.algorithm.NearestNeighbour;
import org.example.algorithm.ThreeOpt;
import org.example.algorithm.TwoOpt;
import org.example.data.Result;
import org.example.data.TspData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AlgorithmsComparison2 {

	public static void compareTwoOpt(String fileName, List<TspData> data) {
		int n = data.size();
		int to[] = new int[n], bto[] = new int[n], bto2[] = new int[n];
		int i = 0;
		for(TspData tspData : data) {
			TwoOpt twoOpt = new TwoOpt(tspData);
			to[i] = twoOpt.calculate().calcObjectiveFunction();

			NearestNeighbour nearestNeighbour = new NearestNeighbour(tspData);
			Result result = nearestNeighbour.calculate();
			TwoOpt betterTwoOpt = new TwoOpt(tspData);
			betterTwoOpt.setStarting(result);
			bto[i] = betterTwoOpt.calculate().calcObjectiveFunction();

			// TODO change to better NN
			NearestNeighbour nearestNeighbour2 = new NearestNeighbour(tspData);
			Result result2 = nearestNeighbour2.calculate();
			TwoOpt betterTwoOpt2 = new TwoOpt(tspData);
			betterTwoOpt2.setStarting(result2);
			bto2[i] = betterTwoOpt2.calculate().calcObjectiveFunction();

			i++;
		}

		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("file;2-OPT;Better2-OPT;Better2-OPT2\n");
			for (int k = 0; k < n; k++) {
				fileWriter.write(Main.names[k] + ";" + to[k] + ";" + bto[k] + ";" + bto2[k] + "\n");
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void compareTwoOptTime(String fileName, List<TspData> data) {
		int n = data.size();
		long to[] = new long[n], bto[] = new long[n], bto2[] = new long[n];
		int i = 0;
		for(TspData tspData : data) {
			long time = System.nanoTime();
			TwoOpt twoOpt = new TwoOpt(tspData);
			twoOpt.calculate().calcObjectiveFunction();
			to[i] = System.nanoTime() - time;

			NearestNeighbour nearestNeighbour = new NearestNeighbour(tspData);
			Result result = nearestNeighbour.calculate();
			time = System.nanoTime();
			TwoOpt betterTwoOpt = new TwoOpt(tspData);
			betterTwoOpt.setStarting(result);
			bto[i] = betterTwoOpt.calculate().calcObjectiveFunction();
			bto[i] = System.nanoTime() - time;

			// TODO change to better NN
			NearestNeighbour nearestNeighbour2 = new NearestNeighbour(tspData);
			Result result2 = nearestNeighbour2.calculate();
			time = System.nanoTime();
			TwoOpt betterTwoOpt2 = new TwoOpt(tspData);
			betterTwoOpt2.setStarting(result2);
			bto2[i] = betterTwoOpt2.calculate().calcObjectiveFunction();
			bto2[i] = System.nanoTime() - time;

			i++;
		}

		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("file;2-OPT;Better2-OPT;Better2-OPT2\n");
			for (int k = 0; k < n; k++) {
				fileWriter.write(Main.names[k] + ";" + to[k] + ";" + bto[k] + ";" + bto2[k] + "\n");
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
