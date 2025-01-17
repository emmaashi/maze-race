
/*Description: This class is responsible for getting and implementing the custom components
 * if the user chooses to create their own level 
*/
//Import statements
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// This class allows the user to customize their own level by choosing their own character and colour scheme
public class MazeRaceCustom extends JFrame implements ActionListener {

	// Create the panels used in this class
	JPanel customLevelPanel = new JPanel();
	JPanel customBackgroundPanel = new JPanel();

	JButton characterButtonArray[] = new JButton[7]; // Used to store which character the user chooses
	JButton backgroundButtonArray[] = new JButton[3]; // Array that stores all the different possible board themes
	static String character[] = { "SONIC", "MARIO", "KIRBY", "LUIGI", "BOWSER", "KOOPATROOPA" }; // Store the different
																									// characters

	// Default to mario, unless the user decides to choose a different character
	public static String characterName = "MARIO"; // used to store the user's character
	public static int background;
	int index;
	JLabel colourScreenLabel = new JLabel();
	JLabel powerUpLabel = new JLabel();

	// Arrays for the user's choice of WALL and OUT_OF_BOUNDS colours
	static String customColourWall[] = { "red", "light green", "purple" };
	static String customColourOutOfBounds[] = { "black", "dark green", "blue" };
	static String colourWall;
	static String colourOutOfBounds;

	// Fields for the components in the maze
	String numCoins;
	String numPortals;

	// Import the constants from the MazeRaceGUI class
	int CELL_SIZE = MazeRaceGUI.CELL_SIZE;
	int NUM_CELLS_WIDTH = MazeRaceGUI.NUM_CELLS_WIDTH;
	int NUM_CELLS_HEIGHT = MazeRaceGUI.NUM_CELLS_HEIGHT;

//	JPanel customDetailsPanel = new JPanel(); // Would have been used for the third customizable screen
//	JButton playButton = new JButton(); // Would have been used on the final customizing screen
//	JTextArea detailsTextArea[] = new JTextArea[4]; // Would have been used for the third customizable screen

	// Constructor method used to
	public MazeRaceCustom() {

		// Call the sub methods
		selectCharacter();
		frameSetup();

		// selectDetails(); Would have been used for the third customizable screen
	}

	// selectCharacter() - allow the user to choose their own character
	private void selectCharacter() {
		customLevelPanel.setLayout(null);
		// Create the home screen panel
		customLevelPanel.setBounds(0, 0, CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT + 110);

		// Create the character screen label
		ImageIcon characterScreen = new ImageIcon("imagesMaze/character.png");
		Image character = characterScreen.getImage().getScaledInstance(CELL_SIZE * NUM_CELLS_WIDTH,
				CELL_SIZE * NUM_CELLS_HEIGHT + 110, Image.SCALE_SMOOTH);
		JLabel characterScreenLabel = new JLabel();
		characterScreenLabel.setIcon(new ImageIcon(character));
		characterScreenLabel.setBounds(0, 0, CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT + 110);
		characterScreenLabel.setLayout(null);
		// Add to the panel
		customLevelPanel.add(characterScreenLabel);
		characterButtons(); // helper method
	}

	// characterButtons() - used to create the invisible buttons for each potential
	// character
	private void characterButtons() {

		// Set the positioning and add an action listener to each of the buttons

		characterButtonArray[0] = new JButton(); // Initialize characterButtonArray[0]
		characterButtonArray[0].setBounds(49, 242, 148, 138); // Setting the bounds
		customLevelPanel.add(characterButtonArray[0]);
		characterButtonArray[0].addActionListener(this);

		characterButtonArray[1] = new JButton(); // Initialize characterButtonArray[1]
		characterButtonArray[1].setBounds(236, 242, 148, 138); // Setting the bounds
		customLevelPanel.add(characterButtonArray[1]);
		characterButtonArray[1].addActionListener(this);

		characterButtonArray[2] = new JButton(); // Initialize characterButtonArray[2]
		characterButtonArray[2].setBounds(49, 414, 148, 138); // Setting the bounds
		customLevelPanel.add(characterButtonArray[2]);
		characterButtonArray[2].addActionListener(this);

		characterButtonArray[3] = new JButton(); // Initialize characterButtonArray[3]
		characterButtonArray[3].setBounds(236, 414, 148, 138); // Setting the bounds
		customLevelPanel.add(characterButtonArray[3]);
		characterButtonArray[3].addActionListener(this);

		characterButtonArray[4] = new JButton(); // Initialize characterButtonArray[4]
		characterButtonArray[4].setBounds(49, 596, 148, 138); // Setting the bounds
		customLevelPanel.add(characterButtonArray[4]);
		characterButtonArray[4].addActionListener(this);

		characterButtonArray[5] = new JButton(); // Initialize characterButtonArray[5]
		characterButtonArray[5].setBounds(236, 596, 148, 138); // Setting the bounds
		customLevelPanel.add(characterButtonArray[5]);
		characterButtonArray[5].addActionListener(this);

	}

	// selectColourScheme() - used to choose a map theme
	private void selectColourScheme() {
		customBackgroundPanel.setLayout(null);
		// Create the home screen panel
		customBackgroundPanel.setBounds(0, 0, CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT + 110);

		// Create a new ImageIcon object
		ImageIcon colourScheme = new ImageIcon("imagesMaze/colour scheme.png");
		Image colourSchemeImg = colourScheme.getImage().getScaledInstance(CELL_SIZE * NUM_CELLS_WIDTH,
				CELL_SIZE * NUM_CELLS_HEIGHT + 110, Image.SCALE_SMOOTH);
		colourScreenLabel.setIcon(new ImageIcon(colourSchemeImg));
		colourScreenLabel.setBounds(0, 0, CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT + 110);
		colourScreenLabel.setLayout(null);
		// Add to the panel
		customBackgroundPanel.add(colourScreenLabel);

		colourSchemeButtons(); // helper method

	}

	// colourSchemeButtons() - buttons on the panel used to choose the map theme
	private void colourSchemeButtons() {

		// Set the positioning and add an action listener to each of the buttons

		backgroundButtonArray[0] = new JButton(); // Initialize backgroundButtonArray[0[
		backgroundButtonArray[0].setBounds(54, 220, 565, 137); // Setting the bounds
		colourScreenLabel.add(backgroundButtonArray[0]);
		backgroundButtonArray[0].addActionListener(this);
		backgroundButtonArray[0].setOpaque(false); // Make the button transparent
		backgroundButtonArray[0].setContentAreaFilled(false); // Make the button content area transparent
		backgroundButtonArray[0].setBorderPainted(false); // Remove the button border
		backgroundButtonArray[0].setFocusable(false); // Disable the focusable property

		backgroundButtonArray[1] = new JButton(); // Initialize backgroundButtonArray[1]
		backgroundButtonArray[1].setBounds(52, 409, 565, 137); // Setting the bounds
		colourScreenLabel.add(backgroundButtonArray[1]);
		backgroundButtonArray[1].addActionListener(this);
		backgroundButtonArray[1].setOpaque(false); // Make the button transparent
		backgroundButtonArray[1].setContentAreaFilled(false); // Make the button content area transparent
		backgroundButtonArray[1].setBorderPainted(false); // Remove the button border
		backgroundButtonArray[1].setFocusable(false); // Disable the focusable property

		backgroundButtonArray[2] = new JButton(); // Initialize backgroundButtonArray[2]
		backgroundButtonArray[2].setBounds(53, 584, 565, 135); // Setting the bounds
		colourScreenLabel.add(backgroundButtonArray[2]);
		backgroundButtonArray[2].addActionListener(this);
		backgroundButtonArray[2].setOpaque(false); // Make the button transparent
		backgroundButtonArray[2].setContentAreaFilled(false); // Make the button content area transparent
		backgroundButtonArray[2].setBorderPainted(false); // Remove the button border
		backgroundButtonArray[2].setFocusable(false); // Disable the focusable property

	}

	// (ATTEMPTED ADDED CUSTOMIZATION) - the panel is functional, but I did not have
	// time to actually implement the features in the GUI class

	// selectPowerUps() - panel for the user to add certain power ups to the map
//	private void selectPowerUps() {
//		customDetailsPanel.setLayout(null);
//		// Create the home screen panel
//		customDetailsPanel.setBounds(0, 0, CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT + 110);
//
//		// Create the home screen label
//		ImageIcon powerUp = new ImageIcon("imagesPowerUps/powerUp.png");
//		Image scaledPowerUp = powerUp.getImage().getScaledInstance(CELL_SIZE * NUM_CELLS_WIDTH,
//				CELL_SIZE * NUM_CELLS_HEIGHT + 110, Image.SCALE_SMOOTH);
//		powerUpLabel.setIcon(new ImageIcon(scaledPowerUp));
//		powerUpLabel.setBounds(0, 0, CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT + 110);
//		powerUpLabel.setLayout(null);
//		// Add to the panel
//		customDetailsPanel.add(powerUpLabel);
//		userTextAreas();
//	}

	// userTextAreas() - allow the user to further customize the map
	// (ATTEMPTED ADDED CUSTOMIZATION) - able to retrieve the user's inputs, but did
	// not implement it in
	// the game
//	private void userTextAreas() {
//		detailsTextArea[0] = new JTextArea(); // Initialize detailsTextArea[0]
//		detailsTextArea[0].setBounds(73, 230, 166, 62);
//		detailsTextArea[0].setOpaque(false); // Set the text area background to be transparent
//		powerUpLabel.add(detailsTextArea[0]);
//		Font font = new Font("Inter", Font.BOLD, 18); // Adjust the font size as needed
//		detailsTextArea[0].setForeground(Color.WHITE); // Set the font color to white
//		detailsTextArea[0].setFont(font);
//
//		detailsTextArea[1] = new JTextArea(); // Initialize detailsTextArea[0]
//		detailsTextArea[1].setBounds(67, 500, 110, 62);
//		detailsTextArea[1].setOpaque(false); // Set the text area background to be transparent
//		powerUpLabel.add(detailsTextArea[1]);
//		detailsTextArea[1].setFont(font);
//		detailsTextArea[1].setForeground(Color.WHITE); // Set the font color to white
//
//		detailsTextArea[2] = new JTextArea(); // Initialize detailsTextArea[0]
//		detailsTextArea[2].setBounds(258, 500, 110, 62);
//		detailsTextArea[2].setOpaque(false); // Set the text area background to be transparent
//		powerUpLabel.add(detailsTextArea[2]);
//		detailsTextArea[2].setFont(font);
//		detailsTextArea[2].setForeground(Color.WHITE); // Set the font color to white
//		
//		detailsTextArea[3] = new JTextArea(); // Initialize detailsTextArea[0]
//		detailsTextArea[3].setBounds(450, 500, 110, 62);
//		detailsTextArea[3].setOpaque(false); // Set the text area background to be transparent
//		powerUpLabel.add(detailsTextArea[3]);
//		detailsTextArea[3].setFont(font);
//		detailsTextArea[3].setForeground(Color.WHITE); // Set the font color to white
//		
//		numCoins = detailsTextArea[0].getText();
//		numPortals = detailsTextArea[1].getText();
//		numTimers = detailsTextArea[2].getText();
//		numMushrooms = detailsTextArea[3].getText();
//		
//		confirmationButtons();
//	}

//	confirmationButtons() - helper method to store the save and play buttons
//	private void confirmationButtons() {
//		//save button 
//		
//		//Play button
//		playButton.setBounds(374, 600, 202, 80); // Setting the bounds
//		powerUpLabel.add(playButton);
//		playButton.addActionListener(this);
//		playButton.setOpaque(false); // Make the button transparent
//		playButton.setContentAreaFilled(false); // Make the button content area transparent
//		playButton.setBorderPainted(false); // Remove the button border
//		playButton.setFocusable(false); // Disable the focusable property
//		
//	}

	// playMusic() - used for the click sound effect
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

	// frameSetup() - set up the frame
	private void frameSetup() {

		add(customBackgroundPanel);
		add(customLevelPanel);
//		add(customDetailsPanel);
		setSize(CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT + 130);
		setTitle("CUSTOMIZE YOUR MAP!");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		setVisible(true); // Make the JFrame visible

	}

	@Override
	public void actionPerformed(ActionEvent event) {

		// Check if the event source matches the current character button
		for (int index = 0; index < characterButtonArray.length; index++) {
			if (event.getSource() == characterButtonArray[index]) {
				// Set the character name based on the selected button
				characterName = character[index];
				// Play the sound effect
				playMusic("audio/click.wav");
				// Hide the customLevelPanel
				customLevelPanel.setVisible(false);
				// Show the customBackgroundPanel
				customBackgroundPanel.setVisible(true);
				// Call the selectColourScheme() method
				selectColourScheme();
				// Exit the loop
				break;

			}

		}

		if (event.getSource() == backgroundButtonArray[0]) {
			playMusic("audio/click.wav");
			// Set the custom colors for the wall and out-of-bounds areas
			colourWall = customColourWall[0];
			colourOutOfBounds = customColourOutOfBounds[0];
			// Hide the customBackgroundPanel
			customBackgroundPanel.setVisible(false);
//			selectPowerUps(); // Was going to be used for the third customization screen
			// Create a new instance of MazeRaceGUI with the initials and level as
			// parameters
			new MazeRaceGUI(MazeRaceHome.getInitials(), 1);
		}

		else if (event.getSource() == backgroundButtonArray[1]) {
			playMusic("audio/click.wav");
			colourWall = customColourWall[1];
			colourOutOfBounds = customColourOutOfBounds[1];
			customBackgroundPanel.setVisible(false);
//			selectPowerUps(); // Was going to be used for the third customization screen
			new MazeRaceGUI(MazeRaceHome.getInitials(), 1);

		}

		else if (event.getSource() == backgroundButtonArray[2]) {
			playMusic("audio/click.wav");
			colourWall = customColourWall[2];
			colourOutOfBounds = customColourOutOfBounds[2];
			customBackgroundPanel.setVisible(false);
//			selectPowerUps(); // Was going to be used for the third customization screen
			new MazeRaceGUI(MazeRaceHome.getInitials(), 1);
		}

		// Functional if the last screen input was actually used
//		else if (event.getSource() == playButton) {
//			playMusic("audio/click.wav");
//			new MazeRaceGUI(MazeRaceHome.getInitials(),1);
//			
//		}

		else if (event.getSource() == MazeRaceHome.highscoreButton)
			dispose();
	}

}
