package com.example.asus.discoversg.ThirdFragment.Router;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.discoversg.R;

/**
 * Created by ASUS on 11/28/2017.
 */

public class RouterMapTab extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.router_map_tab, container, false);
        return view;
    }
}
