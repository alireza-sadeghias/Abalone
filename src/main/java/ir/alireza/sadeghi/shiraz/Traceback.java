package ir.alireza.sadeghi.shiraz;

import java.util.ArrayList;
import java.util.Hashtable;

/*
 * Stores a traceback, in hashtable form.
 * The traceback is an arraylist -> with index 1 being the board at the start of the game.
 * Also stores a separate traceback for every player - to make sure they won't do the same thing twice.
 */

public class Traceback {
	private static ArrayList<Hashtable<String, Hexagon>> traceback = new ArrayList<>();
	public static ArrayList<String> oldMovePlayer1 = new ArrayList<>();
	public static ArrayList<String> oldMovePlayer2 = new ArrayList<>();
	public static ArrayList<String> oldMovePlayer3 = new ArrayList<>();
	public static int direct1 = 0;
	public static int direct2 = 0;
	public static int direct3 = 0;
 	public static int current= 1;
	public static int totalMoves = 1;
	private final int tracebacksize = 30;
	
	
	//add a hashboard to the traceback - this one is for boardstates
	public void add(Hashtable<String, Hexagon> board) {
		if (current < tracebacksize - 1) {
			current++;
		}
		else {
			current = 1;
		}
		
		if (traceback.size() < tracebacksize) {
			traceback.add(BoardMethods.copyHashBoard(board));
		}
		else {
			traceback.set(current, BoardMethods.copyHashBoard(board));
		}
		
		totalMoves++;
	}
	
	//add a move to a player's log
	public void addForPlayer(int playerNumber, String first, String second, String third, int direction) {
		if (first == null && second == null && third == null) {
			return;
		}

		if (playerNumber ==1) {
			oldMovePlayer1.clear();
			oldMovePlayer1.add(first);
			if (second != null) {
				oldMovePlayer1.add(second);
			}
			if(third != null) {
				oldMovePlayer1.add(third);
			}
			direct1 = direction;
		} else if(playerNumber ==2) {
			oldMovePlayer2.clear();
			oldMovePlayer2.add(first);
			if (second != null) {
				oldMovePlayer2.add(second);
			}
			if(third != null) {
				oldMovePlayer2.add(third);
			}
			direct2 = direction;
		} else if (playerNumber ==3){
			oldMovePlayer3.clear();
			oldMovePlayer3.add(first);
			if (second != null) {
				oldMovePlayer3.add(second);
			}
			if(third != null) {
				oldMovePlayer3.add(third);
			}
			direct3 = direction;
		}
	}
	
	//return the whole arraylist
	public ArrayList<Hashtable<String, Hexagon>> getTB() {
		return traceback;
	}
	
	// reset the traceback fully
	public void reset() {
		traceback = new ArrayList<Hashtable<String, Hexagon>>();
		oldMovePlayer1 = new ArrayList<>();
		oldMovePlayer2 = new ArrayList<>();
		oldMovePlayer3 = new ArrayList<>();
		direct1 = 0;
		direct2 = 0;
		direct3 = 0;
		current= 1;
		totalMoves = 1;
	}
}
