package com.example.asus.discoversg.SecondFragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.asus.discoversg.CardItemClickListener;
import com.example.asus.discoversg.R;

/**
 * Created by ASUS on 11/26/2017.
 */

public class AttractionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView attractionName, description;
    ImageButton addtoItinerary, wikiInfo;

    CardItemClickListener itemClickListener;

    public AttractionHolder(View itemView) {
        super(itemView);
        this.attractionName = (TextView) itemView.findViewById(R.id.suggestedname);
        this.description = (TextView) itemView.findViewById(R.id.attractiondescription);
        this.addtoItinerary = (ImageButton) itemView.findViewById(R.id.addtoItinerary);
        this.wikiInfo = (ImageButton) itemView.findViewById(R.id.wikiInfo);
        addtoItinerary.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    public void setItemClickListener(CardItemClickListener ic){
        this.itemClickListener = ic;
    }
}
