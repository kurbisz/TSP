package org.example.algorithm;

import org.example.algorithm.Neighbourhoods.Moves.Move;

import java.util.ArrayList;

public class BasicTabooList extends ArrayList<Move> {

    int maxSize;
    boolean symmetricTSP;
    boolean aspiration;

    BasicTabooList(int maxSize, boolean symmetricTSP, boolean aspiration) {
        this.maxSize = maxSize;
        this.symmetricTSP = symmetricTSP;
        this.aspiration = aspiration;
    }

    @Override
    public boolean add(Move move) {
        if(this.size()+1 > maxSize) {
            remove(this.size()-1);
        }
        return super.add(move);
    }
}
