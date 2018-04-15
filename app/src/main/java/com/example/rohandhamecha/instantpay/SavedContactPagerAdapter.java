package com.example.rohandhamecha.instantpay;

import android.app.Activity;
import android.app.ActivityManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Keyur on 2/13/2018.
 */

public class SavedContactPagerAdapter extends FragmentPagerAdapter {

    public SavedContactPagerAdapter(FragmentManager fm){

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new SavedFragment();
            case 1:
                return new ContactFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
    /*
    public CharSequence getPageTitle(int position){

        switch (position){
            case 0:
                return "SAVED";
            case 1:
                return "CONTACTS";
            default:
                return null;
        }
    }
    */
}
