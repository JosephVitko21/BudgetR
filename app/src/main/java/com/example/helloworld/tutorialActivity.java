package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class tutorialActivity extends AppCompatActivity {

    private ViewPager viewPager;
    CustomSwipeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);


    }
}
