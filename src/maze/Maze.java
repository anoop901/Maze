/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.util.List;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;
import sun.awt.HorizBagLayout;

/**
 *
 * @author anoop
 */
public class Maze {

	private int numRows;
	private int numCols;
	private boolean[][] horizWalls;
	private boolean[][] vertWalls;

	public Maze(int nR, int nC) {
		numRows = nR;
		numCols = nC;
		horizWalls = new boolean[numRows + 1][numCols];
		vertWalls = new boolean[numRows][numCols + 1];
	}

	public static Maze generateWithRandomDFS(int nR, int nC) {

		Maze m = new Maze(nR, nC);
		boolean[][] visited = new boolean[nR][nC];

		for (int i = 0; i <= nR; i++) {
			for (int j = 0; j < nC; j++) {
				m.horizWalls[i][j] = true;
			}
		}

		for (int i = 0; i < nR; i++) {
			for (int j = 0; j <= nC; j++) {
				m.vertWalls[i][j] = true;
			}
		}

		Stack<Point> stack = new Stack<Point>();
		stack.add(new Point(0, 0));
		visited[0][0] = true;

		while (!stack.isEmpty()) {

			Point currentPoint = stack.peek();
			int currRow = currentPoint.x;
			int currCol = currentPoint.y;
			ArrayList<Point> possibleNeighbors = new ArrayList<Point>();

			// find all the possible neighbors
			if (currRow - 1 >= 0 && !visited[currRow - 1][currCol])
				possibleNeighbors.add(new Point(currRow - 1, currCol));
			if (currRow + 1 < nR && !visited[currRow + 1][currCol])
				possibleNeighbors.add(new Point(currRow + 1, currCol));
			if (currCol - 1 >= 0 && !visited[currRow][currCol - 1])
				possibleNeighbors.add(new Point(currRow, currCol - 1));
			if (currCol + 1 < nC && !visited[currRow][currCol + 1])
				possibleNeighbors.add(new Point(currRow, currCol + 1));

			if (possibleNeighbors.isEmpty()) {
				// there were no possible neighbors

				// backtrack
				stack.pop();

			} else {
				// there were some possible neighbors

				// choose a random possible neighbor
				Point neighbor = possibleNeighbors.get((int) (Math.random() * possibleNeighbors.size()));
				int nextRow = neighbor.x;
				int nextCol = neighbor.y;

				// remove the wall between the current point and the neighbor
				if (nextRow == currRow - 1) {
					m.horizWalls[currRow][currCol] = false;
				} else if (nextRow == currRow + 1) {
					m.horizWalls[currRow + 1][currCol] = false;
				} else if (nextCol == currCol - 1) {
					m.vertWalls[currRow][currCol] = false;
				} else if (nextCol == currCol + 1) {
					m.vertWalls[currRow][currCol + 1] = false;
				}

				// mark neigbor as visited
				visited[nextRow][nextCol] = true;

				// push neighbor to stack
				stack.push(neighbor);
			}
		}

		return m;
	}
	
	public static Maze generateLTiles(int n, Point empty) {
		
		for (int i = 0; i < n; i++)
			System.out.print("\t");
		System.out.println(n + ", (" + empty.x + ", " + empty.y + ")");
		
		int length = 1;
		for (int i = 0; i < n; i++)
			length *= 2;
		
		Maze m = new Maze(length, length);
		
		for (int i = 0; i <= length; i++) {
			for (int j = 0; j < length; j++) {
				m.horizWalls[i][j] = true;
			}
		}

		for (int i = 0; i < length; i++) {
			for (int j = 0; j <= length; j++) {
				m.vertWalls[i][j] = true;
			}
		}
		
		if (n == 0)
			return m;
		
		Maze topLeft;
		if (empty.x < length / 2 && empty.y < length / 2) {
			m.horizWalls[length / 2][length / 2] = false;
			m.vertWalls[length / 2][length / 2] = false;
			topLeft = Maze.generateLTiles(n - 1, new Point(empty.x, empty.y));
		} else {
			topLeft = Maze.generateLTiles(n - 1, new Point(length / 2 - 1, length / 2 - 1));
		}
		
		Maze topRight;
		if (empty.x < length / 2 && empty.y >= length / 2) {
			m.horizWalls[length / 2][length / 2 - 1] = false;
			m.vertWalls[length / 2][length / 2] = false;
			topRight = Maze.generateLTiles(n - 1, new Point(empty.x, empty.y - length / 2));
		} else {
			topRight = Maze.generateLTiles(n - 1, new Point(length / 2 - 1, 0));
		}
		
		Maze bottomLeft;
		if (empty.x >= length / 2 && empty.y < length / 2) {
			m.horizWalls[length / 2][length / 2] = false;
			m.vertWalls[length / 2 - 1][length / 2] = false;
			bottomLeft = Maze.generateLTiles(n - 1, new Point(empty.x - length / 2, empty.y));
		} else {
			bottomLeft = Maze.generateLTiles(n - 1, new Point(0, length / 2 - 1));
		}
		
		Maze bottomRight;
		if (empty.x >= length / 2 && empty.y >= length / 2) {
			m.horizWalls[length / 2][length / 2 - 1] = false;
			m.vertWalls[length / 2 - 1][length / 2] = false;
			bottomRight = Maze.generateLTiles(n - 1, new Point(empty.x - length / 2, empty.y - length / 2));
		} else {
			bottomRight = Maze.generateLTiles(n - 1, new Point(0, 0));
		}
		
		for (int i = 0; i < length / 2; i++) {
			for (int j = 0; j < length / 2; j++) {
				m.horizWalls[i][j] &= topLeft.horizWalls[i][j];
				m.vertWalls[i][j] &= topLeft.vertWalls[i][j];
			}
		}
		
		for (int i = 0; i < length / 2; i++) {
			for (int j = 0; j < length / 2; j++) {
				m.horizWalls[i][j + length / 2] &= topRight.horizWalls[i][j];
				m.vertWalls[i][j + length / 2] &= topRight.vertWalls[i][j];
			}
		}
		
		for (int i = 0; i < length / 2; i++) {
			for (int j = 0; j < length / 2; j++) {
				m.horizWalls[i + length / 2][j] &= bottomLeft.horizWalls[i][j];
				m.vertWalls[i + length / 2][j] &= bottomLeft.vertWalls[i][j];
			}
		}
		
		for (int i = 0; i < length / 2; i++) {
			for (int j = 0; j < length / 2; j++) {
				m.horizWalls[i + length / 2][j + length / 2] &= bottomRight.horizWalls[i][j];
				m.vertWalls[i + length / 2][j + length / 2] &= bottomRight.vertWalls[i][j];
			}
		}
		
		return m;
	}
	
	public List<Point> findPath(Point from, Point to) {
		Stack<Point> path = new Stack<Point>();
		path.push(from);
		
		if (from.equals(to))
			return path;
		
		boolean[][] visited = new boolean[numRows][numCols];
		visited[from.x][from.y] = true;
		
		while (!path.isEmpty()) {
			Point currentPoint = path.peek();
			int currRow = currentPoint.x;
			int currCol = currentPoint.y;
			
			Point nextPoint;

			// find all the possible neighbors
			if (!horizWalls[currRow][currCol] && !visited[currRow - 1][currCol]) // above
				nextPoint = new Point(currRow - 1, currCol);
			else if (!horizWalls[currRow + 1][currCol] && !visited[currRow + 1][currCol]) // below
				nextPoint = new Point(currRow + 1, currCol);
			else if (!vertWalls[currRow][currCol] && !visited[currRow][currCol - 1]) // to left
				nextPoint = new Point(currRow, currCol - 1);
			else if (!vertWalls[currRow][currCol + 1] && !visited[currRow][currCol + 1]) // to right
				nextPoint = new Point(currRow, currCol + 1);
			else
				nextPoint = null;
			
			if (nextPoint != null) {
				visited[nextPoint.x][nextPoint.y] = true;
				path.push(nextPoint);
				if (nextPoint.equals(to)) {
					return path;
				}
			} else {
				path.pop();
			}
		}
		
		
		return null;
	}

	/**
	 * @return the numRows
	 */
	public int getNumRows() {
		return numRows;
	}

	/**
	 * @return the numCols
	 */
	public int getNumCols() {
		return numCols;
	}

	/**
	 * @return the horizBars
	 */
	public boolean[][] getHorizBars() {
		return horizWalls;
	}

	/**
	 * @return the vertBars
	 */
	public boolean[][] getVertBars() {
		return vertWalls;
	}
}
