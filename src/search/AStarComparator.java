package search;
import java.util.Comparator;

import node.EightPuzzleNode;

public class AStarComparator implements Comparator<EightPuzzleNode>
{
	/*
	 * Comparator used in A* search with Manhattan distance
	 * being used as the heuristic.
	 */
	
    @Override
    public int compare(EightPuzzleNode x, EightPuzzleNode y)
    {
    	int totalCostX = x.pathCost() + x.getManhattanDistance();
    	int totalCostY = y.pathCost() + y.getManhattanDistance();
    	
        if (totalCostX < totalCostY)
        {
            return -1;
        }
        else if (totalCostX > totalCostY)
        {
            return 1;
        }
        else {
        	return 0;
        }
    }
}