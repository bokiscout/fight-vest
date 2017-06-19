package dobrink.fight_vest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Dobrin on 15-Jun-17.
 */

@SuppressWarnings("DefaultFileTemplate")
public class BTDevicesFragment extends android.support.v4.app.ListFragment{
    // Member fields
    private BluetoothAdapter mBluetoothAdapter;
    private DeviceListAdapter mAdapter;
    private ArrayList<BluetoothDevice> mDeviceList;

    private String redFighterMacAddress;
    private String redFighterDevice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("BT DEVICE FRAGMENT", "onCreate()");
        super.onCreate(savedInstanceState);

        ensureBTisLive();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null){
//            Log.d("BT S&C", "default Bluetooth adapter = NULL");
            Log.d("BT DEVICE", "default Bluetooth adapter = NULL");
        }

        mDeviceList = new ArrayList<>();
        Set<BluetoothDevice> boundedDevices = mBluetoothAdapter.getBondedDevices();
        Log.d("BT DEVICE FRAGMENT", "bounded devices: " + boundedDevices.size());

        mDeviceList.addAll(mBluetoothAdapter.getBondedDevices());
        Log.d("BT DEVICE FRAGMENT", "paired devices: " + mDeviceList.size());

        mAdapter = new DeviceListAdapter(getContext());
        mAdapter.setData(mDeviceList);
        mAdapter.notifyDataSetChanged();
        if (mDeviceList.isEmpty()){
            showToast("No Paired BT Devices, Pair via Settings");
        }

        mAdapter.setListener(new DeviceListAdapter.OnPairButtonClickListener() {
            @Override
            public void onPairButtonClick(int position) {
                BluetoothDevice device = mDeviceList.get(position);

                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    // connect to this device
                    Log.d("BT DEVICE FRAGMENT", "connecting to bounded device: " + device.toString() );
                    redFighterMacAddress = device.getAddress();
                    redFighterDevice = device.getName();
                    startService();
                } else {
                    // try to pair
                    // but will no pair without previous discovering
                    Log.d("BT DEVICE FRAGMENT", "connecting to unbounded device: " + device.toString() );
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("BT DEVICE FRAGMENT", "onCreateView()");

        setListAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        return inflater.inflate(R.layout.activity_bluetooth_search_and_connect,container,false);
    }

    @Override
    public void onStart() {
        Log.d("BT DEVICE FRAGMENT", "onStart()");

        super.onStart();
        mDeviceList.clear();
        mDeviceList.addAll(mBluetoothAdapter.getBondedDevices());
        Log.d("BT DEVICE FRAGMENT", "paired devices: " + mDeviceList.size());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        Log.d("BT DEVICE FRAGMENT", "onResume()");
        ensureBTisLive();

        super.onResume();
        mDeviceList.clear();
        mDeviceList.addAll(mBluetoothAdapter.getBondedDevices());
        Log.d("BT DEVICE FRAGMENT", "paired devices: " + mDeviceList.size());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        Log.d("BT DEVICE FRAGMENT", "onPause()");

        super.onPause();
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
        // check if BT is turned on and eventually turn it on
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(mBluetoothAdapter == null) {
            Log.d("BT DEVICE FRAGMENT", " ensureBTisLive() -> Bluetooth adapter is not available");
            Toast.makeText(getActivity().getBaseContext(), "This device does not support Bluetooth", Toast.LENGTH_SHORT).show();
            //finish();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Log.d("BT DEVICE FRAGMENT", " ensureBTisLive() -> Bluetooth adapter is not enabled -> ask to enable");

                //Prompt user to turn on BT
                Intent btIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                btIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(btIntent);
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
