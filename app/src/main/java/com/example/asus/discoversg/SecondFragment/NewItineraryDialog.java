package com.example.asus.discoversg.SecondFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asus.discoversg.Database.ItineraryDbManager;
import com.example.asus.discoversg.MainActivity;

/**
 * Created by ASUS on 11/25/2017.
 */

public class NewItineraryDialog extends DialogFragment{
    AlertDialog.Builder builder;
    String attraction;
    ItineraryDbManager itineraryDbManager;

    public static NewItineraryDialog newInstance(CharSequence attraction) {
        Bundle args = new Bundle();
        NewItineraryDialog fragment = new NewItineraryDialog();
        args.putCharSequence("attraction", attraction);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //get args
        attraction = getArguments().getCharSequence("attraction").toString();
        Context context = getActivity();
        //setup layout
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setPadding(50, 50, 50, 50);
        final EditText inputName = new EditText(context);
        inputName.setHint("Name your itinerary");
        inputName.setPadding(20, 0, 20, 30);
        layout.addView(inputName);
        final EditText inputDescription = new EditText(context);
        inputDescription.setHint("Add a description");
        inputDescription.setPadding(20, 30, 20, 0);
        layout.addView(inputDescription);

        //build dialog
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Create a new itinerary")
            .setCancelable(false)
            .setView(layout)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Context context = getContext();
                    String inputItineraryName = inputName.getText().toString().trim();
                    String inputItineraryDes = inputDescription.getText().toString();
                    itineraryDbManager = new ItineraryDbManager(context);
                    itineraryDbManager.addItinerary(inputItineraryName, inputItineraryDes, attraction);
                    Toast.makeText(context, "New itinerary " + inputItineraryName + " added", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    Intent i = new Intent(context, MainActivity.class);
                    startActivity(i);
                }
            })
        .setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddtoItineraryDialog newFragment = AddtoItineraryDialog.newInstance(attraction);
                newFragment.show(getFragmentManager().beginTransaction(), "addtoItineraryDialog");
                dismiss();
            }
        });
        return builder.create();
    }
}