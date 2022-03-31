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
		long time[] = new long[n];
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

}
