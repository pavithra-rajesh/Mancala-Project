/**
 * 
 */
package com.mancala.test.service;

import org.junit.After;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.mancala.model.BoardPit;
import com.mancala.service.MancalaGameService;
import com.mancala.test.AbstractTest;
import com.mancala.transferObject.GameBoard;

/**
 * @author "Pavithra"
 *
 */
/**
 * @author "Pavithra"
 *
 */
public class MancalaServiceTest extends AbstractTest {
	
	private final Integer INITIAL_STONES_COUNT = 6;
	private final Integer SELECTED_PIT_A = 0;
	private final Integer SELECTED_PIT_B = 8;
		
	@Autowired
	private MancalaGameService mancalaGameServiceTest;

	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
	}
	
	@After 
	public void tearDown() {
		//clean up after each test method
	}
	
	
	/**
	 * Test the startNewGame method.
	 * 
	 */
	@Test
	public void testStartNewGame() {
		
		mancalaGameServiceTest.startNewGame(INITIAL_STONES_COUNT);
		
		//Verify if board pit 6 and board pit 13 are set as Mancalas
		LinkedList<BoardPit> boardPitsTest = mancalaGameServiceTest.getBoardPits();
		
		//Verify if there are 14 board pits on board.
		assertEquals("Not enough board pits on board ", 14, boardPitsTest.size());
		
		//Verify if board pits 6 and 13 are set as Mancalas
		assertEquals(true, boardPitsTest.get(6).IsPitMancala);
		assertEquals(true, boardPitsTest.get(13).IsPitMancala);
		assertEquals(true, mancalaGameServiceTest.isPlayerA());
	}
	
	/**
	 * Test the getGameBoard method.
	 */
	@Test
	public void testGetGameBoard() {
		
		mancalaGameServiceTest.startNewGame(INITIAL_STONES_COUNT);

		//Verify if each board pit created on the game board contains INITIAL_STONES_COUNT 
		GameBoard gameBoardTest = mancalaGameServiceTest.getGameBoard();
		assertNotNull(gameBoardTest);
		
		ArrayList<Integer> boardPitsStones = gameBoardTest.getBoardPitsStones(); 
		
		//Verify Player A's board pits
		for(int i = 0; i < 6; i++) {
			assertEquals("Player A's boardPit(s) have incorrect initial stones", INITIAL_STONES_COUNT, boardPitsStones.get(i));
		}
		
		//Verify Player B's board pits
		for (int i = 7; i < 13; i++) {
			assertEquals("Player B's boardPit(s) have incorrect initial stones", INITIAL_STONES_COUNT, boardPitsStones.get(i));
		}
		
		//Verify if both player's mancalas are empty at start
		assertEquals("Player A's mancala is not empty", Integer.valueOf(0), boardPitsStones.get(6));
		assertEquals("Player B's mancala is not empty", Integer.valueOf(0), boardPitsStones.get(13));
	}
	
	@Test
	public void testValidateSelectedPit()
	{
		mancalaGameServiceTest.startNewGame(2);

		//perform a move to empty one of the pits of Player A
		mancalaGameServiceTest.performMove(SELECTED_PIT_A);
		mancalaGameServiceTest.setPlayerA(true);
		
		//Select the same pit and verify if empty pit validation works
		boolean isValid = mancalaGameServiceTest.validateSelectedPit(SELECTED_PIT_A);
		assertEquals("Error in validate pit - Pit is not empty", false, isValid);
		
		//Verify if belongs to player validation works
		isValid = mancalaGameServiceTest.validateSelectedPit(8);
		assertEquals("Error in validate pit - belongs to player", false, isValid);
		
		//Verify if pit is not mancala validation works
		isValid = mancalaGameServiceTest.validateSelectedPit(6);
		assertEquals("Error in validate pit - is Mancala", false, isValid);
	}
	
	@Test
	public void testPerformMoveA() {
		
		mancalaGameServiceTest.startNewGame(INITIAL_STONES_COUNT);

		mancalaGameServiceTest.performMove(SELECTED_PIT_A);
		
		GameBoard gameBoardTest = mancalaGameServiceTest.getGameBoard();
		ArrayList<Integer> boardPitsStones = gameBoardTest.getBoardPitsStones(); 
		
		//Player A's pit has one extra stone after perform move
		for (int i = SELECTED_PIT_A + 1 ; i <= INITIAL_STONES_COUNT && i < 6; i++)
			assertEquals("Incorrect stones in player A's pit",Integer.valueOf(INITIAL_STONES_COUNT + 1), boardPitsStones.get(i) );
	}
	
	@Test
	public void testPerformMoveB() {
		mancalaGameServiceTest.startNewGame(INITIAL_STONES_COUNT);

		mancalaGameServiceTest.setPlayerA(false);
		mancalaGameServiceTest.performMove(SELECTED_PIT_B);
		
		GameBoard gameBoardMock = mancalaGameServiceTest.getGameBoard();
		ArrayList<Integer> boardPitsStones = gameBoardMock.getBoardPitsStones(); 
		
		//Player B's pit has one extra stone after perform move
		for (int i = SELECTED_PIT_B + 1 ; i <= INITIAL_STONES_COUNT && i < 13; i++)
			assertEquals("Incorrect stones in player B's pit",Integer.valueOf(INITIAL_STONES_COUNT + 1), boardPitsStones.get(i) );	
	}
	
	@Test
	public void testPitBelongsToPlayer() {
		mancalaGameServiceTest.startNewGame(INITIAL_STONES_COUNT);
		//Verify if pit belongs to player logic works
		assertEquals(true, mancalaGameServiceTest.isPlayerA());
		boolean belongsToPlayer = mancalaGameServiceTest.pitBelongsToPlayer(SELECTED_PIT_B);
		assertEquals("Error in pit belongs to player logic", false, belongsToPlayer);
		mancalaGameServiceTest.performMove(SELECTED_PIT_A);
		belongsToPlayer = mancalaGameServiceTest.pitBelongsToPlayer(SELECTED_PIT_A);
		assertEquals("Error in pit belongs to player logic", true, belongsToPlayer);
	}
	
	@Test
	public void testDeclarewinner() {
		mancalaGameServiceTest.startNewGame(INITIAL_STONES_COUNT);
		
		//Verify if declare winner sets the winner string as a tie
		mancalaGameServiceTest.declareWinner();
		GameBoard gameBoardTest = mancalaGameServiceTest.getGameBoard();
		assertEquals("Error in declareWinner - Tie logic", "It is a tie!", gameBoardTest.getWinnerString());
		
		//Perform a move and now verify if player A is winner works
		mancalaGameServiceTest.performMove(SELECTED_PIT_A);
		mancalaGameServiceTest.declareWinner();
		gameBoardTest = mancalaGameServiceTest.getGameBoard();
		assertEquals("Error in declareWinner - winner logic", "Player A is the winner", gameBoardTest.getWinnerString());
		
		
	}
}
