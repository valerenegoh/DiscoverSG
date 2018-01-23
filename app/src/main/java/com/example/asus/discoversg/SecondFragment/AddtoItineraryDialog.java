package com.example.asus.discoversg.SecondFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.example.asus.discoversg.Database.ItineraryDbManager;

/**
 * Created by ASUS on 11/26/2017.
 */

public class AddtoItineraryDialog extends DialogFragment{

    final CharSequence[] items = {"New Itinerary", "Existing Itinerary"};
    AlertDialog.Builder builder;
    int useExistingItinerary;
    CharSequence attraction;

    public static AddtoItineraryDialog newInstance(CharSequence attraction) {
        Bundle args = new Bundle();
        AddtoItineraryDialog fragment = new AddtoItineraryDialog();
        args.putCharSequence("attraction", attraction);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //build dialog
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select where to add this local attraction to...")
            .setCancelable(false)
            .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    useExistingItinerary = which;
                }
            })
            .setPositiveButton("NEXT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Context context = getActivity();
                    //Get argument
                    attraction = getArguments().getCharSequence("attraction");
                    //if user choose to add attraction to existing itinerary
                    if(useExistingItinerary == 1){
                        //fetch current list of itineraries from database
                        ItineraryDbManager itineraryDbManager = new ItineraryDbManager(context);
                        String[] itineraries = itineraryDbManager.fetchItineraryNames();
                        //check if there exists any pre-created itineraries
                        if(itineraries == null){
                            Toast.makeText(context, "No itineraries saved", Toast.LENGTH_SHORT).show();
                        } else{
                            ExistingItineraryDialog newFragment = ExistingItineraryDialog.newInstance(attraction, itineraries);
                            newFragment.show(getFragmentManager().beginTransaction(),"existingItineraryDialog");
                            dismiss();
                        }
                        //if user choose to add attraction to new itinerary
                    } else if(useExistingItinerary == 0){
                        NewItineraryDialog newFragment = NewItineraryDialog.newInstance(attraction);
                        newFragment.show(getFragmentManager().beginTransaction(), "newItineraryDialog");
                        dismiss();
                    }
                }
            })
            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        return builder.create();
    }
}