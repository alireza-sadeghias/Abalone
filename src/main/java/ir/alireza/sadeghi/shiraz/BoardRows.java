package ir.alireza.sadeghi.shiraz;

import java.util.ArrayList;
import java.util.Hashtable;

/*
 * Creates and contains lists of rows of boards.
 * Gives insights to the board structure.
 * NOTE: Needs to be created when the board is already in place.
 */

public class BoardRows {
	public ArrayList<ArrayList<String>> horizontal = new ArrayList<>();
	public ArrayList<ArrayList<String>> topLeft = new ArrayList<>();
	public ArrayList<ArrayList<String>> topRight = new ArrayList<>();
	
	//automatically creates every row when this is called
	public BoardRows() {
		
		//horizontal rows
		for (char ch='A'; ch <= 'I'; ch++) {
			String letterCode = Character.toString(ch);
			ArrayList<String> row = new ArrayList<String>();
			for (int j = 0; j < 10; j++) {
				if (Board.hashBoard.containsKey((letterCode+j))) {
					row.add(letterCode+j);
				}
			}
			horizontal.add(row);
		}
		
		//starting from top left (going to bottom right)
		for (int j = 0; j < 10; j++) {
			ArrayList<String> row = new ArrayList<String>();
			for (char ch='A'; ch <= 'I'; ch++) {
				String letterCode = Character.toString(ch);
				if (Board.hashBoard.containsKey((letterCode+j))) {
					row.add(letterCode+j);
				}
			}
			topLeft.add(row);
		}
		
		//starting from top right (going to bottom left)
		char start = 'I';
		for (int i = 13; i >= 5; i--) {
			ArrayList<String> row = new ArrayList<String>();
			for (int j = 0; j < 9; j++) {
				String lettercode = Character.toString(start);
				int temp = i-j;
				if (Board.hashBoard.containsKey((lettercode+temp))) {
					row.add(lettercode+temp);
				}
				start = (char) (start -1);
			}
			start = 'I';
			topRight.add(row);
		}
	}
	
	
	//checks whether three marbles are in the same row
	public boolean sameRowThree(String one, String two, String three) {
		boolean sameRow = false;
		
		for (int i = 0; i < horizontal.size(); i++) {
			if (sameRow) {
				break;
			}
			if (horizontal.get(i).contains(one) && horizontal.get(i).contains(two) && horizontal.get(i).contains(three)) {
				sameRow = true;
			}
		}
		
		for (int i = 0; i < topLeft.size(); i++) {
			if (sameRow) {
				break;
			}
			if (topLeft.get(i).contains(one) && topLeft.get(i).contains(two) && topLeft.get(i).contains(three)) {
				sameRow = true;
				
			}
		}
		
		for(int i = 0; i < topRight.size(); i++) {
			if (sameRow) {
				break;
			}
			if (topRight.get(i).contains(one) && topRight.get(i).contains(two) && topRight.get(i).contains(three)) {
				sameRow = true;
			}
		}
		return sameRow;
	}
	
	
	
	
	
	//checks whether a movement is sideways or not
	public boolean sideways(String first, String second, String moveTo) {
		if (this.direction(first, second) == 1 & this.direction(first, moveTo) ==4 || this.direction(first, second) == 4 & this.direction(first, moveTo) ==1) {
			return false;
		}
		else if (this.direction(first, second) == 2 & this.direction(first, moveTo) ==5 || this.direction(first, second) == 5 & this.direction(first, moveTo) ==2) {
			return false;
		}
		else if(this.direction(first, second) == 3 & this.direction(first, moveTo) ==6 || this.direction(first, second) == 6 & this.direction(first, moveTo) ==3) {
			return false;
		}
		else if (this.direction(first, second) != this.direction(first, moveTo)){
			return true;
		}
		else
		return false;
	}
	
	//if it's sideways with two- check whether two hexagons in a certain direction are still available
	public boolean twoFree(String first, String second, String moveTo, Hashtable<String, Hexagon> board) {
		int direction = direction(first, moveTo);
		
		char letterFirst = first.charAt(0);
		char letterSecond = second.charAt(0);
		
		String letterFirstSt = first.substring(0,1);
		String letterSecondSt = second.substring(0,1);
		
		String numberFirst = first.substring(1);
		String numberSecond = second.substring(1);
		int numberOne = Integer.parseInt(numberFirst);
		int numberTwo = Integer.parseInt(numberSecond);
		
		//when the value is one bigger
		int numberOnePlus = numberOne + 1;
		int numberTwoPlus = numberTwo + 1;
		char letterOnePlus = (char) (letterFirst +1);
		char letterTwoPlus = (char) (letterSecond +1);
		String letterOnePlusSt = Character.toString(letterOnePlus);
		String letterTwoPlusSt = Character.toString(letterTwoPlus);
		
		
		//when the value is one smaller
		int numberOneMinus = numberOne - 1;
		int numberTwoMinus = numberTwo - 1;
		char letterOneMinus = (char) (letterFirst -1);
		char letterTwoMinus = (char) (letterSecond -1);
		String letterOneMinusSt = Character.toString(letterOneMinus);
		String letterTwoMinusSt = Character.toString(letterTwoMinus);
		
		
		if (direction ==1) {
			if (board.containsKey(letterFirstSt+numberOneMinus) && board.containsKey(letterSecondSt+numberTwoMinus)) {
				if(board.get(letterFirstSt+numberOneMinus).empty && board.get(letterSecondSt+numberTwoMinus).empty) {
					return true;
				}
			}
			else {
				return false;
			}
		}
		
		else if (direction ==2) {
			if (board.containsKey(letterOnePlusSt + numberOne) && board.containsKey(letterTwoPlusSt+ numberTwo)){
				if(board.get(letterOnePlusSt + numberOne).empty && board.get(letterTwoPlusSt+ numberTwo).empty) {
					return true;
				}	
			}
			else {
				return false;
			}
		}
		
		else if(direction ==3) {
			if (board.containsKey(letterOnePlusSt + numberOnePlus) && board.containsKey(letterTwoPlusSt+numberTwoPlus)) {
				if(board.get(letterOnePlusSt + numberOnePlus).empty && board.get(letterTwoPlusSt+numberTwoPlus).empty) {
					return true;
				}
			}
			else {
				return false;
			}
			
		}
		else if (direction ==4) {
			if (board.containsKey(letterFirstSt+numberOnePlus) && board.containsKey(letterSecondSt+numberTwoPlus)) {
				if(board.get(letterFirstSt+numberOnePlus).empty && board.get(letterSecondSt+numberTwoPlus).empty) {
					return true;
				}
			}
			else {
				return false;
			}
		}
		else if (direction ==5) {
			if (board.containsKey(letterOneMinusSt + numberOne) && board.containsKey(letterTwoMinusSt + numberTwo)) {
				if(board.get(letterOneMinusSt + numberOne).empty && board.get(letterTwoMinusSt + numberTwo).empty) {
					return true;
				}
			}
			else {
				return false;
			}
		}
		else if (direction ==6) {
			if (board.containsKey(letterOneMinusSt + numberOneMinus) && board.containsKey(letterTwoMinusSt + numberTwoMinus)) {
				if(board.get(letterOneMinusSt + numberOneMinus).empty && board.get(letterTwoMinusSt + numberTwoMinus).empty) {
					return true;
				}
			}
			else {
				return false;
				
			}	
		}
		return false;
	}
	
	//if it's sideways with three - check whether the three adjacent hexagons in a certain direction are still empty
	public boolean threeFree(String first, String second, String third, String moveTo, Hashtable<String, Hexagon> board) {
		int direction = direction(first, moveTo);
		char letterFirst = first.charAt(0);
		char letterSecond = second.charAt(0);
		char letterThird = third.charAt(0);
		
		String letterFirstSt = first.substring(0,1);
		String letterSecondSt = second.substring(0,1);
		String letterThirdSt = third.substring(0,1);
		
		String numberFirst = first.substring(1);
		String numberSecond = second.substring(1);
		String numberThird = third.substring(1);
		int numberOne = Integer.parseInt(numberFirst);
		int numberTwo = Integer.parseInt(numberSecond);
		int numberThree = Integer.parseInt(numberThird);
		
		//when the value is one bigger
		int numberOnePlus = numberOne + 1;
		int numberTwoPlus = numberTwo + 1;
		int numberThreePlus = numberThree + 1;
		char letterOnePlus = (char) (letterFirst +1);
		char letterTwoPlus = (char) (letterSecond +1);
		char letterThreePlus = (char) (letterThird + 1);
		String letterOnePlusSt = Character.toString(letterOnePlus);
		String letterTwoPlusSt = Character.toString(letterTwoPlus);
		String letterThreePlusSt = Character.toString(letterThreePlus);
		
		
		//when the value is one smaller
		int numberOneMinus = numberOne - 1;
		int numberTwoMinus = numberTwo - 1;
		int numberThreeMinus = numberThree -1;
		char letterOneMinus = (char) (letterFirst -1);
		char letterTwoMinus = (char) (letterSecond -1);
		char letterThreeMinus = (char) (letterThird -1);
		String letterOneMinusSt = Character.toString(letterOneMinus);
		String letterTwoMinusSt = Character.toString(letterTwoMinus);
		String letterThreeMinusSt = Character.toString(letterThreeMinus);
		
		
		if (direction ==1) {
			if(board.containsKey(letterFirstSt+numberOneMinus) && board.containsKey(letterSecondSt+numberTwoMinus) && board.containsKey(letterThirdSt + numberThreeMinus)) {
				if(board.get(letterFirstSt+numberOneMinus).empty && board.get(letterSecondSt+numberTwoMinus).empty && board.get(letterThirdSt + numberThreeMinus).empty) {
					return true;
				}	
			}
		}
		
		else if (direction ==2) {
			if(board.containsKey(letterOnePlusSt + numberOne) && board.containsKey(letterTwoPlusSt+ numberTwo) && board.containsKey(letterThreePlusSt + numberThree)) {
				if(board.get(letterOnePlusSt + numberOne).empty && board.get(letterTwoPlusSt+ numberTwo).empty && board.get(letterThreePlusSt + numberThree).empty) {
					return true;
				}	
			}
		}
		
		else if(direction ==3) {
			if(board.containsKey(letterOnePlusSt + numberOnePlus) && board.containsKey(letterTwoPlusSt+numberTwoPlus) && board.containsKey(letterThreePlusSt + numberThreePlus)) {
				if(board.get(letterOnePlusSt + numberOnePlus).empty && board.get(letterTwoPlusSt+numberTwoPlus).empty && board.get(letterThreePlusSt + numberThreePlus).empty) {
					return true;
				}	
			}
		}
		else if (direction ==4) {
			if(board.containsKey(letterFirstSt+numberOnePlus) && board.containsKey(letterSecondSt+numberTwoPlus) && board.containsKey(letterThirdSt + numberThreePlus)) {
				if(board.get(letterFirstSt+numberOnePlus).empty && board.get(letterSecondSt+numberTwoPlus).empty && board.get(letterThirdSt + numberThreePlus).empty) {
					return true;
				}
			}
		}
		else if (direction ==5) {
			if(board.containsKey(letterOneMinusSt + numberOne) && board.containsKey(letterTwoMinusSt + numberTwo) && board.containsKey(letterThreeMinusSt + numberThree)) {
				if(board.get(letterOneMinusSt + numberOne).empty && board.get(letterTwoMinusSt + numberTwo).empty && board.get(letterThreeMinusSt + numberThree).empty) {
					return true;
				}
			}
		}
		else if (direction ==6) {
			if(board.containsKey(letterOneMinusSt + numberOneMinus) && board.containsKey(letterTwoMinusSt + numberTwoMinus) && board.containsKey(letterThreeMinusSt + numberThreeMinus)) {
				if(board.get(letterOneMinusSt + numberOneMinus).empty && board.get(letterTwoMinusSt + numberTwoMinus).empty && board.get(letterThreeMinusSt + numberThreeMinus).empty) {
					return true;
				}
			}
		}
		return false;
	}
	
	//returns the number which is the direction in which two adjacent hexagons are
	public int direction(String first, String moveTo) {
		for (int i = 0; i < horizontal.size(); i++) {
			if (horizontal.get(i).contains(first) && horizontal.get(i).contains(moveTo)) {
				String numberFirst = first.substring(1);
				String numberMoveTo = moveTo.substring(1);
				
				int numberOne = Integer.parseInt(numberFirst);
				int numberTwo = Integer.parseInt(numberMoveTo);
					
				if (numberOne>numberTwo) {
					return 1;
				}
				else {
					return 4;
				}
			}
		}
			
		for (int i = 0; i < topLeft.size(); i++) {
			if (topLeft.get(i).contains(first) && topLeft.get(i).contains(moveTo)) {
					char letterFirst = first.charAt(0);
					char letterMoveTo = moveTo.charAt(0);
					
					if (letterFirst>letterMoveTo) {
						return 5;
					}
					else {
						return 2;
					}
			}
		}
			
		for(int i = 0; i < topRight.size(); i++) {
			if (topRight.get(i).contains(first) && topRight.get(i).contains(moveTo)) {
				String numberFirst = first.substring(1);
				String numberMoveTo = moveTo.substring(1);
				int numberOne = Integer.parseInt(numberFirst);
				int numberTwo = Integer.parseInt(numberMoveTo);
					
				if (numberOne>numberTwo) {
					return 6;
				}
				else {
					return 3;
				}
			}	
		}
		return 0;
	}
	
	//returns the code of an adjacent hexagon/marble in a certain direction from another code
	public String adjacentDirection(String moveTo, int direction ) {
		char letterMoveTo = moveTo.charAt(0);
		String letterMoveToSt = moveTo.substring(0,1);
		String numberMoveToSt = moveTo.substring(1);
		int numberMoveTo = Integer.parseInt(numberMoveToSt);
		
		//when the value is one bigger
		int numberMoveToPlus = numberMoveTo + 1;
		char letterMoveToPlus = (char) (letterMoveTo +1);
		String letterMoveToPlusSt = Character.toString(letterMoveToPlus);
		
		//when the value is one smaller
		int numberMoveToMinus = numberMoveTo - 1;
		char letterMoveToMinus = (char) (letterMoveTo -1);
		String letterMoveToMinusSt = Character.toString(letterMoveToMinus);
		
		if (direction ==1) {
			return letterMoveToSt+numberMoveToMinus;
		}
		if (direction ==2) {
			return letterMoveToPlusSt + numberMoveTo;
		}
		if (direction ==3) {
			return letterMoveToPlusSt + numberMoveToPlus;
		}
		if (direction ==4) {
			return letterMoveToSt+numberMoveToPlus;
		}
		if (direction ==5) {
			return letterMoveToMinusSt + numberMoveTo;
		}
		if (direction ==6) {
			return letterMoveToMinusSt + numberMoveToMinus;
		}
		return null;
	}
	
	
	//return the opposite direction of a certain direction
	public int oppositeDirection(int direction) {
		if (direction ==1) {
			direction = 4;
		}
		else if (direction ==2) {
			direction = 5;
		}
		else if (direction ==3) {
			direction = 6;
		}
		else if (direction ==4) {
			direction = 1;
		}
		else if (direction ==5) {
			direction = 2;
		}
		else if (direction ==6) {
			direction = 3;
		}
		return direction;
	}
}
