package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = (Button) findViewById(R.id.button);
        final TextView helloWorldLabel = (TextView) findViewById(R.id.helloWorldLabel );

        View.OnClickListener ocl;
        ocl = new View.OnClickListener()
        {

            public void onClick(View v)
            {
                if (helloWorldLabel.getVisibility() == View.INVISIBLE) {
                    helloWorldLabel.setVisibility(View.VISIBLE);
                }
                if (helloWorldLabel.getVisibility() == View.VISIBLE) {
                    helloWorldLabel.setVisibility(View.INVISIBLE);
                }

            }
        };
        button.setOnClickListener(ocl);
    }
}
