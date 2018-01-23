package com.example.asus.discoversg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.asus.discoversg.Database.ExternalDatabaseAdapter;
import com.example.asus.discoversg.FirstFragment.FragmentLocator;
import com.example.asus.discoversg.SecondFragment.FragmentItinerary;
import com.example.asus.discoversg.ThirdFragment.FragmentBudget;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExternalDatabaseAdapter globalData = ExternalDatabaseAdapter.getInstance(MainActivity.this);

        navBar = (BottomNavigationView) findViewById(R.id.bottomNav);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.btmBarLocator:
                        selectedFragment = FragmentLocator.newInstance();
                        break;
                    case R.id.btmBarItenrary:
                        try {
                            selectedFragment = FragmentItinerary.newInstance();
                        } catch(Exception e){
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.btmBarBudget:
                        try {
                        selectedFragment = FragmentBudget.newInstance();
                        } catch(Exception e){
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                return true;
            }
        });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, FragmentLocator.newInstance());
        transaction.commit();
    }
}
