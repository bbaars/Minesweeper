package project2;

import javax.swing.*;

public class MineSweeper {
	
	/******************************************************************
	 * Main function that constructs the JFrame which starts and 
	 * maintains the game
	 *****************************************************************/
	public static void main (String[] args)
	{
		JFrame frame = new JFrame ("***Mine Sweeper***");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		MineSweeperPanel panel = new MineSweeperPanel(frame);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

}
