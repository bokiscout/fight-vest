package dobrink.fight_vest;

import android.app.Application;
import android.util.Log;

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

    Random generator = new Random();

    public FightLogicHelper(){
        Log.d("FIGHT LOGIC HELPER", "constructor()" );

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
        Log.d("FIGHT LOGIC HELPER", "getInstance()" );
        if (ALFSInstance == null) {
            ALFSInstance = new FightLogicHelper();
        }
        return ALFSInstance;
    }

    //Make n fake fights , for testing
    public synchronized void makeFakeFights(int n){
        Log.d("FIGHT LOGIC HELPER", "makeFakeFights()" );
        if (ALFSInstance == null) {
            ALFSInstance = new FightLogicHelper();
        }
        for (int i = 0; i < n ; i++) {
            fights.add(new Fight());
        }
    }

    //Checks if a fight was selected in the FightList
    public synchronized boolean checkIfFightPicked(){
        Log.d("FIGHT LOGIC HELPER", "checkIfFightPicked()" );
        if (selectedFight == null){
            return false;
        }
        return true;
    }

    public synchronized void  registerHit() {
        Log.d("FIGHT LOGIC HELPER", "registerHit()" );

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

        Log.d("FIGHT LOGIC HELPER", "registerHit() -> hit: " + hit.toString() );
    }
    //used for fake hits
    public synchronized void registerHitFake() {
        Log.d("FIGHT LOGIC HELPER", "registerHitFake()" );

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
        Log.d("FIGHT LOGIC HELPER", "addPoints()" );
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
        Log.d("FIGHT LOGIC HELPER", "startFight()" );

        startRoundTime = new Date(); //used to get StartTime of first round
        rounds = new ArrayList<RoundDTO>();
        hits = new ArrayList<Hit>();
        registerPoints = true;
    }
    public synchronized void nextRound() {
        Log.d("FIGHT LOGIC HELPER", "nextRound()" );

        int roundID = rounds.size(); // roundID to be position of Rounds list, will be unique, incremented
        endRoundTime = new Date(); // endTime of last round;
        RoundDTO currentRound = new RoundDTO(roundID, startRoundTime, endRoundTime,MatchID,hits);
        startRoundTime = endRoundTime; // make last round end time the new start time for next round

        rounds.add(currentRound); // add round to the list of rounds
    }
    public void endFight() {
        Log.d("FIGHT LOGIC HELPER", "endFight()" );

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
        FighterOnePoints = 0 ;
        FighterTwoPoints = 0 ;

        this.selectedFight = selectedFight;
    }

    public ArrayList<Fight> getFights() {
        Log.d("FIGHT LOGIC HELPER", "getFight()" );
        return fights;
    }

    public Fight getSelectedFight() {
        Log.d("FIGHT LOGIC HELPER", "getSelectedFight()" );
        return selectedFight;
    }

    public int getMatchID() {
        Log.d("FIGHT LOGIC HELPER", "getMatchID()" );
        return MatchID;
    }

    public void setMatchID(int matchID) {
        Log.d("FIGHT LOGIC HELPER", "setMatchID()" );
        MatchID = matchID;
    }

    public static String getParsedMsg() {
        Log.d("FIGHT LOGIC HELPER", "getParsedMsg()" );
        return parsedMsg;
    }

    public static void setParsedMsg(String parsedMsg) {
        Log.d("FIGHT LOGIC HELPER", "setParsedMsg" );
        FightLogicHelper.parsedMsg = parsedMsg;
    }

    public static int getPlayer() {
        Log.d("FIGHT LOGIC HELPER", "getPlayer()" );
        return player;
    }

    public static void setPlayer(int player) {
        Log.d("FIGHT LOGIC HELPER", "setPlayer()" );
        FightLogicHelper.player = player;
    }

    public static int getStrength() {
        Log.d("FIGHT LOGIC HELPER", "getStrength()" );
        return strength;
    }

    public static void setStrength(int strength) {
        Log.d("FIGHT LOGIC HELPER", "setStrength()" );
        FightLogicHelper.strength = strength;
    }

    public static int getFighterOnePoints() {
        Log.d("FIGHT LOGIC HELPER", "getFighterOneInfoPoints()" );
        return FighterOnePoints;
    }

    public static int getFighterTwoPoints() {
        Log.d("FIGHT LOGIC HELPER", "getFighterTwoInfoPoints()" );
        return FighterTwoPoints;
    }
}
