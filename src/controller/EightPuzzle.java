package controller;

import java.awt.*;

import view.EightPuzzleGUI;
import model.EightPuzzleModel;

/**
 * MVP version of https://stackoverflow.com/q/3066590/230513
 */
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