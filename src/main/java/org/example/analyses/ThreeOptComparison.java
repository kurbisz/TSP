package org.example.analyses;

import org.example.algorithm.ThreeOpt;
import org.example.algorithm.TwoOpt;
import org.example.data.Result;
import org.example.data.TspData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ThreeOptComparison {

	public static void calc(String fileName, TspData tspData) {
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("TimeInMilis;Result\n");

			ThreeOpt threeOpt = new ThreeOpt(tspData);
			threeOpt.setFileWriter(fileWriter);
			threeOpt.calculate();

			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
