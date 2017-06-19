package dobrink.fight_vest.models;

import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */

@SuppressWarnings({"DefaultFileTemplate", "CanBeFinal"})
public class FighterCategory{
    private int ID;
    private String Name=null;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public FighterCategory(int ID, String name) {
        this.ID = ID;
        Name = name;
    }

    public FighterCategory() {
        Random random = new Random();
        this.ID = random.nextInt();
        this.Name = "FighterCategory";
    }
}
