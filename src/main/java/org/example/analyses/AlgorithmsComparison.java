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

public class AlgorithmsComparison {

	public static void compareKRAndNN(String fileName, List<TspData> data) {
		int n = data.size();
		int kr[] = new int[n], nn[] = new int[n];
		long time[] = new long[n];
		int i = 0;
		for(TspData tspData : data) {
			time[i] = System.nanoTime();
			NearestNeighbour nearestNeighbour = new NearestNeighbour(tspData);
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
			fileWriter.write("file;K-Random;NearestNeighbour;TimeInNanos\n");
			for (int k = 0; k < n; k++) {
				fileWriter.write(Main.names[k] + ";" + kr[k] + ";" + nn[k] + ";" + time[k] + "\n");
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void compareTwoOptAndNN(String fileName, List<TspData> data) {
		int n = data.size();
		int to[] = new int[n], nn[] = new int[n];
		long time[] = new long[n];
		int i = 0;
		for(TspData tspData : data) {
			time[i] = System.nanoTime();
			NearestNeighbour nearestNeighbour = new NearestNeighbour(tspData);
			nn[i] = nearestNeighbour.calculate().calcObjectiveFunction();
			time[i] = System.nanoTime() - time[i];
			i++;
		}
		i=0;
		for(TspData tspData : data) {
			TwoOpt twoOpt = new TwoOpt(tspData);
			twoOpt.setTime(time[i]);
			to[i] = twoOpt.calculate().calcObjectiveFunction();
			i++;
		}
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("file;2-PT;NearestNeighbour;TimeInNanos\n");
			for (int k = 0; k < n; k++) {
				fileWriter.write( Main.names[k] + ";" + to[k] + ";" + nn[k] + ";" + time[k] + "\n");
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void compareKrAndTwoOpt(String fileName, List<TspData> data) {
		int n = data.size();
		int to[] = new int[n], nn[] = new int[n];
		long time[] = new long[n];
		int i = 0;
		for(TspData tspData : data) {
			time[i] = System.nanoTime();
			TwoOpt twoOpt = new TwoOpt(tspData);
			nn[i] = twoOpt.calculate().calcObjectiveFunction();
			time[i] = System.nanoTime() - time[i];
			i++;
		}
		i=0;
		for(TspData tspData : data) {
			KRandom kRandom = new KRandom(tspData, time[i]);
			to[i] = kRandom.calculate().calcObjectiveFunction();
			i++;
		}
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("file;2-OPT;K-Random;TimeInNanos\n");
			for (int k = 0; k < n; k++) {
				fileWriter.write( Main.names[k] + ";" + to[k] + ";" + nn[k] + ";" + time[k] + "\n");
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void compareNNAndNN2(String fileName, List<TspData> data) {

	}

}
