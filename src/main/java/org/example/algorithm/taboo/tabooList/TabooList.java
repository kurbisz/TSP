package org.example.algorithm.taboo.tabooList;

import org.example.algorithm.taboo.Neighbourhoods.Moves.Move;

public abstract class TabooList {

    protected int maxSize;
    protected int n;
    protected Move movesList[];
    protected int down, up;

    public TabooList(int maxSize, int n) {
        this.maxSize = maxSize;
        this.n = n;
        movesList = new Move[maxSize];
        this.down = 0;
        this.up = 0;
    }

    public abstract void add(Move move);

    public abstract boolean contains(Move move);

    public abstract TabooList clone();

}
