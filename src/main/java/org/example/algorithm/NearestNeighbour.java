package org.example.algorithm;

import org.example.data.Result;
import org.example.data.TspData;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NearestNeighbour extends Algorithm{

    private boolean upgraded = true;
    private boolean multiThreaded = false;
    private int threadCount = 40;

    private class NearestNeighRunner implements Runnable{

        int start;
        Result res;

        NearestNeighRunner(int start){
            this.start = start;
        }

        @Override
        public void run() {
            res = new Result(tspData);
            simpleNearestNeighbour(res, start, 0);
//            addCandidate(res);
        }
    }

    public ArrayList<Result> candidates;

    public NearestNeighbour(TspData tspData) {
        super(tspData);
        candidates = new ArrayList<>();
    }

    public NearestNeighbour(TspData tspData, boolean multiThreaded, boolean upgraded){
        super(tspData);
        candidates = new ArrayList<>();
        this.multiThreaded = multiThreaded;
        this.upgraded = upgraded;
    }

    @Override
    public Result calculate() {
        if(multiThreaded) { //multiThreaded nearest neighbour
            ExecutorService pool = Executors.newFixedThreadPool(threadCount);
            ArrayList<NearestNeighRunner> list = new ArrayList<>();
            for (int i = 0; i < tspData.getSize(); i++) {
                list.add(new NearestNeighRunner(i));
                pool.execute(list.get(i));
            }
            pool.shutdown();
            try {
                pool.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (NearestNeighRunner r : list) {
                addCandidate(r.res);
            }

        } else if(upgraded){    //upgraded nearest neighbour (starting from each city)
            candidates.clear();
            for(int i = 0; i < tspData.getSize(); i++){
                Result res = new Result(tspData);
                simpleNearestNeighbour(res, i, 0);
                addCandidate(res);
            }
        } else {    //simple nearest neighbour starting from city no 0
            candidates.clear();
            Result res = new Result(tspData);
            simpleNearestNeighbour(res, 0, 0);
//            nearestNeighbourCheckAllEqual(res, 0, 0);
            addCandidate(res);
        }

        //wybranie najlepszego rozwiązania
        Result toReturn = candidates.get(0);
        int bestWay = toReturn.calcObjectiveFunction();
        for (Result r : candidates) {
            int temp = r.calcObjectiveFunction();
            if (temp < bestWay) {
                toReturn = r;
                bestWay = temp;
            }
        }
        return toReturn;
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

    public void simpleNearestNeighbour(Result res, int start, int startWaypoint){
        ArrayList<Integer> nonVisited = new ArrayList<>();
        for(int i = 0; i < tspData.getSize(); i++) nonVisited.add(i);
        res.way[startWaypoint] = start;
        for(int i =0; i <= startWaypoint; i++){
            nonVisited.remove(res.way[i]);
        }
        int i = startWaypoint;

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

    public void nearestNeighbourCheckAllEqual(Result res, int start, int startWaypoint){
        ArrayList<Integer> nonVisited = new ArrayList<>();
        for(int i = 0; i < tspData.getSize(); i++) nonVisited.add(i);
        res.way[startWaypoint] = start;
        for(int i =0; i <= startWaypoint; i++){
            nonVisited.remove(Integer.valueOf(res.way[i]));
        }
        int i = startWaypoint;

        while(nonVisited.size()>0){
            int nearest = nonVisited.get(0);
            int nearestDist = tspData.getDistance(res.way[i], nearest);
            for(int city: nonVisited){
                if(city == nearest) continue;
                int tempDist = tspData.getDistance(res.way[i], city);
                if(tempDist < nearestDist){
                    nearest = city;
                    nearestDist = tempDist;
                } else if (tempDist == nearestDist){
                    Result tempRes = new Result(tspData);
                    System.arraycopy(res.way, 0, tempRes.way, 0, i+1);
                    nearestNeighbourCheckAllEqual(tempRes, city, i+1);
                    addCandidate(tempRes);
                }
            }
            i++;
//            System.out.print(nearest+", ");
//            System.out.println(i);
            res.way[i] = nearest;
            nonVisited.remove(Integer.valueOf(nearest));
        }
    }

    private void addCandidate(Result res){
        candidates.add(res);
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    //TODO optimalizaton (optional)
}
