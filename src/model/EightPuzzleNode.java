package model;
import java.util.ArrayList;

/**
 * EightPuzzleNode is an object used to store moves in a solution
 * to an 8-puzzle, usually used by EightPuzzleSearch. 
 * 
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
	 * @param parent This is the parent of this node, which holds the previous state of the board
	 * @param cost This is the cost of the action taken to get to the current state, used for A* search
	 * @param action This is the direction the empty tile shifted to get to the current state
	 * 
	 * @author Edward B.
	 */
	
	public EightPuzzleNode(int size, int[][] current, int[][]goal, EightPuzzleNode parent, int cost, String action) {
		setSize(size);
		setCurrentState(current);
		setGoalState(goal);
		setParent(parent);
		setCost(cost);
		setAction(action);
		setDepth();
		setManhattanDistance();
	}
	
	/**
	 * Sets the width and height of this node's board.
	 * 
	 * @return Nothing
	 */
	
	private void setSize(int s) {
		assert(s > 1);
		
		size = s;
	}
	
	/**
	 * Sets this node's current state.
	 * 
	 * @return Nothing
	 */
	
	private void setCurrentState(int[][] current) {
		assert current != null;
		
		currentState = new int[size][size];
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				currentState[i][j] = current[i][j];
			}
		}
	}
	
	/**
	 * Sets this node's goal state.
	 * 
	 * @return Nothing
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
	 * Sets the parent of this node to e.
	 * 
	 * @return Nothing
	 */
	
	private void setParent(EightPuzzleNode e) {
		parent = e;
	}
	
	/**
	 * Sets the cost of the action taken to result in this node's current state.
	 * 
	 * @return Nothing
	 */
	
	private void setCost (int c) {
		assert c >= 0;
		
		cost = c;
	}
	
	/**
	 * Sets the action taken to result in this node's current state.
	 * 
	 * @return Nothing
	 */
	
	private void setAction(String str) {
		assert str.equals("START") || str.equals("UP")
				|| str.equals("DOWN") || str.equals("LEFT")
				|| str.equals("RIGHT");
		
		action = str;
	}
	
	/**
	 * Sets the current depth of the node to be 0 if this is the root
	 * or the parent node's depth plus 1.
	 * 
	 * @return Nothing
	 */
	
	private void setDepth() {
		if (parent == null) {
			depth = 0;
		}
		else {
			depth = parent.getDepth() + 1;
		}
	}
	
	/**
	 * Calculates the Manhattan distance, or absolute value of the distance
	 * each tile needs to travel vertically and horizontally to get to the
	 * goal position.
	 * 
	 * @return Nothing
	 */
	
	private void setManhattanDistance() {
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
		
		manhattanDistance = sum;
	}
	
	/**
	 * Returns the width and height of this node
	 * 
	 * @return int This node's size
	 */
	
	public int getSize() {
		return size;
	}
	
	/**
	 * Returns a copy of this node's currentState
	 * 
	 * @return int[][] Copy of the board's current state
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
	 * Returns a copy of this node's goalState
	 * 
	 * @return int[][] Copy of the board's goal state
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
	
	/**
	 * Determines the x and y position of a given number's tile on a given board state.
	 * 
	 * @param target This is the number whose tile will be searched for
	 * @param state This is the board that will be searched through
	 * 
	 * @return int[] These are the array indices, or coordinates of the target in state
	 */
	
	private int[] getCoordinates(int target, int[][]state) {
		assert (target >= 0) && (target < (size * size));
		assert (state != null) && (state[0].length == size);
		
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
	
	private void newSuccessor(int x1, int y1, int x2, int y2,
			ArrayList<EightPuzzleNode> storage, String act) {
		assert (x1 >= 0) && (x1 < size);
		assert (x2 >= 0) && (x2 < size);
		assert (y1 >= 0) && (y1 < size);
		assert (y2 >= 0) && (y2 < size);
		assert storage != null;
		assert act.equals("UP") || act.equals("DOWN")
				|| act.equals("LEFT") || act.equals("RIGHT");
		
		
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
		boolean result = false;
		
		if(other instanceof EightPuzzleNode) {
			result = true;
			EightPuzzleNode o = (EightPuzzleNode) other;
			int[][] otherState = o.getCurrentState();
			
			for(int i = 0; i < size; i++) {
				for(int j = 0; j < size; j++) {
					if(currentState[i][j] != otherState[i][j]) {
						result = false;
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Checks whether or not the current state is equivalent to
	 * the goal state.
	 * 
	 * @return boolean Whether the current state is solved
	 */
	
	public boolean isGoal() {
		boolean result = true;
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(currentState[i][j] != goalState[i][j]) {
					result = false;
				}
			}
		}
		
		return result;
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
}