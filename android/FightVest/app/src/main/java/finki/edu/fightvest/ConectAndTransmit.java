package finki.edu.fightvest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ConectAndTransmit extends AppCompatActivity {
    Button btnOne, btnTwo;
    TextView tvInputString, tvInputStringLength, tvSensor;
    Handler bluetoothInHandler;

    // used to identify handler message
    final int handlerState = 0;

    // adapter must be reinitialised as in previous activity
    // socket is used for transiting data
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;

    // used to concatenate input bytes till we receive EOL character ($)
    private StringBuilder recDataString = new StringBuilder();

    // use asynchronous connection, don't block the main thread
    private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for arduino's BT module MAC address
    private static String arduinosMACaddress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conect_and_transmit);

        //Link the buttons and textViews to respective views
        btnOne = (Button) findViewById(R.id.btnSendOne);
        btnTwo = (Button) findViewById(R.id.btnSendTwo);
        tvInputString = (TextView) findViewById(R.id.txtString);
        tvInputStringLength = (TextView) findViewById(R.id.inputLength);
        tvSensor = (TextView) findViewById(R.id.sensorInput);

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        ensureBTisLive();

        // Set up onClick listeners for buttons to send 1 or 0 to turn on/off LED
        btnTwo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("2");    // Send "2" via Bluetooth
                Toast.makeText(getBaseContext(), "Send 2", Toast.LENGTH_SHORT).show();
            }
        });

        btnOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("1");    // Send "1" via Bluetooth
                Toast.makeText(getBaseContext(), "Send 2", Toast.LENGTH_SHORT).show();
            }
        });

        bluetoothInHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {                                             //if message is what we want
                    String readMessage = (String) msg.obj;                                  // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);                                      //keep appending to string until $

                    int endOfLineIndex = recDataString.indexOf("]");                        // determine the end-of-line
                  
                    if (endOfLineIndex > 0) {                                               // <=0 if '$' (EOL) is not readed, >0 if '$' EOL is already readed
                        String dataInput = recDataString.substring(0, endOfLineIndex);      // extract string without the EOL sign
                        tvInputString.setText("Data Received = " + dataInput);

                        int dataLength = dataInput.length();                                //get length of data received
                        tvInputStringLength.setText("String Length = " + String.valueOf(dataLength));

//                        tvSensor.setText("Sensor value = " + recDataString.toString());     //update the textviews with sensor values
                        tvSensor.setText("Sensor value = " + parseRedData(recDataString.toString()));     //update the textviews with sensor values
                        recDataString.delete(0, recDataString.length());                    //clear all string data;
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
                    result += " string: " + parts.toString();
                } else {
                    char player = parts[0].charAt(1);
                    char strength = parts[1].charAt(0);

                    if(player == '0'){
                        result += "blue fighter";
                    }
                    else{
                        result += "red fighter";
                    }

                    if(strength == '1'){
                        result += ", medium punch";
                    }
                    else{
                        result += ", strong punch";
                    }
                }

                return result;
            }
        };
    }
  
    // creates secure outgoing connection with BT device using UUID
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    @Override
    public void onResume() {
        super.onResume();

        //get arduinos MAC address from MainActivity via intent
        Intent intent = getIntent();
        arduinosMACaddress = intent.getStringExtra(MainActivity.EXTRA_DEVICE_ADDRESS);

        //create device
        BluetoothDevice device = btAdapter.getRemoteDevice(arduinosMACaddress);

        // create socket for the connection
        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }

        // Establish the Bluetooth socket connection.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
                // deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();                           // if everything OK connection is done

        // send any data to test connection
        // if connection was terminated while activity was paused exception will be thrown in write() method
        mConnectedThread.write("x");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            // don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            // deal with this
        }
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    // check if BT is turened on and eventually turn it on
    private void ensureBTisLive() {
        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "This device does not support Bluetooth", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            if (!btAdapter.isEnabled()) {
                //Prompt user to turn on BT
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

                // second parameter must be greater than 0.
                // The system passes this constant back to you in your
                // onActivityResult() implementation as the requestCode parameter.
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothInHandler.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        // Send to arduino
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
