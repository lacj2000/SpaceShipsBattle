package com.example.laphiscontroller02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.net.InetAddress;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MenuActivity extends AppCompatActivity {

    Context context;

    RadioGroup radioGroup;
    EditText editText;

    ArrayList<RadioButton> buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        context = getApplicationContext();
        radioGroup = findViewById(R.id.availableHosts);
        editText = findViewById(R.id.typedId);
        buttons = new ArrayList<>();

        Log.i("A QUE EU FIZ", "FILTRO BOM");

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()!= 0 && s.charAt(s.length() - 1) == '-') {
                    editText.setText(s.subSequence(0, s.length()-1)+".");
                    editText.setSelection(s.length());
                }
            }
        });


        new Thread(new listConnections()).start();
    }

    public void listAvailableConnection(View view) {
        for (RadioButton button : buttons) {
            radioGroup.addView(button);
        }
        if (buttons.size() != 0) {
            buttons.clear();
        }
    }

    public void goGaming(View view) {
        try {
            int id = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(id);
            String value = radioButton.getText().toString();
            value = value.split("\"")[1];

            if (id != -1) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("ID_CONNECT", value);
                startActivity(intent);
            }
        }catch (IllegalStateException e){
            Toast.makeText(MenuActivity.this, "Select or type an IP Address", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Well, that's not good!!!");
        }
    }

    public void sendIp(View view) {
        String value = editText.getText().toString();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("ID_CONNECT", value);
        startActivity(intent);
    }

    private class  listConnections implements Runnable {

        @Override
        public void run() {
            try {
                Context context = getApplicationContext();

                if (context != null) {

                    ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

                    WifiInfo connectionInfo = wm.getConnectionInfo();
                    int ipAddress = connectionInfo.getIpAddress();
                    String ipString = Formatter.formatIpAddress(ipAddress);


                    Log.d(TAG, "activeNetwork: " + activeNetwork);
                    Log.d(TAG, "ipString: " + ipString);

                    String prefix = ipString.substring(0, ipString.lastIndexOf(".") + 1);
                    Log.d(TAG, "prefix: " + prefix);

                    for (int i = 0; i < 255; i++) {
                        new Thread(new TryCatch(prefix, i)).start();
                    }
                }
            } catch (Throwable t) {
                Log.e(TAG, "Well that's not good.", t);
            }
        }

        public class TryCatch implements Runnable{
            String prefix;
            int i;
            TryCatch(String prefix, int i){
                this.i = i;
                this.prefix = prefix;
            }


            @Override
            public void run() {
                try {
                    String testIp = prefix + i;

                    InetAddress address = InetAddress.getByName(testIp);
                    boolean reachable = address.isReachable(1000);
                    String hostName = address.getCanonicalHostName();

                    if (reachable) {
                        RadioButton radioButton = new RadioButton(getApplicationContext());
                        radioButton.setId(i);
                        radioButton.setText("Host: " + hostName + "\"" + testIp + "\" is reachable!");
                        buttons.add(radioButton);
                        Log.i(TAG, "Host: " + hostName + "(" + testIp + ") is reachable!");
                    }
                }catch (Throwable t){
                    Log.e(TAG, "Well that's not good.", t);
                }
            }
        }
    }
}
