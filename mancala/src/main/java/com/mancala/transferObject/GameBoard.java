package com.mancala.transferObject;

import java.util.ArrayList;

/**
 * @author "Pavithra"
 *
 */
public class GameBoard {
	
	private ArrayList<Integer> boardPitsStones;
	private boolean gameOver;
	private String winnerString;
	private boolean turnPlayerA;

	public GameBoard(ArrayList<Integer> countStonesBoardPits, boolean isTurnPlayerA, boolean isGameOver, String winnerStr)
	{
		this.boardPitsStones = countStonesBoardPits;
		this.gameOver = gameOver;
		this.winnerString = winnerStr;
		this.turnPlayerA = isTurnPlayerA;
	}
	
	/**
	 * @return the boardPits
	 */
	public ArrayList<Integer> getBoardPitsStones() {
		return boardPitsStones;
	}
	/**
	 * @param boardPits the boardPits to set
	 */
	public void setBoardPitsStones(ArrayList<Integer> boardPitsStones) {
		this.boardPitsStones = boardPitsStones;
	}
	/**
	 * @return the isGameOver
	 */
	public boolean isGameOver() {
		return gameOver;
	}
	/**
	 * @param isGameOver the isGameOver to set
	 */
	public void setGameOver(boolean isGameOver) {
		this.gameOver = isGameOver;
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
	
	/**
	 * @return the isTurnPlayerA
	 */
	public boolean isTurnPlayerA() {
		return turnPlayerA;
	}

	/**
	 * @param isTurnPlayerA the isTurnPlayerA to set
	 */
	public void setTurnPlayerA(boolean isTurnPlayerA) {
		this.turnPlayerA = isTurnPlayerA;
	}
	
}
