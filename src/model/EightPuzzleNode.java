package model;
import java.util.ArrayList;

/**
 * EightPuzzleNode is an object used to store moves in a solution
 * to an 8-puzzle, usually used by EightPuzzleSearch. 
 * @author Edward B.
 */
public class EightPuzzleNode {
	private EightPuzzleNode parent;
	private int[][] currentState;
	private int[][] goalState;
	private int depth;
	private int manhattanDistance;
	private int cost;
	private int size;
	private String action;
	
	/**
	 * Constructor for an EightPuzzleNode. Initializes all fields
	 * and calculates Manhattan distance and depth of the node.
	 * 
	 * @param size This is the dimensions (height and width) of the board
	 * @param current This is the current state of the board
	 * @param p This is the parent of this node, which holds the previous state of the board
	 * @param cost This is the cost of the action taken to get to the current state, used for A* search
	 * @param action This is the direction the empty tile shifted to get to the current state
	 * 
	 * @author Edward B.
	 */
	
	public EightPuzzleNode(int size, int[][] current, int[][]goalState, EightPuzzleNode p, int cost, String action) {
		this.size = size;
		this.action = action;
		this.cost = cost;
		
		currentState = new int[size][size];
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				currentState[i][j] = current[i][j];
			}
		}
		
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
	
	
	/**
	 * Calculates the Manhattan distance, or absolute value of the distance
	 * each tile needs to travel vertically and horizontally to get to the
	 * goal position.
	 * 
	 * @return int This returns the sum of the Manhattan distance of every tile
	 */
	
	private int setManhattanDistance() {
		int sum = 0;
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(currentState[i][j] != 0) {
					if(currentState[i][j] != goalState[i][j]) {
						int[] goalCoordinates = getCoordinates(currentState[i][j], goalState);
						sum += (Math.abs(goalCoordinates[0] - i) + Math.abs(goalCoordinates[1] - j));
					}
				}
			}
		}
		
		return sum;
	}
	
	/**
	 * Determines the x and y position of a given number's tile on a given board state.
	 * 
	 * @param target This is the number whose tile will be searched for
	 * @param state This is the board that will be searched through
	 * 
	 * @return int[] These are the array indices, or coordinates of the target in state
	 */
	
	public int[] getCoordinates(int target, int[][]state) {
		int[] coordinates = new int[2];
		
		for(int i = 0; i < size; i++) { //i
			for(int j = 0; j < size; j++) {
				if(state[i][j] == target) {
					coordinates[0] = i;
					coordinates[1] = j;
				}
			}
		}
		
		return coordinates;
	}
	
	/**
	 * Generates successor nodes, or nodes with valid tile swaps
	 * for use in EightPuzzleSearch.
	 * 
	 * @return ArrayList<EightPuzzleNode> This is the list of nodes with valid tile swap actions
	 */
	
	public ArrayList<EightPuzzleNode> generateSuccessors() {
		ArrayList<EightPuzzleNode> successors = new ArrayList<EightPuzzleNode>();
		int[] coordinates = getCoordinates(0, currentState);
		
		if((coordinates[0] - 1) >= 0) { // swap empty tile with tile above
			newSuccessor(coordinates[0], coordinates[1], coordinates[0] - 1, coordinates[1], successors, "UP");
		}
		if((coordinates[1] + 1) < size) { // swap empty tile with tile to the right
			newSuccessor(coordinates[0], coordinates[1], coordinates[0], coordinates[1] + 1, successors, "RIGHT");
		}
		if((coordinates[0] + 1) < size) { // swap empty tile with tile below
			newSuccessor(coordinates[0], coordinates[1], coordinates[0] + 1, coordinates[1], successors, "DOWN");
		}
		if((coordinates[1] - 1) >= 0) { // swap empty tile with tile to the left
			newSuccessor(coordinates[0], coordinates[1], coordinates[0], coordinates[1] - 1, successors, "LEFT");
		}
		
		return successors;
	}
	
	/**
	 * Creates a successor EightPuzzleNode where the tiles at (x1, y1)
	 * and (x2, y2) are swapped. This successor is then added to the
	 * list of successors.
	 * 
	 * @param x1 The x-coordinate of the first tile to be swapped
	 * @param y1 The y-coordinate of the first tile to be swapped
	 * @param x2 The x-coordinate of the second tile to be swapped
	 * @param y2 The y-coordinate of the second tile to be swapped
	 * @param storage The list of EightPuzzleNodes where the newly created node will be added
	 * @param act The action taken that results in the two tiles being swapped
	 * 
	 * @return Nothing
	 */
	
	private void newSuccessor(int x1, int y1, int x2, int y2, ArrayList<EightPuzzleNode> storage, String act) {
		int[][] stateCopy = new int[size][size];
		int[][] goalCopy = new int[size][size];
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				stateCopy[i][j] = currentState[i][j];
				goalCopy[i][j] = goalState[i][j];
			}
		}
		
		int temp = stateCopy[x2][y2];
		stateCopy[x2][y2] = stateCopy[x1][y1];
		stateCopy[x1][y1] = temp;
		
		EightPuzzleNode successor = new EightPuzzleNode(size, stateCopy, goalCopy, this, 1, act);
		
		if(parent != null) {
			if(!successor.equals(parent)) {
				storage.add(successor);
			}
		}
		else {
			storage.add(successor);
		}
	}
	
	/**
	 * Equals method for EightPuzzleNodes. Returns whether or not
	 * this node and the other node have the same tile configuration.
	 * 
	 * @param other The other node this node will be compared to
	 * 
	 * @return boolean Whether or not this node is equal to the other node
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
	
	/**
	 * Checks whether or not the current state is equivalent to
	 * the goal state.
	 * 
	 * @return boolean Whether the current state is solved
	 */
	
	public boolean isGoal() {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(currentState[i][j] != goalState[i][j]) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Returns the total cost of the path from
	 * the root to this node.
	 * 
	 * @return int This is the cost so far to get to this node
	 */
	
	public int pathCost() {
		int count = 0;
		EightPuzzleNode currNode = this;
		
		while (currNode != null) {
			count += currNode.getCost();
			currNode = currNode.getParent();
		}
		
		return count;
	}
	
	/**
	 * Generates string representation of currentState.
	 * For use in storing visited nodes during EightPuzzleSearch
	 * as opposed to storing the whole node.
	 * 
	 * @return String Representation of the current node's board state as a string
	 */
	
	public String generateNumberString() {
		StringBuilder sb = new StringBuilder(9);
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				sb.append(currentState[i][j]);
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * Returns a copy of this node's currentState
	 * 
	 * @return int[][] Copy of the board's current state
	 */
	
	public int[][] getCurrentState() {
		int[][] temp = new int[3][3];
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				temp[i][j] = currentState[i][j];
			}
		}
		
		return temp;
	}
	
	/**
	 * Returns the current node's depth.
	 * 
	 * @return int This node's depth
	 */
	
	public int getDepth() {
		return depth;
	}
	
	/**
	 * Returns the Manhattan distance of this node
	 * 
	 * @return int This node's Manhattan distance
	 */
	
	public int getManhattanDistance() {
		return manhattanDistance;
	}
	
	/**
	 * Returns the parent, or previous state, of this node
	 * 
	 * @return EightPuzzleNode This node's parent
	 */
	
	public EightPuzzleNode getParent() {
		return parent;
	}
	
	/**
	 * Returns the cost of this node
	 * 
	 * @return int This node's cost
	 */
	
	public int getCost() {
		return cost;
	}
	
	/**
	 * Returns the action that resulted in this node's current state.
	 * 
	 * @return String This node's action
	 */
	
	public String getAction() {
		return action;
	}
}