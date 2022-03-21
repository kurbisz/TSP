package org.example.algorithm;

import org.example.data.Result;
import org.example.data.TspData;

import java.util.List;

public class TwoOpt extends Algorithm{

    boolean async;

    public TwoOpt(TspData tspData) {
        this(tspData, false);
    }

    public TwoOpt(TspData tspData, boolean async) {
        super(tspData);
        this.async = async;
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
        int betterI = 0, betterJ = 0;
        while(betterI >= 0) {
            bestWay = betterWay;
            reverse(result, betterI, betterJ);
            betterI = -1;
            betterJ = -1;
            for(int i = 0; i < n; i++) {
                for(int j = i+1; j < n; j++) {
                    int way = bestWay;
                    int p = i-1;
                    if(p < 0) p += n;
                    int q = j+1;
                    if(q >= n) q -= n;
                    if(p == j) continue;
                    way -= tspData.getDistance(result.way[p], result.way[i]);
                    way -= tspData.getDistance(result.way[j], result.way[q]);
                    way += tspData.getDistance(result.way[p], result.way[j]);
                    way += tspData.getDistance(result.way[i], result.way[q]);
                    if(way < betterWay) {
                        betterWay = way;
                        betterI = i;
                        betterJ = j;
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
        int betterI = 0, betterJ = 0;
        while(betterI >= 0) {
            reverse(result, betterI, betterJ);
            betterI = -1;
            betterJ = -1;
            for(int i = 0; i < n; i++) {
                for(int j = i+1; j < n; j++) {
                    Result r = result.clone();
                    reverse(r, i, j);
                    int way = r.calcObjectiveFunction();
                    if(way < bestWay) {
                        bestWay = way;
                        betterI = i;
                        betterJ = j;
                    }
                }
            }
        }
        return result;
    }

    private void reverse(Result result, int i, int j) {
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
