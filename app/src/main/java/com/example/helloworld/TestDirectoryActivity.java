package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestDirectoryActivity extends AppCompatActivity {

    private Button ocrTestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_directory);

        ocrTestButton = findViewById(R.id.ocr_activity_button);
        View.OnClickListener ocrListener;
        ocrListener = new View.OnClickListener()
        {

            public void onClick(View v)
            {
                openOCR();
            }
        };

        ocrTestButton.setOnClickListener(ocrListener);



    }


    public void openOCR(){

        Intent intent = new Intent(this, OcrTestActivity.class);
        this.startActivity(intent);

    }
}
