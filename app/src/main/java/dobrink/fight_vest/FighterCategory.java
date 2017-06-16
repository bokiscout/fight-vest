package dobrink.fight_vest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */

public class FighterCategory implements Parcelable {
    Random random = new Random();
    int ID;
    String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getID() {

        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public FighterCategory(int ID, String name) {
        this.ID = ID;
        Name = name;
    }

    public FighterCategory() {
        this.ID = random.nextInt();
        this.Name = "FighterCategory";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.random);
        dest.writeInt(this.ID);
        dest.writeString(this.Name);
    }

    protected FighterCategory(Parcel in) {
        this.random = (Random) in.readSerializable();
        this.ID = in.readInt();
        this.Name = in.readString();
    }

    public static final Parcelable.Creator<FighterCategory> CREATOR = new Parcelable.Creator<FighterCategory>() {
        @Override
        public FighterCategory createFromParcel(Parcel source) {
            return new FighterCategory(source);
        }

        @Override
        public FighterCategory[] newArray(int size) {
            return new FighterCategory[size];
        }
    };
}
