package org.example.algorithm;

import org.example.algorithm.Neighbourhoods.Moves.Move;

import java.util.ArrayList;

public class TabooList extends ArrayList<Move> {

    int maxSize;

    TabooList(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public boolean add(Move move) {
        if(this.size()+1 >= maxSize) {
//            System.out.println("Taboo list is full");
            this.remove(this.size()-1);
        }
        return super.add(move);
    }
}
