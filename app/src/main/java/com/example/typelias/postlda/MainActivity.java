package com.example.typelias.postlda;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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

    FirebaseDatabase database;
    DatabaseReference myRef;


    NotificationCompat.Builder notification;
    public static final int uniqID = 4729375;
    public static final String Chanel_ID = "my_ch_id";
    NotificationChannel mChanel;




    public void init() {
        but1 = (Button) findViewById(R.id.next);
        but2 = (Button) findViewById(R.id.send);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy = new Intent(MainActivity.this, settings.class);


                startActivity(toy);
            }
        });

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildRun();
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

        int temp = data.getInt("id",0);
        String id = String.valueOf(temp);

        //Notification
        @TargetApi(mChanel = new NotificationChannel(Chanel_ID,"myChanel",NotificationManager.IMPORTANCE_LOW);)
        notification = new NotificationCompat.Builder(this,Chanel_ID);
        notification.setAutoCancel(true);


        //Database

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        myRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (tryData(dataSnapshot)) {
                    String temp = dataSnapshot.getValue(String.class);
                    boolean test = isInt(temp);

                    if (test == true) {
                        int status = Integer.parseInt(temp);

                        if (status == 1) {
                            mailStatus.setText("Det finns post");
                            buildRun();
                        } else if (status == 0) {
                            mailStatus.setText("Ingen post");
                        } else {
                            mailStatus.setText("error");
                        }
                    }
                }else
                {
                    mailStatus.setText("error in database");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public boolean isInt(String input)
    {
        try
        {
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public boolean tryData(DataSnapshot dataSnapshot)
    {
        try
        {
            dataSnapshot.getValue(String.class);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public void buildRun()
    {
        //Build
        notification.setSmallIcon(R.drawable.ic_launcher_foreground);
        notification.setTicker("Post");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("MailAlert");
        notification.setContentText("Du har fått post");
        notification.setChannelId(Chanel_ID);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //Run
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqID,notification.build());

    }

}
