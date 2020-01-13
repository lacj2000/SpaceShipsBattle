package com.example.laphiscontroller02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    private static RelativeLayout layout_joystick;
    private static RelativeLayout connection_status;
    private static JoyStickClass js;

    private static String SERVER_IP = "10.0.2.2";
    private static int SERVER_PORT = 5000;
    private static InetAddress serverAddress;
    private static DatagramSocket dtSocket;

    private static String up_command = "#UP";
    private static String down_command = "#DW";
    private static String right_command = "#RI";
    private static String left_command = "#LF";
    private static String up_right_command = "#UR";
    private static String up_left_command = "#UL";
    private static String down_left_command = "#DL";
    private static String down_right_command = "#DR";
    private static String stop_command = "#ST";
    private static String attack_command = "#AK";
    private static String defense_command = "#DF";
    private static String special_command = "#SP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        SERVER_IP = intent.getStringExtra("ID_CONNECT");

        new Thread(new ClientThread(getApplicationContext())).start();

        layout_joystick = findViewById(R.id.layout_joystick);

        TextView textView = findViewById(R.id.txt);
        connection_status = findViewById(R.id.connection_status);
        textView.setText(SERVER_IP);

        js = new JoyStickClass(getApplicationContext()
                , layout_joystick, R.drawable.image_button);
        //js.setStickSize(100, 100);
        //js.setLayoutSize(400,400);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);

        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            String str;
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {

                    int direction = js.get8Direction();

                    if(direction == JoyStickClass.STICK_UP) str = up_command;
                    else if(direction == JoyStickClass.STICK_UPRIGHT) str = up_right_command;
                    else if(direction == JoyStickClass.STICK_RIGHT) str = right_command;
                    else if(direction == JoyStickClass.STICK_DOWNRIGHT) str = down_right_command;
                    else if(direction == JoyStickClass.STICK_DOWN) str = down_command;
                    else if(direction == JoyStickClass.STICK_DOWN_LEFT) str = down_left_command;
                    else if(direction == JoyStickClass.STICK_LEFT) str = left_command;
                    else if(direction == JoyStickClass.STICK_UP_LEFT) str = up_left_command;
                    else if(direction == JoyStickClass.STICK_NONE) str = stop_command;

                } else if(arg1.getAction() == MotionEvent.ACTION_UP) str = "#ST";
                new Thread(new sends(str)).start();
                return true;
            }
        });
    }


    public static class ClientThread implements Runnable {
        Context context;

        ClientThread(Context ct){
            super();
            this.context = ct;
        }

        @Override
        public void run() {
            try {

                byte[] buffer = new byte[1024];

                dtSocket = new DatagramSocket();

                serverAddress = InetAddress.getByName(SERVER_IP);

                DatagramPacket datagramPacket;
                datagramPacket = new DatagramPacket(buffer, buffer.length, serverAddress, SERVER_PORT);

                dtSocket.send(datagramPacket);

                datagramPacket = new DatagramPacket(buffer,buffer.length);

                dtSocket.receive(datagramPacket);

                SERVER_PORT = Integer.valueOf(new String(datagramPacket.getData()));

                connection_status.setBackgroundColor(Color.parseColor("#00FF00"));

                Log.i(TAG, "Connected!!!!");

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }

    public class sends implements Runnable{
        String msg;

        sends(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            DatagramPacket datagramPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length,serverAddress,SERVER_PORT);
            try {
                dtSocket.send(datagramPacket);
            }catch (NullPointerException | IOException e){
                Looper.prepare();
                Toast.makeText(MainActivity.this, "You aren't connected!!!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void attackCommand(View view){
        new Thread(new sends(attack_command)).start();
    }

    public void defenseCommand(View view){
        new Thread(new sends(defense_command)).start();
    }

    public void specialCommand(View view){
        new Thread(new sends(special_command)).start();
    }

}
