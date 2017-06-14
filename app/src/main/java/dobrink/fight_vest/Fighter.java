package dobrink.fight_vest;

import java.util.Date;
import java.util.Random;

/**
 * Created by Dobrin on 14-Jun-17.
 */

public class Fighter implements Cloneable {
    Random random = new Random();
    int ID;
    String FirstName;
    String LastName;
    String Avatar;
    String County;
    String City;
    Date BirthDate;
    String FullName;
    String AvatarUrl;
    FighterCategory FighterCategory;

    public Fighter() {
        this.ID = random.nextInt();
        FirstName = "FirstName";
        LastName = "LastName";
        Avatar = "Avatar";
        County = "County";
        City = "City";
        BirthDate = new Date();
        FullName = "FirstName LastName";
        AvatarUrl = "AvatarUrl";
        FighterCategory = new FighterCategory();
    }

    public Fighter(int ID, String firstName, String lastName, String avatar, String county, String city, Date birthDate, String fullName, String avatarUrl, dobrink.fight_vest.FighterCategory fighterCategory) {
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        Fighter tempClone = (Fighter) super.clone();
        FighterCategory fcClone = new FighterCategory(this.FighterCategory.getID(),this.FighterCategory.getName());
        tempClone.setFighterCategory(fcClone);
        return tempClone;
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

    public Date getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(Date birthDate) {
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

    public dobrink.fight_vest.FighterCategory getFighterCategory() {
        return FighterCategory;
    }

    public void setFighterCategory(dobrink.fight_vest.FighterCategory fighterCategory) {
        FighterCategory = fighterCategory;
    }
}
