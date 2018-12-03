package mancala.services;

import java.util.LinkedList;

import mancala.models.BoardPit;

/**
 * @author "Pavithra"
 *
 */
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
	 *  Reset isLastStoneInMancala to false
	 *  1. check if next pit is Mancala - Add stone if it is Mancala of the current player else do nothing.
	 *  								- Also check if that is the last stone in the chosenPit, then set IsLastStoneInMancala to true.
	 *  2. Capture logic - Check if the stone is 'last stone in current pit', 'next pit is empty' on the 'current player's side' and has 'non-empty opposite pit'
	 *  3. If none of the above two cases, then simply push a stone in to the next pit 
	 * @param chosenPit - the index of the chosenPit from the linked list.
	 * @return
	 */
	/* (non-Javadoc)
	 * @see mancala.services.IStrategyGame#performMove(int)
	 */
	@Override
	public void performMove(int selectedPit) {
		
		//Validate the selected pit before performing the move.
		if(validateChosenPit(selectedPit)) {
			isLastStoneInMancala = false;
			int successivePit = selectedPit + 1;
			int stonesInSelectedPit = boardPits.get(selectedPit).getCountOfStones();
			
			for(int i = 0 ; i < stonesInSelectedPit ; i++) {
				
				//If playerA is active(playerA true) and successive pit is his Mancala (pit 6), then add a stone to pit 6.
				if(playerA && successivePit == 6) {
					boardPits.get(successivePit).PushInStone();
					//Check if that is the last stone of current pit, then set isLastStoneInMancala to true.
					if(boardPits.get(selectedPit).IsStoneLast())
						isLastStoneInMancala = true;
					boardPits.get(selectedPit).popOutStone();
				}
				
				//If playerB is active (playerA false) and successive pit is his Mancala (pit 13), then add a stone to pit 13.
				else if(!playerA && successivePit == 13) {
					boardPits.get(successivePit).PushInStone();
					//Check if that is the last stone of current pit, then set isLastStoneInMancala to true.
					if(boardPits.get(selectedPit).IsStoneLast())
						isLastStoneInMancala = true;
					boardPits.get(selectedPit).popOutStone();
				}
				
				/* Capturing logic : 4 conditions to be satisfied to capture opponent's stones
				 * 1. If last stone of the selected pit 
				 * 2. ends on an empty pit
				 * 3. of the active player
				 * 4. and the pit paired with the landing pit is not empty
				 * If above 4 are true, then capture stones from both the paired pits and transfer to active player's Mancala.
				 */
				else if(boardPits.get(selectedPit).IsStoneLast() && 
						boardPits.get(successivePit).isEmpty() && 
						pitBelongsToPlayer(successivePit) &&
						!IsPairedPitEmpty(successivePit)) {
					//Pop out the last stone from the selected pit and add it to the Mancala of the active player.
					pushInToMancala();
					boardPits.get(selectedPit).popOutStone();
					//Capture the opponent's stones from the paired pit.
					for(int j = 0 ; j < getPairedPit(selectedPit).getCountOfStones() ; j++) {
						pushInToMancala();
						getPairedPit(selectedPit).popOutStone();
					}
				}
				
				//If pit is none of the above and is just a normal pit simply add a stone to it and pop out from selected pit.
				else {
					boardPits.get(successivePit).PushInStone();
					boardPits.get(selectedPit).popOutStone();
					//After adding the stone in the pit if that is the only stone, then set it as last stone.
					if((boardPits.get(successivePit)).getCountOfStones() == 1)
						boardPits.get(successivePit).peek().setIsLastStone(true);
				}
				successivePit++;
			}
			
			//At the end of every move, check if last stone is in Mancala of active player. Then player gets another turn else turn changes.
			if(!isLastStoneInMancala) {
				if(playerA) {
					playerA = false;
					//FIXME: Send a message to UI indicating that it is playerB's turn
				}
				else {
					playerA = true;
					//FIXME: Send a message to UI indicating that it is playerA's turn
				}
			}	
			else
			{
				if(playerA) {
					//FIXME: Send a message to UI indicating that the playerA gets a free turn
				}
				else {
					//FIXME: Send a message to UI indicating that the playerB gets a free turn
				}
				
			}
		}
		else {
			//FIXME: Message to UI stating that the move is invalid
		}
		
	}
	
	/**
	 * Method to push a stone in to Mancala of the active player.
	 */
	public void pushInToMancala() {
		if(playerA)
			boardPits.get(6).PushInStone();
		else
			boardPits.get(13).PushInStone();
	}
	
	/**
	 * @param selectedPit - the landing pit that is empty
	 * @return boolean - true if the pit paired with landing pit is empty.
	 */
	public boolean IsPairedPitEmpty(int selectedPit) {
		return getPairedPit(selectedPit).isEmpty();
	}
	
	 /**
	 * Validate the chosen pit
	 * @param selectedPit - the index of the selected pit from the linked list.
	 *  * Three conditions to be satisfied for a move to be valid.
	 *  1. Selected pit should not be empty
	 *  2. Selected pit should be that of the active player
	 *  3. Selected pit should not be a Mancala pit
	 * If all these conditions are satisfied, return true - the selected pit is valid.

	 * @return a boolean - true if the selectedPit is valid and move can proceed.
	 */
	public boolean validateChosenPit(int selectedPit) {
		boolean isValid = true;
		//Check if pit is empty
		if(boardPits.get(selectedPit).isEmpty())
			isValid = false;
		//Check if the pit belongs to the active player
		else if (!pitBelongsToPlayer(selectedPit))
			isValid = false;
		//Check if pit is Mancala
		else if (selectedPit == 6 || selectedPit == 13)
			isValid = false;
		return isValid;
	}
	
	
	/**
	 * @param chosenPit - Verify if the chosenPit belongs to the active player.
	 * @return boolean - true if pit belongs to the active player else false.
	 */
	public boolean pitBelongsToPlayer(int pitToVerify) {
		boolean belongsToPlayer = true;
		if (playerA && pitToVerify >= 7 && pitToVerify < 13) 
			belongsToPlayer = false;
		else if (!playerA && pitToVerify >=0 && pitToVerify < 6)
			belongsToPlayer = false;
		return belongsToPlayer;
	}
	/* 
	 * @see mancala.services.IStrategyGame#DeclareWinner()
	 */
	@Override
	public void declareWinner() {
		
	}

	/* 
	 * @see mancala.services.IStrategyGame#GameOver()
	 */
	@Override
	public void gameOver() {
		
	}
	
	
	/**
	 * @param landedEmptyPit
	 * @return a BoardPit object - the board pit that is opposite to the landed empty pit.
	 */
	public BoardPit getPairedPit(int landedEmptyPit) {
		BoardPit pairedPit = boardPits.get(12 - landedEmptyPit);
		return pairedPit;
	}
}
