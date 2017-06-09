package project2;

public class Cell {
	
	/** tracks the total mine count */
	private int mineCount;
	
	/** whether or not the cell is flagged */
	private boolean isFlagged;
	
	/** whether the cell is exposed */
	private boolean isExposed;
	
	/** if the cell is a mine */
	private boolean isMine;
	
	
	/******************************************************************
	 * Constructor that handles sets the components to the correct 
	 * state
	 * 
	 * @param mineCount sets the mine count
	 * @param isFlagged sets whether the cell is flagged
	 * @param isExposed sets the state of the cell
	 * @param isMine sets whether or not the cell is a mine
	 *****************************************************************/
	public Cell(int mineCount, boolean isFlagged, boolean isExposed,
			boolean isMine) {
		super();
		this.mineCount = mineCount;
		this.isFlagged = isFlagged;
		this.isExposed = isExposed;
		this.isMine = isMine;
	}
	
	/******************************************************************
	 * Constructor that sets whether or not the cell is exposed or a 
	 * mine
	 * 
	 * @param isExposed sets the state of the cell
	 * @param isMine sets whether or not the cell is a mine
	 *****************************************************************/
	public Cell(boolean isExposed, boolean isMine) {
		this.isExposed = isExposed;
		this.isMine = isMine;
	}
	
	/******************************************************************
	 * Gets the mine count
	 * 
	 * @return mineCount integer that hold the mine count
	 *****************************************************************/
	public int getMineCount() {
		return mineCount;
	}
	
	/******************************************************************
	 * Sets the mine count
	 * 
	 * @param mineCount that is set to the cell
	 *****************************************************************/
	public void setMineCount(int mineCount) {
		this.mineCount = mineCount;
	}
	
	/******************************************************************
	 * Whether or not the cell is flagged
	 * 
	 * @return isFlagged boolean that determines the state
	 *****************************************************************/
	public boolean isFlagged() {
		return isFlagged;
	}
	
	/******************************************************************
	 * Set whether or not the cell is flagged
	 * 
	 * @param isFlagged sets the state of the cell
	 *****************************************************************/
	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}
	
	/******************************************************************
	 * Whether or not the cell is exposed
	 * 
	 * @return isExposed 
	 *****************************************************************/
	public boolean isExposed() {
		return isExposed;
	}
	
	/******************************************************************
	 * Sets the state of the cell if it is exposed
	 * 
	 * @param isExposed
	 *****************************************************************/
	public void setExposed(boolean isExposed) {
		this.isExposed = isExposed;
	}
	
	/******************************************************************
	 * Returns if the cell is a mine or not
	 * 
	 * @return isMine
	 *****************************************************************/
	public boolean isMine() {
		return isMine;
	}
	
	/******************************************************************
	 * Sets whether or not the cell is a mine
	 * 
	 * @param isMine
	 *****************************************************************/
	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}
		
}
