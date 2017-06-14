package dobrink.fight_vest;

import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */

public class FighterCategory {
    Random random = new Random();
    int ID;
    String Name;

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
        this.ID = random.nextInt();
        this.Name = "FighterCategory";
    }

}
