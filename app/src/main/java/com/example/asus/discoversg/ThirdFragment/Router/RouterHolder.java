package com.example.asus.discoversg.ThirdFragment.Router;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.asus.discoversg.CardItemClickListener;
import com.example.asus.discoversg.R;

/**
 * Created by ASUS on 11/26/2017.
 */

public class RouterHolder extends RecyclerView.ViewHolder{

    TextView locationNode, travelDetails;

    CardItemClickListener itemClickListener;

    public RouterHolder(View itemView) {
        super(itemView);
        this.locationNode = (TextView) itemView.findViewById(R.id.locationNode);
        this.travelDetails = (TextView) itemView.findViewById(R.id.travelDetails);
    }
}
