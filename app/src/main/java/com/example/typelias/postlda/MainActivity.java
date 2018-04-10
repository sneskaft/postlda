package com.example.typelias.postlda;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.ubidots.ApiClient;
import com.ubidots.Variable;
import com.ubidots.*;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public Button but1;
    public Button but2;

    public TextView owner;
    public TextView adress;
    public TextView zipcode;
    public TextView mailName;
    public TextView mailStatus;

    SharedPreferences data;
    static public String filename = "info";









    public void init() {
        but1 = (Button) findViewById(R.id.next);
        but2 = (Button)findViewById(R.id.hej);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy = new Intent(MainActivity.this, settings.class);


                startActivity(toy);
            }
        });

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApiUbidots GetApiUbidots = new ApiUbidots ();
                GetApiUbidots.execute();

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        owner = (TextView)findViewById(R.id.owner);
        adress = (TextView)findViewById(R.id.adress);
        zipcode = (TextView)findViewById(R.id.zipcode);
        mailName = (TextView)findViewById(R.id.mailName);
        mailStatus = (TextView)findViewById(R.id.mailStatus);

        init();
        data = getSharedPreferences(filename,0);
        String loadName = data.getString("name","Om inget visas klicka på setup");
        owner.setText(loadName);
        String loadAdress = data.getString("adress","");
        adress.setText(loadAdress);
        String loadZip = data.getString("zip","");
        zipcode.setText(loadZip);
        String loadMailName = data.getString("mailname","");
        mailName.setText(loadMailName);


        callAsynchronousTask();


    }

    public  class ApiUbidots extends AsyncTask<Object, Object, Value[]> {
        private final String API_KEY = "A1E-fc2e596a3fa569e79fb2c005b2a187b46157";
        private final String VARIABLE_ID = "5acb514ac03f97087bd48306";

        @Override
        protected Value[] doInBackground(Object... params) {
            ApiClient apiClient = new ApiClient(API_KEY);
            Variable batteryLevel = apiClient.getVariable(VARIABLE_ID);
            Value[] variableValues = batteryLevel.getValues();


            return variableValues;
        }

        @Override
        protected void onPostExecute(Value[] variableValues) {

            mailStatus.setText(Double.toString((variableValues[0].getValue())));

        }



    }

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            ApiUbidots GetApiUbidots = new ApiUbidots ();
                            GetApiUbidots.execute();


                        } catch (Exception e) {
                            android.util.Log.i("Error", "Error");
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 2000);
    }
}
