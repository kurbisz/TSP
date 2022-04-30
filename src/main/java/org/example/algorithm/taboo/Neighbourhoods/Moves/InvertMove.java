package org.example.algorithm.taboo.Neighbourhoods.Moves;

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
            // TODO check if this condition is ok
            //zmieniłem symm na !symm, bo to właśnie jak jest symetryczny, to nie ma znaczenia kierunek
            if (!symm) return (from == invertMove.from && to == invertMove.to);
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

