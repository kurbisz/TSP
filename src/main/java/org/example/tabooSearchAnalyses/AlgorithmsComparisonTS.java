package org.example.tabooSearchAnalyses;

import org.example.Main;
import org.example.algorithm.KRandom;
import org.example.algorithm.NearestNeighbour;
import org.example.algorithm.TwoOpt;
import org.example.algorithm.taboo.LongTermList;
import org.example.algorithm.taboo.Neighbourhoods.CloseSwap;
import org.example.algorithm.taboo.Neighbourhoods.Insert;
import org.example.algorithm.taboo.Neighbourhoods.Invert;
import org.example.algorithm.taboo.Neighbourhoods.Swap;
import org.example.algorithm.taboo.TabooSearch2;
import org.example.algorithm.taboo.stopFunctions.IterationsStop;
import org.example.algorithm.taboo.stopFunctions.TimeStop;
import org.example.algorithm.taboo.tabooList.BasicTabooList;
import org.example.algorithm.taboo.tabooList.InvertTabooList;
import org.example.data.Result;
import org.example.data.TspData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AlgorithmsComparisonTS {

	// Porownanie roznych ilosci przejsc (iteracji)
	public static void compareOperations(String fileName, List<TspData> data) {
		int n = data.size();
		int tests[] = {50, 100, 200, 500, 1000, 2000};
		int ts[][] = new int[n][tests.length];
		int i = 0;
		for (TspData tspData : data) {
			Result res = new KRandom(tspData).calculate();
			System.out.println(i + ": " + tspData.getSize());
			for(int j = 0; j < tests.length; j++) {
				TabooSearch2 taboo = new TabooSearch2(tspData, res, false,
						new InvertTabooList(tspData.getSize(), 10), new Invert(),
						new IterationsStop(tests[j]), null, null, 1);
				ts[i][j] = taboo.calculate().calcObjectiveFunction();
			}
			i++;
		}
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("ProblemName;50;100;200;500;1000;2000\n");
			for (int k = 0; k < n; k++) {
				String str = Main.names[k];
				for(int j = 0; j < tests.length; j++) {
					str += ";" + ts[k][j];
				}
				str += "\n";
				fileWriter.write(str);
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Porownanie roznych wielkosci listy tabu
	public static void compareTabooListSize(String fileName, List<TspData> data) {
		int n = data.size();
		int tests[] = {7, 10, 20, 50, 100};
		int ts[][] = new int[n][tests.length + 1];
		int i = 0;
		for (TspData tspData : data) {
			Result res = new KRandom(tspData).calculate();
			System.out.println(i + ": " + tspData.getSize());
			for(int j = 0; j < tests.length; j++) {
				TabooSearch2 taboo = new TabooSearch2(tspData, res, false,
						new BasicTabooList(tests[j]), new Invert(),
						new IterationsStop(100), null, null, 1);
				ts[i][j] = taboo.calculate().calcObjectiveFunction();
			}
			TabooSearch2 taboo = new TabooSearch2(tspData, res, false,
					new BasicTabooList(n/4), new Invert(),
					new IterationsStop(100), null, null, 1);
			ts[i][tests.length] = taboo.calculate().calcObjectiveFunction();
			i++;
		}
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("ProblemName;7;10;20;50;100;n/4\n");
			for (int k = 0; k < n; k++) {
				String str = Main.names[k];
				for(int j = 0; j < tests.length+1; j++) {
					str += ";" + ts[k][j];
				}
				str += "\n";
				fileWriter.write(str);
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Porownanie kryterium aspiracji
	public static void compareAspiration(String fileName, List<TspData> data) {
		int n = data.size();
		boolean tests[] = {false, true};
		int ts[][] = new int[n][2];
		int i = 0;
		for (TspData tspData : data) {
			Result res = new KRandom(tspData).calculate();
			System.out.println(i + ": " + tspData.getSize());
			for(int j = 0; j < tests.length; j++) {
				TabooSearch2 taboo = new TabooSearch2(tspData, res, tests[j],
						new BasicTabooList(10), new Invert(),
						new IterationsStop(100), null, null, 1);
				ts[i][j] = taboo.calculate().calcObjectiveFunction();
			}
			i++;
		}
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("ProblemName;false;true\n");
			for (int k = 0; k < n; k++) {
				String str = Main.names[k];
				for(int j = 0; j < tests.length; j++) {
					str += ";" + ts[k][j];
				}
				str += "\n";
				fileWriter.write(str);
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Porownanie roznych otoczen
	public static void compareNeighbourhood(String fileName, List<TspData> data) {
		int n = data.size();
		int ts[][] = new int[n][4];
		int i = 0;
		for (TspData tspData : data) {
			Result res = new KRandom(tspData).calculate();
			System.out.println(i + ": " + tspData.getSize());
			TabooSearch2 taboo = new TabooSearch2(tspData, res, true,
					new BasicTabooList(10), new Invert(),
					new IterationsStop(100), null, null, 1);
			ts[i][0] = taboo.calculate().calcObjectiveFunction();
			taboo = new TabooSearch2(tspData, res, true,
					new BasicTabooList(10), new Insert(),
					new IterationsStop(100), null, null, 1);
			ts[i][1] = taboo.calculate().calcObjectiveFunction();
			taboo = new TabooSearch2(tspData, res, true,
					new BasicTabooList(10), new Swap(),
					new IterationsStop(100), null, null, 1);
			ts[i][2] = taboo.calculate().calcObjectiveFunction();
			taboo = new TabooSearch2(tspData, res, true,
					new BasicTabooList(10), new CloseSwap(),
					new IterationsStop(100), null, null, 1);
			ts[i][3] = taboo.calculate().calcObjectiveFunction();
			i++;
		}
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("ProblemName;Invert;Insert;Swap;CloseSwap\n");
			for (int k = 0; k < n; k++) {
				String str = Main.names[k];
				for(int j = 0; j < 4; j++) {
					str += ";" + ts[k][j];
				}
				str += "\n";
				fileWriter.write(str);
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Porownanie synchronicznego/asynchronicznego
	public static void compareAsynchronous(String fileName, List<TspData> data) {
		int n = data.size();
		int ts[][] = new int[n][8];
		int i = 0;
		for (TspData tspData : data) {
			System.out.println(i + ": " + tspData.getSize());
			for(int j = 0; j < 8; j++) {
				Result res = new KRandom(tspData).calculate();
				TabooSearch2 taboo = new TabooSearch2(tspData, res, false,
						new BasicTabooList(10), new Invert(),
						new IterationsStop(1000), null, null, (j+1));
				ts[i][j] = taboo.calculate().calcObjectiveFunction();
			}
			i++;
		}
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("ProblemName;1;2;3;4;5;6;7;8\n");
			for (int k = 0; k < n; k++) {
				String str = Main.names[k];
				for(int j = 0; j < 8; j++) {
					str += ";" + ts[k][j];
				}
				str += "\n";
				fileWriter.write(str);
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Porownanie algorytmu startujacego
	public static void compareStart(String fileName, List<TspData> data) {
		int n = data.size();
		int ts[][] = new int[n][4];
		int i = 0;
		for (TspData tspData : data) {
			System.out.println(i + ": " + tspData.getSize());
			Result res = new KRandom(tspData).calculate();
			TabooSearch2 taboo = new TabooSearch2(tspData, res, true,
					new BasicTabooList(10), new Invert(),
					new IterationsStop(100), null, null, 1);
			ts[i][0] = taboo.calculate().calcObjectiveFunction();
			res = new NearestNeighbour(tspData, NearestNeighbour.Strategy.SIMPLE).calculate();
			taboo = new TabooSearch2(tspData, res, true,
					new BasicTabooList(10), new Invert(),
					new IterationsStop(100), null, null, 1);
			ts[i][1] = taboo.calculate().calcObjectiveFunction();
			res = new NearestNeighbour(tspData, NearestNeighbour.Strategy.UPGRADED_SINGLE).calculate();
			taboo = new TabooSearch2(tspData, res, true,
					new BasicTabooList(10), new Invert(),
					new IterationsStop(100), null, null, 1);
			ts[i][2] = taboo.calculate().calcObjectiveFunction();
			res = new TwoOpt(tspData).calculate();
			taboo = new TabooSearch2(tspData, res, true,
					new BasicTabooList(10), new Invert(),
					new IterationsStop(100), null, null, 1);
			ts[i][3] = taboo.calculate().calcObjectiveFunction();

			i++;
		}
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("ProblemName;KR;NN;BNN;2-OPT\n");
			for (int k = 0; k < n; k++) {
				String str = Main.names[k];
				for(int j = 0; j < 4; j++) {
					str += ";" + ts[k][j];
				}
				str += "\n";
				fileWriter.write(str);
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Porownanie listy dlugoterminowej
	public static void compareLongTermList(String fileName, List<TspData> data, boolean kick) {
		int n = data.size();
		int tests[] = {0, 50, 100, 200, 300};
		int ts[][] = new int[n][tests.length + 1];
		int i = 0;
		for (TspData tspData : data) {
			Result res = new KRandom(tspData).calculate();
			System.out.println(i + ": " + tspData.getSize());
			for(int j = 0; j < tests.length; j++) {
				LongTermList longTermList = null;
				if(tests[j] > 0) longTermList = new LongTermList(res, new BasicTabooList(10), 5, tests[j], kick);
				TabooSearch2 taboo = new TabooSearch2(tspData, res, false,
						new BasicTabooList(10), new Invert(),
						new IterationsStop(2000), longTermList, null, 1);
				ts[i][j] = taboo.calculate().calcObjectiveFunction();
			}
			LongTermList longTermList = new LongTermList(res, new BasicTabooList(10), 5, n/4, kick);
			TabooSearch2 taboo = new TabooSearch2(tspData, res, false,
					new BasicTabooList(10), new Invert(),
					new IterationsStop(2000), longTermList, null, 1);
			ts[i][tests.length] = taboo.calculate().calcObjectiveFunction();
			i++;
		}
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("ProblemName;off;50;100;200;300;n/4\n");
			for (int k = 0; k < n; k++) {
				String str = Main.names[k];
				for(int j = 0; j < tests.length+1; j++) {
					str += ";" + ts[k][j];
				}
				str += "\n";
				fileWriter.write(str);
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Porownanie procesu zmiany wyniku dla iteracji
	public static void compareProgress(String fileName, List<TspData> data, int operations) {
		int n = data.size();
		int ts[][] = new int[n][11];
		int i = 0;
		for (TspData tspData : data) {
			Result res = new KRandom(tspData).calculate();
			System.out.println(i + ": " + tspData.getSize());
			long time = System.nanoTime();
			TabooSearch2 taboo = new TabooSearch2(tspData, res, false,
					new BasicTabooList(10), new Invert(),
					new IterationsStop(operations), null, null, 1);
			taboo.calculate();
			time = System.nanoTime() - time;
			for(int j = 0; j < 11; j++) {
				TabooSearch2 taboo2 = new TabooSearch2(tspData, res, false,
						new BasicTabooList(10), new Invert(),
						new TimeStop(time/10 * j), null, null, 1);
				ts[i][j] = taboo2.calculate().calcObjectiveFunction();
			}
			i++;
		}
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("ProblemName;0;10;20;30;40;50;60;70;80;90;100\n");
			for (int k = 0; k < n; k++) {
				String str = Main.names[k];
				for(int j = 0; j < 11; j++) {
					str += ";" + ts[k][j];
				}
				str += "\n";
				fileWriter.write(str);
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Porownanie czasu dzialania dla roznych list tabu
	public static void compareTabooListTime(String fileName, TspData data) {
		long ts[][] = new long[2][10];
		Result res = new KRandom(data).calculate();
		for(int i = 0; i < 10; i++) {
			long time = System.nanoTime();
			TabooSearch2 taboo = new TabooSearch2(data, res, false,
					new BasicTabooList(10), new Invert(),
					new IterationsStop((i+1)*100), null, null, 1);
			taboo.calculate();
			ts[0][i] = System.nanoTime() - time;
			time = System.nanoTime();
			TabooSearch2 taboo2 = new TabooSearch2(data, res, false,
					new InvertTabooList(data.getSize(), 10), new Invert(),
					new IterationsStop((i+1)*100), null, null, 1);
			taboo2.calculate();
			ts[1][i] = System.nanoTime() - time;
		}
		try {
			File file = new File(fileName);
			file.delete();
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write("ProblemName;100;200;300;400;500;600;700;800;900;1000\n");
			String str = "Basic";
			for(int j = 0; j < 10; j++) {
				str += ";" + ts[0][j];
			}
			str += "\n";
			fileWriter.write(str);
			str = "Improved";
			for(int j = 0; j < 10; j++) {
				str += ";" + ts[1][j];
			}
			str += "\n";
			fileWriter.write(str);

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}




}
