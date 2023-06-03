package ir.alireza.sadeghi.shiraz.AI;

import ir.alireza.sadeghi.shiraz.*;

import java.util.Hashtable;

public class GameState {
	//all the information needed to store the move made (from the first one)(we need to store first, second and third and moveTo in case the move needs to be done again):
	//use this to store every valid gamestate
	
	//store which move needs to be done to get to this state - such as which player's turn it is and which move they perform
	//if there is no second selected, it means that it is null
	public String first;
	public String second;
	public String third;
	public String moveTo;
	private Move move = GameData.move;
	public int turn;
	public double evaluatedValue;
	public int evaluateFrom =0;
	
	
	public boolean valid;
	//needed for the evaluation function
	public int point1;
	public int point2;
	//optional:
	public int point3;
	public boolean terminal;
	public int winner = 0;
	
	public Hashtable<String, Hexagon> boardState;
	
	GameState oldGameState;
	
	//rootNode
	public GameState(Hashtable<String, Hexagon> state, int turn) {
		this.boardState = BoardMethods.copyHashBoard(state);
		this.point1 = move.point;
		this.point2 = move.point2;
		this.point3 = move.point3;
		//last one who moved
		this.turn = turn;
		this.evaluateFrom = move.playersTurn;  //AddNodes.changePlayer(turn);
	}
	
	public GameState(String first, String second, String third, String moveTo, GameState old, boolean evaluate) {
		//needed if we want a more extended tree
		this.turn = GameMethods.changePlayer(old.turn);
		int save = GameData.move.playersTurn;
		this.evaluateFrom = old.evaluateFrom;
		GameData.move.playersTurn = this.turn;
		GameData.move.adding = true;
		Move.ai = true;
		
		this.first = first;
		this.second = second;
		this.third = third;
		this.moveTo = moveTo;
		
		
			//make a deep copy of the current board
			this.boardState = BoardMethods.copyHashBoard(old.boardState);
		
			if (first != null) {
				move.select(first, boardState);
				if (second != null) {
						move.select(second, boardState);
						if (third != null) {
							move.select(third, boardState);
						} else {
						move.select(first, boardState);
					}
				} else {
					move.select(first, boardState);
				}
			}
			
			move.select(moveTo, boardState);
			move.resetMove();
			
			valid = !BoardMethods.compareHashtables(boardState, old.boardState);
				
			//scores old
			point1 = old.point1;
			point2 = old.point2;
			point3 = old.point3;
		
			if (GameData.move.pushed) {
				if (turn == 1) {
					point1++;
				} else if(turn ==2) {
					point2++;
				
				} else if (turn ==3) {
					point3++;
				}
			}
			
			if (valid) {
				valid = BoardMethods.repetitionChecker(boardState);
			}
			
		if (point1==6 ||point2 == 6 || point3==6) {
			terminal = true;
			if(point1 ==6) {
				winner = 1;
			} else if(point2 ==6) {
				winner =2;
			} else if (point3 ==6) {
				winner = 3;
			}
		} else {
			terminal = false;
		}
		oldGameState = old;
		
		//set the turn back to the one that was actually needed
		move.pushed = false;
		move.playersTurn = save;
		move.adding = false;
		Move.ai = false;

		EvaluationFunction eval= new EvaluationFunction(this);
		evaluatedValue=eval.evaluate();
	}
}