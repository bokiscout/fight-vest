package dobrink.fight_vest;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by borche on 14.6.17.
 */

public class BluetoothService extends Service {

    // member fields
    String fighter;
    String address;
    String deviceName;

    private boolean stopThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private BluetoothAdapter btAdapter = null;
    private Handler bluetoothIn;
    private final int handlerState = 0;                        //used to identify handler message

    private StringBuilder recDataString = new StringBuilder();
    private static String parsedMsg = "EMPTY";

    private ConnectingThread mConnectingThread;
    private ConnectedThread mConnectedThread;

    @Override
    public void onCreate() {
        Log.d("BT SERVICE", "onCreate()");
        showToast("Service created");
        stopThread = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("BT SERVICE", "onStart()");
        showToast("Service started");
        fighter = "";
        address = "";
        deviceName = "";

        if (intent != null) {
            fighter = intent.getStringExtra("fighter");
            address = intent.getStringExtra("deviceAddress");
            deviceName = intent.getStringExtra("deviceName");
        }
        if (fighter == null) {
            Log.d("BT SERVICE", "fighter is NULL");
        }
        if(address == null){
            Log.d("BT SERVICE", "address is NULL");
        }
        if(deviceName == null){
            Log.d("BT SERVICE", "device name is NULL");
        }

        Log.d("BT SERVICE", "service is for: " + fighter + " at " + address + " (" + deviceName +")");

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState(); // quiet othervise

        establishConnection();

        return START_STICKY;

    }

    private void establishConnection() {
        Log.d("BT SERVICE", "establishConnection()");

        if(address != ""){
            bluetoothIn = new Handler() {
                public void handleMessage(android.os.Message msg) {
                    Log.d("BT SERVICE", "handleMessage()");
                    if (msg.what == handlerState) {
                        String readMessage = (String) msg.obj;
                        recDataString.append(readMessage);// enter code here
                        Log.d("BT SERVICE", "recorded: " + recDataString.toString());

                        ///////arduino msg read bellow
                        int endOfLineIndex = recDataString.indexOf("]");
                        if (endOfLineIndex > 0) {
                            String dataInput = recDataString.substring(0, endOfLineIndex);
                            Log.d("BT SERVICE", "data recived: " + dataInput);

                            //sending string to main actity
                            Log.d("BT SERVICE", "Broadcasting message");
                            parsedMsg = parseRedData(dataInput);
                            sendFightData();

                            recDataString.delete(0, recDataString.length());
                        }
                    }
                }

                private String parseRedData(String msg) {
                    // method for parceing input data
                    // determine which player send data
                    // determine strength of the punch

                    String result = "";
                    String[] parts = msg.split("_");

                    if (parts.length != 2) {
                        result += "error while parceing";
                        result += " lentht: " + parts.length;
                        result += " string: " + Arrays.toString(parts);
                    } else {
                        char player = parts[0].charAt(1);
                        char strength = parts[1].charAt(0);

                        if (player == '0') {
                            result += "blue fighter";
                        } else {
                            result += "red fighter";
                        }

                        if (strength == '1') {
                            result += ", medium punch";
                        } else {
                            result += ", strong punch";
                        }
                    }
                    Log.d("PARSED DATA", result);
                    return result;
                }
            };

            try {
                BluetoothDevice device = btAdapter.getRemoteDevice(address);
                Log.d("BT SERVICE", "BT SERVICE -> ATTEMPTING TO CONNECT TO REMOTE DEVICE : " + address);
                mConnectingThread = new ConnectingThread(device);
                mConnectingThread.start();
            } catch (IllegalArgumentException e) {
                Log.d("DEBUG BT", "PROBLEM WITH MAC ADDRESS : " + e.toString());
                Log.d("BT SEVICE", "ILLEGAL MAC ADDRESS, STOPPING SERVICE");
                stopSelf();
            }

        }
    }

    private void sendFightData() {
        Intent intent = new Intent("fightData");
        sendFightBroadcast(intent);
    }

    private void sendFightBroadcast(Intent intent){
        intent.putExtra("parsedMsg", parsedMsg);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    private void checkBTState() {
        //Checks that the Android device Bluetooth is available and prompts to be turned on if off
        if (btAdapter == null) {
            Log.d("BT SERVICE", "BLUETOOTH NOT SUPPORTED BY DEVICE, STOPPING SERVICE");
            stopSelf();
        } else {
            if (btAdapter.isEnabled()) {
                Log.d("DEBUG BT", "BT ENABLED! BT ADDRESS : " + btAdapter.getAddress() + " , BT NAME : " + btAdapter.getName());
            } else {
                Log.d("BT SERVICE", "BLUETOOTH NOT ON, SEND INTENT TO TURN ON BT");
                Intent btIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                btIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(btIntent);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        bluetoothIn.removeCallbacksAndMessages(null);
        stopThread = true;
        showToast("Service done");
    }


    private class ConnectingThread extends Thread {
        // Class for Connecting Thread
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectingThread(BluetoothDevice device) {
            Log.d("BT SERVICE", "ConectingThread() <- in constructor()");

            mmDevice = device;
            BluetoothSocket temp = null;

            Log.d("BT SERVICE", "DEBUG BT -> MAC ADDRESS : " + address);
            Log.d("BT SERVICE", "DEBUG BT -> BT UUID : " + BTMODULEUUID);
            try {
                temp = mmDevice.createRfcommSocketToServiceRecord(BTMODULEUUID);
                Log.d("BT SERVICE", "DEBUG BT -> SOCKET CREATED : " + temp.toString());
            } catch (IOException e) {
                Log.d("BT SERVICE", "DEBUG BT -> SOCKET CREATION FAILED :" + e.toString());
                Log.d("BT SERVICE", "DEBUG BT -> SOCKET CREATION FAILED, STOPPING SERVICE");
                stopSelf();
            }
            mmSocket = temp;
        }

        @Override
        public void run() {
            super.run();
            Log.d("BT SERVICE", "ConnectingThread() <- in run()");

            // Establish the Bluetooth socket connection.
            // Cancelling discovery as it may slow down connection
            btAdapter.cancelDiscovery();

            try {
                mmSocket.connect();
                Log.d("BT SERVICE", "BT SOCKET CONNECTED <- in run()");

                mConnectedThread = new ConnectedThread(mmSocket);
                mConnectedThread.start();
                Log.d("BT SERVICE", "CONNECTED THREAD STARTED <- in run()");
                //I send a character when resuming/beginning transmission to check if device is connected
                //If it is not an exception will be thrown in the write method and finish() will be called
                mConnectedThread.write("x");
            } catch (IOException e) {
                try {
                    Log.d("BT SERVICE", "SOCKET CONNECTION FAILED <- in run() : " + e.toString());
                    Log.d("BT SERVICE", "SOCKET CONNECTION FAILED <- in run() , STOPPING SERVICE");

                    mmSocket.close();
                    stopSelf();
                } catch (IOException e2) {
                    Log.d("BT SERVICE", "SOCKET CLOSING FAILED :" + e2.toString());
                    Log.d("BT SERVICE", "SOCKET CLOSING FAILED, STOPPING SERVICE");

                    stopSelf();
                    //insert code to deal with this
                }
            } catch (IllegalStateException e) {
                Log.d("BT SERVICE", "CONNECTED THREAD START FAILED <- in run() : " + e.toString());
                Log.d("BT SERVICE", "CONNECTED THREAD START FAILED <- in run(), STOPPING SERVICE");

                stopSelf();
            }
        }

        public void closeSocket() {
            try {
                //Don't leave Bluetooth sockets open when leaving activity
                mmSocket.close();
            } catch (IOException e2) {
                //insert code to deal with this
                Log.d("BT SERVICE", "SOCKET CLOSING FAILED: " + e2.toString());
                Log.d("BT SERVICE", "SOCKET CLOSING FAILED, STOPPING SERVICE");
                stopSelf();
            }
        }
    }


    private class ConnectedThread extends Thread {
        // Class for Connected Thread

        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket){
            Log.d("BT SERVICE", "IN CONNECTED THREAD <- constructor");
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.d("BT SERVICE", "UNABLE TO READ/WRITE. " + e.toString());
                Log.d("BT SERVICE", "UNABLE TO READ/WRITE, STOPPING SERVICE");
                stopSelf();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            Log.d("BT SERVICE", "IN CONNECTED THREAD <- run()");
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (!stopThread) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    Log.d("BT SERVICE", "DEBUG BT PART -> CONNECTED THREAD: " + readMessage);

                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    Log.d("DEBUG BT", e.toString());
                    Log.d("BT SERVICE", "UNABLE TO READ/WRITE, STOPPING SERVICE");
                    stopSelf();
                    break;
                }
            }
        }

        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Log.d("BT SERVICE", "DEBUG BT: UNABLE TO READ/WRITE " + e.toString());
                Log.d("BT SERVICE", "UNABLE TO READ/WRITE, STOPPING SERVICE");
                stopSelf();
            }
        }

        public void closeStreams() {
            try {
                //Don't leave Bluetooth sockets open when leaving activity
                mmInStream.close();
                mmOutStream.close();
            } catch (IOException e2) {
                //insert code to deal with this
                Log.d("DEBUG BT", e2.toString());
                Log.d("BT SERVICE", "STREAM CLOSING FAILED, STOPPING SERVICE");
                stopSelf();
            }
        }
    }
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}