package com.org.question008;

public class Checker {
	private int pos;
	private int movementCost;
	public int[] horizontalRowMovementCost;
	public int[] leftDiagonalMovementCost;
	public int[] rightDiagonalMovementCost;
	
	public Checker(int[] horizontalRowMovementCost) {
		this.horizontalRowMovementCost = horizontalRowMovementCost;
	}
	public Checker(int pos, int movementCost) {
		this.setPos(pos);
		this.setMovementCost(movementCost);
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getMovementCost() {
		return movementCost;
	}

	public void setMovementCost(int movementCost) {
		this.movementCost = movementCost;
	}
}	
