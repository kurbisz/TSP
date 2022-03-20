package org.example.data;

import org.example.Point;

public class Result {

    public TspData problem;
    public int[] way;
    public int currInd=0;
    public Result(TspData tspData){
        this.problem = tspData;
        way = new int[problem.getSize()];
        for(int i = 0; i< problem.getSize(); i++) way[i]=i;
    }

    public int calcObjectiveFunction() {
        int sum = 0;
        for(int i = 0; i < problem.getSize() - 1; i++) {
            sum += problem.getDistance(way[i], way[i+1]);
        }
        sum+= problem.getDistance(way[problem.getSize()-1], way[0]);
        return sum;
    }

    public void addWaypoint(int p){
        way[currInd] = p;
        currInd++;
    }

    public void removeLastWaypoint(){
        currInd--;
        way[currInd]=0;
    }

    public Result clone() {
        Result result = new Result(problem);
        for(int i = 0; i < problem.getSize(); i++) {
            result.way[i] = way[i];
        }
        result.currInd = currInd;
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

}
