package view;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.Color;

public class EightPuzzleGUI {

	private JFrame eightPuzzleFrame = new JFrame();
	private JPanel eightPuzzlePanel  = new JPanel();
	private JButton btnGenerateNewPuzzle  = new JButton("Generate New Puzzle");
	private JButton btnSolvePuzzle = new JButton("Solve Puzzle");
	private JLabel lblMoveList = new JLabel("Move List");
	private JScrollPane moveList = new JScrollPane(new JTextArea());
	
	/**
	 * Create the application.
	 */
	public EightPuzzleGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
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
		springLayout.putConstraint(SpringLayout.SOUTH, lblMoveList, -10, SpringLayout.NORTH, moveList);
		springLayout.putConstraint(SpringLayout.EAST, lblMoveList, -10, SpringLayout.EAST, eightPuzzleFrame.getContentPane());
		lblMoveList.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoveList.setFont(new Font("Tahoma", Font.PLAIN, 25));
		eightPuzzleFrame.getContentPane().add(lblMoveList);
		
		springLayout.putConstraint(SpringLayout.WEST, btnGenerateNewPuzzle, 10, SpringLayout.EAST, eightPuzzlePanel);
		springLayout.putConstraint(SpringLayout.SOUTH, btnGenerateNewPuzzle, -10, SpringLayout.NORTH, btnSolvePuzzle);
		springLayout.putConstraint(SpringLayout.EAST, btnGenerateNewPuzzle, -10, SpringLayout.EAST, eightPuzzleFrame.getContentPane());
		btnGenerateNewPuzzle.setFont(new Font("Tahoma", Font.PLAIN, 25));
		eightPuzzleFrame.getContentPane().add(btnGenerateNewPuzzle);
		
		springLayout.putConstraint(SpringLayout.WEST, moveList, 10, SpringLayout.EAST, eightPuzzlePanel);
		springLayout.putConstraint(SpringLayout.SOUTH, moveList, -190, SpringLayout.SOUTH, eightPuzzleFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, moveList, -10, SpringLayout.EAST, eightPuzzleFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnGenerateNewPuzzle, 10, SpringLayout.SOUTH, moveList);
		springLayout.putConstraint(SpringLayout.NORTH, moveList, 45, SpringLayout.NORTH, eightPuzzleFrame.getContentPane());
		eightPuzzleFrame.getContentPane().add(moveList);
		JViewport moveListView = moveList.getViewport();
		JTextArea text = (JTextArea)moveListView.getView();
		text.setEditable(false);
		
		springLayout.putConstraint(SpringLayout.NORTH, btnSolvePuzzle, 340, SpringLayout.NORTH, eightPuzzleFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnSolvePuzzle, 10, SpringLayout.EAST, eightPuzzlePanel);
		springLayout.putConstraint(SpringLayout.SOUTH, btnSolvePuzzle, -10, SpringLayout.SOUTH, eightPuzzleFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnSolvePuzzle, -10, SpringLayout.EAST, eightPuzzleFrame.getContentPane());
		btnSolvePuzzle.setFont(new Font("Tahoma", Font.PLAIN, 25));
		eightPuzzleFrame.getContentPane().add(btnSolvePuzzle);
	}
	
	public void setTiles(int[][] currState) {
		eightPuzzleFrame.setVisible(false);
		
		int tileCount = currState.length;
		int[] temp = new int[tileCount * tileCount];
		int count = 0;
		
		for(int i = 0; i < tileCount; i++) {
			for(int j = 0; j < tileCount; j++) {
				temp[count] = currState[i][j];
				count++;
			}
		}
		
		eightPuzzlePanel.removeAll();
		
		JButton tempButton = new JButton();
		
		for(int i = 0; i < temp.length; i++) {
			if(temp[i] != 0) {
				tempButton = new JButton(Integer.toString(temp[i]));
				tempButton.setFont(new Font("Tahoma", Font.PLAIN, 72));
				eightPuzzlePanel.add(tempButton);
			}
			else {
				eightPuzzlePanel.add(new JLabel(""));
			}
		}
		
		eightPuzzleFrame.setVisible(true);
	}

	public void addGenerateNewPuzzleListener(ActionListener listener) {
		btnGenerateNewPuzzle.addActionListener(listener);
	}
}
