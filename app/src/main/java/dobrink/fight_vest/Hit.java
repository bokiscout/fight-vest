package dobrink.fight_vest;

import java.util.Date;
import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */

class Hit {

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
        return "ID: " + ID + " FighterID: " + Fighter + " Fighter: " + Fighter.FirstName + " timestamp: " + Timestamp.toString();
    }
}
