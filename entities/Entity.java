/**
 * Base class for entities on the map.
 *
 * @author Soma Hayasaka,hayasakas@student.unimelb.edu.au,1396301
 *
 */
package entities;

/**
 * You should derive all your entities from this class.
 */
public abstract class Entity {
	/** Name of the entity */
	private final String name;
	/** Symbol representing the entity */

	private final char symbol;
	/** Habitability score of the entity */
	private int score = 0;
	/** Health points of the entity */
	protected int health = 0;

	/**
	 * Constructs an entity.
	 *
	 * @param name   Name of entity
	 * @param symbol Symbol for entity
	 * @param score  Habitability score
	 */
	public Entity(final String name, final char symbol, int score) {
		this.name = name;
		this.symbol = symbol;
		this.score = score;
	}

	/**
	 * Constructs an entity with no score.
	 *
	 * @param name   Name of entity
	 * @param symbol Symbol for entity
	 */
	public Entity(final String name, final char symbol) {
		this.name = name;
		this.symbol = symbol;
	}

	/**
	 * Gets the entity name.
	 *
	 * @return Entity name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Gets the entity symbol.
	 *
	 * @return Entity symbol
	 */
	public final char getSymbol() {
		return this.symbol;
	}

	/**
	 * Gets the habitability score.
	 *
	 * @return Habitability score
	 */
	public final int getScore() {
		return this.score;
	}

	/**
	 * Gets the health points.
	 *
	 * @return Health points
	 */
	public int getHealth() {
		return this.health;
	}
}
