package org.example.algorithm.taboo.Neighbourhoods.Moves;

public class SwapMove implements Move {
    int from;
    int to;
    boolean symm;

    public SwapMove(int from, int to, boolean symm) {
        this.from = from;
        this.to = to;
        this.symm = symm;
    }

    @Override
    public boolean checkEqual(Move move) {
        if (move instanceof SwapMove) {
            SwapMove swapMove = (SwapMove) move;
            return (swapMove.from == this.from && swapMove.to == this.to) || (swapMove.from == this.to && swapMove.to == this.from);
        }
        return false;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}

