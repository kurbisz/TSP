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
        super(maxSize, 0);
        this.list = new ArrayList<>();
//        this.maxSize = maxSize;
        this.symmetricTSP = symmetricTSP;
        this.aspiration = aspiration;
    }

    @Override
    public void add(Move move) {
        if(list.size()+1 > maxSize) {
            list.remove(list.size()-1);
        }
        //return list.add(move);
    }


    @Override
    public boolean contains(Move move) {
        return list.contains(move);
    }

}
