/**
 * 
 */
package com.mancala.model;

/**
 * @author "Pavithra"
 * Maintains the state of a stone
 */
public class Stone {
	
	private boolean isLastStone;
	
	public boolean isLast() {
		return isLastStone;
	}

	public void setIsLastStone(boolean isLast) {
		isLastStone = isLast;
	}
}
