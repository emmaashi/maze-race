//Import statements 
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// This class contains all the features before the user reaches the level page
public class MazeRaceHome extends JFrame implements ActionListener {

	// Import the constants from the MazeRaceGUI class
	int CELL_SIZE = MazeRaceGUI.CELL_SIZE;
	int NUM_CELLS_WIDTH = MazeRaceGUI.NUM_CELLS_WIDTH;
	int NUM_CELLS_HEIGHT = MazeRaceGUI.NUM_CELLS_HEIGHT;

	// Create the necessary panels
	static JPanel homeScreenPanel = new JPanel(); // for the homescreen
	JPanel initialsPanel = new JPanel(); // to prompt for intials
	JPanel levelPanel = new JPanel(); // to prompt for the level the user wants to play
	JPanel instructionsPanel = new JPanel(); // to display the rules/overview

	// Create the button fields
	JButton startButton = new JButton(); // used to start the game
	JButton instructionsButton = new JButton(); // direct the user to the instructions panel
	JButton confirmButton = new JButton(); // to confirm the user's initials
	JButton homeButton = new JButton(); // to go back to the homepage
	JButton homeButtonInitials = new JButton(); // to go back to the homepage from the initials panel
	JButton levelButton[] = new JButton[4]; // button for each potential level selection

	static JButton highscoreButton; // button used to view the top 5 high scores
	static JTextArea initialsTextArea = new JTextArea(); // for the user to enter their initials or name
	public static String initials; // store the user's initials

	JLabel homeScreenLabel = new JLabel(); // to display the homescreen image created externally

	// Call the constructor
	public MazeRaceHome() {

		showHomeScreen();
		// Implement the action listener for when the start button is clicked
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				playMusic("audio/click.wav"); // play the click sound effect
				homeScreenPanel.setVisible(false);
				promptInitials();

			}

		});

		// The instructions buttons directs the user to the rules page
		instructionsButton.addActionListener(new ActionListener() {
			// Implement the action listener for when the instructions button is clicked
			public void actionPerformed(ActionEvent event) {
				playMusic("audio/click.wav");
				homeScreenPanel.setVisible(false); // close the screen
				instructionsPanel.setVisible(true);

				howToPlay();// helper method
			}

		});

		frameSetup(); // set up the frame
		playMusic("audio/backgroundMusic.wav"); // play background music

	}

	// showHomeScreen() - display all the components on the home screen
	private void showHomeScreen() {
		// Create the home screen panel
		homeScreenPanel.setBounds(0, 0, CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT + 130);
		homeScreenPanel.setLayout(null);

		// Create the home screen label
		ImageIcon homescreen = new ImageIcon("imagesMaze/cover.png");
		// Scale the image to fit the frame
		Image scaledImage = homescreen.getImage().getScaledInstance(CELL_SIZE * NUM_CELLS_WIDTH,
				CELL_SIZE * NUM_CELLS_HEIGHT + 120, Image.SCALE_SMOOTH);
		// Create a new JLabel object and set the layout and positioning
		homeScreenLabel = new JLabel(new ImageIcon(scaledImage));
		homeScreenLabel.setBounds(0, 0, CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT + 130);
		homeScreenPanel.add(homeScreenLabel);
		homeScreenLabel.setLayout(null);

		addHomeButtons(); // helper method
	}

	// addHomeButtons() - add the home buttons to the home panel
	private void addHomeButtons() {

		// Create and make the start button transparent
		startButton.setBounds(410, 241, 222, 67);
		startButton.setOpaque(false); // Make the button transparent
		startButton.setContentAreaFilled(false); // Make the button content area transparent
		startButton.setBorderPainted(false); // Remove the button border
		startButton.setFocusable(false); // Disable the focusable property

		// Create and make the instructions button transparent
		instructionsButton.setBounds(450, 408, 126, 35);
		instructionsButton.setOpaque(false); // Make the button transparent
		instructionsButton.setContentAreaFilled(false); // Make the button content area transparent
		instructionsButton.setBorderPainted(false); // Remove the button border
		instructionsButton.setFocusable(false); // Disable the focusable property

		// Create and make the home button transparent
		homeButton.setBounds(20, 14, 23, 22);
		homeButton.setOpaque(false); // Make the button transparent
		homeButton.setContentAreaFilled(false); // Make the button content area transparent
		homeButton.setBorderPainted(false); // Remove the button border
		homeButton.setFocusable(false); // Disable the focusable property
		homeButton.addActionListener(this);

		// Direct the user to the highscore page if the button is clicked
		highscoreButton = new JButton();// creating instance of the JButton
		highscoreButton.setBounds(212, 688, 349, 52);// setting the bounds
		highscoreButton.setOpaque(false); // Make the button transparent
		highscoreButton.setContentAreaFilled(false); // Make the button content area transparent
		highscoreButton.setBorderPainted(false); // Remove the button border
		highscoreButton.setFocusable(false); // Disable the focusable property
		// implement the action listener for when clicked
		highscoreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// close this screen
				homeScreenPanel.setVisible(false);
				playMusic("audio/click.wav");
				new MazeRaceHighScore();
			}
		});
		// Add the buttons to the homescreen image label
		homeScreenLabel.add(startButton);
		homeScreenLabel.add(instructionsButton);
		homeScreenLabel.add(homeButton);
		homeScreenLabel.add(highscoreButton);

	}

	// promptInitials() - get the user's initials and save in an external text file
	private void promptInitials() {
		// Set the positioning and layout of the initials panel
		initialsPanel.setLayout(null); // set the layout to null so that multiple components can be added on top
		// Create the home screen panel
		initialsPanel.setBounds(0, 0, CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT + 110); // set the //
																										// bounds

		// Create a new instance of the ImageIcon class and assign it to the variable of
		// the image
		ImageIcon getInitials = new ImageIcon("imagesMaze/initials.png");
		// Scale the image to fit the frame
		Image scaleInitials = getInitials.getImage().getScaledInstance(CELL_SIZE * NUM_CELLS_WIDTH,
				CELL_SIZE * NUM_CELLS_HEIGHT + 110, Image.SCALE_SMOOTH);
		JLabel storeInitialsLabel = new JLabel();
		storeInitialsLabel.setIcon(new ImageIcon(scaleInitials));
		storeInitialsLabel.setBounds(0, 0, CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT + 110);
		storeInitialsLabel.setLayout(null);

		// Create the text area for the user's initials
		initialsTextArea.setBounds(110, 373, 520, 130);
		initialsTextArea.setOpaque(false); // et the text area background to be transparent
		storeInitialsLabel.add(initialsTextArea); // add the text area to the label
		Font font = new Font("Inter", Font.BOLD, 50); // set the font size
		initialsTextArea.setFont(font);
		// Create and make the home button transparent
		homeButtonInitials.setBounds(77, 93, 23, 22);
		homeButtonInitials.setOpaque(false); // Make the button transparent
		homeButtonInitials.setContentAreaFilled(false); // Make the button content area transparent
		homeButtonInitials.setBorderPainted(false); // Remove the button border
		homeButtonInitials.setFocusable(false); // Disable the focusable property
		homeButtonInitials.addActionListener(this);

		// Add to the label to the panel
		initialsPanel.add(storeInitialsLabel);
		initialsPanel.add(homeButtonInitials);

		initialConfirmation(); // helper method to verify the user's initials

	}

	// initialConfirmation() - prompt the user to confirm their initials
	private void initialConfirmation() {
		// Set the layout and positioning of the confirmation button
		confirmButton.setBounds(165, 500, 322, 140); // set the bounds of the button
		confirmButton.setOpaque(false); // Make the button transparent
		confirmButton.setContentAreaFilled(false); // Make the button content area transparent
		confirmButton.setBorderPainted(false); // Remove the button border

		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				initials = initialsTextArea.getText(); // Assign the value from the JTextArea to the initials variable
				playMusic("audio/click.wav"); // play the click sound effect
				// Error checking to verify if the user entered a username/their initials
				if (initials == null || initials.isEmpty()) {
					// If not, save their score to the text file as a gguest
					initials = "GUEST";
				} else {
					initials = initialsTextArea.getText(); // Retrieve the user's input from the text area
				}
				initialsPanel.setVisible(false);
				setLevels(); // helper method to allow the user to choose a level

			}
		});

		initialsPanel.add(confirmButton); // add the button to the panel

	}

	// Source: https://www.digitalocean.com/community/tutorials/java-append-to-file
	// storeInitials() - add the user's information to the file
	public static void storeInitials(String initials) {

		// Assign the value of the filePath to the "highscore.txt" file
		String filePath = "highscore.txt";
		try {
			// Open the file in append mode
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

			// Write and concatenate the user's initials and high score
			writer.write(initials + "," + MazeRaceGUI.highScoreValue + "\n");
			writer.close(); // close the writer when done
		} catch (IOException event) {
			event.printStackTrace(); // handle the exception appropriately
		}
	}

	// Source:
	// https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
	// playMusic() - initialize the background music and all sound effects
	public static void playMusic(String filepath) {

		try {

			// Create a new file object, musicPath
			File musicPath = new File(filepath);

			// Error checking
			if (musicPath.exists()) {

				// If the file exists, obtain the audio file using the music path
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);

				// Get a clip object to play the audio
				Clip clip = AudioSystem.getClip();

				// Open the audio input stream and prepare it fo rplayback
				clip.open(audioInput);

				// If the filepath is the background music, loop indefinetly
				if (filepath.equals("backgroundMusic.wav")) {

					clip.loop(Clip.LOOP_CONTINUOUSLY);

				}

				else if (filepath.equals("coin.wav") || filepath.equals("powerUp.wav")
						|| filepath.equals("powerDown.wav") || filepath.equals("victory.wav")
						|| filepath.equals("click.wav")) {
					// Lower the volume
					FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(-20.0f);
				}
				// Start the clip
				clip.start();

			}

		}

		catch (Exception event) // handle any exceptions

		{

			// Display that there is an error
			System.out.println("ERROR");

		}

	}

	// setLevels() - allow the user to choose the level they wish to play
	private void setLevels() {

		// Set the positioning and layout of the panel
		levelPanel.setLayout(null);
		levelPanel.setBounds(0, 0, CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT + 110);

		// Set the background to the levels image
		ImageIcon level = new ImageIcon("imagesMaze/levels.png");
		// Scale the image
		Image levelImg = level.getImage().getScaledInstance(CELL_SIZE * NUM_CELLS_WIDTH,
				CELL_SIZE * NUM_CELLS_HEIGHT + 110, Image.SCALE_SMOOTH);
		JLabel levelLabel = new JLabel(new ImageIcon(levelImg));
		levelLabel.setBounds(0, 0, CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT + 110);

		// Add the label to the panel
		levelPanel.add(levelLabel);
		// Add the panel to the frame
		add(levelPanel);

		// Helper method - allow the user to select the difficulty level they wish to
		// play
		levelSection();
	}

	// Display the level screen and prompt the user to choose their difficulty level
	// or customize their own
	private void levelSection() {

		// Button for the easy level
		levelButton[0] = new JButton(); // Initialize levelButton[0]
		levelButton[0].setBounds(49, 237, 278, 107); // Setting the bounds
		levelPanel.add(levelButton[0]);
		levelButton[0].addActionListener(this);

		// Button for the medium level
		levelButton[1] = new JButton(); // Initialize levelButton[0]
		levelButton[1].setBounds(49, 376, 278, 107); // Setting the bounds
		levelPanel.add(levelButton[1]);
		levelButton[1].addActionListener(this);

		// Button for the difficulty level
		levelButton[2] = new JButton(); // Initialize levelButton[0]
		levelButton[2].setBounds(49, 515, 278, 107); // Setting the bounds
		levelPanel.add(levelButton[2]);
		levelButton[2].addActionListener(this);

		// Button for the difficulty level
		levelButton[3] = new JButton(); // Initialize levelButton[0]
		levelButton[3].setBounds(49, 654, 278, 107); // Setting the bounds
		levelPanel.add(levelButton[3]);
		levelButton[3].addActionListener(this);

	}

	// howToPlay() - rules page that gives the user an overview of the game
	protected void howToPlay() {

		// Set the positioning and layout of the panel
		instructionsPanel.setLayout(null);
		instructionsPanel.setBounds(0, 0, CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT + 110);

		// Display the instructions image
		ImageIcon instructions = new ImageIcon("imagesMaze/instructions.png");
		Image instructionsImg = instructions.getImage().getScaledInstance(CELL_SIZE * NUM_CELLS_WIDTH,
				CELL_SIZE * NUM_CELLS_HEIGHT + 110, Image.SCALE_SMOOTH);
		JLabel instructionsLabel = new JLabel(new ImageIcon(instructionsImg));
		instructionsLabel.setBounds(0, 0, CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT + 110);

		// Add the label to the panel
		instructionsPanel.add(instructionsLabel);
		// Add the homebutton to the panel
		instructionsPanel.add(homeButton);
		// Add the panel to the frame
		add(instructionsPanel);

	}

	// frameSetup() - set up the frame
	private void frameSetup() {
		// Add the panels to the frame
		add(initialsPanel);
		add(homeScreenPanel);
		// Set the size of the frame
		setSize(CELL_SIZE * NUM_CELLS_WIDTH, CELL_SIZE * NUM_CELLS_HEIGHT + 130);
		// Terminate the program when the user chooses to exit/quit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Don't allopw the user to resize the screen
		setResizable(false);
		setLocationRelativeTo(null); // centre it
		setVisible(true); // Make the JFrame visible

	}

	@Override
	// actionPerformed() - carry out the tasks of all the buttons
	public void actionPerformed(ActionEvent event) {

		// Add the necessary customizations to the maze, depending on the user's
		// preferences
		if (event.getSource() == levelButton[0]) {
			playMusic("audio/click.wav");
			MazeRaceCustom.colourWall = "brick";
			MazeRaceCustom.colourOutOfBounds = "black";
			dispose();
			new MazeRaceGUI(initials, 1);

		} else if (event.getSource() == levelButton[1]) {
			playMusic("audio/click.wav");
			MazeRaceCustom.colourWall = "lime brick";
			MazeRaceCustom.colourOutOfBounds = "black";
			dispose();
			new MazeRaceGUI(initials, 2);

		} else if (event.getSource() == levelButton[2]) {
			playMusic("audio/click.wav");
			MazeRaceCustom.colourWall = "purple brick";
			MazeRaceCustom.colourOutOfBounds = "black";
			dispose();
			new MazeRaceGUI(initials, 3);

		} else if (event.getSource() == levelButton[3]) {
			playMusic("audio/click.wav");
			dispose();
			new MazeRaceCustom();

		}

		// Allow the user to go back and forth between the home page and others
		else if (event.getSource() == homeButton) {
			playMusic("audio/click.wav");
			homeScreenPanel.setVisible(true);
			instructionsPanel.setVisible(false);

		}
		
		else if (event.getSource() == homeButtonInitials) {
			playMusic("audio/click.wav");
			homeScreenPanel.setVisible(true);
			initialsPanel.setVisible(false);

		}
	}

	// Getter for the initials
	public static String getInitials() {
		return initials;
	}

}
