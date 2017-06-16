package dobrink.fight_vest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */

public class FightTypeDTO implements Parcelable {
    Random random = new Random();

    int ID;
    String Name;

    public FightTypeDTO() {
        this.ID = random.nextInt();
        this.Name = "FightTypeName";
    }

    public FightTypeDTO(int ID, String name) {
        this.ID = ID;
        Name = name;
    }

    public int getID() {

        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    protected FightTypeDTO(Parcel in) {
        this.random = (Random) in.readSerializable();
        this.ID = in.readInt();
        this.Name = in.readString();
    }

    public static final Parcelable.Creator<FightTypeDTO> CREATOR = new Parcelable.Creator<FightTypeDTO>() {
        @Override
        public FightTypeDTO createFromParcel(Parcel source) {
            return new FightTypeDTO(source);
        }

        @Override
        public FightTypeDTO[] newArray(int size) {
            return new FightTypeDTO[size];
        }
    };
}
