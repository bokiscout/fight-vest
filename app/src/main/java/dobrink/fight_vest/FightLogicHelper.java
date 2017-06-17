package dobrink.fight_vest;

import android.app.Application;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Dobrin on 16-Jun-17.
 */

public class FightLogicHelper extends Application
{
    // Points value
    final static int WEAK_HIT = 1;
    final static int STRONG_HIT = 5;

    private static FightLogicHelper ALFSInstance;
    private static ArrayList<Fight> fights;
    private static ArrayList<RoundDTO> rounds;
    private static ArrayList<Hit> hits;
    private static Fight selectedFight;

    private static int MatchID ;
    private static String parsedMsg ;
    private static int player ; // 0 or 1
    private static int strength ;

    private static boolean registerPoints;
    private static int FighterOnePoints;
    private static int FighterTwoPoints;

    private Date startRoundTime; //used in starFight,used to get StartTime of first round
    private Date endRoundTime; //used in nextRound, used to get starttime of last round;

    public FightLogicHelper(){
        fights = new ArrayList<Fight>();
        rounds = new ArrayList<RoundDTO>();
        hits = new ArrayList<Hit>();
        selectedFight = null;
        MatchID = -1;
        parsedMsg = "HIT MSG";
        player = -1;
        strength = -1;
        FighterOnePoints = 0;
        FighterTwoPoints = 0;
        registerPoints = false;
    }

    public synchronized static FightLogicHelper getInstance() {
        if (ALFSInstance == null) {
            ALFSInstance = new FightLogicHelper();
        }
        return ALFSInstance;
    }

    //Make n fake fights , for testing
    public synchronized void makeFakeFights(int n){
        if (ALFSInstance == null) {
            ALFSInstance = new FightLogicHelper();
        }
        for (int i = 0; i < n ; i++) {
            fights.add(new Fight());
        }
    }

    //Checks if a fight was selected in the FightList
    public synchronized boolean checkIfFightPicked(){
        if (selectedFight == null){
            return false;
        }
        return true;
    }

    public synchronized void  registerHit() {
        int hitID = hits.size(); //set hitID to be position of Hits list, will be unique, incremented
        Date hitTimestamp = new Date(); // Time of hit
        Fighter hitFighter = selectedFight.getFightFighters().get(player).getFighter(); // Fighter hit
        int hitFighterID = hitFighter.getID(); //Fighter hit IDh
        Hit hit = new Hit(hitID,hitTimestamp,hitFighterID,hitFighter); //new Hit event
        //add points
        if (registerPoints){
            addPoints(player,strength);
        }
        hits.add(hit); // add hit event to the list of hits
    }
    //used for fake hits
    public synchronized void registerHitFake() {
        Random generator = new Random();
        player = generator.nextInt(2);
        strength = generator.nextInt(2)+1;
        parsedMsg = "Fake HIT, Player:"+player+" strength:"+strength;
        int hitID = hits.size(); //set hitID to be position of Hits list, will be unique, incremented
        Date hitTimestamp = new Date(); // Time of hit
        if (getFights().isEmpty()){
            makeFakeFights(10);
        }
        if (selectedFight==null){
            selectedFight = fights.get(0);//pick 1st fight in list
        }
        Fighter hitFighter = selectedFight.getFightFighters().get(player).getFighter(); // Fighter hit
        int hitFighterID = hitFighter.getID(); //Fighter hit IDh
        Hit hit = new Hit(hitID,hitTimestamp,hitFighterID,hitFighter); //new Hit event
        //add points
        if (registerPoints){
            addPoints(player,strength);
        }
        hits.add(hit); // add hit event to the list of hits
    }

    private void addPoints(int player, int strength) {
        //player: 0 FigterOne, 1+ FighterTwo, is hit, give points to other player
        int points = 0;
        if (strength==1){
            points=WEAK_HIT;
        }else{
            points=STRONG_HIT;
        }
        if (player==0){
            FighterTwoPoints+=points;
        }else{
            FighterOnePoints+=points;
        }
    }

    public synchronized void startFight() {
        startRoundTime = new Date(); //used to get StartTime of first round
        rounds = new ArrayList<RoundDTO>();
        hits = new ArrayList<Hit>();
        registerPoints = true;
    }
    public synchronized void nextRound() {
        int roundID = rounds.size(); // roundID to be position of Rounds list, will be unique, incremented
        endRoundTime = new Date(); // endTime of last round;
        RoundDTO currentRound = new RoundDTO(roundID, startRoundTime, endRoundTime,MatchID,hits);
        startRoundTime = endRoundTime; // make last round end time the new start time for next round

        rounds.add(currentRound); // add round to the list of rounds
    }
    public void endFight() {
        registerPoints = false;
        int roundID = rounds.size(); // roundID to be position of Rounds list, will be unique, incremented
        endRoundTime = new Date(); //end time for last round
        RoundDTO currentRound = new RoundDTO(roundID, startRoundTime, endRoundTime,MatchID,hits);

        rounds.add(currentRound); // add last round for fight
    }
    //
    //Getters and Setters
    //
    public void setSelectedFight(Fight selectedFight) {
        fights = new ArrayList<Fight>();
        this.selectedFight = selectedFight;
    }

    public ArrayList<Fight> getFights() {
        return fights;
    }

    public Fight getSelectedFight() {
        return selectedFight;
    }

    public int getMatchID() {
        return MatchID;
    }

    public void setMatchID(int matchID) {
        MatchID = matchID;
    }

    public static String getParsedMsg() {
        return parsedMsg;
    }

    public static void setParsedMsg(String parsedMsg) {
        FightLogicHelper.parsedMsg = parsedMsg;
    }

    public static int getPlayer() {
        return player;
    }

    public static void setPlayer(int player) {
        FightLogicHelper.player = player;
    }

    public static int getStrength() {
        return strength;
    }

    public static void setStrength(int strength) {
        FightLogicHelper.strength = strength;
    }

    public static int getFighterOnePoints() {
        return FighterOnePoints;
    }

    public static int getFighterTwoPoints() {
        return FighterTwoPoints;
    }
}
