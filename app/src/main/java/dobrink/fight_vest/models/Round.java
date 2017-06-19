package dobrink.fight_vest.models;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */

@SuppressWarnings("DefaultFileTemplate")
public class Round {
    private Random random = new Random();

    private int ID ;
    private Date StartTime ;
    private Date EndTime ;
    private int FightID ;
    private List<Hit> Hits ;

    public Round(int ID, Date startTime, Date endTime, int fightID, List<Hit> hits) {
        this.ID = ID;
        StartTime = startTime;
        EndTime = endTime;
        FightID = fightID;
        Hits = hits;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) {
        StartTime = startTime;
    }

    public Date getEndTime() {
        return EndTime;
    }

    public void setEndTime(Date endTime) {
        EndTime = endTime;
    }

    public int getFightID() {
        return FightID;
    }

    public void setFightID(int fightID) {
        FightID = fightID;
    }

    public List<Hit> getHits() {
        return Hits;
    }

    public void setHits(List<Hit> hits) {
        Hits = hits;
    }

}
