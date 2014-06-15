package de.logic;

public class HighscoreDataset {

	int id;
	String name;
	int moves;
	String time;
	
	/**
	 * @param name
	 * @param moves
	 * @param time
	 */
	public HighscoreDataset(String name, int moves, String time) {
		super();
		this.name = name;
		this.moves = moves;
		this.time = time;
	}

	
	/**
	 * @param id
	 * @param name
	 * @param z�ge
	 * @param time
	 */
	public HighscoreDataset(int id, String name, int z�ge, String time) {
		super();
		this.id = id;
		this.name = name;
		this.moves = z�ge;
		this.time = time;
	}


	public HighscoreDataset() {
		// TODO Auto-generated constructor stub
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getMoves() {
		return moves;
	}


	public void setMoves(int z�ge) {
		this.moves = z�ge;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}

}
