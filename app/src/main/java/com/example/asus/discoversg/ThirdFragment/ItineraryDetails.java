package com.example.asus.discoversg.ThirdFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.discoversg.Database.ItineraryDbManager;
import com.example.asus.discoversg.MainActivity;
import com.example.asus.discoversg.R;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/24/2017.
 */

public class ItineraryDetails extends AppCompatActivity{

    private TextView itineraryTitle;
    private TextView itineraryDescription;
    private TextView visitingAttractions;
    private Button itineraryRoutebutton;
    private Button delete;
    ItineraryDbManager itineraryDbManager;
    ArrayList<String> attractions;
    String placesToVisit;
    String title;
    BudgetDialog budgetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.itinerary_details);

            Intent intent = getIntent();
            title = intent.getStringExtra(ItineraryAdapter.KEY).trim();
            Log.i("Valerene", "key" + title);
            itineraryDbManager = new ItineraryDbManager(this);

            itineraryTitle = (TextView) findViewById(R.id.itineraryTitle);
            itineraryDescription = (TextView) findViewById(R.id.itineraryDescription);
            visitingAttractions = (TextView) findViewById(R.id.visitingAttractions);
            itineraryRoutebutton = (Button) findViewById(R.id.itineraryRoutebutton);
            delete = (Button) findViewById(R.id.deleteItinerary);

            itineraryTitle.setText(title);
            itineraryDescription.setText(itineraryDbManager.fetchDescription(title));

            attractions = itineraryDbManager.fetchAttractions(title);

            if(attractions.isEmpty()){
                Toast.makeText(ItineraryDetails.this, "no attractions added", Toast.LENGTH_SHORT).show();
            } else {
                placesToVisit = attractions.get(0);
                if (attractions.size() > 0) {
                    for (int i = 1; i < attractions.size(); i++) {
                        placesToVisit += "\n" + attractions.get(i);
                    }
                }
                visitingAttractions.setText(placesToVisit);
            }

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        itineraryDbManager = new ItineraryDbManager(v.getContext());
                        itineraryDbManager.deleteItinerary(title);
                        Toast.makeText(ItineraryDetails.this, "itinerary deleted", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(v.getContext(), MainActivity.class);
                        startActivity(i);
                    } catch(Exception e){
                        Toast.makeText(ItineraryDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            itineraryRoutebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = ItineraryDetails.this;
                    itineraryDbManager = new ItineraryDbManager(context);
                    ArrayList<String> visiting = itineraryDbManager.fetchAttractions(title);
                    if(visiting == null || visiting.isEmpty()){
                        Toast.makeText(context, "nothing to optimise", Toast.LENGTH_SHORT).show();
                    } else {
                        //ask for budget
                        budgetDialog = BudgetDialog.newInstance(title);
                        budgetDialog.show(getFragmentManager(), "budgetDialog");
                    }
                }
            });
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}