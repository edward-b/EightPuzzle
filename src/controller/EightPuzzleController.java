package controller;

import java.awt.*;
import java.awt.event.*;

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
	    view.setTiles(model.getCurrState());

	    view.addGenerateNewPuzzleListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	        model.generateNewPuzzle();
	        view.setTiles(model.getCurrState());
	      }
	    });
	    
	    /*
	    for (int i = 0; i < GamePiece.values().length; i++)
	    {
	      final GamePiece aPiece = GamePiece.values()[i];
	      view.addPieceActionListener(aPiece, new ActionListener()
	      {
	        public void actionPerformed(ActionEvent e)
	        {
	          tileSelected(aPiece);
	        }
	      });
	    }*/
	}
	
	private void tileSelected() {
		
	}
}