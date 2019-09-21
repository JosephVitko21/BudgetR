package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = (Button) findViewById(R.id.button);
        final TextView helloWorldLabel = (TextView) findViewById(R.id.helloWorldLabel );
        final Button activity2button = (Button) findViewById(R.id.activity2button);

        View.OnClickListener ocl;
        ocl = new View.OnClickListener()
        {

            public void onClick(View v)
            {
                if (helloWorldLabel.getVisibility() == View.INVISIBLE) {
                    helloWorldLabel.setVisibility(View.VISIBLE);
                } else {

                    helloWorldLabel.setVisibility(View.INVISIBLE);

                }

            }
        };

        View.OnClickListener newOcl;
        newOcl = new View.OnClickListener()
        {

            public void onClick(View v)
            {
                openActivity2();


            }
        };

        button.setOnClickListener(ocl);
        activity2button.setOnClickListener(newOcl);
    }

    public void openActivity2() {

        Intent intent = new Intent(this, page2test.class);
        this.startActivity(intent);

    }
}
