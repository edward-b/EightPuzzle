package model;
import java.util.ArrayList;

public class EightPuzzleNode {
	private EightPuzzleNode parent;		// this node's parent node, or previous state
	private int[][] currentState;		// this node's state
	private int depth;					// number of nodes away from the root
	private int misplacedTiles;			// number of misplaced tiles
	private int manhattanDistance;		// Manhattan distance
	private int cost;					// cost to get to this state from the previous state
	private int size;					// height and width of the board state
	private String action;				// direction the 0 tile slid to get to the current state
	
	/*
	 * Constructor. Takes size of board, sets currentState,
	 * sets goalState, sets parent node, sets cost of node,
	 * and sets what action was taken to result in this node.
	 * Also calculates and stores number of misplaced tiles,
	 * Manhattan distance, and depth of the node.
	 */
	
	public EightPuzzleNode(int size, int[][] current, EightPuzzleNode p, int cost, String action) {
		this.size = size;
		this.action = action;
		this.cost = cost;
		
		currentState = new int[size][size];
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				currentState[i][j] = current[i][j];
			}
		}
		
		misplacedTiles = setMisplacedTiles();
		manhattanDistance = setManhattanDistance();
		
		if (p == null) {
			parent = null;
			depth = 0;
		}
		else {
			parent = p;
			depth = p.getDepth() + 1;
		}
	}
	
	/*
	 * Sets number of misplaced tiles.
	 */
	
	private int setMisplacedTiles() {
		int count = 0;
		
		for(int i = 0; i < size; i++) {		// iterates through entire currentState and
			for(int j = 0; j < size; j++) {	// compares with goalState for each tile
				if(currentState[i][j] != 0) {
					if(currentState[i][j] != EightPuzzleModel.goal[i][j]) {
						count++;
					}
				}
			}
		}
		
		return count;
	}
	
	/*
	 * Sets Manhattan distance
	 */
	
	private int setManhattanDistance() {
		int sum = 0;
		
		for(int i = 0; i < size; i++) {			// iterates through entire currentState and
			for(int j = 0; j < size; j++) {		// computes absolute value of distance needed to
				if(currentState[i][j] != 0) {	// travel vertically and horizontally to get to the 
					if(currentState[i][j] != EightPuzzleModel.goal[i][j]) {	// goal position for each tile
						int[] goalCoordinates = getCoordinates(currentState[i][j], EightPuzzleModel.goal);
						sum += (Math.abs(goalCoordinates[0] - i) + Math.abs(goalCoordinates[1] - j));
					}
				}
			}
		}
		
		return sum;
	}
	
	/*
	 * Returns the coordinates of the tile in currentState
	 * specified by target as an array of two numbers.
	 */
	
	public int[] getCoordinates(int target, int[][]state) {
		int[] coordinates = new int[2];
		
		for(int i = 0; i < size; i++) { //i
			for(int j = 0; j < size; j++) {
				if(currentState[i][j] == target) {
					coordinates[0] = i;
					coordinates[1] = j;
				}
			}
		}
		
		return coordinates;
	}
	
	/*
	 * Successor generation method.
	 * Returns an ArrayList of children nodes, each with an action
	 * associated with them and specifying this node to be their
	 * parent nodes.
	 */
	
	public ArrayList<EightPuzzleNode> generateSuccessors() {
		ArrayList<EightPuzzleNode> successors = new ArrayList<EightPuzzleNode>();
		int[] coordinates = getCoordinates(0, currentState);
		
		if((coordinates[0] - 1) >= 0) { // swap 0 with tile above
			swap(coordinates[0], coordinates[1], coordinates[0] - 1, coordinates[1], successors, "UP");
		}
		if((coordinates[1] + 1) < size) { // swap 0 with tile to the right
			swap(coordinates[0], coordinates[1], coordinates[0], coordinates[1] + 1, successors, "RIGHT");
		}
		if((coordinates[0] + 1) < size) { // swap 0 with tile below
			swap(coordinates[0], coordinates[1], coordinates[0] + 1, coordinates[1], successors, "DOWN");
		}
		if((coordinates[1] - 1) >= 0) { // swap 0 with tile to the left
			swap(coordinates[0], coordinates[1], coordinates[0], coordinates[1] - 1, successors, "LEFT");
		}
		
		return successors;
	}
	
	/*
	 * Creates a copy of currentState where the tiles at (x1, y1)
	 * and (x2, y2) are swapped, creates a new EightPuzzleNode
	 * of the copy as a child of this node, and adds it to storage.
	 */
	
	private void swap(int x1, int y1, int x2, int y2, ArrayList<EightPuzzleNode> storage, String s) {
		int[][] copy = new int[size][size];
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				copy[i][j] = currentState[i][j];
			}
		}
		
		int temp = copy[x2][y2];
		copy[x2][y2] = copy[x1][y1];
		copy[x1][y1] = temp;
		
		EightPuzzleNode successor = new EightPuzzleNode(size, copy, this, 1, s);
		
		if(parent != null) {
			if(!successor.equals(parent)) {
				storage.add(successor);
			}
		}
		else {
			storage.add(successor);
		}
	}
	
	/* 
	 * Equals method. Compares values of tiles in
	 * currentState to other's currentState. If all
	 * are equal, return true; else return false.
	 */
	
	@Override
	public boolean equals(Object other) {
		if(other instanceof EightPuzzleNode) {
			EightPuzzleNode o = (EightPuzzleNode) other;
			int[][] otherState = o.getCurrentState();
			
			for(int i = 0; i < size; i++) {
				for(int j = 0; j < size; j++) {
					if(currentState[i][j] != otherState[i][j]) {
						return false;
					}
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	/* 
	 * HashCode method. Creates a hash by combining
	 * this node's currentState string representation
	 * with the path cost of this node.
	 */
	
	@Override
	public int hashCode() {
		int hash = 7;
		int numberString = Integer.parseInt(generateNumberString());
        hash = 17 * hash + numberString + pathCost();
        return hash;
	}
	
	/*
	 * Returns whether or not currentState and
	 * goalState are equal.
	 */
	
	public boolean isGoal() {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(currentState[i][j] != EightPuzzleModel.goal[i][j]) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/*
	 * Returns the total cost of the path from
	 * the root to this node.
	 */
	
	public int pathCost() {
		int count = 0;
		EightPuzzleNode currNode = this;
		
		while (currNode != null) {			// adds cost of nodes till
			count += currNode.getCost();	// parent is null
			currNode = currNode.getParent();
		}
		
		return count;
	}
	
	/*
	 * Generates string representation of currentState.
	 * For use in storing visited nodes during search as
	 * opposed to storing the whole node.
	 */
	
	public String generateNumberString() {
		StringBuilder sb = new StringBuilder(9);
		
		for(int i = 0; i < size; i++) {		// appends all tile values in numerical
			for(int j = 0; j < size; j++) {	// order to the end of the string
				sb.append(currentState[i][j]);
			}
		}
		
		return sb.toString();
	}
	
	/*
	 * Get method for currentState
	 */
	
	public int[][] getCurrentState() {
		return currentState;
	}
	
	/*
	 * Get method for depth
	 */
	
	public int getDepth() {
		return depth;
	}
	
	/*
	 * Get method for misplacedTiles
	 */
	
	public int getMisplacedTiles() {
		return misplacedTiles;
	}
	
	/*
	 * Get method for manhattanDistance
	 */
	
	public int getManhattanDistance() {
		return manhattanDistance;
	}
	
	/*
	 * Get method for parent
	 */
	
	public EightPuzzleNode getParent() {
		return parent;
	}
	
	/*
	 * Get method for cost
	 */
	
	public int getCost() {
		return cost;
	}
	
	/*
	 * Get method for action
	 */
	
	public String getAction() {
		return action;
	}
	
	/*
	 * Prints currentState as a 3x3 grid 
	 */
	
	public void printNode() {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				System.out.print(currentState[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
}