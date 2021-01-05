package application;

import javafx.beans.property.*;

/**
 * A Player class that manages input from the player
 */
public class Player {
	private SimpleStringProperty playerName;
	private SimpleStringProperty playerDate;
	private SimpleIntegerProperty playerLevel;
	
	/**
	 * Constructor of the Player class that manages name,date,& level
	 * @param playerName the player name
	 * @param playerDate the date
	 * @param playerLevel the level
	 */
	public Player(String playerName, String playerDate, int playerLevel) {
		this.playerName = new SimpleStringProperty(playerName);
		this.playerDate = new SimpleStringProperty(playerDate);
		this.playerLevel = new SimpleIntegerProperty(playerLevel);
	}

	/**
	 * Gets the player name
	 * @return the player name
	 */
	public String getPlayerName() {
		return playerName.get();
	}

	/**
	 * Sets the player name
	 * @param playerName the player name
	 */
	public void setPlayerName(String playerName) {
		this.playerName = new SimpleStringProperty(playerName);
	}

	/**
	 * Gets the date played
	 * @return the date played
	 */
	public String getPlayerDate() {
		return playerDate.get();
	}

	/**
	 * Sets the date played
	 * @param playerDate the date played
	 */
	public void setPlayerDate(String playerDate) {
		this.playerDate = new SimpleStringProperty(playerDate);
	}

	/**
	 * Gets the player level
	 * @return the player level
	 */
	public int getPlayerLevel() {
		return playerLevel.get();
	}

	/**
	 * Sets the player level
	 * @param playerLevel the player level
	 */
	public void setPlayerLevel(int playerLevel) {
		this.playerLevel = new SimpleIntegerProperty(playerLevel);
	}
	
	
	
	
}
