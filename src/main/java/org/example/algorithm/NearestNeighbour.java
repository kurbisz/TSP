package org.example.algorithm;

import org.example.data.Result;
import org.example.data.TspData;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NearestNeighbour extends Algorithm{

    ArrayList<Result> candidates;

    public NearestNeighbour(TspData tspData) {
        super(tspData);
    }

    @Override
    public Result calculate() {
        Result res = new Result(tspData);
        simpleNearestNeighbour(res, 0);
        return res;
    }

    /**
     *
     * @param res - object, to which we save route
     *            it is obligatory, that first waypoint is selected -> this will be starting city
     * @param notVisited - arrayList of indexes of non-visited cities
     */
    public void nearestNeighbour(Result res, ArrayList<Integer> notVisited, int waypointInd){
        int currCityInd = res.way[waypointInd];
        notVisited.remove(currCityInd);
        int nearest = tspData.getDistance(currCityInd, notVisited.get(0));
        for(int i = 1; i<notVisited.size(); i++){
            int tempDist = tspData.getDistance(currCityInd, notVisited.get(i));
            if(tempDist < nearest ) nearest = tempDist;
        }
        for(int ind: notVisited){
            int tempDist = tspData.getDistance(currCityInd, ind);
            if(tempDist == nearest){
                currCityInd++;
//                res.way[currCityInd]
            }
        }
    }

//    public void simpleNearestNeighbour(Result res){
//        boolean[] visited = new boolean[tspData.getSize()];
//        for(int i = 0; i< tspData.getSize(); i++) visited[i] = false;
//
//        for(int i = 0; i < tspData.getSize()-1; i++){
//            visited[res.way[i]] = true;
//            int nearest = (res.way[i]+1)% tspData.getSize();
//            int nearestDist = tspData.getDistance(res.way[i],nearest);
//            for(int j=0; j< tspData.getSize(); j++){
//                if(!visited[j]){
//                    int tempDist = tspData.getDistance(res.way[i], j);
//                    if(tempDist < nearestDist) {
//                        nearest = j;
//                        nearestDist = tempDist;
//                    }
//                }
//            }
//            //mamy znalezionego najbliższego sąsiada do res.way[i]
//            res.way[i+1] = nearest;
//            System.out.println("Nearest is: "+nearest);
//        }
//    }

    public void simpleNearestNeighbour(Result res, int start){
        ArrayList<Integer> nonVisited = new ArrayList<>();
        for(int i = 0; i < tspData.getSize(); i++) nonVisited.add(i);
        res.way[0] = start;
        nonVisited.remove(start);
        int i = 0;

        while(nonVisited.size()>0){
            int nearest = nonVisited.get(0);
            int nearestDist = tspData.getDistance(res.way[i], nearest);
            for(int city: nonVisited){
                int tempDist = tspData.getDistance(res.way[i], city);
                if(tempDist < nearestDist){
                    nearest = city;
                    nearestDist = tempDist;
                }
            }
            i++;
//            System.out.print(nearest+", ");
//            System.out.println(i);
            res.way[i] = nearest;
            nonVisited.remove(Integer.valueOf(nearest));
        }

    }

    //TODO optimalizaton (optional)
}
