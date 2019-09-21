package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import android.support.v4.view.ViewPager;

import android.os.Bundle;

public class tutorialActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
    }
}
