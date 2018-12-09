package com.mancala.service;

import java.util.ArrayList;
import java.util.LinkedList;

import org.springframework.stereotype.Service;

import com.mancala.model.BoardPit;
import com.mancala.transferObject.GameBoard;

/**
 * @author "Pavithra"
 *
 */
@Service
public class MancalaGame implements IStrategyGame{

	private boolean playerA;
	private boolean isLastStoneInMancala;
	private LinkedList<BoardPit> boardPits;
	private final int NUM_STONES_IN_PIT = 6;
	private String winnerString;
	private boolean gameOver;
	
	/* 
	 * @see mancala.services.IStrategyGame#PerformMove(int)
	 * Perform move when a pit is clicked
	 * Validate the selected pit - If valid, then loop through the pits and push a stone in to each.
	 * While looping through pits:
	 *  Reset isLastStoneInMancala to false
	 *  1. check if next pit is Mancala - Add stone if it is Mancala of the current player else do nothing.
	 *  								- Also check if that is the last stone in the chosenPit, then set IsLastStoneInMancala to true.
	 *  2. Capture logic - Check if the stone is 'last stone in current pit', 'next pit is empty' on the 'current player's side' and has 'non-empty opposite pit'
	 *  3. If none of the above two cases, then simply push a stone in to the next pit 
	 * @param selectedPit - the index of the selectedPit from the linked list.
	 * @return
	 */
	@Override
	public void performMove(int selectedPit) {
		
		//Validate the selected pit before performing the move.
		if(validateSelectedPit(selectedPit)) {
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
			//End of Move, determine the next player
			playerTurnChange();
			
		}
		else {
			//FIXME: Message to UI stating that the move is invalid
		}
		
	}
	
	/* At the end of every move, 
	 * - Check if last stone is in Mancala of active player. 
	 * - Then player gets another turn else turn changes.
	 */
	public void playerTurnChange() {
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
	@Override
	public boolean validateSelectedPit(int selectedPit) {
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
	 * Player with greater number of stones in his Mancala is the winner.
	 * If equal, then it is a tie.
	 */
	@Override
	public void declareWinner() {
		if(boardPits.get(6).getCountOfStones() > boardPits.get(13).getCountOfStones())
			winnerString = "Player A is the winner";
		else if (boardPits.get(13).getCountOfStones() > boardPits.get(6).getCountOfStones())
			winnerString = "Player B is the winner";
		else 
			winnerString = "It is a tie!";
		//FIXME: Message the winner string to UI
	}

	/* 
	 * @see mancala.services.IStrategyGame#GameOver()
	 * Game is over if either top row or bottom row is empty.
	 * Then transfer the remaining stones to the corresponding player's Mancala.
	 */
	@Override
	public boolean isGameOver() {
		gameOver = (isTopRowEmpty() || isBottomRowEmpty()) ? transferLeftOversToMancala() : false;
		return gameOver;
	}
	
	/**
	 * Check if the top row is empty.
	 * @return boolean - true if top row is empty.
	 */
	public boolean isTopRowEmpty() {
		boolean isEmpty = true;
		for(int i = 0; i < 6 ; i++){
			if(!boardPits.get(i).isEmpty()) {
				isEmpty = false;
				break;
			}
		}
		return isEmpty;
	}
	
	/**
	 * Check if the bottom row is empty.
	 * @return boolean - true if top row is empty.
	 */
	public boolean isBottomRowEmpty() {
		boolean isEmpty = true;
		for(int i = 7; i < 13 ; i++){
			if(!boardPits.get(i).isEmpty()) {
				isEmpty = false;
				break;
			}
		}
		return isEmpty;
	}
	
	/**
	 * @return boolean - always true as at least one of the rows is empty at this point.
	 */
	public boolean transferLeftOversToMancala() {
		int countLeftOverStones = 0;
		if(isTopRowEmpty()) {
			for (int i = 7 ; i < 13 ; i++) {
				countLeftOverStones += boardPits.get(i).getCountOfStones();
				boardPits.get(i).emptyPit();
			}
			boardPits.get(13).addStones(countLeftOverStones);
		}
		else if (isBottomRowEmpty()) {
			for (int i = 0 ; i < 6 ; i++) {
				countLeftOverStones += boardPits.get(i).getCountOfStones();
				boardPits.get(i).emptyPit();  
			}
		}
		boardPits.get(6).addStones(countLeftOverStones);
		return true;
	}
	
	/**
	 * @param landedEmptyPit
	 * @return a BoardPit object - the board pit that is opposite to the landed empty pit.
	 */
	public BoardPit getPairedPit(int landedEmptyPit) {
		BoardPit pairedPit = boardPits.get(12 - landedEmptyPit);
		return pairedPit;
	}

	/* 
	 * @see mancala.services.IStrategyGame#startNewGame()
	 * Create a new game with 6 stones in each pit and two empty mancalas
	 * Set the player boolean to playerA 
	 * Set the game status as game not over
	 * Empty winner string
	 */
	@Override
	public void startNewGame() {
		boardPits = new LinkedList<BoardPit>();
		boolean isMancala = false;
		for(int i = 0; i < 14 ; i++) {
			if( i != 6)
				isMancala = true;
			else if (i != 13)
				isMancala = true;
			// To keep the Mancalas empty
			if (!isMancala)
				boardPits.add(new BoardPit(NUM_STONES_IN_PIT));
		}
		boardPits.get(6).setPitAsMancala();
		boardPits.get(13).setPitAsMancala();
		playerA = true;
		winnerString = "";
		gameOver = false;
	}

	/* 
	 * @see mancala.services.IStrategyGame#getGameBoard()
	 * To get the count of stones in each pit on the game board, status of the game and the winner string at any point
	 */
	@Override
	public GameBoard getGameBoard() {
		ArrayList<Integer> countStonesBoardPits = new ArrayList<Integer>();
		for(int i = 0; i < boardPits.size(); i++) 
			countStonesBoardPits.add(boardPits.get(i).getCountOfStones());
		GameBoard gameBoard = new GameBoard(countStonesBoardPits, gameOver, winnerString);
		System.out.println(gameBoard.toString());
		return gameBoard;
	}
	
	
}
