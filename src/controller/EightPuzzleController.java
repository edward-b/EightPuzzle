package controller;

import java.awt.event.*;
import java.util.List;
import java.util.Stack;

import javax.swing.SwingWorker;

import model.EightPuzzleModel;
import model.EightPuzzleNode;
import model.EightPuzzleSearch;
import view.EightPuzzleGUI;

public class EightPuzzleController {
	
	private EightPuzzleModel model;
	private EightPuzzleGUI view;
	
	public EightPuzzleController (EightPuzzleModel model, EightPuzzleGUI view) {
		this.model = model;
		this.view = view;
	}
	
	public void start() {
		view.initializeTiles(model.getTileCount());
		view.updateTiles(model.getCurrState());
		
		view.addPuzzlePanelListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		model.move(e.getActionCommand());
	    		view.updateTiles(model.getCurrState());
	    		}
	    	});

	    view.addGenerateNewPuzzleListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e) {
	    		view.clearMoveList();
	    		model.generateNewPuzzle();
	    		view.updateTiles(model.getCurrState());
	    		}
	      });
	    
	    view.addSolvePuzzleListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		(new PrintSolution()).execute();
	    		}
	    	});
	}
	
    private class PrintSolution extends SwingWorker<Void, EightPuzzleNode> {
        @Override
        protected Void doInBackground() {

        	view.disableGenerateNewPuzzleButton();
        	view.disableSolvePuzzleButton();
        	
        	EightPuzzleNode currNode = new EightPuzzleNode(3, model.getCurrState(), null, 0, "START");
	        EightPuzzleNode solution = EightPuzzleSearch.aStarSearch(currNode);
	        
        	Stack<EightPuzzleNode> printStack = new Stack<EightPuzzleNode>();
    		
    		while(solution != null) { 			// traces up from leaf to root, putting nodes
    			printStack.push(solution); 		// in correct order on the stack
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
            
            return null;
        }

        @Override
        protected void process(List<EightPuzzleNode> nodes) {
            EightPuzzleNode currNode = nodes.get(nodes.size() - 1);
            String curAction = currNode.getAction();
            if(!curAction.equals("START")) {
            	view.updateMoveList("Empty space shifted: " + curAction);
            	model.updateZeroCoords(curAction);
            }
			model.updateTiles(currNode.getCurrentState());
			view.updateTiles(model.getCurrState());
			
        }
        
        @Override
        protected void done() {
        	
        }
    }
}