// Template class that represents a user in the Maze Race game
public class MazeRaceUser {

	// Generate the fields to store the user's username/initials and high score
	private String username;
	private double highScore;

	public MazeRaceUser(String username, double highScore) {
		this.username = username;
		this.highScore = highScore;
	}

	// Generate getters and setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Double getHighScore() {
		return highScore;
	}

	public void setHighScore(double highScore) {
		this.highScore = highScore;
	}

}
