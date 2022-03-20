package org.example.algorithm;

import org.example.data.Result;
import org.example.data.TspData;

import java.util.Random;

public class KRandom extends Algorithm{

    private int k;
    private boolean async = false;
    private Random rand = new Random();

    public KRandom(TspData tspData) {
        this(tspData, 100, false);
    }

    public KRandom(TspData tspData, int k) {
        this(tspData, k, false);
    }

    public KRandom(TspData tspData, int k, boolean async) {
        super(tspData);
        this.k = k;
        this.async = async;
    }

    @Override
    public Result calculate() {
        if(async) return calcAsync();
        return calcSync();
    }

    private Result calcAsync() {
        final Result result[] = new Result[k];
        final int dist[] = new int[k];
        Thread t[] = new Thread[k];
        for(int i = 0; i < k; i++) {
            final int l = i;
            t[i] = new Thread() {
                @Override
                public void run() {
                    Result r = new Result(tspData);
                    for(int j = 0; j < tspData.getSize(); j++) {
                        int ra = rand.nextInt(tspData.getSize());
                        int tmp = r.way[ra];
                        r.way[ra] = r.way[j];
                        r.way[j] = tmp;
                    }
                    result[l] = r;
                    dist[l] = result[l].calcObjectiveFunction();
                }
            };
            t[i].start();
        }
        for(int i = 0; i < k; i++) {
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int min = 0;
        for(int i = 1; i < k; i++) {
            if(dist[i] < dist[min]) {
                min = i;
            }
        }
        return result[min];
    }

    private Result calcSync() {
        Result result = null;
        int dist = -1;
        for(int i = 0; i < k; i++) {
            Result r = new Result(tspData);
            for(int j = 0; j < tspData.getSize(); j++) {
                int ra = rand.nextInt(tspData.getSize());
                int tmp = r.way[ra];
                r.way[ra] = r.way[j];
                r.way[j] = tmp;
            }
            System.out.println(r);
            int d = r.calcObjectiveFunction();
            if(dist < 0) {
                result = r;
                dist = d;
            }
            else if(dist > d) {
                result = r;
                dist = d;
            }
        }
        return result;
    }

}
