package finki.edu.fightvest;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    /*################################################################################
    ##                                                                              ##
    ##      This activity is used only to search for already paired devices.        ##
    ##      Connecting and transmitting is done in ConectAndTransmit Activity.      ##
    ##                                                                              ##
    ##################################################################################
    */

    // Debugging for LOGCAT
    private static final String TAG = "MainActivity";

    // declare TextView for connection status
    TextView tvConecting;

    // EXTRA string to send on to other activity
    //
    // used if searching for paired devices is made in this activity but
    // actual connection and other business logic is in other activity
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    // Member fields
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//    }

    @Override
    public void onResume()
    {
        super.onResume();

        // check state of BT for the device and eventually turn it on
        //
        // it is mandatory to check the state when first starting the activity and
        // it BT might be turned off while activity was paused
        ensureBTisLive();

        tvConecting = (TextView) findViewById(R.id.connecting);
        //tvConecting.setTextSize(40);
        tvConecting.setText(" ");

        // Initialize array adapter for paired devices
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

        // Find and set up the ListView for paired devices
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // Get the local (on tis device) Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get a set of currently paired devices (including arduino's BT module) and append to 'pairedDevices'
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // Add already paired devices to the array
        // Note taht at this moment we haven't searched for available devices yet
        if (pairedDevices.size() > 0) {
            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);//make title viewable
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = "0 devices";
            mPairedDevicesArrayAdapter.add(noDevices);
        }
    }

    // Set up on-click listener for the list (nicked this - unsure)
    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            tvConecting.setText("Connecting...");

            // Get the device MAC address, which is the last 17 chars in the View
            //
            // otherwise we might store our device MAC address hardcoded
            String info = ((TextView) v).getText().toString();
            String arduinosMACadress = info.substring(info.length() - 17);

            // Make an intent to start next activity while taking an extra which is the MAC address.
            // actual conection is done in next activity if device is available
            Intent i = new Intent(MainActivity.this, ConectAndTransmit.class);
            i.putExtra(EXTRA_DEVICE_ADDRESS, arduinosMACadress);
            startActivity(i);
        }
    };

    // check if BT is turened on and eventually turn it on
    private void ensureBTisLive() {
        mBtAdapter=BluetoothAdapter.getDefaultAdapter();

        if(mBtAdapter==null) {
            Toast.makeText(getBaseContext(), "This device does not support Bluetooth", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            if (!mBtAdapter.isEnabled()) {
                //Prompt user to turn on BT
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

                // second parameter must be greater than 0.
                // The system passes this constant back to you in your
                // onActivityResult() implementation as the requestCode parameter.
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    // unimportant at tis moment, can be skipped
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(resultCode == 1){
            Toast.makeText(getBaseContext(), "User accepted to TURN ON Bluetooth", Toast.LENGTH_SHORT).show();
        }
        else if (resultCode == 0){
            Toast.makeText(getBaseContext(), "User DON\'t accepted to TURN ON Bluetooth", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getBaseContext(), "onActivityResult() -> Unknown resultCode", Toast.LENGTH_SHORT).show();
        }
    }
}
