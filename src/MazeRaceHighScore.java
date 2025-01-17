//Import statements
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class MazeRaceHighScore extends JFrame {

	// Import the constants from the MazeRaceGUI class
	int CELL_SIZE = MazeRaceGUI.CELL_SIZE;
	int NUM_CELLS_WIDTH = MazeRaceGUI.NUM_CELLS_WIDTH;
	int NUM_CELLS_HEIGHT = MazeRaceGUI.NUM_CELLS_HEIGHT;

	JLabel tableLabel; // label to store the high score table
	JLabel highScores[] = new JLabel[5];// array for each
	JPanel topFiveScores = new JPanel(); // panel for the table label
	JButton homeButton; // for the user to navigate between the current screen and the home
	
	// Fields to store the initials and score of the top 5 high scores
	static int topScore;
	String topScoreUser;
	int secondScore;
	String secondScoreUser;
	int thirdScore;
	String thirdScoreUser;
	int fourthScore;
	String fourthScoreUser;
	int fifthScore;
	String fifthScoreUser;

	static int index; // static loop counter
	static int highestScore;

	// Constructor method
	public MazeRaceHighScore() {

		// Load the image of the high scores
		ImageIcon table = new ImageIcon("imagesMaze/high scores.png");
		// Scale the image to fit the frame size
		Image scaledImage = table.getImage().getScaledInstance(CELL_SIZE * NUM_CELLS_WIDTH,
				CELL_SIZE * NUM_CELLS_HEIGHT + 110, Image.SCALE_SMOOTH);
		// Add the image to the label
		tableLabel = new JLabel(new ImageIcon(scaledImage));

		// Set up the highScorePanel
		add(tableLabel);
		pack();

		// Set up the frame
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);

		// Call a helper method and pass in the highscore text file
		getTopFiveScores(readHighScoresFromFile("highscore.txt"));

	}

	// readHighScoresFromFile() - reads the high scores from the file and returns an
	// array list of high score objects
	public ArrayList<HighScoreEntry> readHighScoresFromFile(String filename) {
		// Initialize empty array list
		ArrayList<HighScoreEntry> highScores = new ArrayList<>();

		// Source: https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html
		// Create a BufferedReader and read the file using a FileReader
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			// Continue to read the lines in the file until there are no more
			while ((line = reader.readLine()) != null) {
				// Seperate the initials and score using a comma
				// https://www.geeksforgeeks.org/split-string-java-examples/
				String[] parts = line.split(",");
				// Check to see that there are two elements
				if (parts.length == 2) {
					// Extract the username and trim the rest
					String username = parts[0].trim();
					// Extract the score and part it as an int
					int score = Integer.parseInt(parts[1].trim());
					// HighScoreEntry object is created and is added to the highScores list
					highScores.add(new HighScoreEntry(username, score));
				}
			}

			// catch any exceptions
		} catch (IOException event) {
			event.printStackTrace();
		}

		// return the highScores list containing the read high scores
		return highScores;

	}

	// getTopFiveScores() - takes an ArrayList of high scores as input and returns
	// a new array list containing the top 5 scores
	public ArrayList<HighScoreEntry> getTopFiveScores(ArrayList<HighScoreEntry> highScores) {
		// Sort the high scores in descending order
		highScores.sort(Comparator.comparingInt(HighScoreEntry::getScore).reversed());

		// Initialize a results sub list that ensures that there are no more than 5
		// entries added
		ArrayList<HighScoreEntry> result = new ArrayList<>(highScores.subList(0, Math.min(highScores.size(), 5)));
		highestScore = highScores.get(0).getScore();
		
		// Loop through the size of the results array list
		for (index = 0; index < result.size(); index++) {

			// Assign the data entry to the corresponding high score variabe
			HighScoreEntry entry = result.get(index);
			if (index == 0) {
				topScoreUser = entry.getUsername();
				topScore = entry.getScore();
			} else if (index == 1) {
				secondScoreUser = entry.getUsername();
				secondScore = entry.getScore();
			} else if (index == 2) {
				thirdScoreUser = entry.getUsername();
				thirdScore = entry.getScore();
			} else if (index == 3) {
				fourthScoreUser = entry.getUsername();
				fourthScore = entry.getScore();
			} else if (index == 4) {
				fifthScoreUser = entry.getUsername();
				fifthScore = entry.getScore();
			}

			addTopScoreLabels(); // helper method to add the high score labels
			addHomeButton(); // help method to add the button
		}

		return result; // return the list of the top 5 high scores

	}

	// addHomeButton() - added the home button to the screen
	private void addHomeButton() {
		// Create a new home button and set the layout and positioning
		homeButton = new JButton();
		homeButton.setBounds(571, 161, 33, 29);
		homeButton.setOpaque(false); // Make the button transparent
		homeButton.setContentAreaFilled(false); // Make the button content area transparent
		homeButton.setBorderPainted(false); // Remove the button border
		homeButton.setFocusable(false); // Disable the focusable property
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				playMusic("audio/click.wav");
				setVisible(false);
				MazeRaceHome.homeScreenPanel.setVisible(true);// navigate to the home screen

			}
		});
		add(homeButton); // add to the frame

	}

	// addTopScoreLabels() - add the high scores to the label for the user to see
	private void addTopScoreLabels() {
		highScores[0] = new JLabel();
		highScores[0].setBounds(138, 342, 400, 70); // x coordinate
		highScores[0].setForeground(Color.WHITE);
		highScores[0].setText(topScoreUser + ": " + topScore);
		highScores[0].setFont(new Font("INTER", Font.BOLD, 34)); // Set font to Arial, bold, size 16
		tableLabel.add(highScores[0]);

		if (index == 1) {
			highScores[1] = new JLabel();
			highScores[1].setBounds(138, 408, 400, 85); // x coordinate
			highScores[1].setForeground(Color.WHITE);
			highScores[1].setText(secondScoreUser + ": " + secondScore);
			highScores[1].setFont(new Font("INTER", Font.BOLD, 34)); // Set font to Arial, bold, size 16
			tableLabel.add(highScores[1]);

		}

		else if (index == 2) {
			highScores[2] = new JLabel();
			highScores[2].setBounds(138, 478, 400, 85); // x coordinate
			highScores[2].setForeground(Color.WHITE);
			highScores[2].setText(thirdScoreUser + ": " + thirdScore);
			highScores[2].setFont(new Font("INTER", Font.BOLD, 34)); // Set font to Arial, bold, size 16
			tableLabel.add(highScores[2]);

		}

		else if (index == 3) {
			highScores[3] = new JLabel();
			highScores[3].setBounds(138, 549, 400, 85); // x coordinate
			highScores[3].setForeground(Color.WHITE);
			highScores[3].setText(fourthScoreUser + ": " + fourthScore);
			highScores[3].setFont(new Font("INTER", Font.BOLD, 34)); // Set font to Arial, bold, size 16
			tableLabel.add(highScores[3]);
		}

		else if (index == 4) {
			highScores[4] = new JLabel();
			highScores[4].setBounds(138, 620, 400, 85); // x coordinate
			highScores[4].setForeground(Color.WHITE);
			highScores[4].setText(fifthScoreUser + ": " + fifthScore);
			highScores[4].setFont(new Font("INTER", Font.BOLD, 34)); // Set font to Arial, bold, size 16
			tableLabel.add(highScores[4]);
		}
	}

	// Source:
	// https://stackoverflow.com/questions/20811728/adding-music-sound-to-java-programs
	// playMusic() - play any sound effects and background music
	private void playMusic(String filepath) {
		try {
			// Create a File object representing the music file
			File musicPath = new File(filepath);

			// Check if the file exists
			if (musicPath.exists()) {
				// Create an AudioInputStream from the music file
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);

				// Create a Clip object to play the music
				Clip clip = AudioSystem.getClip();
				Clip background = AudioSystem.getClip();

				// Open the Clip and the background Clip with the audio input
				clip.open(audioInput);
				background.open(audioInput);

				// Adjust the volume for specific sound effects
				if (filepath.equals("coin.wav") || filepath.equals("powerUp.wav") || filepath.equals("powerDown.wav")
						|| filepath.equals("victory.wav") || filepath.equals("click.wav")) {
					FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(-20.0f); // Set the volume to -20 decibels
				}

				// Start playing the music and the background music
				clip.start();
				background.start();
		
			}
			
		// Catch any exceptions
		} catch (Exception event) {
			System.out.println("ERROR, can't find file");

		}

	}
	
	public int getTopScore() {
	    return topScore;
	}
	
	// This class represents an entry in the high scores list
	public class HighScoreEntry {
	    private final String username; // Stores the username of the high score entry
	    private final int score; // Stores the score of the high score entry

	    //Constructor method that initializes the instance variables 
	    public HighScoreEntry(String username, int score) {
	        this.username = username;
	        this.score = score;
	    }

	    //Getters 
	    public String getUsername() {
	        return username;
	    }

	    public int getScore() {
	        return score;
	    }
	}
}
