package com.mancala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mancala.service.IStrategyGame;
import com.mancala.transferObject.GameBoard;

@RestController
@Controller
public class GameController {

	@Autowired
	private IStrategyGame mancalaGame;
	
	@Autowired
	private GameBoard gameBoard;
	
	@RequestMapping("/error")
	public String getError() {
		return "Error";
	}
	
	@MessageMapping(value="/startGame")
	@SendTo("/topic/game")
	public GameBoard startGame() {
		System.out.println("Hi");
		mancalaGame.startNewGame();
		gameBoard = mancalaGame.getGameBoard();
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
