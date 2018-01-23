package com.example.asus.discoversg.ThirdFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asus.discoversg.Database.ItineraryDbManager;
import com.example.asus.discoversg.MainActivity;

/**
 * Created by ASUS on 11/25/2017.
 */

public class NewEmptyItineraryDialog extends DialogFragment{
    AlertDialog.Builder builder;
    ItineraryDbManager itineraryDbManager;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        //setup layout
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 0, 50, 0);
        final EditText inputName = new EditText(context);
        inputName.setHint("Name your itinerary");
        inputName.setPadding(20, 30, 20, 30);
        layout.addView(inputName);
        final EditText inputDescription = new EditText(context);
        inputDescription.setHint("Add a description");
        inputDescription.setPadding(20, 30, 20, 30);
        layout.addView(inputDescription);

        //build dialog
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Create a new itinerary")
            .setCancelable(false)
            .setView(layout)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        Context context = getContext();
                        String inputItineraryName = inputName.getText().toString().trim();
                        String inputItineraryDes = inputDescription.getText().toString();
                        itineraryDbManager = new ItineraryDbManager(context);
                        itineraryDbManager.addEmptyItinerary(inputItineraryName, inputItineraryDes);
                        Toast.makeText(context, "New itinerary " + inputItineraryName + " added", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Intent i = new Intent(context, MainActivity.class);
                        startActivity(i);
                    } catch(Exception e){
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }
}