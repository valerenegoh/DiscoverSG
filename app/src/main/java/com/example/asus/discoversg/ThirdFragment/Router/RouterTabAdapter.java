package com.example.asus.discoversg.ThirdFragment.Router;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 11/28/2017.
 */

public class RouterTabAdapter extends FragmentPagerAdapter{

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentNames = new ArrayList<>();

    public RouterTabAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String fragmentName){
        fragmentList.add(fragment);
        fragmentNames.add(fragmentName);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentNames.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
