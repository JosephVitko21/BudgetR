package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.content.Intent;
import me.relex.circleindicator.CircleIndicator;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button activity2button = (Button) findViewById(R.id.activity2button);
        final Button tutorialButton = (Button) findViewById(R.id.tutorialButton);
        final CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        indicator.setViewPager(viewPager);


        View.OnClickListener a2Listener;
        a2Listener = new View.OnClickListener()
        {

            public void onClick(View v)
            {
                openActivity2();
            }
        };

        View.OnClickListener tutListener;
        tutListener = new View.OnClickListener()
        {
            public void onClick(View v)
            {
                openTutorial();
            }
        };

        activity2button.setOnClickListener(a2Listener);
        tutorialButton.setOnClickListener(tutListener);
    }

    public void openActivity2() {

        Intent intent = new Intent(this, page2test.class);
        this.startActivity(intent);

    }

    public void openTutorial(){

        Intent intent = new Intent(this, tutorialActivity.class);
        this.startActivity(intent);

    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable(){

                @Override
                public void run() {

                    if(viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    } else if(viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    } else if(viewPager.getCurrentItem() == 2){
                        viewPager.setCurrentItem(0);
                    }
                }


            });

        }
    }
}
