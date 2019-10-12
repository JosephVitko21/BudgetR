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

            case 0: return FirstFragment.newInstance("FirstFragment, Instance 1");
            case 1: return SecondFragment.newInstance("SecondFragment, Instance 1");
            case 2: return ThirdFragment.newInstance("ThirdFragment, Instance 1");
            case 3: return FourthFragment.newInstance("FourthFragment, Instance 2");
            case 4: return FifthFragment.newInstance("FifthFragment, Instance 3");
            default: return ThirdFragment.newInstance("FirstFragment, Default");
        }
    }

    @Override
    public int getCount() {
        return 5;
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
            return "Add";
        }
        if(position==4){
            return "Graphs";
        }
        if(position==5){
            return "Settings";
        }else{
            return null;
        }
    }
}
