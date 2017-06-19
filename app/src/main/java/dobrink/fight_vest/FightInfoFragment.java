package dobrink.fight_vest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import dobrink.fight_vest.models.Fight;
import dobrink.fight_vest.models.Fighter;

/**
 * Created by Dobrin on 15-Jun-17.
 */

@SuppressWarnings({"DefaultFileTemplate", "CanBeFinal"})
public class FightInfoFragment extends android.support.v4.app.Fragment {

    private final static String URL = "http://www.fv.pdtransverzalec.org.mk";
    private Context context;
    private FightLogicHelper fightLogic;
    private Button buttonStartFight;
    private Button buttonStartNextRound;
    private Button buttonEndLastRound;
    private Button buttonEndMatch;
    private TextView tvFightID;
    private TextView tvFightStartTime;
    private TextView tvFightLocation;
    private TextView tvFighter1FullName;
    private TextView tvFighter1Location;
    private TextView tvFightet1Class;
    private TextView tvFighter1Birth;
    private TextView tvFighter2FullName;
    private TextView tvFighter2Location;
    private TextView tvFighter2Class;
    private TextView tvFighter2Birth;
    private TextView textViewFighterOnePoints;
    private TextView textViewFighterTwoPoints;
    private TextView mTextMessage;
    private ImageView imageViewFighter1;
    private ImageView imageViewFighter2;

    //used for fake hits
    private int mInterval = 5000; // 5000 = 1s
    private Handler mHandler;
    //


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        fightLogic = FightLogicHelper.getInstance();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("fightData"));
        //used for fake hits
        mHandler = new Handler();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info_fight, container, false);

        Log.d("FIGHT INFO FRAGMENT", "INSIDE onCreateView");
        buttonStartFight = view.findViewById(R.id.buttonStartFight);
        buttonStartNextRound = view.findViewById(R.id.buttonStartNextRound);
        buttonEndLastRound = view.findViewById(R.id.buttonEndLastRound);
        buttonEndMatch = view.findViewById(R.id.buttonEndMatch);
        tvFightID = view.findViewById(R.id.tvFightID);
        tvFightStartTime = view.findViewById(R.id.tvFightStartTime);
        tvFightLocation = view.findViewById(R.id.tvFightLocation);
        imageViewFighter1 =view.findViewById(R.id.imageViewFighter1);
        imageViewFighter2 = view.findViewById(R.id.imageViewFighter2);
        tvFighter1FullName = view.findViewById(R.id.tvFighter1FullName);
        tvFighter1Location = view.findViewById(R.id.tvFighter1Location);
        tvFighter1Location.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_location_on_black_24dp, 0, 0, 0);
        tvFightet1Class = view.findViewById(R.id.tvFightet1Class);
        tvFightet1Class.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_school_black_24dp, 0, 0, 0);
        tvFighter1Birth = view.findViewById(R.id.tvFighter1Birth);
        tvFighter1Birth.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_cake_black_24dp, 0, 0, 0);
        tvFighter2FullName = view.findViewById(R.id.tvFighter2FullName);
        tvFighter2Location = view.findViewById(R.id.tvFighter2Location);
        tvFighter2Location.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_location_on_black_24dp, 0);
        tvFighter2Class = view.findViewById(R.id.tvFighter2Class);
        tvFighter2Class.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_school_black_24dp, 0);
        tvFighter2Birth = view.findViewById(R.id.tvFighter2Birth);
        tvFighter2Birth.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cake_black_24dp, 0);
        textViewFighterOnePoints = view.findViewById(R.id.textViewFighterOnePoints);
        textViewFighterTwoPoints = view.findViewById(R.id.textViewFighterTwoPoints);
        mTextMessage = view.findViewById(R.id.textViewMSG);
        Log.d("FIGHT INFO FRAGMENT", "After FindByView");

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("FIGHT INFO FRAGMENT", "onActivityCreated()");

        super.onActivityCreated(savedInstanceState);
        buttonStartFight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Fight Started");
                fightLogic.startFight(context);
                setButtonsEnabled(false,true,false,false);
            }
        });
        buttonStartNextRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Start Next Round");
                fightLogic.startNextRound(context);
                setButtonsEnabled(false,false,true,false);
            }
        });
        buttonEndLastRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("End Last Round");
                fightLogic.endLastRound(context);
                setButtonsEnabled(false,true,false,true);
            }
        });
        buttonEndMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Fight Ended, pick a new Fight");
                fightLogic.endFight(context);
                setButtonsEnabled(false,false,false,false);
            }
        });
        startRepeatingTask(); //for fake hits
    }

    @Override
    public void onResume() {
        Log.d("FIGHT INFO FRAGMENT", "onResume()");
        super.onResume();

        if (fightLogic.checkIfFightPicked()) { //May start Fight
            //Show info of fight
            displayFightInfo(fightLogic.getSelectedFight());
            //setButtonsEnabled(true,false,false,false);
        } else {
            //May not start Fight, must pick a fight from list
            showToast("Pick a Fight from the Fights List");
            setButtonsEnabled(false,false,false,false);
        }
    }
    //used for fake hits
    @Override
    public void onDestroy() {
        Log.d("FIGHT INFO FRAGMENT", "onDestroy()");
        super.onDestroy();
        stopRepeatingTask();
    }

    private Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                fightLogic.registerHitFake(context);//this function can change value of mInterval.
                updateHitInfo();
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    private void updateHitInfo() {
        Log.d("FIGHT INFO FRAGMENT", "updateInfo()");
        mTextMessage.setText(FightLogicHelper.getParsedMsg());
        textViewFighterOnePoints.setText(String.valueOf(FightLogicHelper.getFighterOnePoints()));
        textViewFighterTwoPoints.setText(String.valueOf(FightLogicHelper.getFighterTwoPoints()));
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    private void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    // true = enabled , false = disabled
    @SuppressWarnings("SameParameterValue")
    private void setButtonsEnabled(boolean StartFight, boolean StartNextRound, boolean EndLastRound, boolean EndMatch) {
        buttonStartFight.setEnabled(StartFight);
        buttonStartNextRound.setEnabled(StartNextRound);
        buttonEndLastRound.setEnabled(EndLastRound);
        buttonEndMatch.setEnabled(EndMatch);
    }

    private void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void displayFightInfo(Fight fight) {
        showToast("FightID: " + fight.getID());
        mTextMessage.setText(FightLogicHelper.getParsedMsg());
        tvFightID.setText("Fight ID: "+String.valueOf(fight.getID()));
        tvFightStartTime.setText("Fight Start: "+fight.getStartTime());
        tvFightLocation.setText(fight.getAddress()+", "+fight.getCity()+", "+fight.getCountry());
        //Finds the fighters for the fight, and displays info
        for (int i = 0; i < fight.getFightFighters().size(); i++) {
            Fighter fighter = fight.getFightFighters().get(i).getFighter();
            //Fighter 1
            if (i == 0) {
                Picasso.with(getContext()).load(URL+fighter.getAvatarUrl()).placeholder(R.mipmap.ic_launcher).into(imageViewFighter1);
                tvFighter1FullName.setText(fighter.getFullName());
                tvFighter1Location.setText(fighter.getCity()+", "+fighter.getCounty());
                //tvFightet1Class.setText(fighter.getFighterCategory().getName());
                tvFighter1Birth.setText(fighter.getBirthDate());
            }
            //Fighter 2
            else {
                Picasso.with(getContext()).load(URL+fighter.getAvatarUrl()).placeholder(R.mipmap.ic_launcher).into(imageViewFighter2);
                tvFighter2FullName.setText(fighter.getFullName());
                tvFighter2Location.setText(fighter.getCity()+", "+fighter.getCounty());
                //tvFighter2Class.setText(fighter.getFighterCategory().getName());
                tvFighter2Birth.setText(fighter.getBirthDate());
            }
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("FIGHT INFO FRAGMENT", "broadcastReceiver() -> onReceive()");
            // debugging
            Log.d("FIGHT INFO FRAGMENT", "broadcastReceiver() -> onReceive() -> receive" + intent.getStringExtra("parsedMsg"));
            Log.d("FIGHT INFO FRAGMENT", "broadcastReceiver() -> onReceive() -> player" + intent.getIntExtra("player", -1));
            Log.d("FIGHT INFO FRAGMENT", "broadcastReceiver() -> onReceive() -> strength" + intent.getIntExtra("strength", -1));

            FightLogicHelper.setParsedMsg(intent.getStringExtra("parsedMsg"));
            FightLogicHelper.setPlayer(intent.getIntExtra("player", -1)); //if nothing is stored , returns -1
            FightLogicHelper.setStrength(intent.getIntExtra("strength", -1)); //if nothing is stored , returns -1

            if (fightLogic.getFights().isEmpty()){
                showToast("Pick a Fight from the Fights List");
                Log.d("FIGHT INFO FRAGMENT", "onReceive() -> empty fight");
            }else{
                Log.d("FIGHT INFO FRAGMENT", "onReceive() -> fight is OK (not Empty)");
                fightLogic.registerHit(context);
                updateHitInfo();
            }
        }
    };

}