package gui;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class EightPuzzleGUI {

	private JFrame frmEightpuzzle;
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
		frmEightpuzzle = new JFrame();
		frmEightpuzzle.setResizable(false);
		frmEightpuzzle.setTitle("EightPuzzle");
		frmEightpuzzle.setBounds(0, 0, 800, 500);
		frmEightpuzzle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmEightpuzzle.getContentPane().setLayout(springLayout);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, frmEightpuzzle.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, frmEightpuzzle.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -10, SpringLayout.SOUTH, frmEightpuzzle.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, 440, SpringLayout.WEST, frmEightpuzzle.getContentPane());
		frmEightpuzzle.getContentPane().add(panel);
		panel.setLayout(new GridLayout(3, 3, 0, 0));
		
		JButton btnNewButton = new JButton("1");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 72));
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("2");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 72));
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("3");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 72));
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("4");
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 72));
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("5");
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 72));
		panel.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("6");
		btnNewButton_5.setFont(new Font("Tahoma", Font.PLAIN, 72));
		panel.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("7");
		btnNewButton_6.setFont(new Font("Tahoma", Font.PLAIN, 72));
		panel.add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("8");
		btnNewButton_7.setFont(new Font("Tahoma", Font.PLAIN, 72));
		panel.add(btnNewButton_7);
		
		JButton btnSolvePuzzle = new JButton("Solve Puzzle");
		springLayout.putConstraint(SpringLayout.NORTH, btnSolvePuzzle, 370, SpringLayout.NORTH, frmEightpuzzle.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnSolvePuzzle, 10, SpringLayout.EAST, panel);
		springLayout.putConstraint(SpringLayout.SOUTH, btnSolvePuzzle, -10, SpringLayout.SOUTH, frmEightpuzzle.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnSolvePuzzle, -10, SpringLayout.EAST, frmEightpuzzle.getContentPane());
		btnSolvePuzzle.setFont(new Font("Tahoma", Font.PLAIN, 25));
		frmEightpuzzle.getContentPane().add(btnSolvePuzzle);
		
		JButton btnGenerateNewPuzzle = new JButton("Generate New Puzzle");
		springLayout.putConstraint(SpringLayout.WEST, btnGenerateNewPuzzle, 10, SpringLayout.EAST, panel);
		springLayout.putConstraint(SpringLayout.SOUTH, btnGenerateNewPuzzle, -10, SpringLayout.NORTH, btnSolvePuzzle);
		springLayout.putConstraint(SpringLayout.EAST, btnGenerateNewPuzzle, -10, SpringLayout.EAST, frmEightpuzzle.getContentPane());
		btnGenerateNewPuzzle.setFont(new Font("Tahoma", Font.PLAIN, 25));
		frmEightpuzzle.getContentPane().add(btnGenerateNewPuzzle);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.EAST, panel);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -190, SpringLayout.SOUTH, frmEightpuzzle.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, frmEightpuzzle.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnGenerateNewPuzzle, 10, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 45, SpringLayout.NORTH, frmEightpuzzle.getContentPane());
		frmEightpuzzle.getContentPane().add(scrollPane);
		
		JLabel lblMoveList = new JLabel("Move List");
		springLayout.putConstraint(SpringLayout.NORTH, lblMoveList, 10, SpringLayout.NORTH, frmEightpuzzle.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblMoveList, 10, SpringLayout.EAST, panel);
		springLayout.putConstraint(SpringLayout.SOUTH, lblMoveList, -10, SpringLayout.NORTH, scrollPane);
		springLayout.putConstraint(SpringLayout.EAST, lblMoveList, -10, SpringLayout.EAST, frmEightpuzzle.getContentPane());
		lblMoveList.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoveList.setFont(new Font("Tahoma", Font.PLAIN, 25));
		frmEightpuzzle.getContentPane().add(lblMoveList);
	}
	
	public JFrame getFrame() {
		return frmEightpuzzle;
	}
}
