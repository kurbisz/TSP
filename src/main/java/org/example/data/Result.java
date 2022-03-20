package org.example.data;

import org.example.Point;

public class Result {

    public TspData problem;
    public Point[] way;
    public int currInd=0;
    public Result(TspData tspData){
        this.problem = tspData;
        way = new Point[problem.getSize()];
        for(int i = 0; i< problem.getSize(); i++) way[i]=null;
    }

    public int calcObjectiveFunction() {
        int sum = 0;
        for(int i = 0; i < problem.getSize() - 1; i++) {
            sum += way[i].getDistance(way[i+1]);
        }
        sum+= way[problem.getSize()-1].getDistance(way[0]);
        return sum;
    }

    public void addWaypoint(Point p){
        way[currInd] = p;
        currInd++;
    }

    public void removeLastWaypoint(){
        currInd--;
        way[currInd]=null;
    }

}
