/**
 * Enumerates entities on the map.
 *
 * @author Soma Hayasaka,hayasakas@student.unimelb.edu.au,1396301
 *
 */
package entities;

/**
 * Enumerates entities that can exist on the Martian land map.
 * Provides access to details of each entity.
 */
public enum Entitys {

    SpaceRobot(new SpaceRobot("space robot", 'Z')),
    SpaceRover(new SpaceRover("space rovers", 'X')),
    Heebie(new MartianAnimals("Heebie", 'H', 15)),
    Jeebie(new MartianAnimals("Jeebie", 'J', 15)),

    /* Entity */
    Rose(new PlantType("ROSE", 'R')),
    Lily(new PlantType("LILY", 'L')),
    Eucalyptus(new PlantType("EUCALYPTUS", 'E')),
    Potato(new VegetableType("POTATO", 'P')),
    Tomato(new VegetableType("TOMATO", 'T')),
    Onion(new VegetableType("ONION", 'O')),
    Apple(new VegetableType("APPLE", 'A')),
    Banana(new VegetableType("BANANA", 'B')),
    Cow(new EarthAnimals("COW", 'C')),
    Goat(new EarthAnimals("GOAT", 'G')),
    Sheep(new EarthAnimals("SHEEP", 'S')),
    Dog(new EarthAnimals("DOG", 'D', 10)),
    Rock(new Terrain("ROCK", '@')),
    Mineral(new Terrain("MINERAL", '*'));

    private final Entity entity;

    /**
     * Constructs an Entitys instance.
     *
     * @param entity The Entity object
     */
    private Entitys(Entity entity) {
        this.entity = entity;
    }

    /**
     * Gets the Entity object.
     *
     * @return The Entity
     */
    public Entity getEntity() {
        return this.entity;
    }

    /**
     * Gets the name of the entity.
     *
     * @return Name of the entity
     */
    public String getName() {
        return this.entity.getName();
    }

    /**
     * Gets Pascal case name of entity.
     *
     * @return Pascal case name
     */
    public String getPascalCase() {
        String str = this.entity.getName().toLowerCase();
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Gets the symbol representing the entity.
     *
     * @return Symbol of the entity
     */
    public char getSymbol() {
        return this.entity.getSymbol();
    }

    /**
     * Gets the health points of the entity.
     *
     * @return Health points of the entity
     */
    public int getHealth() {
        return this.entity.getHealth();
    }
}