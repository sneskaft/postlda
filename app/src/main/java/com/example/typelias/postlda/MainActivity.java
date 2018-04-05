package com.example.typelias.postlda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public Button but1;

    public TextView owner;
    public TextView adress;
    public TextView zipcode;
    public TextView mailName;
    public TextView mailStatus;

    SharedPreferences data;
    static public String filename = "info";

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
        getSQLData();


    }

    public void getSQLData()
    {
        mailStatus = (TextView)findViewById(R.id.mailStatus);
        data = getSharedPreferences(filename,0);
        int id = data.getInt("id",-2);
        if(id == -2)
        {
            mailStatus.setText("Error. \nKlicka på setup\neller så är det fel id");
        }
        else
        {


        }

    }



}
