package dobrink.fight_vest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Button buttonBT;
    private Button buttonSer;
    private Button buttonStartFight;
    private Button buttonNextRound;
    private Button buttonEndMatch;
    private TextView textViewFighterOneInfo;
    private TextView textViewFighterOnePoints;
    private TextView textViewFighterTwoInfo;
    private TextView textViewFighterTwoPoints;
    private TextView textViewMSG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonBT = (Button) findViewById(R.id.buttonBT);
        buttonSer = (Button) findViewById(R.id.buttonSer);
        buttonStartFight = (Button) findViewById(R.id.buttonStartFight);
        buttonNextRound = (Button) findViewById(R.id.buttonNextRound);
        buttonEndMatch = (Button) findViewById(R.id.buttonEndMatch);
        textViewFighterOneInfo = (TextView) findViewById(R.id.textViewFighterOneInfo);
        textViewFighterOnePoints = (TextView) findViewById(R.id.textViewFighterOnePoints);
        textViewFighterTwoInfo = (TextView) findViewById(R.id.textViewFighterTwoInfo);
        textViewFighterTwoPoints = (TextView) findViewById(R.id.textViewFighterTwoPoints);
        textViewMSG = (TextView) findViewById(R.id.textViewMSG);

        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter("fightData"));

        buttonBT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("BUTTON CLICK", "BLUETOOTH BUTTON");
                Intent i = new Intent(MainActivity.this,BluetoothActivity.class);
                startActivity(i);
                return true;
            }
        });
        buttonSer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("BUTTON CLICK", "SERVICE BUTTON");
                Intent intent = new Intent(MainActivity.this, BluetoothWebService.class);
                startService(intent);
                return true;
            }
        });
    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("parsedMsg");
            textViewMSG.setText(message);
            Log.d("Received", "Got message from arduino: " + message);
        }
    };

}
