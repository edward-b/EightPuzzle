package model;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class EightPuzzleSearch {
	
	private static HashSet<String> visited = new HashSet<String>(); // stores string representation of node states previously visited
	private static HashMap<String, Integer> inQueue = new HashMap<String, Integer>(); // special map to check for priority queue duplicates for A*
	
	/*
	 * Implementation of A* search with repeated state checking
	 * for visited states and duplicate states with lower path costs
	 * in the priority queue.
	 * Uses Java standard priority queue with a custom comparator
	 * to order the queue by Manhattan distance and path cost, found in
	 * AStarComparator.java.
	 */
	
	public static EightPuzzleNode aStarSearch(EightPuzzleNode root) {
		if (root != null) {
			int space = 0;
			visited = new HashSet<String>();
			inQueue = new HashMap<String, Integer>();
			// uses comparator that sorts priority queue by total path cost + number of misplaced tiles
			Comparator<EightPuzzleNode> comparator = new AStarComparator();
			PriorityQueue<EightPuzzleNode> pQueue = new PriorityQueue<EightPuzzleNode>(comparator);
			pQueue.add(root);
			
			while(!pQueue.isEmpty()){
		        EightPuzzleNode node = pQueue.poll();
		        visited.add(node.generateNumberString());
		        inQueue.remove(node.generateNumberString());
		        
		        if(node.isGoal()) {
		        	return node;
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
		    }
		}
		
		return null;
	}
}