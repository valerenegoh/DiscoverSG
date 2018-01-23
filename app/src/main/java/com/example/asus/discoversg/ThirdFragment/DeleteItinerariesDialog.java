package com.example.asus.discoversg.ThirdFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.asus.discoversg.Database.ItineraryDbManager;
import com.example.asus.discoversg.MainActivity;
import com.example.asus.discoversg.R;

/**
 * Created by ASUS on 11/28/2017.
 */

public class DeleteItinerariesDialog extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete all itineraries?")
            .setIcon(R.drawable.ic_warning_black_24dp)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Context context = getActivity();
                    ItineraryDbManager itineraryDbManager = new ItineraryDbManager(context);
                    itineraryDbManager.deleteItineraryDatabase();
                    dismiss();
                    Intent i = new Intent(context, MainActivity.class);
                    startActivity(i);
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        return builder.create();
    }
}
