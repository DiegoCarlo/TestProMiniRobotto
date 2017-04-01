package com.example.diegocg.testprominirobotto;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Set;

import static java.lang.Math.abs;


public class MainActivity extends AppCompatActivity {

    private OutputStream outputStream;
    private InputStream inputStream;
    private Button buttonStop;
    private SeekBar right;
    private SeekBar left;
    private String address = "20:16:06:06:33:17";
    private TextView log;
    private String messaggio = "0 +000 +000\n";
    private String input = "";

    private BufferedReader mBufferedReader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStop = (Button) findViewById(R.id.button);
        right = (SeekBar) findViewById(R.id.seekBarR);
        left = (SeekBar) findViewById(R.id.seekBarL);
        log = (TextView) findViewById(R.id.textView);

        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        right.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                send();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        left.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                send();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                right.setProgress(255);
                left.setProgress(255);
            }
        });
        /*Thread t = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {

                    //If the current thread is the UI thread, then the action is executed immediately
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                input = mBufferedReader.readLine();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        };
        t.start();*/
        Thread f = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);
                        //If the current thread is the UI thread, then the action is executed immediately
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    write(messaggio);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        f.start();
    }

    private void close(Closeable aConnectedObject) {
        if ( aConnectedObject == null ) return;
        try {
            aConnectedObject.close();
        } catch ( IOException e ) {
        }
        aConnectedObject = null;
    }
    private void init() throws IOException
    {
        BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
        if(blueAdapter != null)
        {
            if(blueAdapter.isEnabled())
            {
                Set<BluetoothDevice> bondedDevice = blueAdapter.getBondedDevices();

                if(bondedDevice.size() > 0)
                {
                    Object[] devices = (Object[]) bondedDevice.toArray();
                    BluetoothDevice device = null;
                    for(int i=0; i<devices.length; i++)
                    {
                        device = (BluetoothDevice)devices[i];
                        if(device.getAddress().equals(address))
                        {
                            break;
                        }
                    }
                    if(device != null)
                    {
                        ParcelUuid[] uuids = device.getUuids();
                        BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(uuids[0].getUuid());
                        socket.connect();
                        outputStream = socket.getOutputStream();
                        inputStream = socket.getInputStream();
                        InputStreamReader aReader = null;
                        aReader = new InputStreamReader( inputStream );
                        mBufferedReader = new BufferedReader( aReader );
                    }
                }
                Log.e("error", "No appropriate paired devices.");
            }
            else
            {
                Log.e("error", "Bluetooth is disabled.");
            }
        }
    }
    public void write(String s) throws IOException
    {
        log.setText(s+"\n"+input);
        outputStream.write(s.getBytes());
    }
    private void send()
    {
        messaggio = "0 ";

        messaggio += (formatta(right.getProgress()-255))+" ";
        messaggio += (formatta(left.getProgress()-255))+"\n";

    }
    private String formatta(int value)
    {
        String out;
        if(value < 0)
        {
            out = "-";
        }
        else
        {
            out = "+";
        }

        int absolute = abs(value);
        if(absolute < 10)
        {
            out += "00" +absolute;
        }
        else
        {
            if(absolute < 100)
            {
                out += "0" + absolute;
            }
            else
            {
                out += absolute;
            }
        }
        return out;
    }
}
