package model;

public class EightPuzzleModel {
	
	public static final int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
	private static int[][] currState;
	private static final int tileCount = 3; // height and width of the puzzle
	private int zeroX, zeroY = tileCount - 1;
	
	public EightPuzzleModel() {
		generateNewPuzzle();
	}
	
	public int getTileCount() {
		return tileCount;
	}
	
	public int[][] getCurrState() {
		return currState;
	}
	
	public static void swapTiles(int xi,  int yi,  int xj, int yj) {
		int temp = currState[xi][yi];
		currState[xi][yi] = currState[xj][yj];
		currState[xj][yj] = temp;
	}
	
	public void updateTiles(int[][] newState) {
		for(int i = 0; i < tileCount; i++) {
			for(int j = 0; j < tileCount; j++) {
				currState[i][j] = newState[i][j]; 
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
	
	public void move(String str) {
		int tile = Integer.parseInt(str);
		int tileX = 0;
		int tileY = 0;
		
		for(int i = 0; i < tileCount; i++) {
			for(int j = 0; j < tileCount; j++) {
				if(currState[i][j] == tile) {
					tileX = i;
					tileY = j;
					break;
				}
			}
		}
		
		if((Math.abs(zeroX - tileX) + Math.abs(zeroY - tileY)) == 1) {
			swapTiles(zeroX, zeroY, tileX, tileY);
			zeroX = tileX;
			zeroY = tileY;
		}
		
	}
	
	private static void initTiles() {
		int i = tileCount * tileCount - 1;
		
		while(i > 0) {
			int j = (int) Math.floor(Math.random() * i);
			
			int xi = i % tileCount;
			int yi = (int) Math.floor(i / tileCount);
			int xj = j % tileCount;
			int yj = (int) Math.floor(j / tileCount);
			
			swapTiles(xi, yi, xj, yj);
			
			i--;
		}
	}
	
	private static int countInversions()
	{
		int inversions = 0;
		int tileNum = tileCount * tileCount;
		int[] temp = new int[tileNum];
		int count = 0;
		
		// converts 2d array to 1d array for easier inversion counting code
		for(int i = 0; i < tileCount; i++) {
			for(int j = 0; j < tileCount; j++) {
				temp[count] = currState[i][j];
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
	
	private static boolean isSolvable() {
		return (countInversions() % 2 == 0);
	}
	
	public void resetZeroPosition() {
		zeroX = tileCount - 1;
		zeroY = tileCount - 1;
	}
	
	public void generateNewPuzzle() {
		currState = new int[tileCount][tileCount];
		zeroX = 0;
		zeroY = 0;
		
		// reset current state to goal state
		for(int i = 0; i < tileCount; i++) {
			for(int j = 0; j < tileCount; j++) {
				currState[i][j] = goal[i][j];
			}
		}
		
		// initialize the start state with random values
		initTiles();
				
		if(!isSolvable()) {
			if((currState[0][0] != 0) && (currState[0][1] != 0)) {
				swapTiles(0, 0, 0, 1);
			}
			else {
				swapTiles(tileCount - 2, tileCount - 1, tileCount - 1, tileCount - 1);
			}
		}
		
		for(int i = 0; i < tileCount; i++) {
			for(int j = 0; j < tileCount; j++) {
				if(currState[i][j] == 0) {
					zeroX = i;
					zeroY = j;
					break;
				}
			}
		}
	}
}