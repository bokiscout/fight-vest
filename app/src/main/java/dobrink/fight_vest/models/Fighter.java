package dobrink.fight_vest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */

@SuppressWarnings({"DefaultFileTemplate", "CanBeFinal"})
public class Fighter {

    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("FirstName")
    @Expose
    private String FirstName;
    @SerializedName("LastName")
    @Expose
    private String LastName;
    @SerializedName("Avatar")
    @Expose
    private String Avatar;
    @SerializedName("Country")
    @Expose
    private String County;
    @SerializedName("City")
    @Expose
    private String City;
    @SerializedName("BirthDate")
    @Expose
    private String BirthDate;
    @SerializedName("FullName")
    @Expose
    private String FullName;
    @SerializedName("AvatarUrl")
    @Expose
    private String AvatarUrl;
    @SerializedName("FighterCategory")
    @Expose
    private FighterCategory FighterCategory;

    public Fighter() {
        Random random = new Random();
        this.ID = random.nextInt();
        FirstName = "FirstName";
        LastName = "LastName";
        Avatar = "Avatar";
        County = "County";
        City = "City";
        BirthDate = null;
        FullName = "FirstName LastName";
        AvatarUrl = "AvatarUrl";
        FighterCategory = new FighterCategory();
    }

    public Fighter(int ID, String firstName, String lastName, String avatar, String county, String city, String birthDate, String fullName, String avatarUrl, dobrink.fight_vest.models.FighterCategory fighterCategory) {
        this.ID = ID;
        FirstName = firstName;
        LastName = lastName;
        Avatar = avatar;
        County = county;
        City = city;
        BirthDate = birthDate;
        FullName = fullName;
        AvatarUrl = avatarUrl;
        FighterCategory = fighterCategory;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String county) {
        County = county;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getAvatarUrl() {
        return AvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        AvatarUrl = avatarUrl;
    }

    public dobrink.fight_vest.models.FighterCategory getFighterCategory() {
        return FighterCategory;
    }

    public void setFighterCategory(dobrink.fight_vest.models.FighterCategory fighterCategory) {
        FighterCategory = fighterCategory;
    }

}
