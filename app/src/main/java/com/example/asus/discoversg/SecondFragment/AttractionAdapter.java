package com.example.asus.discoversg.SecondFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.example.asus.discoversg.Database.AttractionItem;
import com.example.asus.discoversg.R;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/26/2017.
 */

public class AttractionAdapter extends RecyclerView.Adapter<AttractionHolder> implements Filterable{

    public static final String KEY = "AttractionTitle";
    Context context;
    ArrayList<AttractionItem> attractions, filterList;
    AttractionCustomFilter filter;
//    ArrayList<ItineraryItem> itineraries;
    AddtoItineraryDialog addtoItineraryDialog;

    public AttractionAdapter(Context context, ArrayList<AttractionItem> attractions){
        this.context = context;
        this.attractions = attractions;
        this.filterList = attractions;
    }

    @Override
    public AttractionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Convert to view object
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attraction_card, null);
        AttractionHolder holder = new AttractionHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AttractionHolder holder, int position) {
        //bind data to view
        holder.attractionName.setText(attractions.get(position).getAttractionName());
        holder.description.setText(attractions.get(position).getDescription());

        holder.addtoItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence attraction = attractions.get(position).getAttractionName();
                addtoItineraryDialog = AddtoItineraryDialog.newInstance(attraction);
                addtoItineraryDialog.show(((Activity) context).getFragmentManager(), "addtoItineraryDialog");
//                Snackbar.make(v, attractions.get(pos).getAttractionName(), Snackbar.LENGTH_SHORT).show();
            }
        });
        holder.wikiInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CharSequence attractionName = attractions.get(position).getAttractionName();
                    Intent i = new Intent(context, WikiDescription.class);
                    i.putExtra(KEY, attractionName);
                    context.startActivity(i);
                } catch (Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
//                Snackbar.make(v, attractionName, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    //return filter object
    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new AttractionCustomFilter(this, filterList);
        }
        return filter;
    }
}