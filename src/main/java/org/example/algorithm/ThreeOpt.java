package org.example.algorithm;

import org.example.data.Result;
import org.example.data.TspData;

public class ThreeOpt extends Algorithm{
    public ThreeOpt(TspData tspData) {
        super(tspData);
    }

    @Override
    public Result calculate() {
        if(tspData.isSymmetric()) return calcSymmetric();
        return calcAsymmetric();
    }

    private Result calcSymmetric() {
        Result result = new Result(tspData);
        int n = tspData.getSize();
        int betterWay = result.calcObjectiveFunction();
        int bestWay;
        int betterI = 0, betterJ = 0, betterK = 0;
        while(betterI >= 0) {
            bestWay = betterWay;
            reverse(result, betterI, betterJ, betterK);
            betterI = -1;
            betterJ = -1;
            betterK = -1;
            for(int i = 0; i < n; i++) {
                for(int j = i+1; j < n; j++) {
                    for(int k = j+1; k < n; k++) {
                        int way = bestWay;
                        int p = i - 1;
                        if (p < 0) p += n;
                        int q = j + 1;
                        if (q >= n) q -= n;
                        int r = k + 1;
                        if (r >= n) r -= n;
                        if (r == i || q == k) continue;
                        way -= tspData.getDistance(result.way[p], result.way[i]);
                        way -= tspData.getDistance(result.way[j], result.way[q]);
                        way -= tspData.getDistance(result.way[k], result.way[r]);
                        way += tspData.getDistance(result.way[p], result.way[j]);
                        way += tspData.getDistance(result.way[i], result.way[k]);
                        way += tspData.getDistance(result.way[r], result.way[q]);
                        if (way < betterWay) {
                            betterWay = way;
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

}
