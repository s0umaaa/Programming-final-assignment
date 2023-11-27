/**
 * This class represents a position on the Martian land map.
 * 
 * @author Soma Hayasaka,hayasakas@student.unimelb.edu.au,1396301
 *
 */
public class MartianLandPossision {
	/** X coordinate of the position */
	private int x;
	/** Y coordinate of the position */
	private int y;

	/**
	 * Constructs a new position.
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public MartianLandPossision(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the x coordinate.
	 *
	 * @return The x coordinate
	 */
	public int get_x() {
		return this.x;
	}

	/**
	 * Gets the y coordinate.
	 *
	 * @return The y coordinate
	 */
	public int get_y() {
		return this.y;
	}

	/**
	 * Moves the position by the given amount.
	 *
	 * @param x Amount to move x
	 * @param y Amount to move y
	 */
	public void move(int x, int y) {
		this.x += x;
		this.y += y;
	}

	/**
	 * Moves the position in the given direction.
	 *
	 * @param direction The direction to move
	 */
	public void add(Direction direction) {
		this.x += direction.get_x();
		this.y += direction.get_y();
	}

}
