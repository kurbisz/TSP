package org.example.algorithm.taboo.tabooList;

import org.example.algorithm.taboo.Neighbourhoods.Moves.Move;

import java.util.ArrayList;

//TODO
public class BasicTabooList extends TabooList {

    ArrayList<Move> list;
    int maxSize;
    boolean symmetricTSP;
    boolean aspiration;

    public BasicTabooList(int maxSize, boolean symmetricTSP, boolean aspiration) {
        //TODO
        this.list = new ArrayList<>();
        this.maxSize = maxSize;
        this.symmetricTSP = symmetricTSP;
        this.aspiration = aspiration;
    }

    @Override
    public boolean add(Move move) {
        //TODO
        if(list.size()+1 > maxSize) {
            list.remove(list.size()-1);
        }
        return list.add(move);
    }


    @Override
    public boolean contains(Move move) {
        return list.contains(move);
    }

    @Override
    public TabooList clone() {
        BasicTabooList basicTabooList = new BasicTabooList(this.maxSize, this.symmetricTSP, this.aspiration);
        //TODO
        basicTabooList.down = this.down;
        basicTabooList.up = this.up;
        for(int i = 0; i < n; i++) {
            basicTabooList.movesList[i] = this.movesList[i];
        }
        return basicTabooList;
    }
}
