package model;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class EightPuzzleSearch {
	
	private static HashSet<String> previouslyVisited = new HashSet<String>();
	private static HashMap<String, Integer> inQueue = new HashMap<String, Integer>();
	
	/**
	 * Implementation of A* search with repeated state checking
	 * for visited states and duplicate states with lower path costs
	 * in the priority queue. Uses Java standard priority queue with
	 * a custom comparator (found in AStarComparator) to order the
	 * queue by Manhattan distance and path cost.
	 * 
	 * @return EightPuzzleNode The leaf of the path of nodes that are a solution to the puzzle
	 */
	
	public static EightPuzzleNode aStarSearch(EightPuzzleNode root) {
		if (root != null) {
			previouslyVisited = new HashSet<String>();
			inQueue = new HashMap<String, Integer>();
			Comparator<EightPuzzleNode> comparator = new AStarComparator();
			PriorityQueue<EightPuzzleNode> pQueue = new PriorityQueue<EightPuzzleNode>(comparator);
			
			pQueue.add(root);
			
			while(!pQueue.isEmpty()){
		        EightPuzzleNode node = pQueue.poll();
		        
		        if(node.isGoal()) {
		        	return node;
		        }
		        
		        previouslyVisited.add(node.generateNumberString());
		        inQueue.remove(node.generateNumberString());
		        
		        ArrayList<EightPuzzleNode> successors = node.generateSuccessors();
		        
		        for(EightPuzzleNode e : successors) {
		        	String numberString = e.generateNumberString();
		        	int costSoFar = e.pathCost();
		        	
		        	if(!previouslyVisited.contains(numberString)) {
		        		if(!inQueue.containsKey(numberString)) {
		        			pQueue.add(e);
		        			inQueue.put(numberString, costSoFar);
		        		}
		        		else if(costSoFar < inQueue.get(numberString)) {
		        			pQueue.add(e);
		        			inQueue.put(numberString, costSoFar);
		        		}
		        	}
		        }
		    }
		}
		
		return null;
	}
}