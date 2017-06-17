package dobrink.fight_vest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dobrin on 15-Jun-17.
 */

public class FightInfoFragment extends android.support.v4.app.Fragment {

    private FightLogicHelper fightLogic;
    private Button buttonStartFight;
    private Button buttonNextRound;
    private Button buttonEndMatch;
    private TextView textViewFightInfo;
    private TextView textViewFighterOneInfo;
    private TextView textViewFighterOnePoints;
    private TextView textViewFighterTwoInfo;
    private TextView textViewFighterTwoPoints;
    private TextView mTextMessage;

    //used for fake hits
    private int mInterval = 1000; // 1000 = 1s
    private Handler mHandler;
    //


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fightLogic = FightLogicHelper.getInstance();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("fightData"));
        //used for fake hits
        mHandler = new Handler();

        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info_fight, container, false);

        Log.d("FRAGMENT", "INSIDE onCreateView");
        buttonStartFight = (Button) view.findViewById(R.id.buttonStartFight);
        buttonNextRound = (Button) view.findViewById(R.id.buttonNextRound);
        buttonEndMatch = (Button) view.findViewById(R.id.buttonEndMatch);
        textViewFightInfo = (TextView) view.findViewById(R.id.textViewFightInfo);
        textViewFighterOneInfo = (TextView) view.findViewById(R.id.textViewFighterOneInfo);
        textViewFighterOnePoints = (TextView) view.findViewById(R.id.textViewFighterOnePoints);
        textViewFighterTwoInfo = (TextView) view.findViewById(R.id.textViewFighterTwoInfo);
        textViewFighterTwoPoints = (TextView) view.findViewById(R.id.textViewFighterTwoPoints);
        mTextMessage = (TextView) view.findViewById(R.id.textViewMSG);
        Log.d("FRAGMENT", "AFTER FINDBYVIEW");

        buttonStartFight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showToast("Fight Started");
                fightLogic.startFight();
                return false;
            }
        });
        buttonNextRound.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showToast("New Round");
                fightLogic.nextRound();
                return false;
            }
        });
        buttonEndMatch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showToast("Fight Ended, pick a new Fight");
                setItemsEnabled(false);
                fightLogic.endFight();

                return false;
            }
        });
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //startRepeatingTask(); //for fake hits
    }
    @Override
    public void onResume() {
        super.onResume();

        if (fightLogic.checkIfFightPicked()) { //May start Fight
            //Show info of fight
            displayFightInfo(fightLogic.getSelectedFight());
        } else {
            //May not start Fight, must pick a fight from list
            showToast("Pick a Fight from the Fights List");
            setItemsEnabled(false);
        }
    }
    //used for fake hits
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }
    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                fightLogic.registerHitFake();//this function can change value of mInterval.
                updateHitInfo();
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    private void updateHitInfo() {
        mTextMessage.setText(fightLogic.getParsedMsg());
        textViewFighterOnePoints.setText(String.valueOf(fightLogic.getFighterOnePoints()));
        textViewFighterTwoPoints.setText(String.valueOf(fightLogic.getFighterTwoPoints()));
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    // true = enabled , false = disabled
    private void setItemsEnabled(boolean bool) {
        buttonStartFight.setEnabled(bool);
        buttonNextRound.setEnabled(bool);
        buttonEndMatch.setEnabled(bool);
    }

     private String dateFormat(Date birthDate) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String result = formatter.format(birthDate);
        return result;
    }

    private void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void displayFightInfo(Fight fight) {
        showToast("FightID: " + fight.getID());
        mTextMessage.setText(fightLogic.getParsedMsg());
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
            Fighter fighter = fight.getFightFighters().get(i).getFighter();

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

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            fightLogic.setParsedMsg(intent.getStringExtra("parsedMsg"));
            fightLogic.setPlayer(intent.getIntExtra("player", -1)); //if nothing is stored , returns -1
            fightLogic.setStrength(intent.getIntExtra("strength", -1)); //if nothing is stored , returns -1
            if (fightLogic.getFights().isEmpty()){
                showToast("Pick a Fight from the Fights List");
            }else{
                fightLogic.registerHit();
                updateHitInfo();
            }
        }
    };

}