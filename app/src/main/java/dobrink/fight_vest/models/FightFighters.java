package dobrink.fight_vest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */

@SuppressWarnings({"DefaultFileTemplate", "CanBeFinal"})
public class FightFighters {

    @SerializedName("ID")
    @Expose
    private int ID ;
    @SerializedName("FightID")
    @Expose
    private
    int FightID ;
    @SerializedName("FighterID")
    @Expose
    private
    int FighterID ;
    @SerializedName("Fighter")
    @Expose
    private
    Fighter Fighter ;

    public FightFighters(int FightID) {
        Fighter tmpFighter = new Fighter();
        Random random = new Random();
        this.ID = random.nextInt();
        this.FightID = FightID ;
        this.FighterID = tmpFighter.getID();
        Fighter = tmpFighter;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getFightID() {
        return FightID;
    }

    public void setFightID(int fightID) {
        FightID = fightID;
    }

    public int getFighterID() {
        return FighterID;
    }

    public void setFighterID(int fighterID) {
        FighterID = fighterID;
    }

    public Fighter getFighter() {
        return Fighter;
    }

    public void setFighter(dobrink.fight_vest.models.Fighter fighter) {
        Fighter = fighter;
    }

}
