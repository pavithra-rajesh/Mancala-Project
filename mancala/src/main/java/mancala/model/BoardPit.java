package mancala.model;

import java.util.Stack;

/**
 * @author "Pavithra"
 * Bean BoardPit - Maintains the state of a pit on the board.
 * Member variables - A stack of stones, count of stones in it, a bool to decide if the pit is Mancala.
 */
public class BoardPit {
	
	private Stack<Stone> stones;
	private int countStonesInPit;
	public boolean IsPitMancala;
	
	
	/**
	 * @param numStones
	 * Create a new stack of stones and push the stones in to it.
	 * Mark the first stone as the last stone.
	 */
	public BoardPit(int numStones) {
		countStonesInPit = numStones;
		stones = new Stack<Stone>();
		Stone stone1 = new Stone();
		stones.push(stone1);
		stones.peek().setIsLastStone(true);
		numStones--;
		for(int i = 0; i < numStones; i++) {
			stones.push(new Stone());
		}
		IsPitMancala = false;
	}
	
	/**
	 * @return a stone object - the first stone from the stack of stones in this pit.
	 */
	public Stone peek() {
		return stones.peek();
	}
	
	/**
	 * Push a new stone in to the pit and increment the count of stones in this pit.
	 */
	public void PushInStone() {
		stones.push(new Stone());
		countStonesInPit++;
	}
	
	/**
	 * Pop out the top stone from the pit and reduce the count of stones in this pit.
	 */
	public void popOutStone() {
		stones.pop();
		countStonesInPit--;
	}
	
	/**
	 * @return a boolean - true if stone is the last stone in the pit.
	 */
	public boolean IsStoneLast() {
		return stones.peek().isLast();
	}
	
	/**
	 * @return an integer - the total number of stones in this pit.
	 */
	public int getCountOfStones() {
		return countStonesInPit;
	}

	/**
	 * @return a boolean - true if the stack of stones is empty or size is zero.
	 */
	public boolean isEmpty() {
		return stones.empty();
	}
	
	/**
	 * To empty the pit (clear the stack of stones) and set the count of stones to zero.
	 */
	public void emptyPit() {
		stones.clear();
		countStonesInPit = 0;
	}
	
	/**
	 * Add stones to the pit.
	 * @param numStones - the number of stones to be added to pit.
	 */
	public void addStones(int numStones) {
		for(int i = 0; i < numStones; i++) {
			stones.push(new Stone());
			countStonesInPit++;
		}
	}

	/**
	 * To set a pit as Mancala after the game board is laid.
	 */
	public void setPitAsMancala() {
		IsPitMancala = true;
	}
}
