public class EightPuzzle {
	
	public static final int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
	private static int[][] start = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
	
	private static int tileCount = 3; // height and width of the puzzle
	
	
	/*
	 * Main loop of the search. Takes user input for difficulty
	 * then takes user input for search type. Continues taking
	 * input until "quit" is typed at a prompt to exit.
	 */
	
	public static void main (String args[]) {
		EightPuzzleNode e = null;
		
		System.out.println("Eight Puzzle Solver by Edward Ryan Bote");
		
		// initialize the start state with random values
		initTiles();
		
		e = new EightPuzzleNode(tileCount, start, null, 0, "START");
		
		EightPuzzleSearch.aStarSearch(e);
	}
	
	public static void initTiles() {
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
	
	public static void swapTiles(int xi,  int yi,  int xj, int yj) {
		int temp = start[xi][yi];
		start[xi][yi] = start[xj][yj];
		start[xj][yj] = temp;
	}
	
	public static int countInversions(int i, int j) {
		int inversions = 0;
		return inversions;
	}
	
	public static int sumInversions()
	{
		int inversions = 0;
		
		for(int i = 0; i < tileCount; i++) {
			for(int j = 0; j < tileCount; j++) {
				inversions += countInversions(i, j);
			}
		}
		
		return inversions;
	}
}