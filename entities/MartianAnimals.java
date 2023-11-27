/** 
 * Represents Martian animals entity.
 *
 * @author TODO: Soma Hayasaka,hayasakas@student.unimelb.edu.au,1396301
 *
 */
package entities;

public class MartianAnimals extends Entity {
    public MartianAnimals(final String name, final char symbol){
        super(name, symbol);
    }
    public MartianAnimals(final String name, final char symbol, int health){
        super(name, symbol);
        this.health = health;
    }
}