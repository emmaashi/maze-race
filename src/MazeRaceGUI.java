//Import statements
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.swing.*;
import java.util.Scanner;

// Start of the main class 
public class MazeRaceGUI extends JFrame implements KeyListener, ActionListener {

	// Create the numerical constants related to the size of the maze
	public final static int CELL_SIZE = 25;
	public final static int NUM_CELLS_WIDTH = 27;
	public final static int NUM_CELLS_HEIGHT = 27;
	public static int NUM_COINS = 15;
	public final static int NUM_MUSHROOMS = 3;// get rid of final if you want it to be a variable (different levels)
	public static int numCoinsCollected;

	// Create the constants related to the images on the maze
	private final ImageIcon WALL = new ImageIcon("imagesMaze/" + MazeRaceCustom.colourWall + " square.png");
	private final ImageIcon OUT_OF_BOUNDS = new ImageIcon(
			"imagesMaze/" + MazeRaceCustom.colourOutOfBounds + " square.png");
	private final static ImageIcon PATH = new ImageIcon("imagesMaze/grey square.png"); // always grey
	private final ImageIcon COIN = new ImageIcon("imagesMaze/gold coin.gif");

	// An array of icons for all the possible characters
	private final ImageIcon[] PLAYER_ICON = new ImageIcon[7]; // index 0 is not used

	// Create the image icons of all the powerups
	private final ImageIcon MUSHROOM = new ImageIcon("imagesPowerUps/mushroom.png");
	private final ImageIcon PORTAL1 = new ImageIcon("imagesPowerUps/portal.png");
	private final ImageIcon PORTAL2 = new ImageIcon("imagesPowerUps/portal.png");

	// Create the player object
	private Player player;

	// JLabel array that holds the maze dimensions
	private static Cell[][] maze = new Cell[NUM_CELLS_WIDTH][NUM_CELLS_HEIGHT];
	// Create the scoreboard panel
	private JPanel scoreboardPanel = new JPanel();
	// Main panel that holds the maze
	private JPanel mazePanel = new JPanel();
	// General timer that goes up by milliseconds
	private Timer gameTimer = new Timer(100, this);

	public static int highScoreValue; // store the value of the high score
	public static String highScore; // same as the highScoreValue, but a String datatype so that it can be added to the label
	private static String initials; // store the user's initials
	public int level[] = new int[3]; // determine which level the user wishes to play
	
	// Row and column positions
	public int dRow;
	public int dCol;
	public double seconds; // timer seconds

	// Create the fields for the portals
	public int portal1Row;
	public int portal2Row;
	public int portal1Col;
	public int portal2Col;
	public boolean usedPortal1 = false;
	public boolean usedPortal2 = false;

	// Create the labels for the scoreboard
	private JLabel scoreLabel = new JLabel("0");
	private JLabel timerLabel = new JLabel("0");
	private JLabel highScoreLabel = new JLabel(String.valueOf(highScore));
	private JLabel allTimeScoreLabel = new JLabel();

	// Start the time
	private double time = 0;

	// Getter for the initials
	public static String getInitials() {
		return initials;
	}

	// Create the application using the constructor
	@SuppressWarnings("static-access")
	public MazeRaceGUI(String initials, int level) {

		this.initials = initials;

		// Method calls
		setPlayer();
		scoreboardPanelSetup();
		mazePanelSetup();
		frameSetup();

		// The default game is level 1 with no added features

		// Execute the level2 method if the user chooses level 2
		if (level == 2) {
			level2();

		// Execute the level3 method if the user chooses level 3
		} else if (level == 3)
			level3();

	}

	// setPlayer() - set the character icon of the player
	private void setPlayer() {

		// Get the name from the MazeRaceCustom class
		// Assign the name of the image file to the variable name
		String name = ("imagesCharacters/" + (MazeRaceCustom.characterName.toLowerCase()));
		PLAYER_ICON[0] = new ImageIcon(name + "0.gif");
		PLAYER_ICON[1] = new ImageIcon(name + "1.gif");
		PLAYER_ICON[2] = new ImageIcon(name + "2.gif");
		PLAYER_ICON[3] = new ImageIcon(name + "3.gif");

		// The koopa troopa character is not a gif (couldn't find one), so access the
		// file accordingly
		if (MazeRaceCustom.characterName == "KOOPATROOPA") {
			PLAYER_ICON[0] = new ImageIcon(name + "0.png");
			PLAYER_ICON[1] = new ImageIcon(name + "1.png");
			PLAYER_ICON[2] = new ImageIcon(name + "2.png");
			PLAYER_ICON[3] = new ImageIcon(name + "3.png");
		}
		// Make the player start by facing the right
		player = new Player(PLAYER_ICON[1]);
	}

	// scoreboardPanel() - setup the dimensions and attach necessary labels
	private void scoreboardPanelSetup() {

		// Set the bounds
		scoreboardPanel.setBounds(0, 0, CELL_SIZE * NUM_CELLS_WIDTH, 110); // if u want bigger scoreboard, change 50
		scoreboardPanel.setLayout(null);
		scoreboardPanel.setBackground(Color.BLACK);

		// Set up the scorelabels
		scoreLabel.setBounds(scoreboardPanel.getWidth() / 2 - 40, 15, 100, 40); // x coordinate, half of the scoreboard
		scoreLabel.setForeground(Color.WHITE);
		timerLabel.setBounds(scoreboardPanel.getWidth() / 2 - 40, 40, 100, 25);
		timerLabel.setForeground(Color.WHITE);

		// Declare scoreboardLabel outside the try block
		JLabel scoreboardLabel = null;
		JLabel scoreboardRightLabel = null;

		// Scoreboard image + scale
		ImageIcon scoreboard = new ImageIcon("imagesMaze/scoreboard (1).png");
		Image scoreboardImg = scoreboard.getImage().getScaledInstance(scoreboardPanel.getWidth() - 270, 80,
				Image.SCALE_SMOOTH);
		scoreboardLabel = new JLabel(new ImageIcon(scoreboardImg));
		scoreboardLabel.setBounds(0, 0, scoreboardPanel.getWidth() - 260, 80);

		// For the right side of the scorebaord
		ImageIcon scoreboardRight = new ImageIcon("imagesMaze/scoreboard (3).png");
		Image scoreboardRightImg = scoreboardRight.getImage().getScaledInstance(160, 105, Image.SCALE_SMOOTH);
		scoreboardRightLabel = new JLabel(new ImageIcon(scoreboardRightImg));
		scoreboardRightLabel.setBounds(310, -14, scoreboardPanel.getWidth() - 190, 105);

		// Display the all-time high score
		allTimeScoreLabel.setText(Integer.toString(MazeRaceHighScore.highestScore));
		allTimeScoreLabel.setBounds(590, 42, 100, 40);
		allTimeScoreLabel.setForeground(Color.WHITE);
		scoreboardPanel.add(allTimeScoreLabel);

		// Add all the labels to the scoreboard panel
		scoreboardPanel.add(scoreLabel);
		scoreboardPanel.add(timerLabel);
		scoreboardPanel.add(highScoreLabel);
		scoreboardPanel.add(allTimeScoreLabel);
		scoreboardPanel.add(scoreboardRightLabel);
		scoreboardPanel.add(scoreboardLabel);

	}

	// mazePanelSetup() - setting up the maze panel
	private void mazePanelSetup() {

		// Use the constants to set the position of the maze
		mazePanel.setBounds(0, 107, CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT);
		mazePanel.setLayout(new GridLayout(NUM_CELLS_WIDTH, NUM_CELLS_HEIGHT)); // use grid layout manager

		// Call sub-methods
		loadMaze();
		placeCoins();
		placePlayer();
	}

	// placePlayer() - place the player in an empty cell to start the game
	private void placePlayer() {

		// Find an empty cell and assign it to the cell variable
		Cell cell = findEmptyCell();

		// Update the row and column location with the values of the cell object
		player.setRow(cell.getRow());
		player.setCol(cell.getCol());

		// Sets the icon of a specific cell in the maze to the icon of the player
		maze[cell.getRow()][cell.getCol()].setIcon(player.getIcon());

	}

	// placeCoins() - randomly place the coins in the maze
	private void placeCoins() {

		// Looping to display all the coins
		for (int coin = 1; coin <= NUM_COINS; coin++) {

			// Ensure that there is space to add the coin in the cell
			Cell cell = findEmptyCell();
			// Change the picture of the empty cell to a coin
			maze[cell.getRow()][cell.getCol()].setIcon(COIN);

		}

	}

	// findEmptyCell() - find an empty cell for various icons
	private Cell findEmptyCell() {

		// Create a cell object
		Cell cell = new Cell();

		// Generate random positions on the maze for the coins until it is on the path
		do {

			// Set the row in the grid layout
			cell.setRow((int) (Math.random() * 24) + 2);
			// Set the column in the grid layout
			cell.setCol((int) (Math.random() * 24) + 2);

		} while (maze[cell.getRow()][cell.getCol()].getIcon() != PATH); // ensure that the randomly generated cell is on
																		// the path
		return cell; // return the cell object from the method
	}

	// loadMaze() - reading the maze file
	private void loadMaze() {

		// Variables to keep track of the lines of the file
		int row = 0;
		char[] line; // character array

		try {
			// Allow access to the file
			Scanner inputFile = new Scanner(new File("maze.txt"));

			// Condition to check if there is another token in the input stream
			while (inputFile.hasNext())

			{

				// Goes to the next line and converts the value into the character array
				line = inputFile.nextLine().toCharArray();

				// Go through one column at a time
				for (int column = 0; column < line.length; column++) {

					// Fill the position
					fillCell(line[column], row, column);

				}

				// Go to the next row
				row++;

			}
			inputFile.close(); // close the Scanner object

		} catch (FileNotFoundException error) {

			// Print out the error, if there are any
			System.out.println(error);

		}

	}

	// fillCell() - used to fill the cell
	private void fillCell(char character, int row, int column) {

		// Positions to create a new cell
		maze[row][column] = new Cell(row, column);

		// From the file, determine the walls, bounds and paths
		if (character == 'W')
			maze[row][column].setIcon(WALL);

		else if (character == 'X')
			maze[row][column].setIcon(OUT_OF_BOUNDS);

		else if (character == '.')
			maze[row][column].setIcon(PATH);

		// Add it to the panel based on the grid layout
		mazePanel.add(maze[row][column]);

	}

	//level2() - in addition to the coins, there are portals, different wall colour
	public void level2() {

		// Ensure that there is space to add the first portal in the cell
		Cell cell = findEmptyCell();
		// Change the picture of the empty cell to a portal
		maze[cell.getRow()][cell.getCol()].setIcon(PORTAL1);
		// Get the row and column of each portal
		portal1Row = cell.getRow();
		portal1Col = cell.getCol();

		// Ensure that there is space to add the second portal in the cell
		Cell cell2 = findEmptyCell();
		// Change the picture of the empty cell to a portal
		maze[cell2.getRow()][cell2.getCol()].setIcon(PORTAL2);
		// Get the row and column of each portal
		portal2Row = cell2.getRow();
		portal2Col = cell2.getCol();

	}

	// level3() - changes the wall, adds portals and mushrooms
	public void level3() {

		// Looping to display all the mushroom power-ups
		for (int mushroom = 1; mushroom <= NUM_MUSHROOMS; mushroom++) {

			// Ensure that there is space to add the coin in the cell
			Cell cell = findEmptyCell();
			// Change the picture of the empty cell to a coin
			maze[cell.getRow()][cell.getCol()].setIcon(MUSHROOM);

		}

		// Ensure that there is space to add the first portal in the cell
		Cell cell = findEmptyCell();
		// Change the picture of the empty cell to a portal
		maze[cell.getRow()][cell.getCol()].setIcon(PORTAL1);
		// Get the row and column of each portal
		portal1Row = cell.getRow();
		portal1Col = cell.getCol();

		// Ensure that there is space to add the second portal in the cell
		Cell cell2 = findEmptyCell();
		// Change the picture of the empty cell to a portal
		maze[cell2.getRow()][cell2.getCol()].setIcon(PORTAL2);
		// Get the row and column of each portal
		portal2Row = cell2.getRow();
		portal2Col = cell2.getCol();

	}

	// movePlayer() - used to move the character around the maze
	private void movePlayer(int dRow, int dCol) {

		// If the user enters the first portal, go to the second portal
		if (usedPortal1 == true) {
			usedPortal1 = false;
			maze[player.getRow()][player.getCol()].setIcon(PORTAL2);

			// If the user enters the second portal, go to the first
		} else if (usedPortal2 == true) {
			usedPortal2 = false;
			maze[player.getRow()][player.getCol()].setIcon(PORTAL1);

			// Otherwise, set the icon the path
		} else {

			maze[player.getRow()][player.getCol()].setIcon(PATH);

		}

		// If the player collects a coin
		if (maze[player.getRow() + dRow][player.getCol() + dCol].getIcon() == COIN) {
			// Play the sound effect
			playMusic("audio/coin.wav");
			// Increment the number of coins collected
			numCoinsCollected++;
			// Display the newly collected number of coins
			scoreLabel.setText(Integer.toString(numCoinsCollected));

		}

		// If the user collects a mushroom
		else if (maze[player.getRow() + dRow][player.getCol() + dCol].getIcon() == MUSHROOM) {
			// Play the power up sound effect
			playMusic("audio/powerUp.wav");

		}

		// If the user interacts with the first portal, teleport them to the other
		else if (maze[player.getRow() + dRow][player.getCol() + dCol].getIcon() == PORTAL1) {
			playMusic("audio/powerUp.wav"); // play the sound effect
			teleport(portal2Row, portal2Col); // Teleport the player to the location of the second portal
			usedPortal1 = true;
			return;

		}

		// If the user interacts with the second portal, teleport them to the other
		else if (maze[player.getRow() + dRow][player.getCol() + dCol].getIcon() == PORTAL2) {
			playMusic("audio/powerUp.wav");
			teleport(portal1Row, portal1Col); // Teleport the player to the location of the first portal
			usedPortal2 = true;
			return;
		}

		// Move the player
		player.move(dRow, dCol);
		maze[player.getRow()][player.getCol()].setIcon(player.getIcon());

		// Stop the timer once all the coins are collected
		// Check if all coins are collected
		if (numCoinsCollected == NUM_COINS) {
			playMusic("audio/victory.wav"); // play the victory sound effect
			gameTimer.stop();
			calculateScore();

			// Ask the user to play again or quit
			int choice = JOptionPane.showOptionDialog(this, "WINNER!\nPlay again?", "Game Over",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

			// If they click yes, restart the game
			if (choice == JOptionPane.YES_OPTION) {
				restartGame();
			} else {
				// Otherwise, exit
				System.exit(0);
			}
		}
	}

	// teleport() - this method will allow the user to teleport between portals
	private void teleport(int row, int col) {
		player.setRow(row);
		player.setCol(col);

		maze[player.getRow()][player.getCol()].setIcon(player.getIcon());

	}

	// keyPressed() - move the character accordingly to what keys are pressed
	public void keyPressed(KeyEvent key) {

		// Check to see which key got pressed
		if (key.getKeyCode() == KeyEvent.VK_UP && maze[player.getRow() - 1][player.getCol()].getIcon() != WALL) {
			player.setIcon(PLAYER_ICON[0]);
			movePlayer(-1, 0); // Move the player one coordinate up
			if (maze[player.getRow()][player.getCol()].getIcon() == MUSHROOM) {
				movePlayer(-1, 0); // Move the user an additional square up
				keyPressed(key); // Call the method recursively
			}

		} else if (key.getKeyCode() == KeyEvent.VK_RIGHT
				&& maze[player.getRow()][player.getCol() + 1].getIcon() != WALL) {
			player.setIcon(PLAYER_ICON[1]);
			movePlayer(0, 1);
			if (maze[player.getRow()][player.getCol()].getIcon() == MUSHROOM) {
				movePlayer(0, 1); // Move the user an additional square right
				keyPressed(key); // Call the method recursively
			}

		} else if (key.getKeyCode() == KeyEvent.VK_DOWN
				&& maze[player.getRow() + 1][player.getCol()].getIcon() != WALL) {
			player.setIcon(PLAYER_ICON[2]);
			movePlayer(1, 0);
			if (maze[player.getRow()][player.getCol()].getIcon() == MUSHROOM) {
				movePlayer(1, 0); // Move the user an additional square down
				keyPressed(key); // Call the method recursively
			}

		} else if (key.getKeyCode() == KeyEvent.VK_LEFT
				&& maze[player.getRow()][player.getCol() - 1].getIcon() != WALL) {
			player.setIcon(PLAYER_ICON[3]);
			movePlayer(0, -1);
			if (maze[player.getRow()][player.getCol()].getIcon() == MUSHROOM) {
				movePlayer(0, -1); // Move the user an additional square left
				keyPressed(key); // Call the method recursively
			}
		}
	}

	// calculateScore() - calculate the user's score
	private void calculateScore() {
		// Scoring system to ensure that the less time the user takes to collect coins,
		// the higher their score becomes
		highScoreValue = (int) (Double.valueOf((NUM_COINS) / seconds) * 10000);
		highScoreLabel.setText(String.valueOf(highScoreValue));
		highScoreLabel.setBounds(555, 2, 100, 40);
		highScoreLabel.setForeground(Color.WHITE);
		MazeRaceHome.storeInitials(initials); // Store the initials in the file

	}

	// Source:
	// https://stackoverflow.com/questions/20811728/adding-music-sound-to-java-programs
	// playMusic() - play any sound effects and background music
	private void playMusic(String filepath) {
		try {
			File musicPath = new File(filepath);

			if (musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				Clip background = AudioSystem.getClip();

				clip.open(audioInput);
				background.open(audioInput);

				if (filepath.equals("coin.wav") || filepath.equals("powerUp.wav") || filepath.equals("powerDown.wav")
						|| filepath.equals("victory.wav") || filepath.equals("click.wav")) {
					FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(-20.0f);
				} else if (filepath.equals("backgroundMusic.wav")) {
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					// Add LineListener to the background clip
					background.addLineListener(event -> {
						if (event.getType() == LineEvent.Type.STOP && numCoinsCollected == NUM_COINS) {
							background.stop();
						}
					});
				}

				clip.start();
				background.start();
			} else {
				System.out.println("ERROR, can't find file");
			}
		} catch (Exception e) {
			System.out.println("error");
		}
	}

	// restartGame() - used to run the program again if the user wishes to continue
	// playing
	private void restartGame() {
		// Reset the number of coins collected
		numCoinsCollected = 0;
		scoreLabel.setText("0");

		// Reset the timer
		time = 0;
		timerLabel.setText("0");

		// Remove the previous player's icon from the maze
		maze[player.getRow()][player.getCol()].setIcon(PATH);

		// Place new coins in the maze
		placeCoins();
		// Reset the player's position
		placePlayer();
		// Start the timer again
		gameTimer.start();

	}

	// frameSetup() - setting up the main frame of the GUI application
	private void frameSetup() {

		// Setup the frame
		setTitle("Emma's Maze Race");
		// Set the size of the frame
		setSize(mazePanel.getWidth(), mazePanel.getHeight() + 75 + scoreboardPanel.getHeight() + 10);
		setLayout(null); // set layout manager to null

		// Add both panels to the frame
		add(scoreboardPanel);
		add(mazePanel);

		// Register the key listener and passing the "this" argument to receive events
		addKeyListener(this);

		// Exit the application when the frame is closed
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // center the frame
		setResizable(false); // do not allow the user to change the screen size
		setVisible(true); // make the frame visible

		// Start the timer
		gameTimer.start();

	}

	// Empty method needed to satisfy the key listener
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	// Empty method needed to satisfy the KeyListener interface
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	// actionPerformed() - used to increment and update the game timer
	@Override
	public void actionPerformed(ActionEvent event) {

		// Ensure that the action performed is for the timer object
		if (event.getSource() == gameTimer) {
			time++;
			// Convert to seconds with decimal precision
			seconds = time / 10.0;
			// Update with the new time value
			timerLabel.setText(Double.toString(seconds));

		}

	}
}
