package mancala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mancala.service.IStrategyGame;
import transferObject.GameBoard;

@RestController
public class GameController {

	@Autowired
	private IStrategyGame mancalaGame;
	
	@Autowired
	private GameBoard gameBoard;
	
	@RequestMapping("/hello")
	public String sayHi() {
		return "Hi";
	}
	
	@MessageMapping("/startGame")
	@SendTo("/topic/game")
	public GameBoard startGame() {
		mancalaGame.startNewGame();
		gameBoard = mancalaGame.getGameBoard();
		return gameBoard;
	}
	
	@MessageMapping("/performMove")
	@SendTo("/topic/game")
	public GameBoard performMove(@RequestBody int pitClicked) {
		
		mancalaGame.performMove(pitClicked);
		
		if(mancalaGame.isGameOver())
			mancalaGame.declareWinner();
		
		GameBoard gameBoard = mancalaGame.getGameBoard();
		return gameBoard;
	}
}
