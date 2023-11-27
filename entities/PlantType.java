/**
 * Represents plant type entity.
 *
 * @author TODO: Soma Hayasaka,hayasakas@student.unimelb.edu.au,1396301
 *
 */
package entities;

public class PlantType extends Entity {
    private static final int SCORE = 2;
    public PlantType(final String name, final char symbol){
        super(name, symbol, SCORE);
    }
}