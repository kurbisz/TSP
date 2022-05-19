package org.example.data;

import org.example.Point;

import java.util.Random;

public class Result {

    public TspData problem;
    public int[] way;
    public int currInd=0;
    public int objFuncResult;

    public Result(TspData tspData){
        this.problem = tspData;
        way = new int[problem.getSize()];
        for(int i = 0; i< problem.getSize(); i++) way[i]=i;
    }

    public Result(Result result) {
        this.problem = result.problem;
        this.currInd = result.currInd;
        this.objFuncResult = result.objFuncResult;
        this.way = new int[problem.getSize()];
        if (problem.getSize() >= 0) System.arraycopy(result.way, 0, this.way, 0, problem.getSize());
    }

    public int calcObjectiveFunction() {
        int sum = 0;
        for(int i = 0; i < problem.getSize() - 1; i++) {
            sum += problem.getDistance(way[i], way[i+1]);
        }
        sum+= problem.getDistance(way[problem.getSize()-1], way[0]);
        objFuncResult = sum;
        return sum;
    }

    public int getProblemSize() {
        return problem.getSize();
    }


    public Result clone() {
        Result result = new Result(problem);
        for(int i = 0; i < problem.getSize(); i++) {
            result.way[i] = way[i];
        }
        result.currInd = currInd;
        result.objFuncResult = objFuncResult;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < problem.getSize(); i++) {
            stringBuilder.append(way[i]);
            stringBuilder.append(' ');
        }
        return stringBuilder.toString();
    }

    public static Result getRandom(TspData tspData) {
        Random r = new Random();
        Result result = new Result(tspData);
        for(int i = 0; i < tspData.getSize(); i++) {
            int rand = i + r.nextInt(tspData.getSize() - i);
            int tmp = result.way[i];
            result.way[i] = result.way[rand];
            result.way[rand] = tmp;
        }
        return result;
    }

}
