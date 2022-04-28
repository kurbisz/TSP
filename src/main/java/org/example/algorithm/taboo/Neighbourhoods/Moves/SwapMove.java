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
            SwapMove invertMove = (SwapMove) move;
            // TODO check if this condition is ok
            if (symm) return (from == invertMove.from && to == invertMove.to);
            else return (invertMove.from == this.from && invertMove.to == this.to) || (invertMove.from == this.to && invertMove.to == this.from);
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

