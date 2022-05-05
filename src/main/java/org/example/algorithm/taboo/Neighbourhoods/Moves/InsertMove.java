package org.example.algorithm.taboo.Neighbourhoods.Moves;

public class InsertMove implements Move {
    int from;
    int to;

    public InsertMove(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean checkEqual(Move move) {
        if (move instanceof InsertMove) {
            InsertMove insertMove = (InsertMove) move;
            return (from == insertMove.from && to == insertMove.to);
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

