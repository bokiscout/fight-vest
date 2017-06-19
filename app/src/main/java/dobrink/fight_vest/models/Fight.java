package dobrink.fight_vest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */
// Might need @SerializedName("id") and @SerializedName("date") in all classes
@SuppressWarnings({"DefaultFileTemplate", "CanBeFinal"})
public class Fight {

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("StartTime")
    @Expose
    private String startTime;
    @SerializedName("EndTime")
    @Expose
    private String endTime;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("StartedAt")
    @Expose
    private String startedAt;
    @SerializedName("EndedAt")
    @Expose
    private String endedAt;
    @SerializedName("FightTypeID")
    @Expose
    private int fightTypeID;
    @SerializedName("FightType")
    @Expose
    private FightType fightType;
    @SerializedName("FightFighters")
    @Expose
    private List<FightFighters> fightFighters;
    @SerializedName("Rounds")
    @Expose
    private List<Round> rounds = null;

    public Fight() {
        Random random = new Random();
        int id = random.nextInt();
        this.ID = id;
        startTime = null;
        endTime = null;
        country = "Country";
        city = "City";
        address = "Address";
        description ="This is aDescription";
        startedAt = null;
        endedAt = null;
        fightTypeID = random.nextInt();
        fightType = new FightType();
        fightFighters = new ArrayList<>();
        fightFighters.add(new FightFighters(id));
        fightFighters.add(new FightFighters(id));
        rounds = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(String endedAt) {
        this.endedAt = endedAt;
    }

    public int getFightTypeID() {
        return fightTypeID;
    }

    public void setFightTypeID(int fightTypeID) {
        this.fightTypeID = fightTypeID;
    }

    public FightType getFightType() {
        return fightType;
    }

    public void setFightType(FightType fightType) {
        this.fightType = fightType;
    }

    public List<FightFighters> getFightFighters() {
        return fightFighters;
    }

    public void setFightFighters(List<FightFighters> fightFighters) {
        this.fightFighters = fightFighters;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

}
