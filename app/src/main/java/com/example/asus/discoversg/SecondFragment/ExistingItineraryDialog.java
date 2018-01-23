package com.example.asus.discoversg.SecondFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.example.asus.discoversg.Database.ItineraryDbManager;
import com.example.asus.discoversg.Database.ItineraryItem;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/25/2017.
 */

public class ExistingItineraryDialog extends DialogFragment {
    ArrayList<ItineraryItem> itineraries;
    CharSequence[] itineraryNames;
    AlertDialog.Builder builder;
    int selectedItinerary;
    CharSequence attraction;

    public static ExistingItineraryDialog newInstance(CharSequence attraction, String[] itineraries) {
        Bundle args = new Bundle();
        ExistingItineraryDialog fragment = new ExistingItineraryDialog();
        args.putCharSequence("attraction", attraction);
        args.putCharSequenceArray("itineraries", itineraries);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Get args
        attraction = getArguments().getCharSequence("attraction");
        itineraryNames = getArguments().getCharSequenceArray("itineraries");
        //build dialog
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose an existing itinerary...")
            .setCancelable(false)
            .setSingleChoiceItems(itineraryNames, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectedItinerary = which;     //save itinerary contents
                }
            })
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Context context = getActivity();
                    //update itineraries database
                    ItineraryDbManager itineraryDbManager = new ItineraryDbManager(context);
                    itineraryDbManager.addAttraction(itineraryNames[selectedItinerary].toString(), attraction.toString());
                    Toast.makeText(getActivity(), "Added to itinerary", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        return builder.create();
    }
}