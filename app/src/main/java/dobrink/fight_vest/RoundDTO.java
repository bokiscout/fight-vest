package dobrink.fight_vest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */

class RoundDTO implements Parcelable {
    Random random = new Random();

    public int ID ;
    public Date StartTime ;
    public Date EndTime ;
    public int FightID ;
    public List<Hit> Hits ;

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
        dest.writeInt(this.FightID);
        dest.writeTypedList(this.Hits);
    }

    public RoundDTO() {
    }

    protected RoundDTO(Parcel in) {
        this.random = (Random) in.readSerializable();
        this.ID = in.readInt();
        long tmpStartTime = in.readLong();
        this.StartTime = tmpStartTime == -1 ? null : new Date(tmpStartTime);
        long tmpEndTime = in.readLong();
        this.EndTime = tmpEndTime == -1 ? null : new Date(tmpEndTime);
        this.FightID = in.readInt();
        this.Hits = in.createTypedArrayList(Hit.CREATOR);
    }

    public static final Parcelable.Creator<RoundDTO> CREATOR = new Parcelable.Creator<RoundDTO>() {
        @Override
        public RoundDTO createFromParcel(Parcel source) {
            return new RoundDTO(source);
        }

        @Override
        public RoundDTO[] newArray(int size) {
            return new RoundDTO[size];
        }
    };
}
