package com.example.asus.discoversg.ThirdFragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.asus.discoversg.CardItemClickListener;
import com.example.asus.discoversg.Database.ItineraryItem;
import com.example.asus.discoversg.R;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/26/2017.
 */

public class ItineraryAdapter extends RecyclerView.Adapter<ItineraryHolder> implements Filterable{

    Context context;
    ArrayList<ItineraryItem> itineraries, filterList;
    ItineraryCustomFilter filter;
    public static final String KEY = "getItineraryDetails";

    public ItineraryAdapter(Context context, ArrayList<ItineraryItem> itineraries){
        this.context = context;
        this.itineraries = itineraries;
        this.filterList = itineraries;
    }

    @Override
    public ItineraryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Convert to view object
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itinerary_card, null);
        ItineraryHolder holder = new ItineraryHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItineraryHolder holder, int position) {
        //bind data to view
        holder.itineraryName.setText(itineraries.get(position).getItineraryName());
        holder.description.setText(itineraries.get(position).getDescription());

        //implement click listener
        holder.setItemClickListener(new CardItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                CharSequence itinerary = itineraries.get(pos).getItineraryName();
                Intent i = new Intent(context, ItineraryDetails.class);
                i.putExtra(KEY, itinerary);
                context.startActivity(i);
//                Snackbar.make(v, itinerary, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itineraries.size();
    }

    //return filter object
    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new ItineraryCustomFilter(this, filterList);
        }
        return filter;
    }
}