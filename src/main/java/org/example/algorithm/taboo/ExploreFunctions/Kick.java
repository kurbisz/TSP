package org.example.algorithm.taboo.ExploreFunctions;

import org.example.data.Result;
import org.example.data.TspData;

import java.util.Random;

public class Kick implements ExploreFunction {

    int it_to_kick;
    int it_without_improvement = 0;
    int last_improve = Integer.MAX_VALUE;
    int kick_changes;

    public Kick(int it_to_kick, int kick_changes) {
        this.it_to_kick = it_to_kick;
        this.kick_changes = kick_changes;
    }

    @Override
    public boolean shouldExplore(Result current) {
        if(current.objFuncResult < last_improve) {
            last_improve = current.objFuncResult;
            it_without_improvement = 0;
        } else {
            it_without_improvement++;
        }
        return it_without_improvement >= it_to_kick;
    }

    @Override
    public void explore(Result start) {
//        System.out.println("Kicking");
        TspData tspData = start.problem;
        Random r = new Random();
        for (int j = 0; j<kick_changes; j++){
            int ind_1 = r.nextInt(tspData.getSize());
            int ind_2;
            do{
                ind_2 = r.nextInt(tspData.getSize());
            } while(ind_1 == ind_2);
            //losowy swap na wyniku
            int temp = start.way[ind_1];
            start.way[ind_1] = start.way[ind_2];
            start.way[ind_2] = temp;
        }
        start.calcObjectiveFunction();
        it_without_improvement = 0;
    }

    @Override
    public ExploreFunction copy() {
        return new Kick(it_to_kick, kick_changes);
    }
}
