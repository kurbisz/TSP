package org.example.algorithm.Neighbourhoods.Moves;

public class InvertMove implements Move {
    int from;
    int to;
    boolean symm;

    public InvertMove(int from, int to, boolean symm) {
        this.from = from;
        this.to = to;
        this.symm = symm;
    }

    @Override
    public boolean checkEqual(Move move) {
        if (move instanceof InvertMove) {
            InvertMove invertMove = (InvertMove) move;
            if (symm) return (from == invertMove.from && to == invertMove.to);
            else return (invertMove.from == this.from && invertMove.to == this.to) || (invertMove.from == this.to && invertMove.to == this.from);
        }
        return false;
    }

    @Override
    public int[] toIntArray() {
        return new int[]{from, to};
    }


}

