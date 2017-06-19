package dobrink.fight_vest;

import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */

public class FightType {
    Random random = new Random();

    int ID;
    String Name;

    public FightType() {
        this.ID = random.nextInt();
        this.Name = "FightTypeName";
    }

    public FightType(int ID, String name) {
        this.ID = ID;
        Name = name;
    }

    public int getID() {

        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
