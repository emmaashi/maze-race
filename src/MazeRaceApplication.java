/* Name: Emma Shi
 * Date: Sunday, May 28th, 2023
 * Course Code: ICS3U1-06
 * Teacher: Mr. Fernandes
 * Description: This is the main frame for the Maze Race Application. This program will run a 
 * Maze Race game with the objective of collecting all the coins in the map as fast as possible. 
 * Basic Features:
 * - More accurate timer (milliseconds)
 * - Player faces the proper direction as they move
 * - Seperate opening screen/home page
 * - Different board themes
 * - 6 different characters
 * - Sound effects and background music
 * - New levels with mazes and different settings
 * - Portals
 * - Score label for the current game session
 * - An option to restart the game and play again
 * - Rules page (instructions on how to play)
 * - Mushroom power-up (ATTEMPTED)
 * Advanced Features: 
 * - External text file saves all of the scores with the user's initials
 * - The data is filtered to create a high score table/leaderboard with the Top 5 high scores (and is updated)
 * - Level Creator allows the user to customize their own level, but is not saved to play in the future 
 * Areas of Concern:
 * - (MazeRaceHome class): You can only navigate between the home screen and the initials panel once
 * - (MazeRaceGUI class): The current high score label will only display if the user has viewed the leaderboard (on the home page) before playing
 * - (MazeRaceGUI class): The mushroom power-up is not fully functional. I could not get the method to call recursively for a certain duration of time.
 * Major Skills: Arrays, ArrayList, Swing GUI Components, BufferedWriter, Counter-Controlled loops, Getters/Setters, If/Else if Statements
 */

// Application class that will run the program
public class MazeRaceApplication {

	// Main method used to run the application
	public static void main(String[] args) {

		// Create the MazeRaceHome object
		new MazeRaceHome();
	}

}
