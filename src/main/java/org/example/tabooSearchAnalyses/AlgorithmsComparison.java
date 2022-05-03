package org.example.tabooSearchAnalyses;

import org.example.Main;
import org.example.algorithm.KRandom;
import org.example.algorithm.NearestNeighbour;
import org.example.algorithm.TwoOpt;
import org.example.algorithm.taboo.Neighbourhoods.Invert;
import org.example.algorithm.taboo.TabooSearch2;
import org.example.algorithm.taboo.stopFunctions.IterationsStop;
import org.example.algorithm.taboo.tabooList.BasicTabooList;
import org.example.data.Result;
import org.example.data.TspData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AlgorithmsComparison {

//	public static void compareTime(String fileName, List<TspData> data) {
//		int n = data.size();
//		int kr[] = new int[n], nn[] = new int[n];
//		long time[] = new long[n];
//		int i = 0;
//		for(TspData tspData : data) {
//			TabooSearch2 ts = new TabooSearch2(data, startingRes, true, new BasicTabooList(7), new Invert(), new IterationsStop(10), null, 3);
//			NearestNeighbour nearestNeighbour = new NearestNeighbour(tspData);
//			nn[i] = nearestNeighbour.calculate().calcObjectiveFunction();
//			time[i] = System.nanoTime() - time[i];
//			i++;
//		}
//		i=0;
//		for(TspData tspData : data) {
//			KRandom kRandom = new KRandom(tspData, time[i]);
//			kr[i] = kRandom.calculate().calcObjectiveFunction();
//			i++;
//		}
//		try {
//			File file = new File(fileName);
//			file.delete();
//			file.createNewFile();
//			FileWriter fileWriter = new FileWriter(file, true);
//			fileWriter.write("TimeInNanos;K-Random\n");
//			for (int k = 0; k < n; k++) {
//				fileWriter.write(Main.names[k] + ";" + kr[k] + ";" + nn[k] + ";" + time[k] + "\n");
//			}
//
//			fileWriter.close();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}
