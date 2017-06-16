package dobrink.fight_vest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Dobrin on 15-Jun-17.
 */

public class BTDevicesFragment extends android.support.v4.app.ListFragment{
    // Member fields
    private BluetoothAdapter mBluetoothAdapter;
    private DeviceListAdapter mAdapter;
    private ListView mListView;
    private ArrayList<BluetoothDevice> mDeviceList;

    private String redFighterMacAddress;
    private String redFighterDevice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("BT S&C", "onCreate()");
        super.onCreate(savedInstanceState);

        ensureBTisLive();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null){
            Log.d("BT S&C", "default Bluetooth adapter = NULL");
        }

        mDeviceList = new ArrayList<BluetoothDevice>();
        mDeviceList.addAll(mBluetoothAdapter.getBondedDevices());
        Log.d("BT S&C", "paired devices: " + mDeviceList.size());

        mAdapter = new DeviceListAdapter(getContext());
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //mListView = (ListView) getActivity().findViewById(R.id.lv_paired);
        //mListView.setAdapter(mAdapter);
        setListAdapter(mAdapter);
        return inflater.inflate(R.layout.activity_bluetooth_search_and_connect,container,false);
    }


    private void startService() {
        Log.d("BT SERVICE", "startService()");

        Intent intent = new Intent(getActivity(), BluetoothService.class);
        intent.putExtra("fighter", "red");
        intent.putExtra("deviceName", redFighterDevice);
        if(redFighterMacAddress == null){
            Log.d("BT SERVICE", "red fighter missing address ");
        }
        else {
            intent.putExtra("deviceAddress", redFighterMacAddress);
            getActivity().startService(intent);
        }
    }

    private void ensureBTisLive() {
        // check if BT is turened on and eventually turn it on
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(mBluetoothAdapter == null) {
            Log.d("BT S&C", " ensureBTisLive() -> Bluetooth adapter is not available");
            Toast.makeText(getActivity().getBaseContext(), "This device does not support Bluetooth", Toast.LENGTH_SHORT).show();
            //finish();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Log.d("BT S&C", " ensureBTisLive() -> Bluetooth adapter is not enabled -> ask to enable");

                //Prompt user to turn on BT
                Intent btIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                btIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(btIntent);
            }
        }
    }
}
