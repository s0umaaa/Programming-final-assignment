/**
 * Represents a direction of movement on the map.
 *
 * @author Soma Hayasaka,hayasakas@student.unimelb.edu.au,1396301
 *
 */
public class Direction {

	private int xDistance = 0;
	private int yDistance = 0;

	/**
	 * Prints out directions menu.
	 */
	public static void showDirections() {
		System.out.println("[1] to move north.");
		System.out.println("[2] to move west.");
		System.out.println("[3] to move east.");
		System.out.println("[4] to move south.");
		System.out.println("[5] to move north-west.");
		System.out.println("[6] to move south-west.");
		System.out.println("[7] to move north-east.");
		System.out.println("[8] to move south-east.");
		System.out.println("[0] to go back to main menu");
	}

	/**
	 * Creates a direction from a menu key.
	 *
	 * @param key Menu key
	 */
	public Direction(int key) {
		if (key == 1 || key == 5 || key == 7) {
			// north
			yDistance = -1;
		}
		if (key == 4 || key == 6 || key == 8) {
			// south
			yDistance = 1;
		}
		if (key == 2 || key == 5 || key == 6) {
			// west
			xDistance = -1;
		}
		if (key == 3 || key == 7 || key == 8) {
			// east
			xDistance = 1;
		}
	}

	/**
	 * Gets the x distance.
	 *
	 * @return X distance
	 */
	public int get_x() {
		// TODO 自動生成されたメソッド・スタブ
		return xDistance;
	}

	/**
	 * Gets the y distance.
	 *
	 * @return Y distance
	 */
	public int get_y() {
		// TODO 自動生成されたメソッド・スタブ
		return yDistance;
	}

	/**
	 * Sets the position.
	 *
	 * @param x X position
	 * @param y Y position
	 */
	public void set(int x, int y) {
		xDistance = x;
		yDistance = y;
	}

}
