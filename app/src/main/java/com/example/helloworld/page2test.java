package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class page2test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2test);
        final Button backButton = (Button) findViewById(R.id.backButton);

        View.OnClickListener ocl;
        ocl = new View.OnClickListener(){
            public void onClick(View v){
                openActivity1();
            }
        };

        backButton.setOnClickListener(ocl);

    }

    public void openActivity1(){

        Intent intent = new Intent(page2test.this, MainActivity.class);
        this.startActivity(intent);

    }
}
