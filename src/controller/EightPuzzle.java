package controller;

import java.awt.*;

/**
 * EightPuzzle is the driver program that creates the GUI,
 * logic, and controller for the 8-puzzle, in accordance
 * with the MVC design pattern.
 * 
 * @author Edward B.
 */

import view.EightPuzzleGUI;
import model.EightPuzzleModel;

public class EightPuzzle implements Runnable
{
  public static void main(String[] args) {
	 EventQueue.invokeLater(new EightPuzzle());
  }

  @Override
  public void run() {
    EightPuzzleModel model = new EightPuzzleModel();
    EightPuzzleGUI view = new EightPuzzleGUI();
    EightPuzzleController controller = new EightPuzzleController(model, view);
    
    controller.start();
  }
}