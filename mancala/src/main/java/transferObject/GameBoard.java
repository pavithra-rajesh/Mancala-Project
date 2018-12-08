package transferObject;

import java.util.ArrayList;

/**
 * @author "Pavithra"
 *
 */
public class GameBoard {
	
	private ArrayList<Integer> boardPits;
	private boolean isGameOver;
	private String winnerString;
	
	public GameBoard(ArrayList<Integer> countStonesBoardPits, boolean gameOver, String winnerStr)
	{
		boardPits = countStonesBoardPits;
		isGameOver = gameOver;
		winnerString = winnerStr;
	}
	
	/**
	 * @return the boardPits
	 */
	public ArrayList<Integer> getBoardPits() {
		return boardPits;
	}
	/**
	 * @param boardPits the boardPits to set
	 */
	public void setBoardPits(ArrayList<Integer> boardPits) {
		this.boardPits = boardPits;
	}
	/**
	 * @return the isGameOver
	 */
	public boolean isGameOver() {
		return isGameOver;
	}
	/**
	 * @param isGameOver the isGameOver to set
	 */
	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}
	/**
	 * @return the winnerString
	 */
	public String getWinnerString() {
		return winnerString;
	}
	/**
	 * @param winnerString the winnerString to set
	 */
	public void setWinnerString(String winnerString) {
		this.winnerString = winnerString;
	}
	

}
