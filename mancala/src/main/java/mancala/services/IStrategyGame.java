/**
 * 
 */
package mancala.services;

/**
 * @author "Pavithra"
 * 
 * Consists of methods that can be implemented in different ways based on the game implementing it
 */
public interface IStrategyGame {

	//Perform move once a pit is clicked
	public void performMove(int chosenPit);
	
	//Declare the winner
	public void declareWinner();
	
	//Check if Game is over
	public void gameOver();
	
}
