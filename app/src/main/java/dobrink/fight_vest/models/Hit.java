package dobrink.fight_vest.models;

import java.util.Date;
import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */

public class Hit {

    Random random = new Random();
    int ID;
    Date Timestamp;
    int FighterID;
    Fighter Fighter;

    public Hit(int ID, Date timestamp, int fighterID, Fighter fighter) {
        this.ID = ID;
        Timestamp = timestamp;
        FighterID = fighterID;
        Fighter = fighter;
    }

    @Override
    public String toString() {
        return "ID: " + ID + " FighterID: " + Fighter + " Fighter: " + Fighter.getFirstName() + " timestamp: " + Timestamp.toString();
    }
}
