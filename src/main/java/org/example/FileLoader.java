package org.example;

import org.example.data.TspData;
import org.example.parser.AtspParser;
import org.example.parser.EucParser;
import org.example.parser.Parser;
import org.example.parser.TspParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

public class FileLoader {

	private String fileName;

	private TspData tspData;


	public FileLoader(String fileName) {
		this.fileName = fileName;
	}

	public void load() {
		try {
			FileInputStream fileInputStream = new FileInputStream(fileName);
			BufferedReader bufferedReader;
			boolean gzip = fileName.contains(".gz");
			GZIPInputStream gzipInputStream = null;
			if(gzip) {
				gzipInputStream = new GZIPInputStream(fileInputStream);
				bufferedReader = new BufferedReader(
						new InputStreamReader(gzipInputStream, "UTF-8"));
			}
			else {
				bufferedReader = new BufferedReader(
						new InputStreamReader(fileInputStream, "UTF-8"));
			}
			StringBuilder builder = new StringBuilder();
			String line;
			while((line = bufferedReader.readLine()) != null) {
				builder.append(line);
				builder.append('\n');
			}
			String fileStr = builder.toString();
			bufferedReader.close();
			if(gzip) {
				gzipInputStream.close();
			}
			fileInputStream.close();

			Parser parser = getParser(fileStr);

			if(parser == null) {
				System.out.println("Invalid file format!");
				return;
			}

			this.tspData = parser.getTspData();

		} catch (IOException exc) {
			System.out.println("Error occurred while loading file!");
		}
	}

	private Parser getParser(String str) {
		if(str == null) return null;
		String[] spl = str.split("\n");
		for(String line : spl) {
			if (line.contains("EDGE_WEIGHT_TYPE")) {
				try {
					String type = line.split(":")[1].trim();
					if(type.equals("EUC_2D")) return new EucParser(str);
				} catch (NumberFormatException | IndexOutOfBoundsException e) { }
			}
			else if (line.contains("TYPE")) {
				try {
					String type = line.split(":")[1].trim();
					if(type.equals("ATSP")) return new AtspParser(str);
				} catch (NumberFormatException | IndexOutOfBoundsException e) { }
			}
			else if (line.contains("EDGE_WEIGHT_FORMAT")) {
				try {
					String type = line.split(":")[1].trim();
					if(type.equals("LOWER_DIAG_ROW")) return new TspParser(str);
				} catch (NumberFormatException | IndexOutOfBoundsException e) { }
			}
		}
		return null;
	}

	public TspData getTspData() {
		return this.tspData;
	}

}
