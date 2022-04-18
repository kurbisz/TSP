package org.example.algorithm.tabooList;

import org.example.algorithm.Neighbourhoods.Moves.InvertMove;
import org.example.algorithm.Neighbourhoods.Moves.Move;

public class InvertTabooList extends TabooList {

	boolean moves[][];

	public InvertTabooList(int maxSize, int n) {
		super(maxSize, n);
		moves = new boolean[n][n];
	}

	@Override
	public void add(Move move) {
		if(!(move instanceof InvertMove)) return;
		InvertMove invertMove = (InvertMove) move;
		if((up+1)%maxSize == down) {
//			System.out.println("Taboo list is full");
			InvertMove remove = (InvertMove) movesList[down];
			if(remove!=null) setMove(remove, false);
			movesList[down] = move;
			setMove(invertMove, true);
			up = (up+1)%maxSize;
			down = (down+1)%maxSize;
		}
		else {
//			System.out.println("Taboo list is not full");
			movesList[up] = move;
			setMove(invertMove, true);
			up = (up+1)%maxSize;
		}
	}

	@Override
	public boolean contains(Move move) {
		if(!(move instanceof InvertMove)) return false;
		InvertMove invertMove = (InvertMove) move;
		return moves[invertMove.getFrom()][invertMove.getTo()];
	}

	private void setMove(InvertMove m, boolean b) {
		moves[m.getFrom()][m.getTo()] = b;
	}

}
