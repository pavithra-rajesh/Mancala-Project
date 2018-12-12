/**
 * 
 */
package com.mancala.service;

import org.springframework.stereotype.Service;

import com.mancala.transferObject.GameBoard;

/**
 * @author "Pavithra"
 * 
 * Consists of methods that can be implemented in different ways based on the game implementing it
 */
@Service
public interface IStrategyGame {
	
	//Start a new game
	public void startNewGame(int initialStoneCount);
	
	//returnGameBoardObject
	public GameBoard getGameBoard();

	//Validate the selected pit
	public boolean validateSelectedPit(int selectedPit);
	
	//Perform move once a pit is selected
	public void performMove(int selectedPit);
	
	//Declare the winner
	public void declareWinner();
	
	//Check if Game is over
	public boolean isGameOver();
	
}
