package com.example.asus.discoversg.Database;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.discoversg.R;

import java.util.Arrays;

/**
 * Created by ASUS on 11/24/2017.
 */

public class AttractionsDebugger extends AppCompatActivity {

    private EditText attraction;
    private Button fetchAll;
    private Button deleteAll;
    private Button loadAll;
    private Button names;
    private Button fetchDescription;
    private Button fetchLatLong;
    private Button fetchCat;
    private TextView category;
    private TextView description;
    private TextView latlong;
    private TextView database;
    private TextView databaseNames;
    AttractionDbManager attractionDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attractions_debugger);

        names = (Button) findViewById(R.id.fetchNames);
        attraction = (EditText) findViewById(R.id.try_attraction);
        fetchAll = (Button) findViewById(R.id.fetchAllAttractions);
        fetchAll = (Button) findViewById(R.id.fetchAllAttractions);
        deleteAll = (Button) findViewById(R.id.deleteAttractions);
        loadAll = (Button) findViewById(R.id.loadAttractions);
        fetchCat = (Button) findViewById(R.id.categoryButton);
        fetchDescription = (Button) findViewById(R.id.descriptionButton);
        fetchLatLong = (Button) findViewById(R.id.latlongButton);
        category = (TextView) findViewById(R.id.categoryDebugger);
        database = (TextView) findViewById(R.id.attractionsDatabase);
        description = (TextView) findViewById(R.id.descriptionDebugger);
        latlong = (TextView) findViewById(R.id.latlongDebugger);
        databaseNames = (TextView) findViewById(R.id.attractionsDatabaseNames);
        Log.i("Val", "created attractions debugger");

        fetchAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attractionDbManager = new AttractionDbManager(v.getContext());
                String table = attractionDbManager.getEntireDb();
                database.setText(table);
            }
        });

        fetchDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attractionDbManager = new AttractionDbManager(v.getContext());
                description.setText(attractionDbManager.fetchDescription(attraction.getText().toString().trim()));
            }
        });

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attractionDbManager = new AttractionDbManager(v.getContext());
                attractionDbManager.deleteAttractionsDatabase();
            }
        });

        fetchLatLong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    attractionDbManager = new AttractionDbManager(v.getContext());
                    Double latitude = attractionDbManager.fetchLatitude(attraction.getText().toString().trim());
                    Double longitude = attractionDbManager.fetchLatitude(attraction.getText().toString().trim());
                    latlong.setText("[" + Double.toString(latitude) + ", " + Double.toString(longitude) + "]");
                } catch (Exception e){
                    Toast.makeText(AttractionsDebugger.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attractionDbManager = new AttractionDbManager(v.getContext());
                Toast.makeText(v.getContext(), "Please wait for all attractions to load", Toast.LENGTH_SHORT).show();
                attractionDbManager.loadAttractions();
            }
        });

        names.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attractionDbManager = new AttractionDbManager(v.getContext());
                String[] attractions = attractionDbManager.fetchAttractions();
                String setText = "";
                for(int i = 0; i < attractions.length; i++){
                    setText += Arrays.toString(attractions);
                }
                databaseNames.setText(setText);
            }
        });

        fetchCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attractionDbManager = new AttractionDbManager(v.getContext());
                category.setText(attractionDbManager.fetchCategory(attraction.getText().toString().trim()));
            }
        });
    }
}