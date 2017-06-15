package dobrink.fight_vest;

/**
 * Created by Dobrin on 15-Jun-17.
 */

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class BluetoothSearchAndConnect  extends AppCompatActivity {
    // Member fields
    private BluetoothAdapter mBluetoothAdapter;
    private DeviceListAdapter mAdapter;
    private ListView mListView;
    private ArrayList<BluetoothDevice> mDeviceList;

    private String redFighterMacAddress;
    private String redFighterDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("BT S&C", "onCreate()");
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Up action, to get to MainActivity
        }

        setContentView(R.layout.activity_bluetooth_search_and_connect);
        mListView = (ListView) findViewById(R.id.lv_paired);

        ensureBTisLive();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null){
            Log.d("BT S&C", "default Bluetooth adapter = NULL");
        }

        mDeviceList = new ArrayList<BluetoothDevice>();
        mDeviceList.addAll(mBluetoothAdapter.getBondedDevices());
        Log.d("BT S&C", "paired devices: " + mDeviceList.size());

        mAdapter = new DeviceListAdapter(this);
        mAdapter.setData(mDeviceList);
        mAdapter.setListener(new DeviceListAdapter.OnPairButtonClickListener() {
            @Override
            public void onPairButtonClick(int position) {
                BluetoothDevice device = mDeviceList.get(position);

                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    // connect to this device

                    Log.d("BT S&C", "connecting to bounded device: " + device.toString() );
                    redFighterMacAddress = device.getAddress();
                    redFighterDevice = device.getName();
                    startService();

                } else {
                    // try to pair
                    // but will no pair without previous discovering
                    Log.d("BT S&C", "connecting to unbounded device: " + device.toString() );
                }
            }
        });

        mListView.setAdapter(mAdapter);
    }

    private void startService() {
        Log.d("BT SERVICE", "startService()");

        Intent intent = new Intent(this, BluetoothService.class);
        intent.putExtra("fighter", "red");
        intent.putExtra("deviceName", redFighterDevice);
        if(redFighterMacAddress == null){
            Log.d("BT SERVICE", "red fighter missing address ");
        }
        else {
            intent.putExtra("deviceAddress", redFighterMacAddress);
            startService(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("BT S&C", "onResume()");
        // check state of BT for the device and eventually turn it on
        // it is mandatory to check the state when first starting the activity and
        // it BT might be turned off while activity was paused
        ensureBTisLive();
    }

    private void ensureBTisLive() {
        // check if BT is turened on and eventually turn it on
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(mBluetoothAdapter == null) {
            Log.d("BT S&C", " ensureBTisLive() -> Bluetooth adapter is not available");
            Toast.makeText(getBaseContext(), "This device does not support Bluetooth", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Log.d("BT S&C", " ensureBTisLive() -> Bluetooth adapter is not enabled -> ask to enable");

                //Prompt user to turn on BT
                Intent btIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                btIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(btIntent);
            }
        }
    }
}

