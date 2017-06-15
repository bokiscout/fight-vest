package dobrink.fight_vest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends Activity {

    private static String parsedMsg;
    private static int player = -1;
    private static int strength = -1;

    private int MatchID;
    private Button btnBluetooth;
    private Button buttonStartFight;
    private Button buttonNextRound;
    private Button buttonEndMatch;
    private TextView textViewFightInfo;
    private TextView textViewFighterOneInfo;
    private TextView textViewFighterOnePoints;
    private TextView textViewFighterTwoInfo;
    private TextView textViewFighterTwoPoints;
    private TextView mTextMessage;
    private ListView listViewMatch;
    private ArrayList<Fight> arrayOfFights ;
    private listFightsAdapter adapterFights ;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBluetooth = (Button) findViewById(R.id.btnBluetooth);
        buttonStartFight = (Button) findViewById(R.id.buttonStartFight);
        buttonNextRound = (Button) findViewById(R.id.buttonNextRound);
        buttonEndMatch = (Button) findViewById(R.id.buttonEndMatch);
        textViewFightInfo = (TextView) findViewById(R.id.textViewFightInfo);
        textViewFighterOneInfo = (TextView) findViewById(R.id.textViewFighterOneInfo);
        textViewFighterOnePoints = (TextView) findViewById(R.id.textViewFighterOnePoints);
        textViewFighterTwoInfo = (TextView) findViewById(R.id.textViewFighterTwoInfo);
        textViewFighterTwoPoints = (TextView) findViewById(R.id.textViewFighterTwoPoints);
        mTextMessage = (TextView) findViewById(R.id.textViewMSG);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        arrayOfFights = new ArrayList<Fight>();
        //temp until web finished, creating default fights
        arrayOfFights.add(new Fight());
        arrayOfFights.add(new Fight());
        arrayOfFights.add(new Fight());
        arrayOfFights.add(new Fight());
        arrayOfFights.add(new Fight());
        //temp above
        adapterFights = new listFightsAdapter(this, arrayOfFights);
        //this is showen untill a Fight ID is picked
        listViewMatch = (ListView) findViewById(R.id.listViewMatch);
        listViewMatch.setAdapter(adapterFights);

        //Bluetooth button click
        btnBluetooth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("MainAcrivity", "BUTTON: BLUETOOTH -> clicked");
                Intent bluetoothIntend = new Intent(MainActivity.this, BluetoothSearchAndConnect.class);
                MainActivity.this.startActivity(bluetoothIntend);
                return true;
            }
        });
        //Registers receiver for arduino msg to get from BluetoothWebService
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter("fightData"));

        //hides Fight Elements until a Fight ID is picked, shows the list of fights
        hideMatchView();

        //Handels click in listview, used blockedDescendants in listview xml to fix not able to click the items
        listViewMatch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MatchID=arrayOfFights.get(position).getID();
                showMatchView(); //showd Fight Elements until a Fight ID is picked, hides the list of fights
                showToast("Fight ID: "+String.valueOf(MatchID));
                displayFightInfo(arrayOfFights.get(position)); //displays fight info to main screen
            }
        });
    }

    //hides Fight Elements until a Fight ID is picked, shows the list of fights
    private void hideMatchView() {
        buttonStartFight.setVisibility(View.GONE);
        buttonNextRound.setVisibility(View.GONE);
        buttonEndMatch.setVisibility(View.GONE);
        textViewFighterOneInfo.setVisibility(View.GONE);
        textViewFighterOnePoints.setVisibility(View.GONE);
        textViewFighterTwoInfo.setVisibility(View.GONE);
        textViewFighterTwoPoints.setVisibility(View.GONE);
        textViewFightInfo.setVisibility(View.GONE);
        mTextMessage.setVisibility(View.GONE);
        listViewMatch.setVisibility(View.VISIBLE);
    }

    //showd Fight Elements until a Fight ID is picked, hides the list of fights
    private void showMatchView() {
        buttonStartFight.setVisibility(View.VISIBLE);
        buttonNextRound.setVisibility(View.VISIBLE);
        buttonEndMatch.setVisibility(View.VISIBLE);
        textViewFighterOneInfo.setVisibility(View.VISIBLE);
        textViewFighterOnePoints.setVisibility(View.VISIBLE);
        textViewFighterTwoInfo.setVisibility(View.VISIBLE);
        textViewFighterTwoPoints.setVisibility(View.VISIBLE);
        textViewFightInfo.setVisibility(View.VISIBLE);
        mTextMessage.setVisibility(View.VISIBLE);
        listViewMatch.setVisibility(View.GONE);
    }

    //displays fight info to main screen
    private void displayFightInfo(Fight fight) {
        StringBuilder sb = new StringBuilder();
        sb.append("FightID: "+fight.getID()).append(System.getProperty("line.separator")); //lineseparotr instead of \n
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
            if (i == 0){
                textViewFighterOneInfo.setText(sb.toString());
            }
            //Figter 2
            else {
                textViewFighterTwoInfo.setText(sb.toString());
            }
        }
    }
    //Receiver for arduino msg from BluetoothWebService
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            parsedMsg = intent.getStringExtra("parsedMsg");
            player = intent.getIntExtra("player",-1); //if nothing is stored , returns -1
            strength = intent.getIntExtra("strength",-1); //if nothing is stored , returns -1
            mTextMessage.setText(parsedMsg+" player:"+player+" strenght"+strength);
            Log.d("Received", "Got message from arduino: " + parsedMsg+" player:"+player+" strenght"+strength);
        }
    };
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    // Date to string
    private String dateFormat(Date birthDate) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String result = formatter.format(birthDate);
        return result;
    }
}
