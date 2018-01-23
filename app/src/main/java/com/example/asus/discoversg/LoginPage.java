package com.example.asus.discoversg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.asus.discoversg.Database.ExternalDatabaseAdapter;

/**
 * Created by ASUS on 11/29/2017.
 */

public class LoginPage extends AppCompatActivity {

    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        start = (Button) findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExternalDatabaseAdapter cleanSweep = ExternalDatabaseAdapter.getInstance(LoginPage.this);
                try {
                    cleanSweep.open(0);
                    cleanSweep.open(1);
                } catch(Exception e){
                    Toast.makeText(LoginPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(LoginPage.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
