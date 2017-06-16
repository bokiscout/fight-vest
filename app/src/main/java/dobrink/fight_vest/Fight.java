package dobrink.fight_vest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */
// Might need @SerializedName("id") and @SerializedName("date") in all classes
public class Fight implements Parcelable {

    Random random= new Random();

    int ID = random.nextInt();
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
        this.ID = ID;
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
        ArrayList<FightFightersDTO> list = new ArrayList<FightFightersDTO>();
        list.add(new FightFightersDTO(this.ID));
        list.add(new FightFightersDTO(this.ID));
        FightFighters = list;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.random);
        dest.writeInt(this.ID);
        dest.writeLong(this.StartTime != null ? this.StartTime.getTime() : -1);
        dest.writeLong(this.EndTime != null ? this.EndTime.getTime() : -1);
        dest.writeString(this.Country);
        dest.writeString(this.City);
        dest.writeString(this.Address);
        dest.writeString(this.Description);
        dest.writeLong(this.StartedAt != null ? this.StartedAt.getTime() : -1);
        dest.writeLong(this.EndedAt != null ? this.EndedAt.getTime() : -1);
        dest.writeInt(this.FightTypeID);
        dest.writeParcelable(this.FightType, flags);
        dest.writeList(this.FightFighters);
        dest.writeList(this.Rounds);
    }

    protected Fight(Parcel in) {
        this.random = (Random) in.readSerializable();
        this.ID = in.readInt();
        long tmpStartTime = in.readLong();
        this.StartTime = tmpStartTime == -1 ? null : new Date(tmpStartTime);
        long tmpEndTime = in.readLong();
        this.EndTime = tmpEndTime == -1 ? null : new Date(tmpEndTime);
        this.Country = in.readString();
        this.City = in.readString();
        this.Address = in.readString();
        this.Description = in.readString();
        long tmpStartedAt = in.readLong();
        this.StartedAt = tmpStartedAt == -1 ? null : new Date(tmpStartedAt);
        long tmpEndedAt = in.readLong();
        this.EndedAt = tmpEndedAt == -1 ? null : new Date(tmpEndedAt);
        this.FightTypeID = in.readInt();
        this.FightType = in.readParcelable(FightTypeDTO.class.getClassLoader());
        this.FightFighters = new ArrayList<FightFightersDTO>();
        in.readList(this.FightFighters, FightFightersDTO.class.getClassLoader());
        this.Rounds = new ArrayList<RoundDTO>();
        in.readList(this.Rounds, RoundDTO.class.getClassLoader());
    }

    public static final Parcelable.Creator<Fight> CREATOR = new Parcelable.Creator<Fight>() {
        @Override
        public Fight createFromParcel(Parcel source) {
            return new Fight(source);
        }

        @Override
        public Fight[] newArray(int size) {
            return new Fight[size];
        }
    };
}
