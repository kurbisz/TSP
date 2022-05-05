package org.example.algorithm.taboo.tabooList;

import org.example.algorithm.taboo.Neighbourhoods.Moves.InsertMove;
import org.example.algorithm.taboo.Neighbourhoods.Moves.SwapMove;
import org.example.algorithm.taboo.Neighbourhoods.Moves.Move;

public class SwapTabooList extends BaseForTabooList {

	boolean[][] moves;

	public SwapTabooList(int maxSize, int n) {
		super(maxSize, n);
		moves = new boolean[n][n];
	}

	@Override
	public void add(Move move) {
		if(!(move instanceof SwapMove)) return;
		SwapMove swapMove = (SwapMove) move;
		if((up+1)%maxSize == down) {
//			System.out.println("Taboo list is full");
			SwapMove remove = (SwapMove) movesList[down];
			if(remove!=null) setMove(remove, false);
			movesList[down] = move;
			setMove(swapMove, true);
			up = (up+1)%maxSize;
			down = (down+1)%maxSize;
		}
		else {
//			System.out.println("Taboo list is not full");
			movesList[up] = move;
			setMove(swapMove, true);
			up = (up+1)%maxSize;
		}
	}

	@Override
	public boolean contains(Move move) {
		if(!(move instanceof SwapMove)) return false;
		SwapMove invertMove = (SwapMove) move;
		return moves[invertMove.getFrom()][invertMove.getTo()];
	}

	private void setMove(SwapMove m, boolean b) {
		moves[m.getFrom()][m.getTo()] = b;
		moves[m.getTo()][m.getFrom()] = b;
	}

	@Override
	public TabooList cloneTabooList() {
		SwapTabooList invertTabooList = new SwapTabooList(this.maxSize, this.n);
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				invertTabooList.moves[i][j] = moves[i][j];
			}
		}
		invertTabooList.down = this.down;
		invertTabooList.up = this.up;
		for(int i = 0; i < maxSize; i++) {
			invertTabooList.movesList[i] = this.movesList[i];
		}
		return invertTabooList;
	}
}
