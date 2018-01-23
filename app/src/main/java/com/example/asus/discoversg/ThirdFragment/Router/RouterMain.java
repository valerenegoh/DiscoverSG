package com.example.asus.discoversg.ThirdFragment.Router;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.asus.discoversg.R;

public class RouterMain extends AppCompatActivity {

    private RouterTabAdapter routerTabAdapter;

    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.router_main);
        routerTabAdapter = new RouterTabAdapter(getSupportFragmentManager());

        //Setup viewpager with the router tab adapter
        viewpager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewpager);
        TabLayout tablayout = (TabLayout) findViewById(R.id.tabs);
        tablayout.setupWithViewPager(viewpager);
    }

    private void setupViewPager(ViewPager viewPager){
        RouterTabAdapter adapter = new RouterTabAdapter(getSupportFragmentManager());
        adapter.addFragment(new RouterListTab(), "RouterListTab");
        adapter.addFragment(new RouterMapTab(), "RouterMapTab");
        viewPager.setAdapter(adapter);
    }
}