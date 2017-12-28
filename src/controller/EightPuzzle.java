package controller;

import java.awt.*;

import exceptions.InvalidTileException;

/**
 * EightPuzzle is the driver program that creates the GUI,
 * logic, and controller for the 8-puzzle, in accordance
 * with the MVC design pattern.
 * 
 * @author Edward B.
 */

import view.EightPuzzleGUI;
import model.EightPuzzleModel;

/**
 * Driver program for the puzzle.
 * Initializes the model, view, and controller.
 * 
 * @author Edward B.
 */

public class EightPuzzle implements Runnable
{
  public static void main(String[] args) {
	 EventQueue.invokeLater(new EightPuzzle());
  }

  @Override
  public void run() {
	int size = 3;
	int[][] goal = {{1,2,3},{4,5,6},{7,8,0}};
	
    EightPuzzleModel model = new EightPuzzleModel(size, goal);
    EightPuzzleGUI view = new EightPuzzleGUI();
    EightPuzzleController controller = new EightPuzzleController(model, view);
    
    try {
		controller.start();
	} catch (InvalidTileException e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
	}
  }
}