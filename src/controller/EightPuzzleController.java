package controller;

import java.awt.event.*;
import java.util.List;
import java.util.Stack;

import javax.swing.SwingWorker;

import exceptions.InvalidDirectionException;
import exceptions.InvalidTileException;
import exceptions.MismatchedArraySizeException;
import model.EightPuzzleModel;
import model.EightPuzzleNode;
import model.EightPuzzleSearch;
import view.EightPuzzleGUI;

/**
 * EightPuzzleController is the controller that updates the GUI (view)
 * and the logic/data (model) associated with the EightPuzzle.
 * 
 * @author Edward B.
 */

public class EightPuzzleController {
	
	private EightPuzzleModel model;
	private EightPuzzleGUI view;
	
	/**
	 * Constructor for an EightPuzzleController. Initializes the
	 * model and view.
	 * 
	 * @param model This is logic and data representing the puzzle
	 * @param view This is the GUI of the puzzle
	 * 
	 * @author Edward B.
	 */
	
	public EightPuzzleController (EightPuzzleModel model, EightPuzzleGUI view) {
		setModel(model);
		setView(view);
	}
	
	/** 
	 * Sets the model of the puzzle for the controller.
	 * 
	 * @param m This is the model of the puzzle
	 * @return Nothing
	 */
	
	private void setModel(EightPuzzleModel m) {
		assert m != null;
		
		model = m;
	}
	
	/** 
	 * Sets the GUI of the puzzle for the controller.
	 * 
	 * @param v This is the GUI of the puzzle
	 * @return Nothing
	 */
	
	private void setView(EightPuzzleGUI v) {
		assert v != null;
		
		view = v;
	}
	
	/**
	 * Method that initializes the view and model. Listens
	 * for various button presses on the GUI and updates
	 * the model and view accordingly.
	 * 
	 * @throws InvalidTileException
	 * @return Nothing
	 */
	
	public void start() throws InvalidTileException{
		view.initializeTiles(model.getSize());
		view.updateTiles(model.getCurrentState());
		
		// handles when a tile on the board is clicked
		view.addPuzzlePanelListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String action = "";
	    		
				try {
					action = model.move(e.getActionCommand());
				} catch (InvalidTileException e1) {
					System.out.println(e1.getMessage());
					e1.printStackTrace();
				}
				
	    		if(!action.isEmpty()) {
	    			view.updateMoveList("Empty tile shifted: " + action);
	    		}
	    		view.updateTiles(model.getCurrentState());
	    		
	    		if(model.isSolved()) {
	    			view.displaySolutionMessage();
	    			view.clearMoveList();
		    		model.generateNewPuzzle();
		    		view.updateTiles(model.getCurrentState());
	    		}
	    		}
	    	});
		
		// handles when the "Generate New Puzzle" button is pressed
	    view.addGenerateNewPuzzleListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e) {
	    		view.clearMoveList();
	    		model.generateNewPuzzle();
	    		view.updateTiles(model.getCurrentState());
	    		}
	      });
	    
	    // handles when the "Solve Puzzle button is pressed
	    view.addSolvePuzzleListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		(new PrintSolution()).execute();
	    		}
	    	});
	}
	
	
    private class PrintSolution extends SwingWorker<Void, EightPuzzleNode> {
    	
    	/**
    	 * Overridden doInBackground() method that goes through
    	 * each node in the solution list and updates the view
    	 * and model for each node through the process() method.
    	 */
    	
        @Override
        protected Void doInBackground() {

        	view.disableGenerateNewPuzzleButton();
        	view.disableSolvePuzzleButton();
        	view.disablePuzzlePanelButtons();
        	
        	EightPuzzleNode currNode = new EightPuzzleNode(3, model.getCurrentState(), model.getGoalState(), null, 0, "START");
	        EightPuzzleNode solution = EightPuzzleSearch.aStarSearch(currNode);
	        
        	Stack<EightPuzzleNode> printStack = new Stack<EightPuzzleNode>();
        	
        	// reorders nodes for printing in order from the root
    		while(solution != null) {
    			printStack.push(solution);
    			solution = solution.getParent();
    		}
    		
    		int delay = 500;
    		
            while (!printStack.isEmpty()) {
            	currNode = printStack.pop();
                publish(currNode);
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    System.out.println("Background interrupted");
                }
            }
            
            view.enableGenerateNewPuzzleButton();
            view.enableSolvePuzzleButton();
            view.enablePuzzlePanelButtons();
            view.updateMoveList("End of generated solution.");
            
            return null;
        }
        
        /**
         * Overridden process() method that updates the model and view
         * in between each pop off the solution stack.
         */

        @Override
        protected void process(List<EightPuzzleNode> nodes) {
            EightPuzzleNode currNode = nodes.get(nodes.size() - 1);
            String curAction = currNode.getAction();
            if(!curAction.equals("START")) {
            	view.updateMoveList("Empty tile shifted: " + curAction);
				try {
					model.updateZeroCoords(curAction);
				} catch (InvalidDirectionException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
            }
			try {
				model.setCurrentState(currNode.getCurrentState());
			} catch (NullPointerException | MismatchedArraySizeException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			view.updateTiles(model.getCurrentState());
        }
    }
}