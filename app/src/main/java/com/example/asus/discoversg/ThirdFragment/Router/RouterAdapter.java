package com.example.asus.discoversg.ThirdFragment.Router;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.discoversg.R;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/26/2017.
 */

public class RouterAdapter extends RecyclerView.Adapter<RouterHolder>{

    Context context;
    ArrayList<RouterItem> edges, filterList;

    public RouterAdapter(Context context, ArrayList<RouterItem> edges){
        this.context = context;
        this.edges = edges;
        this.filterList = edges;
    }

    @Override
    public RouterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Convert to view object
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_card, null);
        RouterHolder holder = new RouterHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RouterHolder holder, int position) {
        //bind data to view
        holder.locationNode.setText(edges.get(position).getFromName());
        holder.travelDetails.setText(edges.get(position).getTravelDetails());
    }

    @Override
    public int getItemCount() {
        return edges.size();
    }
}