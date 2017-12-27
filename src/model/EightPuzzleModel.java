package model;

public class EightPuzzleModel {
	
	private final int[][] goalState = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
	private int[][] currentState;
	private final int size = 3;
	private int zeroX, zeroY = size - 1;
	
	public EightPuzzleModel() {
		generateNewPuzzle();
	}
	
	public int getSize() {
		return size;
	}
	
	public int[][] getCurrentState() {
		int[][] temp = new int[size][size];
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				temp[i][j] = currentState[i][j];
			}
		}
		
		return temp;
	}
	
	public int[][] getGoalState() {
		int[][] temp = new int[size][size];
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				temp[i][j] = goalState[i][j];
			}
		}
		
		return temp;
	}
	
	public void swapTiles(int xi,  int yi,  int xj, int yj) {
		int temp = currentState[xi][yi];
		currentState[xi][yi] = currentState[xj][yj];
		currentState[xj][yj] = temp;
	}
	
	public void updateTiles(int[][] newState) {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				currentState[i][j] = newState[i][j]; 
			}
		}
	}
	
	public void updateZeroCoords(String str) {
		switch(str) {
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
	
	public String move(String str) {
		String action = "";
		int tile = Integer.parseInt(str);
		int tileX = 0;
		int tileY = 0;
		
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
	
	private boolean isSolvable() {
		return (countInversions() % 2 == 0);
	}
	
	public void resetZeroPosition() {
		zeroX = size - 1;
		zeroY = size - 1;
	}
	
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
		
		// initialize the start state with random values
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