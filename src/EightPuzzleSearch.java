import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

public class EightPuzzleSearch {
	
	private static int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
	private static int[][] start = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
	
	private static HashSet<String> visited; // stores string representation of node states previously visited
	private static HashMap<String, Integer> inQueue; // special map to check for priority queue duplicates for A*
	
	private static int tileCount = 3;
	
	/*
	 * Main loop of the search. Takes user input for difficulty
	 * then takes user input for search type. Continues taking
	 * input until "quit" is typed at a prompt to exit.
	 */
	
	public static void main (String args[]) {
		visited = new HashSet<String>();
		inQueue = new HashMap<String, Integer>();
		EightPuzzleNode e = null;
		
		System.out.println("Eight Puzzle Solver by Edward Ryan Bote");
		
		// initialize the start state with random values
		initTiles();
		
		e = new EightPuzzleNode(3, start, goal, null, 0, "START");
		
		aStarSearch(e);
	}
	
	public static void initTiles() {
		/*
		for(int i = 0; i < 9; i++) {
			int row = (int)(Math.random() * 2 + 0.5);
			int col = (int)(Math.random() * 2 + 0.5);
			
			while(start[row][col] > -1) {
				row = (int)(Math.random() * 2 + 0.5);
				col = (int)(Math.random() * 2 + 0.5);
			}
			
			start[row][col] = i;
		}*/
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
	
	public static int countInversions() {
		int inversions = 0;
		return inversions;
	}
	
	
	/*
	 * Implementation of A* search with repeated state checking
	 * for visited states and duplicate states with lower path costs
	 * in the priority queue.
	 * Uses Java standard priority queue with a custom comparator
	 * to order the queue by Manhattan distance and path cost, found in
	 * AStarComparator.java.
	 */
	
	public static void aStarSearch(EightPuzzleNode root) {
		long startTime = System.nanoTime();
		
		if (root == null) {
			return;
		}
		
		int iteration = 0;
		int space = 0;
		// uses comparator that sorts priority queue by total path cost + number of misplaced tiles
		Comparator<EightPuzzleNode> comparator = new AStarComparator();
		PriorityQueue<EightPuzzleNode> pQueue = new PriorityQueue<EightPuzzleNode>(comparator);
		pQueue.add(root);
		
		while(!pQueue.isEmpty()){
	        EightPuzzleNode node = pQueue.poll();
	        visited.add(node.generateNumberString());
	        inQueue.remove(node.generateNumberString());
	        
	        if(node.isGoal()) {
	        	iteration++;
	        	long endTime = System.nanoTime();
	        	long duration = (endTime - startTime);
	        	
	        	printSolution(node);
	        	System.out.println("A* search (h = Manhattan distance) took: " + duration / 1000000 + " milliseconds.");
	        	System.out.println("Path length: " + node.getDepth());
	        	System.out.println("Path cost: " + node.pathCost());
	        	System.out.println("Number of nodes popped off queue: " + iteration);
	        	System.out.println("Maximum space used: " + space + " nodes.");
	        	return;
	        }
	        
	        ArrayList<EightPuzzleNode> successors = node.generateSuccessors();
	        
	        for(EightPuzzleNode e : successors) {
	        	String numberString = e.generateNumberString();
	        	int costSoFar = e.pathCost();
	        	
	        	if(!visited.contains(numberString)) {
	        		if(!inQueue.containsKey(numberString)) {
	        			pQueue.add(e);
	        			inQueue.put(numberString, costSoFar);
	        			
	        			int tempSize = pQueue.size();
		        		if(tempSize > space) {
		        			space = tempSize;
		        		}
	        		}
	        		else if(costSoFar < inQueue.get(numberString)) {
	        			pQueue.add(e);
	        			inQueue.put(numberString, costSoFar);
	        			
	        			int tempSize = pQueue.size();
		        		if(tempSize > space) {
		        			space = tempSize;
		        		}
	        		}
	        	}
	        }
	        
	        iteration++;
	    }
	}
	
	
	/*
	 * Prints solution path found by searches and
	 * data about the path including: actions taken by nodes,
	 * depth of nodes, cost of individual nodes, and path cost.
	 */
	public static void printSolution(EightPuzzleNode leaf) {
		if(leaf == null) { // argument validation
			return;
		}
		
		System.out.println("Puzzle Solved!\nPrinting Solution Path:");
		
		Stack<EightPuzzleNode> printStack = new Stack<EightPuzzleNode>();
		EightPuzzleNode currNode = leaf;
		
		while(currNode != null) { 			// traces up from leaf to root, putting nodes
			printStack.push(currNode); 		// in correct order on the stack
			currNode = currNode.getParent();
		}
		
		while(!printStack.isEmpty()) {
			currNode = printStack.pop();
			
			currNode.printNode();
			System.out.println("Action: " + currNode.getAction());
			System.out.println("Depth: " + currNode.getDepth());
	        System.out.println("Cost of current node: " + currNode.getCost());
	        System.out.println("Total path cost: " + currNode.pathCost());
	        System.out.println("-------");
		}
	}
}