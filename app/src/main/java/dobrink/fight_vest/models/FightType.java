package dobrink.fight_vest.models;

import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */

@SuppressWarnings({"DefaultFileTemplate", "CanBeFinal"})
public class FightType {

    private int ID;
    private String Name;

    public FightType() {
        Random random = new Random();
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
