package org.example.analyses;

import org.example.algorithm.KRandom;
import org.example.algorithm.TwoOpt;
import org.example.data.Result;
import org.example.data.TspData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TwoOptComparison {

	public static void calc(String fileName, TspData tspData) {
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("TimeInMilis;Result\n");
			for(long l = 500000000L; l < 8000000000L; l += 500000000L) {
				TwoOpt twoOpt = new TwoOpt(tspData);
				twoOpt.setTime(l);
				Result result = twoOpt.calculate();
				fileWriter.write(l/1000000 + ";" + result.calcObjectiveFunction() + '\n');
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
