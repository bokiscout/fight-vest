package dobrink.fight_vest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */
// Might need @SerializedName("id") and @SerializedName("date") in all classes
public class Fight {
    Random random= new Random();

    int ID;
    Date StartTime;
    Date EndTime;
    String Country;
    String City;
    String Address;
    String Description;
    Date StartedAt;
    Date EndedAt;
    int FightTypeID;
    FightTypeDTO FightType;
    List<FightFightersDTO> FightFighters;
    List<RoundDTO> Rounds;

    public Fight() {
        int id = random.nextInt();
        this.ID = id;
        StartTime = new Date();
        EndTime = new Date();
        Country = "Country";
        City = "City";
        Address = "Address";
        Description ="This is aDescription";
        StartedAt = new Date();
        EndedAt = new Date();
        FightTypeID = random.nextInt(); ;
        FightType = new FightTypeDTO();
        FightFighters = new ArrayList<FightFightersDTO>();
        FightFighters.add(new FightFightersDTO(id));
        FightFighters.add(new FightFightersDTO(id));
        Rounds = new ArrayList<RoundDTO>();
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

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getStartedAt() {
        return StartedAt;
    }

    public void setStartedAt(Date startedAt) {
        StartedAt = startedAt;
    }

    public Date getEndedAt() {
        return EndedAt;
    }

    public void setEndedAt(Date endedAt) {
        EndedAt = endedAt;
    }

    public int getFightTypeID() {
        return FightTypeID;
    }

    public void setFightTypeID(int fightTypeID) {
        FightTypeID = fightTypeID;
    }

    public FightTypeDTO getFightType() {
        return FightType;
    }

    public void setFightType(FightTypeDTO fightType) {
        FightType = fightType;
    }

    public List<FightFightersDTO> getFightFighters() {
        return FightFighters;
    }

    public void setFightFighters(List<FightFightersDTO> fightFighters) {
        FightFighters = fightFighters;
    }

    public List<RoundDTO> getRounds() {
        return Rounds;
    }

    public void setRounds(List<RoundDTO> rounds) {
        Rounds = rounds;
    }

}
