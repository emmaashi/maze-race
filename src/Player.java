//Import statement 
import javax.swing.ImageIcon;

// Template class for a player
public class Player extends Cell {
	
	//Constructor method
	public Player(ImageIcon image) {

		//Set the icon of the player to the image
		setIcon(image);

	}


	// Utility method for the player
	// d for delta, changing of the row/col
	public void move(int dRow, int dCol) {
		// Set the row for the object
		setRow(getRow() + dRow);
		// Set the column for the object
		setCol(getCol() + dCol);

	}

}
