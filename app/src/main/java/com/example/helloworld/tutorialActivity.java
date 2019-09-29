package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.os.Bundle;

import me.relex.circleindicator.CircleIndicator;

public class tutorialActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CircleIndicator indicator;
    private Button button;
    private TextView positionText;
    TutorialSwipeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new TutorialSwipeAdapter(this);
        viewPager.setAdapter(adapter);


        indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        button = (Button) findViewById(R.id.end_tutorial_button);
        button.setVisibility(View.INVISIBLE);

        positionText = (TextView) findViewById(R.id.position_label);

        View.OnClickListener buttonListener;
        buttonListener = new View.OnClickListener()
        {
            public void onClick(View v)
            {
                endTutorial();
            }
        };

        button.setOnClickListener(buttonListener);

        viewPager.addOnPageChangeListener(new OnPageChangeListener(){
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                // Check if this is the page you want.
                positionText.setText("position: " + position);
                if(position == 2){
                    showEndButton();
                } else{
                    hideEndButton();
                }

            }
        });






    }

    public void endTutorial(){

        Intent intent = new Intent(this, page2test.class);
        this.startActivity(intent);

    }

    public void showEndButton(){

        button.setVisibility(View.VISIBLE);

    }

    public void hideEndButton(){

        button.setVisibility(View.INVISIBLE);

    }

    public void determinePos(){

        int position = viewPager.getCurrentItem();
        positionText.setText("Position" + position);
        if(position==0){
            showEndButton();
        } else{
            hideEndButton();
        }


    }
}
