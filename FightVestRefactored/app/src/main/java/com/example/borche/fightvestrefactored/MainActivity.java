package com.example.borche.fightvestrefactored;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private EditText etFighterRed;
    private EditText etFighterBlue;
    private TextView tvMsg;
    private Button  btnBluetooth;


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
        Log.d("MainAcrivity", "onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        btnBluetooth = (Button) findViewById(R.id.btnBluetooth);

        btnBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainAcrivity", "BUTTON: BLUETOOTH -> clicked");
                Intent bluetoothIntend = new Intent(MainActivity.this, BluetoothSearchAndConnect.class);
                MainActivity.this.startActivity(bluetoothIntend);
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter("fightData"));
    }

    @Override
    protected void onStart() {
        Log.d("MainAcrivity", "onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("MainAcrivity", "onResume()");
        super.onResume();

        if (isMyServiceRunning(BluetoothService.class)){
            Log.d("MainAcrivity", "SERVICE -> running");
        }
        else{
            Log.d("MainAcrivity", "SERVICE -> not running");
        }
    }

    @Override
    protected void onPause() {
        Log.d("MainActivity", "onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("MainActivity", "onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("MainActivity", "onDestroy()");
        super.onDestroy();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("parsedMsg");
            mTextMessage.setText(message);
            Log.d("receiver", "Got message: " + message);
        }
    };

}
