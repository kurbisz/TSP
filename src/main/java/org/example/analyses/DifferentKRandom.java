package org.example.analyses;

import org.example.algorithm.KRandom;
import org.example.data.Result;
import org.example.data.TspData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DifferentKRandom {

	public static void calc(String fileName, TspData tspData) {
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			for (int k = 100; k < 100000;) {
				KRandom kRandom = new KRandom(tspData, k);
				Result result = kRandom.calculate();
				fileWriter.write(k + ";" + result.calcObjectiveFunction() + '\n');
				if(k>=10000) k+=1000;
				else k+=100;
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void calcAverage(String fileName, TspData tspData) {
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			for (int k = 100; k < 1000; k += 100) {
				fileWriter.write(k + ";" + generateAverage(tspData, k) + '\n');
			}
			for(int k = 1000; k < 10000; k += 1000) {
				fileWriter.write(k + ";" + generateAverage(tspData, k) + '\n');
			}
			for(int k = 10000; k < 110000; k += 10000) {
				fileWriter.write(k + ";" + generateAverage(tspData, k) + '\n');
			}

			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int generateAverage(TspData tspData, int k) throws IOException {
		int sum = 0;
		int n = 100;
		for(int i = 0; i < n; i++) {
			KRandom kRandom = new KRandom(tspData, k);
			Result result = kRandom.calculate();
			sum += result.calcObjectiveFunction();
		}
		return sum/n;
	}

}
