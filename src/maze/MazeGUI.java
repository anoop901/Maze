/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author anoop
 */
public class MazeGUI extends JPanel implements MouseListener, MouseMotionListener {

	private Maze maze;
	private Point from;
	private Point to;
	private List<Point> path;
	private final int cellSize;
	private final int pointRadius;

	public MazeGUI(Maze m) {
		maze = m;
		from = null;
		to = null;
		path = null;
		cellSize = 20;
		pointRadius = 3;
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(cellSize * (m.getNumCols() + 2), cellSize * (m.getNumRows() + 2)));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public void setMaze(Maze m) {
		maze = m;
		setPreferredSize(new Dimension(cellSize * (m.getNumCols() + 2), cellSize * (m.getNumRows() + 2)));
		path = maze.findPath(new Point(0, 0), new Point(maze.getNumRows() - 1, maze.getNumCols() - 1));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1));

		for (int i = 0; i <= maze.getNumRows(); i++) {
			for (int j = 0; j < maze.getNumCols(); j++) {
				if (maze.getHorizBars()[i][j]) {
					g2.drawLine((j + 1) * cellSize, (i + 1) * cellSize, (j + 2) * cellSize, (i + 1) * cellSize);
				}
			}
		}

		for (int i = 0; i < maze.getNumRows(); i++) {
			for (int j = 0; j <= maze.getNumCols(); j++) {
				if (maze.getVertBars()[i][j]) {
					g2.drawLine((j + 1) * cellSize, (i + 1) * cellSize, (j + 1) * cellSize, (i + 2) * cellSize);
				}
			}
		}

		if (path != null && !path.isEmpty()) {

			g2.setColor(Color.RED);
			g2.setStroke(new BasicStroke(2));

			Point p1 = path.get(0);
			g2.fillOval((2 * p1.y + 3) * cellSize / 2 - pointRadius, (2 * p1.x + 3) * cellSize / 2 - pointRadius,
					2 * pointRadius, 2 * pointRadius);
			for (int i = 1; i < path.size(); i++) {
				Point p2 = path.get(i);
				g2.drawLine((2 * p1.y + 3) * cellSize / 2, (2 * p1.x + 3) * cellSize / 2,
						(2 * p2.y + 3) * cellSize / 2, (2 * p2.x + 3) * cellSize / 2);
				p1 = p2;
				g2.fillOval((2 * p1.y + 3) * cellSize / 2 - pointRadius, (2 * p1.x + 3) * cellSize / 2 - pointRadius,
						2 * pointRadius, 2 * pointRadius);
			}
		}
	}

	private Point pointAtCoordinates(Point coords) {
		int r = coords.y / cellSize - 1;
		int c = coords.x / cellSize - 1;
		if (r >= 0 && c >= 0 && r < maze.getNumRows() && c < maze.getNumCols())
			return new Point(r, c);
		else
			return null;
	}

	@Override
	public void mouseClicked(MouseEvent me) {
	}

	@Override
	public void mousePressed(MouseEvent me) {
		from = pointAtCoordinates(me.getPoint());
		to = from;
		if (from != null)
			path = maze.findPath(from, to);
		else
			path = null;
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent me) {
	}

	@Override
	public void mouseExited(MouseEvent me) {
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		if (from != null) {
			Point currentPoint = pointAtCoordinates(me.getPoint());
			if (currentPoint != null) {
				if (!currentPoint.equals(to)) {
					to = currentPoint;
					path = maze.findPath(from, to);
					repaint();
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent me) {
	}
}
