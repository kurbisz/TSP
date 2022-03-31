package org.example.algorithm;

import org.example.data.Result;
import org.example.data.TspData;

import java.io.FileWriter;
import java.io.IOException;

public class ThreeOpt extends Algorithm {

    FileWriter fileWriter;

    public ThreeOpt(TspData tspData) {
        super(tspData);
    }

    @Override
    public Result calculate() {
        if(tspData.isSymmetric()) return calcSymmetric();
        return calcAsymmetric();
    }

    private Result calcSymmetric() {
        Solution s = new Solution();
        s.result = new Result(tspData);
        int n = tspData.getSize();
        s.betterWay = s.result.calcObjectiveFunction();
        s.betterI = 0;
        s.betterJ = 0;
        s.betterK = 0;
        long t = System.nanoTime();
        while(s.betterI >= 0) {
            s.bestWay = s.betterWay;
            reverse(s.result, s.betterI, s.betterJ, s.betterK);
            s.betterI = -1;
            s.betterJ = -1;
            s.betterK = -1;
            int cnt = 0;
            for(int i = 0; i < n; i++) {
                for(int j = i+1; j < n; j++) {
                    for(int k = j+1; k < n; k++) {
                        reverseSymmetric(i, j, k, n, s);
                    }
                }
                if((cnt++)%50 == 0) saveToFile(System.nanoTime() - t, s.bestWay);
            }
        }
        System.out.println("Wynik:" + (System.nanoTime() - t));
        return s.result;
    }

    private void saveToFile(long l, int result) {
        if(fileWriter!=null) {
            System.out.print(l / 1000000 + ";" + result + '\n');
            try {
                fileWriter.write(l / 1000000 + ";" + result + '\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Result calcAsymmetric() {
        Result result = new Result(tspData);
        int n = tspData.getSize();
        int bestWay = result.calcObjectiveFunction();
        int betterI = 0, betterJ = 0, betterK = 0;
        while(betterI >= 0) {
            reverse(result, betterI, betterJ, betterK);
            betterI = -1;
            betterJ = -1;
            betterK = -1;
            for(int i = 0; i < n; i++) {
                for(int j = i+1; j < n; j++) {
                    for(int k = j+1; k < n; k++) {
                        Result r = result.clone();
                        reverse(r, i, j, k);
                        int way = r.calcObjectiveFunction();
                        if (way < bestWay) {
                            bestWay = way;
                            betterI = i;
                            betterJ = j;
                            betterK = k;
                        }
                    }
                }
            }
        }
        return result;
    }

    private void reverseSymmetric(int i, int j, int k, int n, Solution s) {
        int way = s.bestWay;
        int p = i - 1;
        if (p < 0) p += n;
        int q = j + 1;
        if (q >= n) q -= n;
        int r = k + 1;
        if (r >= n) r -= n;
        if (r == i || q == k) return;
        way -= tspData.getDistance(s.result.way[p], s.result.way[i]);
        way -= tspData.getDistance(s.result.way[j], s.result.way[q]);
        way -= tspData.getDistance(s.result.way[k], s.result.way[r]);
        way += tspData.getDistance(s.result.way[p], s.result.way[j]);
        way += tspData.getDistance(s.result.way[i], s.result.way[k]);
        way += tspData.getDistance(s.result.way[r], s.result.way[q]);
        if (way < s.betterWay) {
            s.betterWay = way;
            s.betterI = i;
            s.betterJ = j;
            s.betterK = k;
        }
    }

    private void reverse(Result result, int i, int j, int k) {
        reversePart(result, i, j);
        reversePart(result, j+1, k);
    }

    private void reversePart(Result result, int i, int j) {
        int n = result.problem.getSize();
        int k = 0;
        int am = j-i;
        if(am < 0) am+=n;
        while(k < am/2+1) {
            int from = k+i;
            if(from >= n) from -= n;
            int to = j-k;
            if(to < 0) to += n;
            int tmp = result.way[from];
            result.way[from] = result.way[to];
            result.way[to] = tmp;
            k++;
        }
    }

    private class Solution {

        Result result;
        int betterWay, bestWay, betterI, betterJ, betterK;

    }

    public void setFileWriter(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }
}
