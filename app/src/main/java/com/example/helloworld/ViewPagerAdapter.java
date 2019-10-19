package com.example.helloworld;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int pos) {
        switch(pos) {

            case 0: return FirstFragment.newInstance("Home Page (FirstFragment)");
            case 1: return SecondFragment.newInstance("Expenses Page (SecondFragment)");
            case 2: return ThirdFragment.newInstance("Data Page (ThirdFragment)");
            default: return ThirdFragment.newInstance("FirstFragment, Default");
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        position = position+1;
        if(position==1){
            return "Home";
        }
        if(position==2){
            return "Expenses";
        }
        if(position==3){
            return "Data";
        } else{
            return null;
        }
    }



}
