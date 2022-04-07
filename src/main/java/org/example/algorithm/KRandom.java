package org.example.algorithm;

import org.example.data.Result;
import org.example.data.TspData;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class KRandom extends Algorithm{

    private int k;
    private boolean async = false;
    private Random rand = new Random();
    private long time = -1;
    private int threads = 8;
    private long doTime = 0;

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

    public KRandom(TspData tspData, long time) {
        super(tspData);
        this.time = time;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    @Override
    public Result calculate() {
        if(time >= 0) return calcTime();
        if(async) return calcAsync();
        return calcSync();
    }



    private Result calcAsync() {
        long time = System.nanoTime();
        final Result result[] = new Result[k];
        final int dist[] = new int[k];
        final Thread thread[] = new Thread[threads];
        for(int i = 0; i < threads; i++) {
            final int index = i;
            thread[i] = new Thread() {
                @Override
                public void run() {
                    for(int l = (k*index)/threads; l < (k*(index+1)/threads); l++) {
                        Result r = new Result(tspData);
                        for (int j = 0; j < tspData.getSize(); j++) {
                            int ra = ThreadLocalRandom.current().nextInt(tspData.getSize());
                            int tmp = r.way[ra];
                            r.way[ra] = r.way[j];
                            r.way[j] = tmp;
                        }
                        result[l] = r;
                        dist[l] = result[l].calcObjectiveFunction();
                    }
                }
            };
            thread[i].start();
        }
        for(int i = 0; i < threads; i++) {
            try {
                thread[i].join();
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
        this.doTime = System.nanoTime() - time;
        return result[min];
    }

    private Result calcAsync2(){
        long time = System.nanoTime();

        ArrayList<KRunner> jobs = new ArrayList<>();

        ExecutorService pool = Executors.newFixedThreadPool(threads);

        for(int i = 0; i < k; i++) {
            jobs.add(new KRunner());
            pool.execute(jobs.get(i));
        }

        Result best = jobs.get(0).r;
        int dist = jobs.get(0).objFunc;

        for(KRunner job : jobs) {
            if(job.objFunc < dist) {
                dist = job.objFunc;
                best = job.r;
            }
        }

        this.doTime = System.nanoTime() - time;

        return best;
    }

    private class KRunner implements Runnable{
        Result r;
        int objFunc;
        Random random;

        public KRunner(){
            r = new Result(tspData);
            random = new Random();
        }

        @Override
        public void run() {

            for (int j = 0; j < tspData.getSize(); j++) {
                int ra = random.nextInt(tspData.getSize());
                int tmp = r.way[ra];
                r.way[ra] = r.way[j];
                r.way[j] = tmp;
            }
            objFunc = r.calcObjectiveFunction();
        }
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

    private Result calcTime() {
        Result result = null;
        int dist = -1;
        long t = System.nanoTime();
        while (System.nanoTime() - t < time) {
            Result r = new Result(tspData);
            for(int j = 0; j < tspData.getSize(); j++) {
                int ra = rand.nextInt(tspData.getSize());
                int tmp = r.way[ra];
                r.way[ra] = r.way[j];
                r.way[j] = tmp;
            }
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

    public long getTime() {
        return doTime;
    }
}
