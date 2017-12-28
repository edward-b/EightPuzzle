package model;

import exceptions.InvalidDirectionException;
import exceptions.InvalidTileException;
import exceptions.MismatchedArraySizeException;

/**
 * EightPuzzleModel is a an object that stores the 
 * current board state and empty tile position. It
 * can also generate new board states.
 * 
 * @author Edward B.
 */

public class EightPuzzleModel {
	
	private int size;
	private int[][] goalState;
	private int[][] currentState;
	private int zeroX;
	private int zeroY;
	
	/**
	 * EightPuzzleModel constructor that sets the dimensions
	 * of the board, sets the goal, empty tile position, and
	 * then generates a new, solvable puzzle and stores it
	 * in currentState.
	 * 
	 * @param size The width and height of the board
	 * @param goal The goal state
	 */
	
	public EightPuzzleModel(int size, int[][] goal) {
		setSize(size);
		setZeroX(size - 1);
		setZeroY(size - 1);
		setGoalState(goal);
		generateNewPuzzle();
	}
	
	
	/** 
	 * Sets the width and height of the board.
	 * 
	 * @param s The dimensions of the board
	 */
	
	private void setSize(int s) {
		assert s > 1;
		
		size = s;
	}
	
	/**
	 * Sets the first index of the empty tile position.
	 * 
	 * @param x The empty tile's x-coordinate
	 */
	
	private void setZeroX (int x) {
		assert (x >= 0) && (x < size);
		
		zeroX = x;
	}
	
	/**
	 * Sets the second index of the empty tile position.
	 * 
	 * @param x The empty tile's y-coordinate
	 */
	
	private void setZeroY (int y) {
		assert (y >= 0) && (y < size);
		
		zeroY = y;
	}
	
	/**
	 * Sets the goal state of the board.
	 * 
	 * @param goal The goal state
	 */
	
	private void setGoalState(int[][] goal) {
		assert goal != null;
		
		goalState = new int[size][size];
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				goalState[i][j] = goal[i][j];
			}
		}
	}
	
	/**
	 * Updates the current state of the board
	 * 
	 * @param curr The new board state
	 * 
	 * @throws NullPointerException Thrown when curr is null
	 * @throws MismatchedArraySizeException Thrown when curr and currentState have different dimensions
	 */
	
	public void setCurrentState(int[][] curr) throws NullPointerException, MismatchedArraySizeException{
		if(curr == null) {
			throw new NullPointerException("Uninitialized state");
		}
		
		if((curr.length != size) || (curr[0].length != size)) {
			throw new MismatchedArraySizeException("Input array and board do"
					+ "not have the same dimensions");
		}
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				currentState[i][j] = curr[i][j]; 
			}
		}
	}
	
	/**
	 * Returns the width and height of the board.
	 * 
	 * @return int The dimensions of the board
	 */
	
	public int getSize() {
		return size;
	}
	
	/**
	 * Returns a copy of the board's current state
	 * 
	 * @return int[][] The current board state
	 */
	
	public int[][] getCurrentState() {
		int[][] temp = new int[size][size];
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				temp[i][j] = currentState[i][j];
			}
		}
		
		return temp;
	}
	
	/**
	 * Returns a copy of the board's goal state
	 * 
	 * @return int[][] The goal state
	 */
	
	public int[][] getGoalState() {
		int[][] temp = new int[size][size];
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				temp[i][j] = goalState[i][j];
			}
		}
		
		return temp;
	}
	
	/**
	 * Swaps the tiles located at (x1, y1) and (x2, y2)
	 * 
	 * @param x1 The first tile's x-coordinate
	 * @param y1 The first tile's y-coordinate
	 * @param x2 The second tile's x-coordinate
	 * @param y2 The second tile's y-coordinate
	 */
	
	private void swapTiles(int x1,  int y1,  int x2, int y2) {
		assert (x1 >= 0) && (x1 < size);
		assert (x2 >= 0) && (x2 < size);
		assert (y1 >= 0) && (y1 < size);
		assert (y2 >= 0) && (y2 < size);
		
		int temp = currentState[x1][y1];
		currentState[x1][y1] = currentState[x2][y2];
		currentState[x2][y2] = temp;
	}
	
	/**
	 * Updates the current empty tile coordinates based on the action
	 * the empty tile moved.
	 * 
	 * @param str The direction the empty tile moved
	 * @throws InvalidDirectionException
	 */
	
	public void updateZeroCoords(String str)  throws InvalidDirectionException{
		switch(str) {
			default:
				throw new InvalidDirectionException("Direction must be"
						+ " \"UP\", \"DOWN\", \"LEFT\", or \"RIGHT\"");
			case "UP":
				zeroX--;
				break;
			case "RIGHT":
				zeroY++;
				break;
			case "DOWN":
				zeroX++;
				break;
			case "LEFT":
				zeroY--;
				break;
		}
	}
	
	/**
	 * Moves the tile specified and the empty tile.
	 * 
	 * @param str The tile to be moved
	 * 
	 * @return String The direction the empty tile moved
	 * 
	 * @throws InvalidTileException Thrown when the tile is out of range of the board
	 */
	
	public String move(String str) throws InvalidTileException{
		int tile = Integer.parseInt(str);
		if((tile < 0) || (tile > (size * size - 1))) {
			throw new InvalidTileException("Tiles must be between 0-" + (size * size - 1) + ", inclusive");
		}
		int tileX = 0;
		int tileY = 0;
		String action = "";
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(currentState[i][j] == tile) {
					tileX = i;
					tileY = j;
					break;
				}
			}
		}
		
		if((Math.abs(zeroX - tileX) + Math.abs(zeroY - tileY)) == 1) {
			swapTiles(zeroX, zeroY, tileX, tileY);
			
			int xDiff = tileX - zeroX;
			int yDiff = tileY - zeroY;
			
			if((xDiff < 0) && (yDiff == 0)) {
				action = "UP";
			}
			else if ((xDiff == 0) && (yDiff > 0)) {
				action = "RIGHT";
			}
			else if ((xDiff > 0) && (yDiff == 0)) {
				action = "DOWN";
			}
			else if ((xDiff == 0) && (yDiff < 0)) {
				action = "LEFT";
			}
			
			zeroX = tileX;
			zeroY = tileY;
		}
		
		return action;
	}
	
	/**
	 * Randomly scrambles tiles using Fischer-Yates algorithm
	 * 
	 * @return Nothing
	 */
	
	private void initTiles() {
		int i = size * size - 1;
		
		while(i > 0) {
			int j = (int) Math.floor(Math.random() * i);
			
			int xi = i % size;
			int yi = (int) Math.floor(i / size);
			int xj = j % size;
			int yj = (int) Math.floor(j / size);
			
			swapTiles(xi, yi, xj, yj);
			
			i--;
		}
	}
	
	/**
	 * Counts the number of inversions in the current board state
	 * 
	 * @return int The sum of every tile's inversions
	 */
	
	private int countInversions()
	{
		int inversions = 0;
		int tileNum = size * size;
		int[] temp = new int[tileNum];
		int count = 0;
		
		// converts 2d array to 1d array for easier inversion counting code
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				temp[count] = currentState[i][j];
				count++;
			}
		}
		
		
		for (int i = 0; i < tileNum - 1; i++) {
			 for (int j = i + 1; j < tileNum; j++) {
				 if ((temp[i] != 0) && (temp[j] != 0) &&  (temp[i] > temp[j])) {
					 inversions++;
				 }
			 }
		}
		
		return inversions;
	}
	
	/**
	 * Returns whether or not the current board state is solvable.
	 * 
	 * @return boolean Whether the board can be solved or not
	 */
	
	private boolean isSolvable() {
		return (countInversions() % 2 == 0);
	}
	
	/**
	 * Resets the empty tile's coordinates to their position on the goal state
	 * 
	 * @return Nothing
	 */
	
	public void resetZeroPosition() {
		zeroX = size - 1;
		zeroY = size - 1;
	}
	
	/**
	 * Checks to see if the current state is solved or not.
	 * 
	 * @return boolean Whether the current board is at the goal
	 */
	
	public boolean isSolved() {
		boolean solved = true;
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(currentState[i][j] != goalState[i][j]) {
					solved = false;
				}
			}
		}
		
		return solved;
	}
	
	/**
	 * Generates a new puzzle by randomly scrambling from
	 * the goal state, then correcting for board states
	 * unsolvable by their number of inversions. Also
	 * updates the empty tile's coordinates.
	 * 
	 * @return Nothing
	 */
	
	public void generateNewPuzzle() {
		currentState = new int[size][size];
		zeroX = 0;
		zeroY = 0;
		
		// reset current state to goal state
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				currentState[i][j] = goalState[i][j];
			}
		}
		
		initTiles();
		
		// checks to see if the board generated is solvable
		// if not, then two tiles are swapped in order to
		// change the amount of inversions to make it solvable
		if(!isSolvable()) {
			if((currentState[0][0] != 0) && (currentState[0][1] != 0)) {
				swapTiles(0, 0, 0, 1);
			}
			else {
				swapTiles(size - 2, size - 1, size - 1, size - 1);
			}
		}
		
		// sets the zero coordinates
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(currentState[i][j] == 0) {
					zeroX = i;
					zeroY = j;
					break;
				}
			}
		}
	}
}