package com.example.asus.discoversg.ThirdFragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.asus.discoversg.CardItemClickListener;
import com.example.asus.discoversg.R;

/**
 * Created by ASUS on 11/26/2017.
 */

public class ItineraryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView itineraryName, description;
    ImageButton viewItinerary;

    CardItemClickListener itemClickListener;

    public ItineraryHolder(View itemView) {
        super(itemView);
        this.itineraryName = (TextView) itemView.findViewById(R.id.itineraryname);
        this.description = (TextView) itemView.findViewById(R.id.itinerarydescription);
        this.viewItinerary = (ImageButton) itemView.findViewById(R.id.viewItinerary);

        viewItinerary.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    public void setItemClickListener(CardItemClickListener ic){
        this.itemClickListener = ic;
    }
}
