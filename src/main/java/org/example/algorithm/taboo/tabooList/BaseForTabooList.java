package org.example.algorithm.taboo.tabooList;

import org.example.algorithm.taboo.Neighbourhoods.Moves.Move;

/**
 * Baza dla tych bardziej rozbudowanych list tabu.
 * zawiera te pola, które przeniosłem z interfejsu TabooList
 */
public abstract class BaseForTabooList implements TabooList {
    protected int maxSize;
    protected int n;
    protected Move[] movesList;
    protected int down, up;

    public BaseForTabooList(int maxSize, int n) {
        this.maxSize = maxSize;
        this.n = n;
        movesList = new Move[maxSize];
        this.down = 0;
        this.up = 0;
    }

}
