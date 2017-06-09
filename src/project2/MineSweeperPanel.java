package project2;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/***********************************************************************
 * Mine Sweeper Game!
 * @author Brandon Baars 
 * @version 1.0
 * @Description A minesweeper gui that displays the board and buttons
 * 				and allows the user to click on them and interact.
 **********************************************************************/


public class MineSweeperPanel extends JPanel {
		
	/** universal version identifier for serializable class */
	private static final long serialVersionUID = 1L;
	
	/** creates an array of JButtons that serves as the board */
	private JButton[][] board;
	
	/** quit button to end the game */
	private JMenuItem quitButton;
	
	/** a cell of type cell */
	private Cell iCell;
	
	/** a new mine sweeper game */
	private MineSweeperGame game;
	
	/** image icons for the board */
	private ImageIcon mineIcon, hiddenIcon, emptyIcon, flagIcon;
	
	/** image icons for displaying neighbors */
	private ImageIcon one,two,three,four,five,six,seven,eight;
	
	/** menu bar to add the quit button to */
	private JMenuBar menuBar;
	
	/** two different JPanels to add components to */
	private JPanel bottom, center;
	
	/** to expose and hide the mines on the board */
	private JButton isExposedButton, hideAll;
	
	/** determines the state of the hide and expose mines button */
	private boolean exposeMines;
	
	/** holds the size of the board */
	private int boardSize;
	
	/** holds the number of mines */
	private int numOfMines;
	
	/** labels to display the amount of wins and loses */
	private JLabel loseLabel,winLabel, minesLabel;
	
	/** file menu */
	private JMenu fileMenu;
	
	/** holds the amount of wins */
	private int wins;
	
	/** amounts of losses */
	private int losses;
	
	/** adds a button listener to when the board is pushed */
	private ButtonListener listener;
	
	/** adds a mouse listener for flagging */
	private MouseListener l;
	
	/** to update the mines Label */
	private int minesRemaining;
	
	/** to start a new game */
	private JMenuItem newGameItem;
	
	private JFrame frame;

	
	/******************************************************************
	 * Constructor that handles the generation of all the display and 
	 * board
	 * 
	 *****************************************************************/
	public MineSweeperPanel(JFrame frame) {
				
		//sets all the button and panels
		bottom = new JPanel();
		center = new JPanel();
		isExposedButton = new JButton("Expose All");
		isExposedButton.setEnabled(true);
		hideAll = new JButton("Hide All");
		hideAll.setEnabled(true);
		exposeMines = false;
		this.frame = frame;
		
		add(bottom, BorderLayout.PAGE_START);
		add(center, BorderLayout.PAGE_END);
		bottom.setLayout(new GridLayout(3, 2));
		bottom.add(hideAll, BorderLayout.PAGE_START);
		bottom.add(isExposedButton, BorderLayout.PAGE_START);
		
		
		//sets the wins and loses to 0
		wins = 0;
		losses = 0;
		
		hiddenIcon = new ImageIcon(this.getClass().getResource("/images"
				+ "/hidden.png"));
		
		Image hidden1 = hiddenIcon.getImage();
		Image hidden2 = hidden1.getScaledInstance(20, 20,
				java.awt.Image.SCALE_SMOOTH);
		hiddenIcon = new ImageIcon(hidden2);
		
		//imports the icons from the folder
		mineIcon = new ImageIcon(this.getClass().getResource("/images/"
				+ "mine.png"));
	
		emptyIcon = new ImageIcon(this.getClass().getResource("/images/"
				+ "empty.png"));
		flagIcon = new ImageIcon(this.getClass().getResource("/images/"
				+ "flag.png"));
		one = new ImageIcon(this.getClass().getResource("/images/1.png"
				));
		two = new ImageIcon(this.getClass().getResource("/images/2.png"
				));
		three = new ImageIcon(this.getClass().getResource("/images/3."
				+ "png"));
		four = new ImageIcon(this.getClass().getResource("/images/4."
				+ "png"));
		five = new ImageIcon(this.getClass().getResource("/images/5."
				+ "png"));
		six = new ImageIcon(this.getClass().getResource("/images/6."
				+ "png"));
		seven = new ImageIcon(this.getClass().getResource("/images/"
				+ "7.png"));
		eight = new ImageIcon(this.getClass().getResource("/images/"
				+ "8.png"));


		//resizes the icons to fit on the button
		Image image = mineIcon.getImage();
		Image mineIcon2 = image.getScaledInstance(20, 20, 
				java.awt.Image.SCALE_SMOOTH);
		mineIcon = new ImageIcon(mineIcon2);

		Image flag1 = flagIcon.getImage();
		Image flag = flag1.getScaledInstance(20, 20,
				java.awt.Image.SCALE_SMOOTH);
		flagIcon = new ImageIcon(flag);

		Image image1 = one.getImage();
		Image oneIcon = image1.getScaledInstance(20, 20, 
				java.awt.Image.SCALE_SMOOTH);
		one = new ImageIcon(oneIcon);

		Image image2 = two.getImage();
		Image twoIcon = image2.getScaledInstance(20, 20,
				java.awt.Image.SCALE_SMOOTH);
		two = new ImageIcon(twoIcon);

		Image image3 = three.getImage();
		Image threeIcon = image3.getScaledInstance(20, 20,
				java.awt.Image.SCALE_SMOOTH);
		three = new ImageIcon(threeIcon);

		Image image4 = four.getImage();
		Image fourIcon = image4.getScaledInstance(20, 20, 
				java.awt.Image.SCALE_SMOOTH);
		four = new ImageIcon(fourIcon);

		Image image5 = five.getImage();
		Image fiveIcon = image5.getScaledInstance(20, 20,
				java.awt.Image.SCALE_SMOOTH);
		five = new ImageIcon(fiveIcon);

		Image image6 = six.getImage();
		Image sixIcon = image6.getScaledInstance(20, 20, 
				java.awt.Image.SCALE_SMOOTH);
		six = new ImageIcon(sixIcon);

		Image image7 = seven.getImage();
		Image sevenIcon = image7.getScaledInstance(20, 20, 
				java.awt.Image.SCALE_SMOOTH);
		seven = new ImageIcon(sevenIcon);

		Image image8 = eight.getImage();
		Image eightIcon = image8.getScaledInstance(20, 20,
				java.awt.Image.SCALE_SMOOTH);
		eight = new ImageIcon(eightIcon);
	
		// create game, listeners
		listener = new ButtonListener();
		l = new MouseListener();
		isExposedButton.addActionListener(listener);
		hideAll.addActionListener(listener);

		//creates a new game and gets the size of board and mine count
		setSizeOfBoard();
		setNumberOfMines();
		game = new MineSweeperGame(boardSize, numOfMines);	
		minesRemaining = numOfMines;
		this.frame.setSize((boardSize*20)+250, (boardSize*20)+300);
		
		// create the board
		createBoard(boardSize);
		
		//sets the labels
		loseLabel = new JLabel("Loses: " + losses);
		winLabel = new JLabel("Wins: " + wins);
		minesLabel = new JLabel("Number of Mines Left: "
				+ minesRemaining);
				
		loseLabel.setForeground(Color.RED);
		loseLabel.setBackground(Color.BLACK);
		winLabel.setForeground(Color.BLUE);
		
		// add all to contentPane
		
		bottom.add(loseLabel, BorderLayout.PAGE_START);
		bottom.add(winLabel,BorderLayout.PAGE_START);
		bottom.add(minesLabel, BorderLayout.PAGE_START);
		

		//adds the menus to the top
		fileMenu = new JMenu("File");
		quitButton = new JMenuItem("Quit");
		newGameItem = new JMenuItem("New Game");
		fileMenu.add(newGameItem);
		fileMenu.add(quitButton);
		menuBar = new JMenuBar();	
		frame.setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		
		//sets the panels to the screen 
		quitButton.addActionListener(listener);
		newGameItem.addActionListener(listener);

	
	}
	
	/******************************************************************
	 * Private method that prompts the user to enter the size of the 
	 * board. It then sets the variable to the size entered. 
	 *
	 *****************************************************************/
	private void setSizeOfBoard(){
		
		int board;
			
		//make sure the entered data is a number
		try{
			String size = JOptionPane.showInputDialog(null, "Enter in "
					+ "the size of the board: ");
				
			if(size == null)
				System.exit(0);
			
			board = Integer.parseInt(size);
			
		}
		catch(NumberFormatException error){
			
			JOptionPane.showMessageDialog(null, "Invalid Number, "
					+ "default board size is 10");
			board = 10;
			
		}
		
		//checks if it's out of range
		if(board < 3 || board > 30){
			
			JOptionPane.showMessageDialog(null, "Sorry board size must"
			+ " be between 3 and 30, will be set to default size 10");
			board = 10;
		}	
		
		this.boardSize = board;
		
	}
	
	/******************************************************************
	 * Returns the size of the board
	 * 
	 * @return boardSize integer that is the size of the board
	 *****************************************************************/
	public int getBoardSize(){
		
		return boardSize;
	}
	
	/******************************************************************
	 * Prompts the user to enter total number of mines they want.
	 * Checks for validity and that there are enough spaces on the board
	 *
	 *****************************************************************/
	private void setNumberOfMines(){
		
		int mines;
			
		//makes sure the total mines entered is a number
		try{
			String num = JOptionPane.showInputDialog(null, "Enter"
					+ " total number of mines: ");
							
			if(num == null)
				System.exit(0);			
			
			mines = Integer.parseInt(num);
		}
		
		//catches it just in case it isn't and sets default value
		catch(NumberFormatException error){
			
			JOptionPane.showMessageDialog(null, "Invalid Number, "
					+ "default mine is fourth of the board size");
			mines = (boardSize*boardSize)/4;
			
		}
		
		//checks if the amount of mines exceed number of spaces
		if(mines>=(boardSize*boardSize) || mines < 1){
			
			JOptionPane.showMessageDialog(null, "Sorry mine count must"
					+ " be less than board size, default is fourth of "
					+ "the board size");
			mines = (boardSize*boardSize)/4;
		}
		
			
		this.numOfMines = mines;
			
	}
	
	/******************************************************************
	 * Returns the number of mines that the user has set
	 *
	 * @return totalMineCount
	 *****************************************************************/
	public int getNumberOfMines(){
		
		return numOfMines;
	}

	
	/******************************************************************
	 * Creates the board with the desire size. Sets the buttons on the
	 * screen and adds a listener to each
	 *
	 * @param boardSize
	 *****************************************************************/
	private void createBoard(int boardSize){
		
		board = new JButton[boardSize][boardSize];	
		center.setLayout(new GridLayout(boardSize,boardSize,1,1));
		
		//creates the board and adds listeners 
		for (int row = 0; row < boardSize; row++){
			for (int col = 0; col < boardSize; col++) {
				board[row][col] = new JButton("",hiddenIcon);
				board[row][col].addActionListener(listener);
				board[row][col].addMouseListener(l);
				board[row][col].setPreferredSize(new Dimension(20,20));
				board[row][col].setEnabled(true);
				center.add(board[row][col]);
			}
		}
		
		add(center, BorderLayout.PAGE_END);
	}
	
	/******************************************************************
	 * Displays the board based on the state of the cells
	 * 
	 *****************************************************************/
	private void displayBoard() {

		//runs through the entire board
		for (int r = 0; r < boardSize; r++)
			for (int c = 0; c < boardSize; c++) {
				iCell = game.getCell(r, c);

				//if its exposed it displays the neighbors
				if (iCell.isExposed()){
					displayNeighbors(r,c);
				}
				//if its not exposed sets the board to a hidden icon
				else if(!exposeMines && !iCell.isFlagged())
					board[r][c].setIcon(hiddenIcon);			
			}
		
				
	}
	
	/******************************************************************
	 * Exposes the mines when the button is pressed or when the game
	 * ends
	 * 
	 *****************************************************************/
	private void exposeMines() {
		
		//sets the state of the expose mines
		exposeMines = true;
		
		//runs through the entire board at each cell
		for(int r = 0 ; r < boardSize ; r++){
			for(int c = 0 ; c < boardSize ; c++){
				iCell = game.getCell(r,c);
				
				//if the cell is a mine sets its icon
				if(iCell.isMine() && !iCell.isFlagged()){
					board[r][c].setIcon(mineIcon);
				}
			}
		}
	}
	
	/******************************************************************
	 * Hides the mines when the button is pushed 
	 * 
	 *****************************************************************/
	private void hideMines() {
		
		//sets the state of expose mines
		exposeMines = false;
		
		//runs through the entire board
		for(int r = 0 ; r < boardSize ; r++){
			for(int c = 0 ; c < boardSize ; c++){
				iCell = game.getCell(r,c);
				
				//if the cell is a mine its now a hidden icon
				if(iCell.isMine() && !iCell.isFlagged()){
					board[r][c].setIcon(hiddenIcon);
				}
			}
		}
	}
	
	/******************************************************************
	 * Displays the neighbors of the cell that it is passed, updates
	 * the icon appropriately
	 * 
	 * @param r the row that is passed
	 * @param c the col that is passed
	 *****************************************************************/
	private void displayNeighbors(int r, int c) {
		
		iCell = game.getCell(r, c);
		
		if(iCell.isFlagged())			
			return;
				
		//based on how many neighbors updates the board
		if(game.getNeighbors(r, c) == 1)							
			board[r][c].setIcon(one);					
		else if(game.getNeighbors(r, c) == 2)
			board[r][c].setIcon(two);	
		else if(game.getNeighbors(r, c) == 3)
			board[r][c].setIcon(three);
		else if(game.getNeighbors(r, c) == 4)
			board[r][c].setIcon(four);
		else if(game.getNeighbors(r, c) == 5)
			board[r][c].setIcon(five);
		else if(game.getNeighbors(r, c) == 6)
			board[r][c].setIcon(six);
		else if(game.getNeighbors(r, c) == 7)
			board[r][c].setIcon(seven);
		else if(game.getNeighbors(r, c) == 8)
			board[r][c].setIcon(eight);	
		
		//if no neighbors it shows empty
		else{
			board[r][c].setIcon(emptyIcon);
		}
		
	}	
	
	/******************************************************************
	 * New game that is called, removes all the buttons and sets 
	 * everything back to a new game with new board size and number of
	 * mines
	 * 
	 *****************************************************************/
	private void newGame(){
		
		//removes all the buttons
		for (int r = 0; r < boardSize; r++)
			for (int c = 0; c < boardSize; c++){		
				board[r][c].setIcon(null);	
				center.remove(board[r][c]);						
		}
		exposeMines = false;	
		setSizeOfBoard();
		setNumberOfMines();
		game = new MineSweeperGame(boardSize, numOfMines);
		game.reset();
		minesLabel.setText("Number of Mines Left: " 
		+ numOfMines);
		minesRemaining = numOfMines;
		createBoard(boardSize);
		frame.setSize((boardSize*20)+250, (boardSize*20)+300);
		displayBoard();
		exposeMines();
		hideMines();
		
		
	}
			
	/******************************************************************
	 * Private class that handles all the button events
	 * 
	 *****************************************************************/
	private class ButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
	
			//gets the source of the button pressed
			JComponent buttonPressed = (JComponent) e.getSource();
			
			//if the button pressed exposes or hides the mines
			if(buttonPressed == isExposedButton)
				exposeMines();	
			if(buttonPressed == hideAll)
				hideMines();
			
			//runs through the board to see which cell was pressed
			for (int r = 0; r < boardSize; r++)
				for (int c = 0; c < boardSize; c++){
					if (buttonPressed == board[r][c]){
						game.select(r,c);								
						game.reveal(r,c);
						game.select(r,c);
						displayNeighbors(r, c);
						
					}
				}
			
			displayBoard();
			
			//if the game is over
			if (game.getGameStatus() == GameStatus.Lost) {
								
				//increments loses
				losses++;				
				loseLabel.setText("Loses: " + losses);
				
				//shows the user the mines
				exposeMines();
				
				//asks if they want to play again
				int dialog = JOptionPane.showOptionDialog(null,
						"You Lost!"+ " Play Again?","Play Again?"
						,JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,null,null,null);
				
				if(dialog == JOptionPane.YES_OPTION)
					newGame();
				if(dialog == JOptionPane.NO_OPTION)
					System.exit(0);
			}
			
			// if the user has won the game
			if (game.getGameStatus() == GameStatus.Won) {
				
				//increments wins 
				wins ++;
				winLabel.setText("Wins: "+wins);
				
				//prompts them to play again
				int dialog = JOptionPane.showOptionDialog(null,
						"You WON!" + " Play Again?","Play Again?"
						,JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,null,null,null);
				
				if(dialog == JOptionPane.YES_OPTION)
					newGame();
				if(dialog == JOptionPane.NO_OPTION)
					System.exit(0);
				
			}
			
			if(buttonPressed == quitButton)			
				System.exit(0);
			if(buttonPressed == newGameItem)
				newGame();
			


		}
	
	}
	
	/******************************************************************
	 * Private class to handle the right click option on the mouse
	 * The blank methods are there for overriding. 
	 *****************************************************************/
	
	private class MouseListener implements java.awt.event.MouseListener{

		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		/**************************************************************
		 * Checks if the mouse was right clicked, on which cell it was
		 * clicked on, whether or not it was flagged already, and 
		 * whether or not it exposed already.
		 * 
		 *************************************************************/
		@Override
		public void mousePressed(MouseEvent e) {

			//if the mouse was right clicked
			if(e.getButton() == MouseEvent.BUTTON3){

				//iterates through the board
				for (int r = 0; r < boardSize; r++)
					for (int c = 0; c < boardSize; c++){
						iCell = game.getCell(r,c);

						//which cell was clicked on 
						if(board[r][c] == e.getSource() && 
								!iCell.isExposed() &&
								!iCell.isFlagged()){
							
							if(minesRemaining > 0){
								board[r][c].setIcon(flagIcon);
								iCell.setFlagged(true);
								minesRemaining--;
								minesLabel.setText("Number of Mines "
										+ "Left: " + minesRemaining);
							}
						}

						//checks if the mines are exposed
						else if(board[r][c] == e.getSource() &&
								iCell.isMine() && exposeMines &&
								!iCell.isFlagged()){
							
							if(minesRemaining > 0){
								iCell.setFlagged(true);
								board[r][c].setIcon(flagIcon);
								minesRemaining--;
								minesLabel.setText("Number of Mines "
										+ "Left: " + minesRemaining);
							}
						}


						//if it was already flagged, it clears it
						else if(board[r][c] == e.getSource() &&
								iCell.isFlagged() && !iCell.isMine()){
							board[r][c].setIcon(hiddenIcon);
							iCell.setFlagged(false);
							minesRemaining++;
							minesLabel.setText("Number of Mines Left: "
									+ minesRemaining);
						}

						//checks if mines are not exposed and flagged
						else if(board[r][c] == e.getSource() &&
								iCell.isMine() && !exposeMines &&
								iCell.isFlagged()){
							iCell.setFlagged(false);
							board[r][c].setIcon(hiddenIcon);
							minesRemaining++;
							minesLabel.setText("Number of Mines Left: "
									+ minesRemaining);
						}
						//checks if the mines are exposed and flagged
						else if(board[r][c] == e.getSource() &&
								iCell.isMine() && exposeMines &&
								iCell.isFlagged()){
							iCell.setFlagged(false);
							board[r][c].setIcon(mineIcon);
							minesRemaining++;
							minesLabel.setText("Number of Mines Left: "
									+ minesRemaining);
						}
					}


			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
			
	}
}


