package com.example.asus.discoversg.Database;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.asus.discoversg.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ASUS on 11/29/2017.
 */

public class ItinerariesDebugger extends AppCompatActivity{

    private EditText inputAddItinerary;
    private EditText inputAddAttraction;
    private Button addItinerary;
    private Button newItinerary;
    private Button addAttraction;
    private Button fetchDbButton;
    private TextView database;
    private String inputItinerary;  //from edittext
    private String inputAttraction;  //from edittext
    private String[] fetchedDb;       //to display

    private EditText inputGetItinerary;
    private Button fetchAttractionsButton;
    private TextView visitingAttractions;
    private String inputName;             //from edittext
    ItineraryDbManager itineraryDbManager;

    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itineraries_debugger);

        inputAddItinerary = (EditText) findViewById(R.id.addItinerary);
        inputAddAttraction = (EditText) findViewById(R.id.addAttraction);
        addItinerary = (Button) findViewById(R.id.addItineraryButton);
        newItinerary = (Button) findViewById(R.id.newItineraryButton);
        addAttraction = (Button) findViewById(R.id.addAttractionButton);
        fetchDbButton = (Button) findViewById(R.id.displayItineraries);
        database = (TextView) findViewById(R.id.displaydatabase);

        inputGetItinerary = (EditText) findViewById(R.id.fetchVisitingAttractions);
        fetchAttractionsButton = (Button) findViewById(R.id.fetchVisitingAttractionsButton);
        visitingAttractions = (TextView) findViewById(R.id.visitingAttractionsDisplay);

        delete = (Button) findViewById(R.id.deleteDatabase);

        addItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itineraryDbManager = new ItineraryDbManager(ItinerariesDebugger.this);
                inputItinerary = inputAddItinerary.getText().toString().trim();
                inputAttraction = inputAddAttraction.getText().toString().trim();
                itineraryDbManager.addItinerary(inputItinerary, "lorem ipsum", inputAttraction);
            }
        });

        newItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itineraryDbManager = new ItineraryDbManager(ItinerariesDebugger.this);
                inputItinerary = inputAddItinerary.getText().toString().trim();
                itineraryDbManager.addEmptyItinerary(inputItinerary, "lorem ipsum");
            }
        });

        addAttraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itineraryDbManager = new ItineraryDbManager(ItinerariesDebugger.this);
                inputItinerary = inputAddItinerary.getText().toString().trim();
                inputAttraction = inputAddAttraction.getText().toString().trim();
                itineraryDbManager.addAttraction(inputItinerary, inputAttraction);
            }
        });

        fetchDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itineraryDbManager = new ItineraryDbManager(ItinerariesDebugger.this);
                fetchedDb = itineraryDbManager.fetchItineraryNames();
                database.setText(Arrays.toString(fetchedDb));
            }
        });

        fetchAttractionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itineraryDbManager = new ItineraryDbManager(ItinerariesDebugger.this);
                inputName = inputGetItinerary.getText().toString().trim();
                ArrayList<String> myAttractions = itineraryDbManager.fetchAttractions(inputName);
                String fetchedAttractions = myAttractions.toString();
                visitingAttractions.setText(inputName + ": " + fetchedAttractions);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itineraryDbManager = new ItineraryDbManager(ItinerariesDebugger.this);
                itineraryDbManager.deleteItineraryDatabase();
            }
        });
    }
}
