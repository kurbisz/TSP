package org.example.data;

public class Result {

    public TspData problem;
    public int[] way;
    public Result(TspData tspData){
        this.problem = tspData;
        way = new int[problem.getSize()];
    }

    public int calcObjectiveFunction() {
        int sum = 0;
        for(int i = 0; i < problem.getSize() - 1; i++) {
            sum += problem.getDistance(way[i], way[i+1]);
        }
        sum+= problem.getDistance(way[problem.getSize()-1], way[0]);
        return sum;
    }
}
