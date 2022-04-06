package org.example.analyses;

import org.example.Main;
import org.example.algorithm.KRandom;
import org.example.algorithm.NearestNeighbour;
import org.example.algorithm.TwoOpt;
import org.example.data.TspData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BetterNearestNeighbourComparison {

	public static void compareKR(String fileName, List<TspData> data) {
		int n = data.size();
		int kr[] = new int[n], nn[] = new int[n];
		long time[] = new long[n];
		int i = 0;
		for(TspData tspData : data) {
			time[i] = System.nanoTime();
			NearestNeighbour nearestNeighbour = new NearestNeighbour(tspData, true);
			nn[i] = nearestNeighbour.calculate().calcObjectiveFunction();
			time[i] = System.nanoTime() - time[i];
			i++;
		}
		i=0;
		for(TspData tspData : data) {
			KRandom kRandom = new KRandom(tspData, time[i]);
			kr[i] = kRandom.calculate().calcObjectiveFunction();
			i++;
		}
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("file;K-Random;BetterNearestNeighbour;TimeInNanos\n");
			for (int k = 0; k < n; k++) {
				fileWriter.write(Main.names[k] + ";" + kr[k] + ";" + nn[k] + ";" + time[k] + "\n");
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void compareTwoOpt(String fileName, List<TspData> data) {
		int n = data.size();
		int kr[] = new int[n], to[] = new int[n];
		long time[] = new long[n];
		int i = 0;
		for(TspData tspData : data) {
			TwoOpt twoOpt = new TwoOpt(tspData);
			to[i] = twoOpt.calculate().calcObjectiveFunction();
			i++;
		}
		i=0;
		for(TspData tspData : data) {
			NearestNeighbour nearestNeighbour = new NearestNeighbour(tspData, true);
			kr[i] = nearestNeighbour.calculate().calcObjectiveFunction();
			i++;
		}
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("file;2-OPT;BetterNearestNeighbour\n");
			for (int k = 0; k < n; k++) {
				fileWriter.write( Main.names[k] + ";" + to[k] + ";" + kr[k] + "\n");
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void compareNN(String fileName, List<TspData> data) {
		int n = data.size();
		int bnn[] = new int[n], nn[] = new int[n];
		int i = 0;
		for(TspData tspData : data) {
			NearestNeighbour nearestNeighbour = new NearestNeighbour(tspData);
			nn[i] = nearestNeighbour.calculate().calcObjectiveFunction();
			i++;
		}
		i=0;
		for(TspData tspData : data) {
			NearestNeighbour nearestNeighbour = new NearestNeighbour(tspData, true);
			bnn[i] = nearestNeighbour.calculate().calcObjectiveFunction();
			i++;
		}
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("file;NearestNeighbour;BetterNearestNeighbour\n");
			for (int k = 0; k < n; k++) {
				fileWriter.write( Main.names[k] + ";" + nn[k] + ";" + bnn[k] + "\n");
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
