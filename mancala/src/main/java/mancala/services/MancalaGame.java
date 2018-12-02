package mancala.services;

import java.util.LinkedList;

import mancala.models.BoardPit;

public class MancalaGame implements IStrategyGame{

	private boolean playerA;
	private boolean isLastStoneInMancala;
	private LinkedList<BoardPit> boardPits;
	private final int NUM_STONES_IN_PIT = 6;
	
	/* 
	 * @see mancala.services.IStrategyGame#PerformMove(int)
	 * Perform move when a pit is clicked
	 * Validate the chosen pit - If valid, then loop through the pits and push a stone in to each.
	 * While looping through pits:
	 *  Reset IsLastStoneInMancala to false
	 *  1. check if next pit is Mancala - Add stone if it is Mancala of the current player else do nothing.
	 *  								- Also check if that is the last stone in the chosenPit, then set IsLastStoneInMancala to true.
	 *  2. Capture logic - Check if the stone is 'last stone in current pit', 'next pit is empty' on the 'current player's side' and has 'non-empty opposite pit'
	 *  3. If none of the above two cases, then simply push a stone in to the next pit 
	 * @param chosenPit - the index of the chosenPit from the linked list.
	 * @return
	 */
	@Override
	public void PerformMove(int chosenPit) {
		
	}
	
	 /**
	 * Validate the chosen pit
	 * @param chosenPit - the index of the chosenPit from the linked list.
	 *  * Three conditions to be satisfied for a move to be valid.
	 *  1. Chosen pit should not be empty
	 *  2. Chosen pit should be that of the current player
	 *  3. Chosen pit should not be a Mancala pit
	 * If all these conditions are satisfied, return true - the chosen pit is valid.

	 * @return a boolean - true if the chosenPit is valid and move can proceed.
	 */
	public boolean validateChosenPit(int chosenPit) {
		boolean isValid = true;
		if()
		return isValid;
	}

	/* 
	 * @see mancala.services.IStrategyGame#DeclareWinner()
	 */
	@Override
	public void DeclareWinner() {
		
	}

	/* 
	 * @see mancala.services.IStrategyGame#GameOver()
	 */
	@Override
	public void GameOver() {
		
	}

	
}
