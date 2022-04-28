package org.example.algorithm.taboo.tabooList;

import org.example.algorithm.taboo.Neighbourhoods.Moves.InvertMove;
import org.example.algorithm.taboo.Neighbourhoods.Moves.Move;

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

	@Override
	public TabooList clone() {
		InvertTabooList invertTabooList = new InvertTabooList(this.maxSize, this.n);
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				invertTabooList.moves[i][j] = moves[i][j];
			}
		}
		invertTabooList.down = this.down;
		invertTabooList.up = this.up;
		for(int i = 0; i < n; i++) {
			invertTabooList.movesList[i] = this.movesList[i];
		}
		return invertTabooList;
	}
}
