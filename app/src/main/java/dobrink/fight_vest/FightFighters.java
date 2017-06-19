package dobrink.fight_vest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */

public class FightFighters {
    Random random = new Random();

    @SerializedName("ID")
    @Expose
    private int ID ;
    @SerializedName("FightID")
    @Expose
    int FightID ;
    @SerializedName("FighterID")
    @Expose
    int FighterID ;
    @SerializedName("Fighter")
    @Expose
    Fighter Fighter ;

    public FightFighters(int FightID) {
        Fighter tmpFigter = new Fighter();
        this.ID = random.nextInt();
        this.FightID = FightID ;
        this.FighterID = tmpFigter.getID();
        Fighter = tmpFigter;
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

    public void setFighter(dobrink.fight_vest.Fighter fighter) {
        Fighter = fighter;
    }

}
