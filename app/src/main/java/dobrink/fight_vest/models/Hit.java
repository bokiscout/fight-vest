package dobrink.fight_vest.models;

import java.util.Date;

/**
 * Created by Dobrin on 14-Jun-17.
 */

@SuppressWarnings({"DefaultFileTemplate", "CanBeFinal", "FieldCanBeLocal"})
public class Hit {

    private int ID;
    private Date Timestamp;
    private int FighterID;
    private Fighter Fighter;

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
