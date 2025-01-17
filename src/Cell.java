// Import statement
import javax.swing.*;

//First object class
public class Cell extends JLabel {
	
	// Generate the fields
	private int row;
	private int col;
	
	//Constructor method that passes in the row and column as parameters
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	// Empty constructor used to place random objects
	public Cell() {
		
	}
	
	//Generate getters and setters

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	//toString method
	@Override
	public String toString() {
		return "Cell [row=" + row + ", col=" + col + "]";
	}
	
	
	

}
