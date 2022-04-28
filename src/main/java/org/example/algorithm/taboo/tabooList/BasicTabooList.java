package org.example.algorithm.taboo.tabooList;

import org.example.algorithm.taboo.Neighbourhoods.Moves.Move;

import java.util.ArrayList;

public class BasicTabooList implements TabooList {

    ArrayList<Move> list;
    int maxSize;
//    boolean symmetricTSP;
//    boolean aspiration;

    public BasicTabooList(int maxSize) {
        this.list = new ArrayList<>();
        this.maxSize = maxSize;
//        this.symmetricTSP = symmetricTSP;
//        this.aspiration = aspiration;
    }

    @Override
    public void add(Move move) {
        if(list.size()+1 > maxSize) {   //jeśli przekroczymy zadany maksymalny rozmiar, to usuwamy najstarszy element
            list.remove(list.size()-1);
        }
        list.add(move);
    }


    @Override
    public boolean contains(Move move) {
        return list.contains(move);
    }

    @Override
    public TabooList clone() {
        BasicTabooList copy = new BasicTabooList(this.maxSize);
        for(Move move : list) {
            copy.add(move);
        }
        return copy;
    }
}
