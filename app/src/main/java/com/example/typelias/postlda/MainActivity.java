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


    public TextView owner;
    public TextView adress;
    public TextView zipcode;
    public TextView mailName;
    public TextView mailStatus;

    SharedPreferences data;
    static public String filename = "info";

    FirebaseDatabase database;
    DatabaseReference myRef;




    public void init() {
        but1 = (Button) findViewById(R.id.next);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy = new Intent(MainActivity.this, settings.class);


                startActivity(toy);
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

        //Database

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        myRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(String.class);
                int status = Integer.parseInt(temp);

                if(status==1)
                {
                    mailStatus.setText("Det finns post");
                }
                else if(status==0)
                {
                    mailStatus.setText("Ingen post");
                }
                else
                {
                    mailStatus.setText("error");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }






}
