package model;
import java.util.Comparator;

/**
 * AStarComparator is a comparator used for EightPuzzleSearch to
 * compare the Manhattan distance between two EightPuzzleNodes.
 * 
 * @author Edward B.
 */

public class AStarComparator implements Comparator<EightPuzzleNode>
{
	/**
	 * Overridden compare() method that compares the
	 * Manhattan distance between two EightPuzzleNodes.
	 * 
	 * @param x The first node to be compared
	 * @param y The second node to be compared
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