package dobrink.fight_vest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dobrin on 15-Jun-17.
 */

public class FightInfoFragment extends android.support.v4.app.Fragment {

    private ArrayListFightSingleton listOfFights;
    private Button buttonStartFight;
    private Button buttonNextRound;
    private Button buttonEndMatch;
    private TextView textViewFightInfo;
    private TextView textViewFighterOneInfo;
    private TextView textViewFighterOnePoints;
    private TextView textViewFighterTwoInfo;
    private TextView textViewFighterTwoPoints;
    private TextView mTextMessage;

    private Fight selectedFight;
    private int MatchID;
    private static String parsedMsg;
    private static int player = -1;
    private static int strength = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.d("FRAGMENT", "INSIDE onCreate");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("fightData"));
            listOfFights = (ArrayListFightSingleton)getActivity().getApplicationContext();
            Log.d("FRAGMENT", "AFTER LocalBroadcastManager");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("FRAGMENT", "INSIDE onCreateView");
        buttonStartFight = (Button) getActivity().findViewById(R.id.buttonStartFight);
        buttonNextRound = (Button) getActivity().findViewById(R.id.buttonNextRound);
        buttonEndMatch = (Button) getActivity().findViewById(R.id.buttonEndMatch);
        textViewFightInfo = (TextView) getActivity().findViewById(R.id.textViewFightInfo);
        textViewFighterOneInfo = (TextView) getActivity().findViewById(R.id.textViewFighterOneInfo);
        textViewFighterOnePoints = (TextView) getActivity().findViewById(R.id.textViewFighterOnePoints);
        textViewFighterTwoInfo = (TextView) getActivity().findViewById(R.id.textViewFighterTwoInfo);
        textViewFighterTwoPoints = (TextView) getActivity().findViewById(R.id.textViewFighterTwoPoints);
        mTextMessage = (TextView) getActivity().findViewById(R.id.textViewMSG);
        Log.d("FRAGMENT", "AFTER FINDBYVIEW");

        return inflater.inflate(R.layout.fragment_info_fight,container,false);
    }

    @Override
    public void onResume(){
        super.onResume();
        if (listOfFights.checkIfFightPicked()){
            updateFightSelected(listOfFights.getSelectedFight());
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            parsedMsg = intent.getStringExtra("parsedMsg");
            player = intent.getIntExtra("player", -1); //if nothing is stored , returns -1
            strength = intent.getIntExtra("strength", -1); //if nothing is stored , returns -1
            mTextMessage.setText(parsedMsg + " player:" + player + " strenght:" + strength);
            Log.d("Received", "Got message from arduino: " + parsedMsg + " player:" + player + " strenght" + strength);
        }
    };


    private void displayFightInfo(Fight fight) {
        //needed or setText doesn't work
        TextView textViewFightInfo = (TextView) getView().findViewById(R.id.textViewFightInfo);
        TextView textViewFighterOneInfo = (TextView) getView().findViewById(R.id.textViewFighterOneInfo);
        TextView textViewFighterTwoInfo = (TextView) getView().findViewById(R.id.textViewFighterTwoInfo);
        //

        StringBuilder sb = new StringBuilder();
        sb.append("FightID: " + fight.getID()).append(System.getProperty("line.separator")); //lineseparotr instead of \n
        sb.append(fight.getFightType().getName()).append(System.getProperty("line.separator"));
        sb.append(fight.getCountry()).append(System.getProperty("line.separator"));
        sb.append(fight.getCity()).append(System.getProperty("line.separator"));
        sb.append(fight.getAddress()).append(System.getProperty("line.separator"));
        textViewFightInfo.setText(sb.toString());

        //Finds the fighters for the fight, and displays info
        for (int i = 0; i < fight.getFightFighters().size(); i++) {
            sb = new StringBuilder();
            Fighter fighter = null;
            try {
                fighter = (Fighter) fight.getFightFighters().get(i).getFighter().clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            sb.append(fighter.getFullName()).append(System.getProperty("line.separator"));
            sb.append(fighter.getAvatar()).append(System.getProperty("line.separator"));// name + newline
            sb.append(fighter.getFighterCategory().getName()).append(System.getProperty("line.separator"));
            sb.append(fighter.getCounty()).append(", ").append(fighter.getCity()).append(System.getProperty("line.separator"));
            sb.append(dateFormat(fighter.getBirthDate())); // convert Date to dd/MM/yyyy
            //Figter 1
            if (i == 0) {
                textViewFighterOneInfo.setText(sb.toString());
            }
            //Figter 2
            else {
                textViewFighterTwoInfo.setText(sb.toString());
            }
        }
    }

    public void updateFightSelected( Fight fight){
        selectedFight = fight;
        showToast("FightID in FightINFO:"+selectedFight.getID());
        displayFightInfo(selectedFight);
    }


    private String dateFormat(Date birthDate) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String result = formatter.format(birthDate);
        return result;
    }

    private void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


}