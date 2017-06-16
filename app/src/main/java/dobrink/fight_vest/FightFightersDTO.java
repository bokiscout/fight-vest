package dobrink.fight_vest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */

public class FightFightersDTO implements Parcelable {
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

    public dobrink.fight_vest.Fighter getFighter() {
        return Fighter;
    }

    public void setFighter(dobrink.fight_vest.Fighter fighter) {
        Fighter = fighter;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.random);
        dest.writeInt(this.ID);
        dest.writeInt(this.FightID);
        dest.writeInt(this.FighterID);
        dest.writeParcelable(this.Fighter, flags);
    }

    protected FightFightersDTO(Parcel in) {
        this.random = (Random) in.readSerializable();
        this.ID = in.readInt();
        this.FightID = in.readInt();
        this.FighterID = in.readInt();
        this.Fighter = in.readParcelable(dobrink.fight_vest.Fighter.class.getClassLoader());
    }

    public static final Parcelable.Creator<FightFightersDTO> CREATOR = new Parcelable.Creator<FightFightersDTO>() {
        @Override
        public FightFightersDTO createFromParcel(Parcel source) {
            return new FightFightersDTO(source);
        }

        @Override
        public FightFightersDTO[] newArray(int size) {
            return new FightFightersDTO[size];
        }
    };
}
