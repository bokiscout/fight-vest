package dobrink.fight_vest;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Dobrin on 16-Jun-17.
 */

public class ArrayListFightSingleton extends Application
{
    private static ArrayListFightSingleton ALFSInstance;
    private ArrayList<Fight> fights;
    private Fight selectedFight;
    private int MatchID;

    public ArrayListFightSingleton (){
        fights = new ArrayList<Fight>();
        selectedFight = null;
        MatchID = -1;
    }

    public static ArrayListFightSingleton getInstance() {
        if (ALFSInstance == null) {
            ALFSInstance = new ArrayListFightSingleton();
        }
        return ALFSInstance;
    }
    public  void makeFakeFights(int n){
        if (ALFSInstance == null) {
            ALFSInstance = new ArrayListFightSingleton();
        }
        for (int i = 0; i < n ; i++) {
            fights.add(new Fight());
        }
    }
    public boolean checkIfFightPicked(){
        if (selectedFight == null){
            return false;
        }
        return true;
    }
    public ArrayList<Fight> getFights() {
        return fights;
    }

    public Fight getSelectedFight() {
        return selectedFight;
    }

    public void setSelectedFight(Fight selectedFight) {
        this.selectedFight = selectedFight;
    }

    public int getMatchID() {
        return MatchID;
    }

    public void setMatchID(int matchID) {
        MatchID = matchID;
    }
}
