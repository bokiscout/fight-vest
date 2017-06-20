package dobrink.fight_vest;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import dobrink.fight_vest.models.Fight;
import dobrink.fight_vest.models.Fighter;
import dobrink.fight_vest.models.Hit;
import dobrink.fight_vest.models.Round;

/**
 * Created by Dobrin on 16-Jun-17.
 */

@SuppressWarnings({"DefaultFileTemplate", "CanBeFinal"})
public class FightLogicHelper extends Application
{
    // Points value
    private final static int WEAK_HIT = 1;
    private final static int STRONG_HIT = 1;

    private static FightLogicHelper ALFSInstance;
    private static ArrayList<Fight> fights;
    private static ArrayList<Round> rounds;
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
    private Date endRoundTime; //used in nextRound, used to get StartTime of last round;

    private Random generator = new Random();

    public FightLogicHelper(){
        Log.d("FIGHT LOGIC HELPER", "constructor()" );


        fights = new ArrayList<>();
        rounds = new ArrayList<>();
        hits = new ArrayList<>();
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
        if (fights.isEmpty()){
            for (int i = 0; i < n ; i++) {
                fights.add(new Fight());
            }
        }
    }

    //Checks if a fight was selected in the FightList
    public synchronized boolean checkIfFightPicked(){
        Log.d("FIGHT LOGIC HELPER", "checkIfFightPicked()" );
        return selectedFight != null;
    }

    public synchronized void  registerHit(Context context) {
        Log.d("FIGHT LOGIC HELPER", "registerHit()" );

        int hitID = hits.size(); //set hitID to be position of Hits list, will be unique, incremented
        Date hitTimestamp = new Date(); // Time of hit
        Fighter hitFighter = selectedFight.getFightFighters().get(player).getFighter(); // Fighter hitting
        int hitFighterID = hitFighter.getID(); //Fighter hitting IDh
        Hit hit = new Hit(hitID,hitTimestamp,hitFighterID,hitFighter); //new Hit event
        //add points, chech if can tally up pints and matchID is -1 (to wait after matchID is updated)
        if (registerPoints==true && getMatchID()!=-1){
            addPoints(player,strength);
            VolleySingleton.getInstance(context).postHitEvent(hitFighterID, strength);
        }
        hits.add(hit); // add hit event to the list of hits
        Log.d("FIGHT LOGIC HELPER", "registerHit() -> hit: " + hit.toString() );
    }
    //used for fake hits
    public synchronized void registerHitFake(Context context) {
        player = generator.nextInt(2);
        strength = generator.nextInt(2)+1;
        parsedMsg = "Fake HIT, Player:"+player+" strength:"+strength;
        Log.d("FIGHT LOGIC HELPER", "registerHitFake(): "+ parsedMsg);
        int hitID = hits.size(); //set hitID to be position of Hits list, will be unique, incremented
        Date hitTimestamp = new Date(); // Time of hit
        if (getFights().isEmpty()){
            makeFakeFights(10);
        }
        if (selectedFight==null){
            selectedFight = fights.get(0);//pick 1st fight in list
        }
        Fighter hitFighter = selectedFight.getFightFighters().get(player).getFighter(); // Fighter hitting
        int hitFighterID = hitFighter.getID(); //Fighter hitting IDh
        Hit hit = new Hit(hitID,hitTimestamp,hitFighterID,hitFighter); //new Hit event
        //add points
        if (registerPoints==true && MatchID!=-1){
            addPoints(player,strength);
            VolleySingleton.getInstance(context).postHitEvent(hitFighterID,strength);
        }
        hits.add(hit); // add hit event to the list of hits
    }

    private void addPoints(int player, int strength) {
        Log.d("FIGHT LOGIC HELPER", "addPoints()" );
        //player: 0 FighterOne, 1+ FighterTwo, is hit, give points to other player
        int points;
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

    public synchronized void startFight(Context context) {
        Log.d("FIGHT LOGIC HELPER", "startFight(): "+MatchID );

        rounds = new ArrayList<>();
        if (MatchID!=-1){
            VolleySingleton.getInstance(context).postStartFight();
        }else{
            Log.d("FIGHT LOGIC HELPER", "startFight() ID -1 : "+MatchID );
        }
    }
    public synchronized void startNextRound(Context context) {
        Log.d("FIGHT LOGIC HELPER", "startNextRound(): "+MatchID );

        hits = new ArrayList<>();
        startRoundTime = new Date();
        registerPoints = true;
        VolleySingleton.getInstance(context).postStartNextRound();
    }
    public synchronized void endLastRound(Context context) {
        Log.d("FIGHT LOGIC HELPER", "endLastRound(): "+MatchID );
        int roundID = rounds.size(); // roundID to be position of Rounds list, will be unique, incremented
        endRoundTime = new Date(); // endTime of last round;
        Round currentRound = new Round(roundID, startRoundTime, endRoundTime,MatchID,hits);
        rounds.add(currentRound); // add round to the list of rounds
        registerPoints = false;
        VolleySingleton.getInstance(context).postEndLastRound();
    }
    public synchronized void endFight(Context context) {
        Log.d("FIGHT LOGIC HELPER", "endFight(): "+MatchID );

        VolleySingleton.getInstance(context).postEndFight();
    }
    //
    //Getters and Setters
    //
    public void setSelectedFight(Fight selectedFight) {
        FighterOnePoints = 0 ;
        FighterTwoPoints = 0 ;

        FightLogicHelper.selectedFight = selectedFight;
    }

    public ArrayList<Fight> getFights() {
        Log.d("FIGHT LOGIC HELPER", "getFights()" );
        return fights;
    }

    public static void setFights(ArrayList<Fight> mFights) {
        fights = mFights;
        rounds.clear();
        hits.clear();
        Log.d("FIGHT LOGIC HELPER", "setFights(): ");
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
