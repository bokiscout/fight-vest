package dobrink.fight_vest;

import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */

public class FightFightersDTO {
    Random random = new Random();
    int ID ;
    int FightID ;
    int FighterID ;
    Fighter Fighter ;

    public FightFightersDTO(int FightID) {
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
