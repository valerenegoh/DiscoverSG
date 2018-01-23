package com.example.asus.discoversg.ThirdFragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.asus.discoversg.ThirdFragment.Router.RouterMain;

/**
 * Created by ASUS on 12/2/2017.
 */

public class BudgetDialog extends DialogFragment{

    public static final String KEY = "ItineraryRouter";
    String title;
    AlertDialog.Builder builder;
    EditText budgetInput;
    double budget;

    public static BudgetDialog newInstance(String title) {
        Bundle args = new Bundle();
        BudgetDialog fragment = new BudgetDialog();
        args.putCharSequence("itineraryName", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //get args
        title = getArguments().getCharSequence("itineraryName").toString();
        builder = new AlertDialog.Builder(getActivity());
        budgetInput = new EditText(getActivity());
        budgetInput.setHint("Enter budget for optimisation");
        budgetInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        //set layout
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setPadding(10, 0, 10, 0);
        builder.setCancelable(false)
                .setView(layout)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        budget = Double.parseDouble(budgetInput.getText().toString());
                        dialog.dismiss();
                        Intent i = new Intent(getActivity(), RouterMain.class);
                        i.putExtra(KEY, title);
                        //call optimisation method. Input visiting & budget, output reordered visiting, travelDetails, time and cost.
                        //save optimised outputs into database
                        startActivity(i);
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