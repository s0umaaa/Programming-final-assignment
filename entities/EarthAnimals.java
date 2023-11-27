/**
 * Represents Earth animals entity.
 * 
 * @author TODO: Soma Hayasaka,hayasakas@student.unimelb.edu.au,1396301
 *
 */
package entities;

public class EarthAnimals extends Entity {
    private static final int SCORE = 5;
    public EarthAnimals(final String name, final char symbol){
        super(name, symbol, SCORE);
    }
    public EarthAnimals(final String name, final char symbol, int health){
        super(name, symbol, SCORE);
        this.health = health;
    }
}