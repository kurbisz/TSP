package org.example.algorithm;

import org.example.algorithm.Neighbourhoods.Moves.Move;

import java.util.ArrayList;

public class BasicTabooList extends ArrayList<Move> {

    int maxSize;
    boolean symmetricTSP;

    BasicTabooList(int maxSize, boolean symmetricTSP) {
        this.maxSize = maxSize;
        this.symmetricTSP = symmetricTSP;
    }

    @Override
    public boolean add(Move move) {
        if(this.size()+1 > maxSize) {
            remove(this.size()-1);
        }
        return super.add(move);
    }

    public boolean containsMove(Move move) {
        for(Move m : this) {
            if(m.equals(move)) {
                return true;
            }
        }
        return false;
    }
}
