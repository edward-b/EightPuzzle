package model;

public class EightPuzzleModel {
	
	public static final int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
	private static int[][] currState;
	private int zeroX;
	private int zeroY;
	
	private static final int tileCount = 3; // height and width of the puzzle
	
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
	
	public void move(String str) {
		
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
		
		System.out.println("Number of inversions: " + inversions);
		
		return inversions;
	}
	
	private static boolean isSolvable() {
		return (countInversions() % 2 == 0);
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