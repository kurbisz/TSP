package org.example.analyses;

import org.example.Main;
import org.example.algorithm.KRandom;
import org.example.algorithm.NearestNeighbour;
import org.example.algorithm.ThreeOpt;
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
		int kr[] = new int[n], to[] = new int[n];
		long time[] = new long[n];
		int i = 0;
		for(TspData tspData : data) {
			time[i] = System.nanoTime();
			TwoOpt twoOpt = new TwoOpt(tspData);
			to[i] = twoOpt.calculate().calcObjectiveFunction();
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
			fileWriter.write("file;2-OPT;K-Random;TimeInNanos\n");
			for (int k = 0; k < n; k++) {
				fileWriter.write( Main.names[k] + ";" + to[k] + ";" + kr[k] + ";" + time[k] + "\n");
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void compareAll(String fileName, List<TspData> data) {
		int n = data.size();
		int kr[] = new int[n], kr2[] = new int[n], nn[] = new int[n], nn2[] = new int[n],
				to[] = new int[n];
		int i = 0;
		for(TspData tspData : data) {
			KRandom kRandom = new KRandom(tspData, 100);
			kr[i] = kRandom.calculate().calcObjectiveFunction();

			KRandom kRandom2 = new KRandom(tspData, tspData.getSize()*2);
			kr2[i] = kRandom2.calculate().calcObjectiveFunction();

			NearestNeighbour nearestNeighbour = new NearestNeighbour(tspData);
			nn[i] = nearestNeighbour.calculate().calcObjectiveFunction();

			NearestNeighbour betterNearestNeighbour = new NearestNeighbour(tspData, true);
			nn2[i] = betterNearestNeighbour.calculate().calcObjectiveFunction();

			TwoOpt twoOpt = new TwoOpt(tspData);
			to[i] = twoOpt.calculate().calcObjectiveFunction();
			i++;
		}
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("file;K-Random-100;K-Random-2n;NearestNeighbour;NearestNeighbour2;2-OPT;BestSolution\n");
			for (int k = 0; k < n; k++) {
				fileWriter.write(Main.names[k] + ";" + kr[k] + ";" + kr2[k] + ";" + nn[k] + ";" + nn2[k] + ";"
						+ to[k] + ";" + Main.best[k] + "\n");
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void compareAllTimes(String fileName, List<TspData> data) {
		int n = data.size();
		long kr[] = new long[n], kr2[] = new long[n], nn[] = new long[n], nn2[] = new long[n],
				to[] = new long[n];
		int i = 0;
		for(TspData tspData : data) {
			long time = System.nanoTime();
			KRandom kRandom = new KRandom(tspData, 100);
			kRandom.calculate().calcObjectiveFunction();
			kr[i] = System.nanoTime() - time;

			time = System.nanoTime();
			KRandom kRandom2 = new KRandom(tspData, tspData.getSize()*2);
			kRandom2.calculate().calcObjectiveFunction();
			kr2[i] = System.nanoTime() - time;

			time = System.nanoTime();
			NearestNeighbour nearestNeighbour = new NearestNeighbour(tspData);
			nearestNeighbour.calculate().calcObjectiveFunction();
			nn[i] = System.nanoTime() - time;

			time = System.nanoTime();
			NearestNeighbour betterNearestNeighbour = new NearestNeighbour(tspData, true);
			betterNearestNeighbour.calculate().calcObjectiveFunction();
			nn2[i] = System.nanoTime() - time;

			time = System.nanoTime();
			TwoOpt twoOpt = new TwoOpt(tspData);
			twoOpt.calculate().calcObjectiveFunction();
			to[i] = System.nanoTime() - time;
			i++;
		}
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("file;K-Random-100;K-Random-2n;NearestNeighbour;NearestNeighbour2;2-OPT\n");
			for (int k = 0; k < n; k++) {
				fileWriter.write(Main.names[k] + ";" + kr[k] + ";" + kr2[k] + ";" + nn[k] + ";" + nn2[k] + ";"
						+ to[k] + "\n");
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
