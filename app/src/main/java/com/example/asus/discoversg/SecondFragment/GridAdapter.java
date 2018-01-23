package com.example.asus.discoversg.SecondFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.discoversg.R;

/**
 * Created by ASUS on 12/1/2017.
 */

public class GridAdapter extends BaseAdapter {

    private Integer[] imageIDs = {R.drawable.hotspot, R.drawable.food, R.drawable.historic, R.drawable.museums,
            R.drawable.nature, R.drawable.religious, R.drawable.culture};
    private String[] titles = {"TOURIST HOTSPOTS", "LOCAL DELIGHTS", "HISTORIC LANDMARKS",
            "MUSEUMS", "NATURE SITES", "PLACES OF WORSHIP", "CULTURE"};

    private Context context;
    private LayoutInflater inflater;

    public GridAdapter(Context context, Integer[] imageIDs, String[] titles) {
        this.context = context;
        this.imageIDs = imageIDs;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return imageIDs.length;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView= convertView;
            if (convertView == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                gridView = inflater.inflate(R.layout.grid, null);
            }
            TextView label = (TextView) gridView.findViewById(R.id.item_text);
            ImageView icon = (ImageView) gridView.findViewById(R.id.item_image);
            label.setText(titles[position]);
            icon.setImageResource(imageIDs[position]);
        return gridView;
    }
}