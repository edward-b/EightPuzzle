package view;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Component;

/**
 * The GUI for the puzzle. Layout is scaled with a SpringLayout
 * and listeners are added to interact with the EightPuzzleController.
 * 
 * @author Edward B.
 */

public class EightPuzzleGUI {

	private JFrame eightPuzzleFrame = new JFrame();
	private JPanel eightPuzzlePanel  = new JPanel();
	private JButton btnGenerateNewPuzzle  = new JButton("Generate New Puzzle");
	private JButton btnSolvePuzzle = new JButton("Solve Puzzle");
	private JLabel lblMoveList = new JLabel("Move List");
	private JScrollPane moveListPane = new JScrollPane(new JTextArea());
	private JViewport moveListView = moveListPane.getViewport();
	private JTextArea moveListTextArea = (JTextArea)moveListView.getView();
	
	/**
	 * Constructor that creates a window for the puzzle.
	 */
	public EightPuzzleGUI() {
		initialize();
	}

	/**
	 * Initializes the contents of the frame.
	 * Creates the window, puzzle panel, buttons, and move list.
	 * 
	 * @return Nothing
	 */
	private void initialize() {
		eightPuzzleFrame.setResizable(false);
		eightPuzzleFrame.setTitle("EightPuzzle");
		eightPuzzleFrame.setBounds(0, 0, 800, 470);
		eightPuzzleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		eightPuzzleFrame.getContentPane().setLayout(springLayout);
		
		springLayout.putConstraint(SpringLayout.EAST, eightPuzzlePanel, 460, SpringLayout.WEST, eightPuzzleFrame.getContentPane());
		eightPuzzlePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		springLayout.putConstraint(SpringLayout.NORTH, eightPuzzlePanel, 10, SpringLayout.NORTH, eightPuzzleFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, eightPuzzlePanel, 10, SpringLayout.WEST, eightPuzzleFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, eightPuzzlePanel, -10, SpringLayout.SOUTH, eightPuzzleFrame.getContentPane());
		eightPuzzleFrame.getContentPane().add(eightPuzzlePanel);
		eightPuzzlePanel.setLayout(new GridLayout(3, 3, 0, 0));
		
		springLayout.putConstraint(SpringLayout.NORTH, lblMoveList, 10, SpringLayout.NORTH, eightPuzzleFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblMoveList, 10, SpringLayout.EAST, eightPuzzlePanel);
		springLayout.putConstraint(SpringLayout.SOUTH, lblMoveList, -10, SpringLayout.NORTH, moveListPane);
		springLayout.putConstraint(SpringLayout.EAST, lblMoveList, -10, SpringLayout.EAST, eightPuzzleFrame.getContentPane());
		lblMoveList.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoveList.setFont(new Font("Tahoma", Font.PLAIN, 25));
		eightPuzzleFrame.getContentPane().add(lblMoveList);
		
		springLayout.putConstraint(SpringLayout.WEST, btnGenerateNewPuzzle, 10, SpringLayout.EAST, eightPuzzlePanel);
		springLayout.putConstraint(SpringLayout.SOUTH, btnGenerateNewPuzzle, -10, SpringLayout.NORTH, btnSolvePuzzle);
		springLayout.putConstraint(SpringLayout.EAST, btnGenerateNewPuzzle, -10, SpringLayout.EAST, eightPuzzleFrame.getContentPane());
		btnGenerateNewPuzzle.setFont(new Font("Tahoma", Font.PLAIN, 25));
		eightPuzzleFrame.getContentPane().add(btnGenerateNewPuzzle);
		
		springLayout.putConstraint(SpringLayout.WEST, moveListPane, 10, SpringLayout.EAST, eightPuzzlePanel);
		springLayout.putConstraint(SpringLayout.SOUTH, moveListPane, -190, SpringLayout.SOUTH, eightPuzzleFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, moveListPane, -10, SpringLayout.EAST, eightPuzzleFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnGenerateNewPuzzle, 10, SpringLayout.SOUTH, moveListPane);
		springLayout.putConstraint(SpringLayout.NORTH, moveListPane, 45, SpringLayout.NORTH, eightPuzzleFrame.getContentPane());
		eightPuzzleFrame.getContentPane().add(moveListPane);
		
		springLayout.putConstraint(SpringLayout.NORTH, btnSolvePuzzle, 340, SpringLayout.NORTH, eightPuzzleFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnSolvePuzzle, 10, SpringLayout.EAST, eightPuzzlePanel);
		springLayout.putConstraint(SpringLayout.SOUTH, btnSolvePuzzle, -10, SpringLayout.SOUTH, eightPuzzleFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnSolvePuzzle, -10, SpringLayout.EAST, eightPuzzleFrame.getContentPane());
		btnSolvePuzzle.setFont(new Font("Tahoma", Font.PLAIN, 25));
		eightPuzzleFrame.getContentPane().add(btnSolvePuzzle);
		
		moveListTextArea.setEditable(false);
	}
	
	/**
	 * Initializes the tiles as buttons on a square
	 * GridLayout. Empty tile is represented as an
	 * invisible button.
	 * 
	 * @param size The size of the board
	 * 
	 * @return Nothing
	 */
	
	public void initializeTiles(int size) {
		int tileCount = size * size;
		JButton tempButton = new JButton();
		
		for(int i = 0; i < tileCount; i++) {
			tempButton = new JButton("");
			tempButton.setFont(new Font("Tahoma", Font.PLAIN, 72));
			eightPuzzlePanel.add(tempButton);
		}
		
		eightPuzzleFrame.setVisible(true);
	}
	
	/**
	 * Updates the puzzle panel to represent the current state of the board
	 * by reorganizing the buttons and changing which are visible and what
	 * numbers they display.
	 * 
	 * @param currState The state that the puzzle panel has to be updated to
	 * 
	 * @return Nothing
	 */
	
	public void updateTiles(int[][] currState) {
		int tileCount = currState.length;
		int tempLength = tileCount * tileCount;
		int[] temp = new int[tempLength];
		int count = 0;
		
		for(int i = 0; i < tileCount; i++) {
			for(int j = 0; j < tileCount; j++) {
				temp[count] = currState[i][j];
				count++;
			}
		}
		
		JButton tempButton = new JButton();
		
		for(int i = 0; i < tempLength; i++) {
			tempButton = (JButton)eightPuzzlePanel.getComponent(i);
			if(temp[i] != 0) {
				tempButton.setText(Integer.toString(temp[i]));
				tempButton.setVisible(true);
			}
			else {
				tempButton.setText("");
				tempButton.setVisible(false);
			}
		}
		
		eightPuzzleFrame.setVisible(true);
	}
	
	/**
	 * Displays a solution pop up window when a puzzle is manually solved.
	 * 
	 * @return Nothing
	 */
	
	public void displaySolutionMessage() {
		JOptionPane.showMessageDialog(eightPuzzleFrame, "Puzzle solved!", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Updates the move list with the specified text.
	 * 
	 * @param str The text to be inserted in the move list
	 * 
	 * @return Nothing
	 */
	
	public void updateMoveList(String str) {
		moveListTextArea.append(str + "\n");
	}
	
	/**
	 * Empties the move list
	 * 
	 * @return Nothing
	 */
	
	public void clearMoveList() {
		moveListTextArea.setText("");
	}
	
	/**
	 * Creates an ActionListener to listen for when the
	 * "Generate New Puzzle" button is pressed.
	 * 
	 * @param listener Listener for button presses
	 * 
	 * @return Nothing
	 */
	
	public void addGenerateNewPuzzleListener(ActionListener listener) {
		btnGenerateNewPuzzle.addActionListener(listener);
	}
	
	/**
	 * Enables the "Generate New Puzzle" button.
	 * 
	 * @return Nothing
	 */
	
	public void enableGenerateNewPuzzleButton() {
		btnGenerateNewPuzzle.setEnabled(true);
	}
	
	/**
	 * Disables the "Generate New Puzzle" button.
	 * 
	 * @return Nothing
	 */
	
	public void disableGenerateNewPuzzleButton() {
		btnGenerateNewPuzzle.setEnabled(false);
	}
	
	/**
	 * Creates an ActionListener for when the "Solve Puzzle"
	 * button is pressed.
	 * 
	 * @param listener Listener for button presses
	 * 
	 * @return Nothing
	 */
	
	public void addSolvePuzzleListener(ActionListener listener) {
		btnSolvePuzzle.addActionListener(listener);
	}
	
	/**
	 * Enables the "Solve Puzzle" button.
	 * 
	 * @return Nothing
	 */
	
	public void enableSolvePuzzleButton() {
		btnSolvePuzzle.setEnabled(true);
	}
	
	/**
	 * Disables the "Solve Puzzle" button.
	 * 
	 * @return Nothing
	 */
	
	public void disableSolvePuzzleButton() {
		btnSolvePuzzle.setEnabled(false);
	}
	
	/**
	 * Creates an ActionListener to listen for when buttons
	 * representing tiles on the puzzle panel are pressed.
	 * 
	 * @param listener Listener for button presses
	 * 
	 * @return Nothing
	 */
	
	public void addPuzzlePanelListener(ActionListener listener) {
		for(Component c : eightPuzzlePanel.getComponents()) {
			if(c instanceof JButton) {
				((JButton)c).addActionListener(listener);
			}
		}
	}
	
	/**
	 * Enables all tiles on the puzzle panel.
	 * 
	 * @return Nothing
	 */
	
	public void enablePuzzlePanelButtons() {
		Component[] buttons = eightPuzzlePanel.getComponents();
		for(Component c : buttons) {
			if(c instanceof JButton) {
				c.setEnabled(true);
			}
		}
	}
	
	/**
	 * Disables all tiles on the puzzle panel.
	 * 
	 * @return Nothing
	 */
	
	public void disablePuzzlePanelButtons() {
		Component[] buttons = eightPuzzlePanel.getComponents();
		for(Component c : buttons) {
			if(c instanceof JButton) {
				c.setEnabled(false);
			}
		}
	}
}
