package mancala.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mancala.services.MancalaGame;

@RestController
public class GameController {

	@Autowired
	private MancalaGame mancalaGame;
	
	@RequestMapping("/hello")
	public String sayHi() {
		return "Hi";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "3")
	public void performMove(@RequestBody int pitClicked) {
		mancalaGame.performMove(pitClicked);
	}
}
