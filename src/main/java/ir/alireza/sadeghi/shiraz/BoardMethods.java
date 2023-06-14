package ir.alireza.sadeghi.shiraz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Hashtable;

/*
 * Has methods which with you can:
 * -clone a hash board
 * -compare two boards
 * -get the marbles in one board from a certain player
 */

public class BoardMethods {
	public static ArrayList<String> hash = Board.hash;
	private final static Logger logger = LogManager.getLogger(BoardMethods.class);
	//copies a whole hash board - depends on the one you get as input
	public static Hashtable<String, Hexagon> copyHashBoard(Hashtable<String, Hexagon> hex){
		logger.traceEntry();
		logger.trace("hash size " + hash.size());
		Hashtable<String, Hexagon> newBoard = new Hashtable();
		for(int i = 0; hash.size() > i; i++) {
			newBoard.put(hash.get(i), hex.get(hash.get(i)).deepClone());
		}
		return newBoard;
	}
	
	//compares two hashTables, if they contain the same marbles (from the same player), at the same spot, then it returns true
	public static boolean compareHashTables(Hashtable<String, Hexagon> one, Hashtable<String, Hexagon> two) {
		for(int i = 0; i < hash.size(); i++) {
			if(!one.get(hash.get(i)).empty && !two.get(hash.get(i)).empty) {
				if (one.get(hash.get(i)).marble.playerNumber != two.get(hash.get(i)).marble.playerNumber) {
					return false;
				}
			}
			else if((one.get(hash.get(i)).empty && !two.get(hash.get(i)).empty) || (!one.get(hash.get(i)).empty && two.get(hash.get(i)).empty)) {
				return false;
			}
		}
		return true;
	}
	
// creates and returns an array list with all the marbles from a certain player
//	public static ArrayList<String> getMarblesPlayer(Hashtable<String, Hexagon> board, int playerNumber){
//		ArrayList<String> marbles = new ArrayList<>();
//		for (int i = 0; i < hash.size(); i++) {
//			if (!board.get(hash.get(i)).empty) {
//				if (board.get(hash.get(i)).marble.playerNumber == playerNumber) {
//					marbles.add(hash.get(i));
//				}
//			}
//		}
//		return marbles;
//	}
	
	//repetition checker - return false if it is a repetition
	public static boolean repetitionChecker(Hashtable<String, Hexagon> current) {
		ArrayList<Hashtable<String, Hexagon>> tb = GameData.tb.getTB();
		for (int i = 0; i < tb.size(); i++) {
			if (compareHashTables(tb.get(i), current)) {
				return false;
			}
		}
		return true;
	}
	
	//repetition checker - return false if it is a repetition
	public static boolean moveRepetitionChecker(String first, String second, String third, int playerNumber, int direction) {
		ArrayList<String> check = null;
		int olddirection = 0;
		if(playerNumber ==1) {
			check = GameData.tb.oldMovePlayer1;
			olddirection = GameData.tb.direct1;
		}
		else if(playerNumber ==2) {
			check = GameData.tb.oldMovePlayer2;
			olddirection = GameData.tb.direct2;
		}
		else {
			check = GameData.tb.oldMovePlayer3;
			olddirection = GameData.tb.direct3;
		}

		if (first != null && second == null && third == null) {
			if (check.size() == 1) {
				if (check.get(0) == first && GameData.rows.oppositeDirection(olddirection) == direction) {
					return false;
				}
			}
		}
		
		if (first != null && second != null && third == null) {
			if (check.size()== 2) {
				if ((check.get(0) == second && check.get(1) == first || check.get(0) == first && check.get(1) == second) && direction == GameData.rows.oppositeDirection(olddirection)) {
					return false;
				}
			}
			
		}
		if (first != null && second != null && third != null) {
			if(check.size() == 3) {
				if (check.contains(first) && check.contains(second) && check.contains(third) && direction == GameData.rows.oppositeDirection(olddirection)) {
					return false;
				}
			}
		}
		return true;
	}
}
