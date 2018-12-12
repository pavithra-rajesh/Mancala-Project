package com.mancala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.mancala.service.IStrategyGame;
import com.mancala.transferObject.GameBoard;

@Controller
public class GameController {

	@Autowired
	private IStrategyGame mancalaGame;
	
	@MessageMapping(value="/startGame")
	@SendTo("/topic/game")
	public GameBoard startGame(@RequestBody int initialStoneCount) {
		mancalaGame.startNewGame(initialStoneCount);
		GameBoard gameBoard = mancalaGame.getGameBoard();
		return gameBoard;
	}
	
	@MessageMapping(value="/performMove")
	@SendTo("/topic/game")
	public GameBoard performMove(@RequestBody int pitClicked) {
		
		mancalaGame.performMove(pitClicked);
		
		if(mancalaGame.isGameOver())
			mancalaGame.declareWinner();
		
		GameBoard gameBoard = mancalaGame.getGameBoard();
		return gameBoard;
	}
}
