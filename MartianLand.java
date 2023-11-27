
/**
 * This class represents the Martian land map.
 * It contains the map data and provides methods to
 * calculate habitability score.
 *
 * @author Soma Hayasaka,hayasakas@student.unimelb.edu.au,1396301
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import entities.Entity;
import entities.Entitys;

public class MartianLand {
	private List<String> land;

	private int behaviorScore = 0;

	private Map<Integer, Map<Integer, Integer>> healths = new HashMap<Integer, Map<Integer, Integer>>();

	/**
	 * Creates a new MartianLand instance.
	 *
	 * @param land list of strings representing the map
	 * @throws InvalidFileException   if map data is invalid
	 * @throws UnknownEntityException if unknown symbol is found
	 */
	public MartianLand(List<String> land) throws InvalidFileException, UnknownEntityException {
		this.land = land;

		if (!isAllLenSame()) {
			throw new InvalidFileException("Invalid File content, aborting mission.");
		}
		if (!isFirstLowAllHashes()
				|| !isLastLowAllHashes()
				|| !isHeadAndTailHashes()) {
			// boundary is not match
			throw new InvalidFileException("Invalid File content, aborting mission.");
		}

		if (!isAllEntityExist()) {
			throw new UnknownEntityException("An unknown items found in martian land. aborting mission.");
		}

		setDefaultHealths();

	}

	/**
	 * Sets default health values for entities.
	 */
	private void setDefaultHealths() {
		for (int y = 1; y <= this.land.size(); y++) {
			String line = this.land.get(y - 1);
			for (int x = 1; x <= line.length(); x++) {
				char symbol = line.charAt(x - 1);
				Entity entity = getEntityBySymbol(symbol);
				if (symbol == Entitys.Dog.getSymbol() || symbol == Entitys.Heebie.getSymbol()
						|| symbol == Entitys.Jeebie.getSymbol()) {
					if (!this.healths.containsKey(y - 1)) {
						this.healths.put(y - 1, new HashMap<Integer, Integer>());
					}
					this.healths.get(y - 1).put(x - 1, entity.getHealth());
				}
			}
		}
	}

	/**
	 * Gets health at a position.
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @return Health value
	 */
	public int getHealth(int x, int y) {
		return this.healths.get(y).get(x);

	}

	/**
	 * Increases health at a position.
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param v Amount to increase
	 */
	public void addHealth(int x, int y, int v) {
		int now = getHealth(x, y);
		putHealth(x, y, now + v);
	}

	/**
	 * Removes health data at a position.
	 *
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public void removeHealth(int x, int y) {
		this.healths.get(y).remove(x);
	}

	/**
	 * Sets health at a position.
	 *
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param v Health value
	 */
	public void putHealth(int x, int y, int v) {
		if (!this.healths.containsKey(y)) {
			this.healths.put(y, new HashMap<Integer, Integer>());
		}
		this.healths.get(y).put(x, v);
	}

	/**
	 * Moves health data to new position.
	 *
	 * @param posission Current position
	 * @param direction Direction of movement
	 */
	public void moveHealth(MartianLandPossision posission, Direction direction) {
		int health = getHealth(posission.get_x(), posission.get_y());
		removeHealth(posission.get_x(), posission.get_y());
		putHealth(posission.get_x() + direction.get_x(), posission.get_y() + direction.get_y(), health);
	}

	/**
	 * Gets the map data.
	 *
	 * @return list of strings representing the map
	 */
	public List<String> getLand() {
		return this.land;
	}

	/**
	 * Prints the Martian land map to the console.
	 */
	public void show() {
		System.out.println("Here is a layout of Martian land.");
		System.out.println("");
		for (String element : land) {
			System.out.println(element);
		}
		System.out.println();
	}

	/**
	 * Checks if all rows have the same length.
	 * 
	 * @return true if all rows have same length, false otherwise
	 */
	public boolean isAllLenSame() {
		int len = land.get(0).length();
		for (String element : land) {
			if (len != element.length()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if first row contains only '#' symbols.
	 * 
	 * @return true if first row only contains '#', false otherwise
	 */
	public boolean isFirstLowAllHashes() {
		String lastLow = land.get(0);

		for (int i = 0; i < lastLow.length(); i++) {
			if (lastLow.charAt(i) != '#') {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if last row contains only '#' symbols.
	 * 
	 * @return true if last row only contains '#', false otherwise
	 */
	public boolean isLastLowAllHashes() {
		String lastLow = land.get(land.size() - 1);

		for (int i = 0; i < lastLow.length(); i++) {
			if (lastLow.charAt(i) != '#') {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if start and end of each row is '#'.
	 *
	 * @return true if head and tail of each row is '#', false otherwise
	 */
	public boolean isHeadAndTailHashes() {
		for (String element : land) {
			if ((element.charAt(0) != '#') || (element.charAt(element.length() - 1) != '#')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if all symbols on map are valid entities.
	 * 
	 * @return true if only valid entities, false if unknown symbol found
	 */
	public boolean isAllEntityExist() {
		for (String element : land) {
			for (int i = 0; i < element.length(); i++) {
				char c = element.charAt(i);
				if ((c != Entitys.Rose.getSymbol())
						&& (c != Entitys.Lily.getSymbol())
						&& (c != Entitys.Eucalyptus.getSymbol())
						&& (c != Entitys.Potato.getSymbol())
						&& (c != Entitys.Tomato.getSymbol())
						&& (c != Entitys.Onion.getSymbol())
						&& (c != Entitys.Apple.getSymbol())
						&& (c != Entitys.Banana.getSymbol())
						&& (c != Entitys.Cow.getSymbol())
						&& (c != Entitys.Sheep.getSymbol())
						&& (c != Entitys.Dog.getSymbol())
						&& (c != Entitys.Rock.getSymbol())
						&& (c != Entitys.Mineral.getSymbol())
						&& (c != Entitys.SpaceRobot.getSymbol())
						&& (c != Entitys.SpaceRover.getSymbol())
						&& (c != Entitys.Heebie.getSymbol())
						&& (c != Entitys.Jeebie.getSymbol())
						&& (c != '#')
						&& (c != '.')) {
					// System.out.println("not Entity symbol (" + c + ")");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Calculates the total habitability score.
	 */
	public void showHabitabilityStatus() {
		System.out.println("Habitability Status");
		System.out.println("======================");

		HashMap<Entity, Integer> map = new HashMap<>();

		for (String element : land) {
			for (int i = 0; i < element.length(); i++) {
				char c = element.charAt(i);
				Entity entity = getEntityBySymbol(c);
				if (entity != null) {
					map.put(entity, map.getOrDefault(entity, 0) + 1);
				}
			}
		}

		if (map.isEmpty()) {
			System.out.println("No Record found.");
			return;
		}

		List<Entity> order = Arrays.asList(
				Entitys.Potato.getEntity(),
				Entitys.Mineral.getEntity(),
				Entitys.Sheep.getEntity(),
				Entitys.Lily.getEntity(),
				Entitys.Eucalyptus.getEntity(),
				Entitys.Rose.getEntity(),
				Entitys.Cow.getEntity(),
				Entitys.Goat.getEntity(),
				Entitys.Dog.getEntity(),
				Entitys.Tomato.getEntity());

		for (Entity entity : order) {
			Integer count = map.get(entity);
			if (count != null) {
				System.out.println(entity.getName() + " = " + count);
			}
		}

		int score = 0;
		for (java.util.Map.Entry<Entity, Integer> entry : map.entrySet()) {
			score += entry.getValue() * entry.getKey().getScore();
		}
		score += behaviorScore;

		System.out.println("");
		System.out.println("Total Habitability Score: " + score);
	}

	/* for map */
	public void showRobotsMap() {
		HashMap<Integer/* index */, MartianLandPossision> map = getEntityMap(Entitys.SpaceRobot.getEntity());
		final int size = map.size();
		System.out.println("There are " + size + " Space Robot found. Select");

		for (java.util.Map.Entry<Integer, MartianLandPossision> entry : map.entrySet()) {
			System.out
					.println("[" + entry.getKey() + "] for Space Robot at position (" + entry.getValue().get_x() + ", "
							+ entry.getValue().get_y() + ")");
		}
	}

	public void showRoverMap() {
		HashMap<Integer/* index */, MartianLandPossision> map = getEntityMap(Entitys.SpaceRover.getEntity());
		final int size = map.size();
		System.out.println("There are " + size + " Space Rover found. Select");

		for (java.util.Map.Entry<Integer, MartianLandPossision> entry : map.entrySet()) {
			System.out
					.println("[" + entry.getKey() + "] for Space Rover at position (" + entry.getValue().get_x() + ", "
							+ entry.getValue().get_y() + ")");
		}
	}

	public void showMartianAnimals() {
		ArrayList<Pair<Entity, MartianLandPossision>> map = getEntityMap(Entitys.Heebie.getEntity(),
				Entitys.Jeebie.getEntity());
		Collections.sort(map, new Comparator<Pair<Entity, MartianLandPossision>>() {
			@Override
			public int compare(Pair<Entity, MartianLandPossision> p1, Pair<Entity, MartianLandPossision> p2) {
				if (p1.second.get_y() == p2.second.get_y()) {
					return Integer.compare(p1.second.get_x(), p2.second.get_x());
				} else {
					return Integer.compare(p1.second.get_y(), p2.second.get_y());
				}
			}
		});

		int size = map.size();

		if (size > 0) {
			System.out.println("There are " + size + " Martian animal found. Select");
			int num = 1;
			for (Pair<Entity, MartianLandPossision> pair : map) {
				System.out.println("[" + num + "] for " + pair.first.getName().toUpperCase() + " at position ("
						+ pair.second.get_x() + ", " + pair.second.get_y() + ")");
				num++;
			}
		} else {
			System.out.println("No Martian animal found to move.");
		}
	}

	private HashMap<Integer/* index */, MartianLandPossision> getEntityMap(Entity entity) {
		HashMap<Integer/* index */, MartianLandPossision> map = new HashMap<>();
		int y = 0;
		int index = 1;
		for (String element : land) {
			for (int x = 0; x < element.length(); x++) {
				char c = element.charAt(x);
				if (c == entity.getSymbol()) {
					MartianLandPossision pos = new MartianLandPossision(x, y);
					map.put(index, pos);
					index++;
				}
			}
			y++;
		}
		return map;
	}

	public class Pair<T, U> {
		public final T first;
		public final U second;

		public Pair(T first, U second) {
			this.first = first;
			this.second = second;
		}
	}

	// return list of all martian animals
	private ArrayList<Pair<Entity, MartianLandPossision>> getEntityMap(Entity... entities) {
		ArrayList<Pair<Entity, MartianLandPossision>> list = new ArrayList<>();
		for (Entity entity : entities) {
			int y = 0;
			for (String landLine : land) {
				for (int x = 0; x < landLine.length(); x++) {
					if (landLine.charAt(x) == entity.getSymbol()) {
						MartianLandPossision pos = new MartianLandPossision(x, y);
						list.add(new Pair<>(entity, pos));
					}
				}
				y++;
			}
		}
		return list;
	}

	/**
	 * Gets the number of robots on the map.
	 * 
	 * @return Number of robots
	 */
	public int getRobotCount() {
		HashMap<Integer/* index */, MartianLandPossision> map = getEntityMap(Entitys.SpaceRobot.getEntity());
		return map.size();
	}

	public int getRoverCount() {
		HashMap<Integer/* index */, MartianLandPossision> map = getEntityMap(Entitys.SpaceRover.getEntity());
		return map.size();
	}

	public int getMartianAnimalCount() {
		HashMap<Integer/* index */, MartianLandPossision> heebieMap = getEntityMap(Entitys.Heebie.getEntity());
		HashMap<Integer/* index */, MartianLandPossision> jeebieMap = getEntityMap(Entitys.Jeebie.getEntity());
		return heebieMap.size() + jeebieMap.size();
	}

	public void rewriteMapChar(int x, int y, char symbol) {
		String landline = land.get(y);
		StringBuilder new_landline = new StringBuilder(landline);
		new_landline.setCharAt(x, symbol);
		land.set(y, new_landline.toString());
	}

	/**
	 * Moves an entity on the map.
	 *
	 * @param position  Current position
	 * @param direction Direction to move
	 */
	public void move(MartianLandPossision position, Direction direction) {
		char from_symbol = getSymbol(position, new Direction(0));
		rewriteMapChar(position.get_x(), position.get_y(), '.');
		rewriteMapChar(position.get_x() + direction.get_x(), position.get_y() + direction.get_y(), from_symbol);
	}

	public void unMove(MartianLandPossision position, Direction direction, char to_symbol) {
		char now_symbol = getSymbol(position, direction);
		rewriteMapChar(position.get_x(), position.get_y(), now_symbol);
		rewriteMapChar(position.get_x() + direction.get_x(), position.get_y() + direction.get_y(), to_symbol);
	}

	public MartianLandPossision getRobotPosition(int key) {
		HashMap<Integer/* index */, MartianLandPossision> map = getEntityMap(Entitys.SpaceRobot.getEntity());
		return map.get(key);
	}

	public char getSymbol(MartianLandPossision position, Direction direction) {

		List<String> land = getLand();
		return land.get(position.get_y() + direction.get_y()).charAt(position.get_x() + direction.get_x());
	}

	public char getSymbol(Direction action) {
		List<String> land = getLand();
		return land.get(action.get_y()).charAt(action.get_x());
	}

	public void plant(int plantInput, Direction action) {
		Entitys entity = null;
		String article = "A";
		switch (plantInput) {
			case 1:
				entity = Entitys.Potato;
				break;
			case 2:
				entity = Entitys.Tomato;
				break;
			case 3:
				entity = Entitys.Onion;
				article += "n";
				break;
			case 4:
				entity = Entitys.Apple;
				article += "n";
				break;
			case 5:
				entity = Entitys.Banana;
				break;
			case 6:
				entity = Entitys.Lily;
				break;
			case 7:
				entity = Entitys.Rose;
				break;
			case 8:
				entity = Entitys.Eucalyptus;
				break;
		}

		rewriteMapChar(action.get_x(), action.get_y(), entity.getSymbol());
		System.out.println(article + " " + entity.getPascalCase() + " has been planted.");

	}

	public void cattle(int cattleInput, Direction action) {
		Entitys entity = null;
		switch (cattleInput) {
			case 1:
				entity = Entitys.Goat;
				break;
			case 2:
				entity = Entitys.Sheep;
				break;
			case 3:
				entity = Entitys.Cow;
				break;
			case 4:
				entity = Entitys.Dog;
				break;
		}
		rewriteMapChar(action.get_x(), action.get_y(), entity.getSymbol());
		System.out.println("A " + entity.getName() + " has been added.");
	}

	/**
	 * Gets Entity instance for given symbol.
	 *
	 * @param symbol Symbol to lookup
	 * @return Entity instance if found, null otherwise
	 */
	public Entity getEntityBySymbol(char symbol) {
		Entity entity = null;
		if (symbol == Entitys.Apple.getSymbol()) {
			entity = Entitys.Apple.getEntity();
		}
		if (symbol == Entitys.Banana.getSymbol()) {
			entity = Entitys.Banana.getEntity();
		}
		if (symbol == Entitys.Cow.getSymbol()) {
			entity = Entitys.Cow.getEntity();
		}
		if (symbol == Entitys.Dog.getSymbol()) {
			entity = Entitys.Dog.getEntity();
		}
		if (symbol == Entitys.Eucalyptus.getSymbol()) {
			entity = Entitys.Eucalyptus.getEntity();
		}
		if (symbol == Entitys.Goat.getSymbol()) {
			entity = Entitys.Goat.getEntity();
		}
		if (symbol == Entitys.Heebie.getSymbol()) {
			entity = Entitys.Heebie.getEntity();
		}
		if (symbol == Entitys.Jeebie.getSymbol()) {
			entity = Entitys.Jeebie.getEntity();
		}
		if (symbol == Entitys.Lily.getSymbol()) {
			entity = Entitys.Lily.getEntity();
		}
		if (symbol == Entitys.Mineral.getSymbol()) {
			entity = Entitys.Mineral.getEntity();
		}
		if (symbol == Entitys.Onion.getSymbol()) {
			entity = Entitys.Onion.getEntity();
		}
		if (symbol == Entitys.Potato.getSymbol()) {
			entity = Entitys.Potato.getEntity();
		}
		if (symbol == Entitys.Rock.getSymbol()) {
			entity = Entitys.Rock.getEntity();
		}
		if (symbol == Entitys.Rose.getSymbol()) {
			entity = Entitys.Rose.getEntity();
		}
		if (symbol == Entitys.Sheep.getSymbol()) {
			entity = Entitys.Sheep.getEntity();
		}
		if (symbol == Entitys.SpaceRobot.getSymbol()) {
			entity = Entitys.SpaceRobot.getEntity();
		}
		if (symbol == Entitys.SpaceRover.getSymbol()) {
			entity = Entitys.SpaceRover.getEntity();
		}
		if (symbol == Entitys.Tomato.getSymbol()) {
			entity = Entitys.Tomato.getEntity();
		}

		return entity;
	}

	public MartianLandPossision getRooerPosition(int key) {
		HashMap<Integer/* index */, MartianLandPossision> map = getEntityMap(Entitys.SpaceRover.getEntity());
		return map.get(key);
	}

	public MartianLandPossision getAnimal(int key) {
		ArrayList<Pair<Entity, MartianLandPossision>> map = getEntityMap(Entitys.Heebie.getEntity(),
				Entitys.Jeebie.getEntity());
		Collections.sort(map, new Comparator<Pair<Entity, MartianLandPossision>>() {
			@Override
			public int compare(Pair<Entity, MartianLandPossision> p1, Pair<Entity, MartianLandPossision> p2) {
				if (p1.second.get_y() == p2.second.get_y()) {
					return Integer.compare(p1.second.get_x(), p2.second.get_x());
				} else {
					return Integer.compare(p1.second.get_y(), p2.second.get_y());
				}
			}
		});
		// Return the MartianLandPossision based on the key, adjusting for 1-indexing
		if (key >= 1 && key <= map.size()) {
			return map.get(key - 1).second;
		} else {
			return null; // or throw an exception
		}
	}

	public void addBehaviorScore(int score) {
		behaviorScore += score;
	}
}
