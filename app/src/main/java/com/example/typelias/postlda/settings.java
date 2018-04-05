package com.example.typelias.postlda;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class settings extends AppCompatActivity {

    public Button submit;
    public EditText fullname;
    public EditText adres;
    public EditText zip;
    public EditText mail_id;
    public EditText mail_name;

    static public String filename = "info";
    SharedPreferences data;


    public String name;
    public String adress;
    public String zipcode;
    public String id;
    public String mailName;

    public void save()
    {
        submit = (Button)findViewById(R.id.data_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting edit text from settings
                fullname = (EditText) findViewById(R.id.fullname);
                adres = (EditText) findViewById(R.id.adress);
                zip = (EditText) findViewById(R.id.zip);
                mail_id = (EditText) findViewById(R.id.mail_id);
                mail_name = (EditText) findViewById(R.id.mail_name);


                name = fullname.getText().toString();
                adress = adres.getText().toString();
                zipcode = zip.getText().toString();
                id = mail_id.getText().toString();
                mailName = mail_name.getText().toString();

                int intID = Integer.parseInt(id);

                SharedPreferences.Editor editor = data.edit();
                editor.putString("name",name);
                editor.putString("adress",adress);
                editor.putString("zip",zipcode);
                editor.putString("mailname",mailName);
                editor.putInt("id",intID);
                editor.commit();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        save();

        data = getSharedPreferences(filename,0);
    }


}
