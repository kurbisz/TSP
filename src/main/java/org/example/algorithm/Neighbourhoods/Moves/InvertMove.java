package org.example.algorithm.Neighbourhoods.Moves;

public class InvertMove implements Move {
    int from;
    int to;
    public InvertMove(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean checkEqual(Move move) {
        if (move instanceof InvertMove) {
            InvertMove invertMove = (InvertMove) move;
            return (invertMove.from == this.from && invertMove.to == this.to) || (invertMove.from == this.to && invertMove.to == this.from);
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

