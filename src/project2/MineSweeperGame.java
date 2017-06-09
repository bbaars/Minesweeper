package project2;

import java.util.*;

/***********************************************************************
 * Mine Sweeper Game!
 * @author Brandon Baars 
 * @version 1.0
 * @Description A minesweeper game that keeps tracks of your wins and 
 * 				loses and allows you to change the size of the board. 
 **********************************************************************/

public class MineSweeperGame{
	
	/** arrayList of cell objects */
	private Cell[][] board;
	
	/** keeps track of the status of the game */
	private GameStatus status;
	
	/** size of the board the user enters */
	private int boardSize;
	
	/** the total amount of mines */
	private int totalMineCount;
	
	
	/******************************************************************
	 * A constructor that calls the methods to get the user to enter
	 * the size of the board and the total number of mines
	 *
	 *****************************************************************/
	public MineSweeperGame(int boardSize, int numOfMines) {
		
		this.boardSize = boardSize;
		this.totalMineCount = numOfMines;
		status = GameStatus.NotOverYet;
		board = new Cell[boardSize][boardSize];
		setEmpty();
		layMines(totalMineCount);
	
	}

	
	/******************************************************************
	 * Sets the entire board empty. It clears all mines and whether
	 * or not they're exposed or flagged.
	 *
	 *****************************************************************/
	public void setEmpty() {
		for (int r = 0; r < boardSize; r++)
			for (int c = 0; c < boardSize; c++)
				board[r][c] = new Cell(0,false,false, false);//cleared.
	}

	/******************************************************************
	 * Returns the cell at the point on the board in which it was
	 * selected
	 *
	 * @return Cell 
	 *****************************************************************/
	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	/******************************************************************
	 * Sets the point selected to exposed, and checks if the spot 
	 * selected was a mine or i it was the last spot available. Updates
	 * the status of the game.
	 *
	 *****************************************************************/
	public void select(int row, int col) {
		
		// did I lose
		if (board[row][col].isMine() && !board[row][col].isFlagged())
			status = GameStatus.Lost;
		else {
			status = GameStatus.Won;    // did I win

			for (int r = 0; r < boardSize; r++)  // are only mines left
				for (int c = 0; c < boardSize; c++)
					if(!board[r][c].isExposed() && 
							!board[r][c].isMine())
						status = GameStatus.NotOverYet;
		}
	}

	/******************************************************************
	 * Returns the state of the game, Won, Lost, NotOverYet
	 *
	 * @return GameStatus 
	 *****************************************************************/
	public GameStatus getGameStatus() {
		return status;
	}

	/******************************************************************
	 * Resets the board, prompts the user to enter new board dimensions
	 * and number of mines. Resets game status to not over and creates
	 * a new board with new mine count
	 *
	 *****************************************************************/
	public void reset() {
	
		status = GameStatus.NotOverYet;
		board = new Cell[boardSize][boardSize];
		setEmpty();
		layMines(totalMineCount);
	}

	/******************************************************************
	 * Lays the mines randomly around the board until all mines have 
	 * been laid
	 *
	 * @param mineCount
	 *****************************************************************/
	private void layMines(int mineCount) {
		int i = 0;		// ensure all mines are set in place

		Random random = new Random();
		
		//places mines until all mines have been placed
		while (i < mineCount) {
			int r = random.nextInt(boardSize);
			int c = random.nextInt(boardSize);

			//checks if random spot doesn't contain a mine
			if (!board[r][c].isMine()) {
				board[r][c].setMine(true);
				i++;
			}
		}
	}
	
	/******************************************************************
	 * Finds the amount of mines that are neighbors to the selected 
	 * empty cell
	 * 
	 * @param r the row accessed
	 * @param c the column accessed 
	 * @return the amount of mines around a selected cell
	 *****************************************************************/
	public int getNeighbors(int r, int c){
		
		int minRow, minCol, maxRow,maxCol;
		int mineCount = 0; 
		
		//finds the minimum row/column for surrounding 8 cells
		//checks that they don't go out of bounds
		minRow = (r <= 0 ? 0 : r-1);
		minCol = (c <= 0 ? 0 : c-1);
		maxRow = (r >= boardSize - 1 ? boardSize - 1 : r+1);
		maxCol = (c >= boardSize - 1 ? boardSize - 1 : c+1);
		
		//counts within the eight cells the amount of neighbors
		for(int i = minRow ; i <= maxRow ; i++)
			for(int j = minCol ; j <= maxCol ; j++)
				if(board[i][j].isMine()){
					mineCount++;
				}		
		return mineCount;
	}
	
	/******************************************************************
	 * Reveals all empty cells and cells that have neighbors. Uses
	 * recursion to cycle through all empty cells. 
	 *
	 * @param r
	 * @param c
	 *****************************************************************/
	public void reveal(int r, int c){

		//if the cell wants to go out of bounds
		if(r < 0 || r >= boardSize || c < 0 || c >= boardSize)
			return;

		//if cell is already empty
		if(board[r][c].isExposed())
			return;
		
		//if cell is flagged
		if(board[r][c].isFlagged())
			return;

		//checks that the selection is empty
		if(!board[r][c].isMine() && !board[r][c].isExposed()
				&& getNeighbors(r,c) == 0){	

			board[r][c].setExposed(true);
			//uses recursion with 4 surrounding cell
			reveal(r+1,c); //bottom cell
			reveal(r-1,c);  //top cell
			reveal(r,c+1);  //right cell
			reveal(r,c-1);	//left cell		
			reveal(r+1,c+1); //bottom right cell
			reveal(r-1,c-1); //top left cell
			reveal(r-1,c+1); //top right cell
			reveal(r+1,c-1); //bottom left cell

		}		
		
		//if the cell has neighbors, only display that cell
		if(!board[r][c].isMine() && !board[r][c].isFlagged() &&
				getNeighbors(r,c) > 0)
				board[r][c].setExposed(true);	
		else 
			return;
}
		
}
