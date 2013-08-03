/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.awt.Point;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author anoop
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here


		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame f = new MazeFrame();
				f.setVisible(true);
			}
		});
	}
}
