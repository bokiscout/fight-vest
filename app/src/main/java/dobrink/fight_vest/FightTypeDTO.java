package dobrink.fight_vest;

import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */

public class FightTypeDTO {
    Random random = new Random();

    int ID;
    String Name;

    public FightTypeDTO() {
        this.ID = random.nextInt();
        this.Name = "FightTypeName";
    }

    public FightTypeDTO(int ID, String name) {
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
