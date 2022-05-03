package org.example.algorithm.taboo.Neighbourhoods.Moves;

public class InvertMoveWithoutSymm implements Move {
    int from;
    int to;

    public InvertMoveWithoutSymm(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean checkEqual(Move move) {
        if (move instanceof InvertMoveWithoutSymm) {
            InvertMoveWithoutSymm invertMove = (InvertMoveWithoutSymm) move;
            //zmieniłem symm na !symm, bo to właśnie jak jest symetryczny, to nie ma znaczenia kierunek
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
