package com.gmendezm.robo_wheel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Conducir extends Activity {

    Button btn_forward, btn_reverse, btnStop, btnLeft, btnRight, btnFirst, btnSecond, btnThird;
    TextView txtArduino, sensorView0, txtTemperature, txtHumidity, txtUv1, txtUv2, txtUv3;

    Handler bluetoothIn;

    final int handlerState = 0;        				 //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address = null;

    public String temperature;
    public String humidity;
    public String uv1;
    public String uv2;
    public String uv3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.conducir);

        //Link the buttons and textViews to respective views
        btn_forward = (Button) findViewById(R.id.button_forward);
        btn_reverse = (Button) findViewById(R.id.button_reverse);
        btnStop = (Button) findViewById(R.id.button_stop);
        btnLeft = (Button) findViewById(R.id.button_left);
        btnRight = (Button) findViewById(R.id.button_right);
        btnFirst = (Button) findViewById(R.id.button_first_gear);
        btnSecond = (Button) findViewById(R.id.button_second_gear);
        btnThird = (Button) findViewById(R.id.button_third_gear);

       // txtString = (TextView) findViewById(R.id.txtString);
       // txtStringLength = (TextView) findViewById(R.id.testView1);
        sensorView0 = (TextView) findViewById(R.id.sensorView0);
        txtTemperature = (TextView) findViewById(R.id.textView_temperatura);
        txtHumidity = (TextView) findViewById(R.id.textView_humedad);
        txtUv1 = (TextView) findViewById(R.id.textView_uv_vis);
        txtUv2 = (TextView) findViewById(R.id.textView_uv_ir);
        txtUv3 = (TextView) findViewById(R.id.textView_uv_uv);
       // sensorView1 = (TextView) findViewById(R.id.sensorView1);
      //  sensorView2 = (TextView) findViewById(R.id.sensorView2);
      //  sensorView3 = (TextView) findViewById(R.id.sensorView3);



        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {										//if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);      								//keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
                        //txtString.setText("Datos recibidos = " + dataInPrint);
                        int dataLength = dataInPrint.length();							//get length of data received
                        //txtStringLength.setText("Tamaño del String = " + String.valueOf(dataLength));

                        if (recDataString.charAt(0) == '#')								//if it starts with # we know it is what we are looking for
                        {
                            String sensor0 = recDataString.substring(1, 5);             //get sensor value from string between indices 1-5
                            String sensor1 = recDataString.substring(6, 10);            //same again...
                            String sensor2 = recDataString.substring(11, 15);
                            String sensor3 = recDataString.substring(16, 20);

                            if(sensor0.equals("1.00"))
                            sensorView0.setText("Encendido");	//update the textviews with sensor values
                            else
                                sensorView0.setText("Apagado");	//update the textviews with sensor values
                           // sensorView1.setText(sensor1);
                            //sensorView2.setText(sensor2);
                            //sensorView3.setText(sensor3);
                            //sensorView3.setText(" Sensor 3 Voltage = " + sensor3 + "V");
                        }
                        recDataString.delete(0, recDataString.length()); 					//clear all string data
                        // strIncom =" ";
                        dataInPrint = " ";
                    }
                }
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();


        // Set up onClick listeners for buttons to send 1 or 0 to turn on/off LED
        btn_reverse.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btn_reverse.setBackgroundResource(R.mipmap.atras_presionado);
                btn_forward.setBackgroundResource(R.mipmap.adelante_suelto);
                btnStop.setBackgroundResource(R.mipmap.detener_suelto);
                btnLeft.setBackgroundResource(R.mipmap.izquierda_suelto);
                btnRight.setBackgroundResource(R.mipmap.derecha_suelto);
                mConnectedThread.write("e");    // Send "0" via Bluetooth
                // Toast.makeText(getBaseContext(), "Manejar en reversa, pip pip", Toast.LENGTH_SHORT).show();
            }
        });

        btn_forward.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                btn_reverse.setBackgroundResource(R.mipmap.atras_suelto);
                btn_forward.setBackgroundResource(R.mipmap.adelante_presionado);
                btnStop.setBackgroundResource(R.mipmap.detener_suelto);
                btnLeft.setBackgroundResource(R.mipmap.izquierda_suelto);
                btnRight.setBackgroundResource(R.mipmap.derecha_suelto);
                mConnectedThread.write("a");    // Send "1" via Bluetooth
                // Toast.makeText(getBaseContext(), "Llevar al carro hacia adelante", Toast.LENGTH_SHORT).show();
            }
        });

        btnStop.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btn_reverse.setBackgroundResource(R.mipmap.atras_suelto);
                btn_forward.setBackgroundResource(R.mipmap.adelante_suelto);
                btnStop.setBackgroundResource(R.mipmap.detener_presionado);
                btnLeft.setBackgroundResource(R.mipmap.izquierda_suelto);
                btnRight.setBackgroundResource(R.mipmap.derecha_suelto);
                mConnectedThread.write("g");    // Send "1" via Bluetooth
               // Toast.makeText(getBaseContext(), "Detener auto.", Toast.LENGTH_SHORT).show();
            }
        });

        btnLeft.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btn_reverse.setBackgroundResource(R.mipmap.atras_suelto);
                btn_forward.setBackgroundResource(R.mipmap.adelante_suelto);
                btnStop.setBackgroundResource(R.mipmap.detener_suelto);
                btnLeft.setBackgroundResource(R.mipmap.izquierda_presionado);
                btnRight.setBackgroundResource(R.mipmap.derecha_suelto);
                mConnectedThread.write("b");    // Send "1" via Bluetooth
               // Toast.makeText(getBaseContext(), "Girar izq.", Toast.LENGTH_SHORT).show();
            }
        });

        btnRight.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btn_reverse.setBackgroundResource(R.mipmap.atras_suelto);
                btn_forward.setBackgroundResource(R.mipmap.adelante_suelto);
                btnStop.setBackgroundResource(R.mipmap.detener_suelto);
                btnLeft.setBackgroundResource(R.mipmap.izquierda_suelto);
                btnRight.setBackgroundResource(R.mipmap.derecha_presionado);
                mConnectedThread.write("d");    // Send "1" via Bluetooth
               // Toast.makeText(getBaseContext(), "Girar der.", Toast.LENGTH_SHORT).show();
            }
        });

        btnFirst.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("x");    // Send "1" via Bluetooth
                // Toast.makeText(getBaseContext(), "Girar der.", Toast.LENGTH_SHORT).show();
            }
        });

        btnSecond.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("y");    // Send "1" via Bluetooth
                // Toast.makeText(getBaseContext(), "Girar der.", Toast.LENGTH_SHORT).show();
            }
        });

        btnThird.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("z");    // Send "1" via Bluetooth
                // Toast.makeText(getBaseContext(), "Girar der.", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void setText(final TextView text,final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();

        //Get MAC address from ListaDispositivos via intent
        Intent intent = getIntent();

        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(ListaDispositivos.EXTRA_DEVICE_ADDRESS);

        //create device and set the MAC address
        //Log.i("ramiro", "adress : " + address);
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
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
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("x");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "El dispositivo no soporta bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
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
                    bytes = mmInStream.read(buffer);        	//read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();


                    System.out.println(readMessage);
                    //txtTemperature.setText("Temperatura: " + readMessage);
                    if(readMessage.length() > 3 && readMessage.charAt(0) == '.' && readMessage.charAt(1) == 'T'){
                        String temperatureGetted = readMessage.substring(2);
                        if(temperatureGetted != temperature){
                            if(temperatureGetted.length() > 4){
                                temperatureGetted = temperatureGetted.substring(0, 4);
                            }
                            temperature = temperatureGetted;
                            setText(txtTemperature, "Temperatura: " + temperature);
                           // System.out.println("readMessage: " + readMessage);
                           // System.out.println("temperatureGetted: " + temperatureGetted);
                        }

                    }

                    if(readMessage.length() > 3 && readMessage.charAt(0) == '.' && readMessage.charAt(1) == 'H'){
                        String humidityGetted = readMessage.substring(2);
                        if(humidityGetted != humidity){
                            if(humidityGetted.length() > 4){
                                humidityGetted = humidityGetted.substring(0, 4);
                            }
                            humidity = humidityGetted;
                            setText(txtHumidity, "Humedad: " + humidity);
                            // System.out.println("readMessage: " + readMessage);
                            // System.out.println("temperatureGetted: " + temperatureGetted);
                        }

                    }

                    if(readMessage.length() > 3 && readMessage.charAt(0) == '.' && readMessage.charAt(1) == 'P'){
                        String uv1Getted = readMessage.substring(2);
                        if(uv1Getted != uv1){
                            if(uv1Getted.length() > 5){
                                uv1Getted = uv1Getted.substring(0, 5);
                            }
                            uv1 = uv1Getted;
                            setText(txtUv1, "UV Visible: " + uv1);
                            // System.out.println("readMessage: " + readMessage);
                            // System.out.println("temperatureGetted: " + temperatureGetted);
                        }

                    }

                    if(readMessage.length() > 3 && readMessage.charAt(0) == '.' && readMessage.charAt(1) == 'Q'){
                        String uv2Getted = readMessage.substring(2);
                        if(uv2Getted != uv2){
                            if(uv2Getted.length() > 5){
                                uv2Getted = uv2Getted.substring(0, 5);
                            }
                            uv2 = uv2Getted;
                            setText(txtUv2, "IR: " + uv2);
                            // System.out.println("readMessage: " + readMessage);
                            // System.out.println("temperatureGetted: " + temperatureGetted);
                        }

                    }

                    if(readMessage.length() > 3 && readMessage.charAt(0) == '.' && readMessage.charAt(1) == 'R'){
                        String uv3Getted = readMessage.substring(2);
                        if(uv3Getted != uv3){
                            if(uv3Getted.length() > 5){
                                uv3Getted = uv3Getted.substring(0, 5);
                            }
                            uv3 = uv3Getted;
                            setText(txtUv3, "UV: " + uv3);
                            // System.out.println("readMessage: " + readMessage);
                            // System.out.println("temperatureGetted: " + temperatureGetted);
                        }

                    }


                } catch (IOException e) {
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
                Toast.makeText(getBaseContext(), "La Conexión fallo", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }
}

