/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author anoop
 */
public class MazeFrame extends JFrame {

	private Maze maze;
	private MazeGUI mazeGUI;
	private JButton generateBtn;

	public MazeFrame() {
		initComponents();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initComponents() {
		mazeGUI = new MazeGUI(new Maze(0, 0));
		generateNewMaze();

		this.add(mazeGUI);

		JPanel controlPanel = new JPanel();
		this.add(controlPanel, BorderLayout.LINE_END);
		controlPanel.setLayout(new BorderLayout());
		Box b = Box.createVerticalBox();
		controlPanel.add(b);

		generateBtn = new JButton("New Maze");
		generateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				generateNewMaze();
			}
		});
		b.add(generateBtn);

		this.pack();
	}

	private void generateNewMaze() {
		maze = Maze.generateWithRandomDFS(20, 30);
		//maze = Maze.generateLTiles(5, new Point(13, 5));
		mazeGUI.setMaze(maze);
		mazeGUI.repaint();
		this.pack();
	}
}
