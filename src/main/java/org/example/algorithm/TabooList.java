package org.example.algorithm;

import org.example.algorithm.Neighbourhoods.Moves.Move;

import java.util.ArrayList;

public class TabooList extends ArrayList<Move> {

    int maxSize;
    boolean moves[][];

    TabooList(int maxSize) {
        this.maxSize = maxSize;
        moves = new boolean[maxSize][maxSize];
    }

    @Override
    public boolean add(Move move) {
        if(this.size()+1 >= maxSize) {
//            System.out.println("Taboo list is full");
            //TODO repair going through whole list
            Move m = this.get(this.size()-1);
            int[] arr = m.toIntArray();
            moves[arr[0]][arr[1]] = false;
            this.remove(m);
        }
        int[] arr = move.toIntArray();
        moves[arr[0]][arr[1]] = true;
        return super.add(move);
    }

    public boolean containsMove(Move move) {
        int[] m = move.toIntArray();
        return moves[m[0]][m[1]];
    }

}
