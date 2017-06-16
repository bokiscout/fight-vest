package dobrink.fight_vest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */

class Hit implements Parcelable {
    Random random = new Random();
    int ID;
    Date Timestamp;
    int FighterID;
    Fighter Fighter;


    public Hit(int ID, Date timestamp, int fighterID, Fighter fighter) {
        this.ID = ID;
        Timestamp = timestamp;
        FighterID = fighterID;
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
        dest.writeLong(this.Timestamp != null ? this.Timestamp.getTime() : -1);
        dest.writeInt(this.FighterID);
        dest.writeParcelable(this.Fighter, flags);
    }

    protected Hit(Parcel in) {
        this.random = (Random) in.readSerializable();
        this.ID = in.readInt();
        long tmpTimestamp = in.readLong();
        this.Timestamp = tmpTimestamp == -1 ? null : new Date(tmpTimestamp);
        this.FighterID = in.readInt();
        this.Fighter = in.readParcelable(dobrink.fight_vest.Fighter.class.getClassLoader());
    }

    public static final Parcelable.Creator<Hit> CREATOR = new Parcelable.Creator<Hit>() {
        @Override
        public Hit createFromParcel(Parcel source) {
            return new Hit(source);
        }

        @Override
        public Hit[] newArray(int size) {
            return new Hit[size];
        }
    };
}
