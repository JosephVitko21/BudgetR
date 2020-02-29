package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompatExtras;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;


import static java.security.AccessController.getContext;

public class OcrTestActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button photoButton;
    private Button galleryButton;
    private TextView resultText;
    private TextView blankText;
    private TextView totalText;
    private Context context;

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private String mediaPath;

    private File cameraImage;


    Bitmap image;

    ImagePicker imagePicker;


    private TessBaseAPI mTess;
    String datapath = "";
    private Button confirmInfoButton;

    private static final int IMAG_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_test);



        imageView = findViewById(R.id.ocr_test_image);
        photoButton = findViewById(R.id.ocr_test_button);
        galleryButton = findViewById(R.id.ocr_test_gallery_button);
        resultText = findViewById(R.id.ocr_test_result);
        blankText = findViewById(R.id.ocr_test_blank_text);
        totalText = findViewById(R.id.total_text);


        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED) {
                        //permission not granted, request it
                        String[] permissions = {Manifest.permission.CAMERA};
                        //show popup for runtime permission
                        requestPermissions(permissions, MY_CAMERA_PERMISSION_CODE);

                    }
                    else {
                        //permission already granted
                        takePicture();

                    }
                } else {
                    // system os is less then marshmallow
                    takePicture();
                }

            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check runtime permissions
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        //permission not granted, request it
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);

                    }
                    else {
                        //permission already granted
                        pickImageFromGallery();

                    }
                } else {
                    // system os is less then marshmallow
                    pickImageFromGallery();
                }
            }
        });

        String language = "eng";
        datapath = getFilesDir()+ "/tesseract/";
        mTess = new TessBaseAPI();
        checkFile(new File(datapath + "tessdata/"));
        mTess.init(datapath, language);

        context = this;



    }

    private void takePicture() {

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);


    }


    public void runOCR(Bitmap image){

        String nullResult = mTess.getUTF8Text();

        String OCRresult = null;
        mTess.setImage(image);
        OCRresult = mTess.getUTF8Text();
        OCRresult.replaceAll("\\p{C}", "?");
        displayMessage(OCRresult);


        if(OCRresult == null){
            blankText.setText("Image Read:");
            resultText.setText("No text found!");
        } else{
            blankText.setText("Image Read:");
            resultText.setText(OCRresult);
            String totalLine= new String();
            String lines[] = OCRresult.split("\\r?\\n");
            Log.d("Output", Integer.toString(lines.length));
            for (int i = 0; i < lines.length; i++){
                String line = lines[i].toLowerCase();
                Log.d("Output", "Line: " + line);
                if (line.contains("total")){
                    totalLine = line;
                }
            }
            String total = totalLine.replaceAll("[^\\d.]", "");
            totalText.setText("Total: " + total);
            Log.d("Output", total);
        }


    }

    private void checkFile(File dir) {
        if (!dir.exists()&& dir.mkdirs()){
            copyFiles();
        }
        if(dir.exists()) {
            String datafilepath = datapath+ "/tessdata/eng.traineddata";
            File datafile = new File(datafilepath);
            if (!datafile.exists()) {
                copyFiles();
            }
        }
    }

    private void copyFiles() {
        try {
            String filepath = datapath + "/tessdata/eng.traineddata";
            AssetManager assetManager = getAssets();
            InputStream instream = assetManager.open("tessdata/eng.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();
            File file = new File(filepath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void displayMessage(String message){

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }

    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAG_PICK_CODE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("Output", "Request code: " + requestCode);
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    pickImageFromGallery();
                } else {
                    //permission was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
            case MY_CAMERA_PERMISSION_CODE:{
                    if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    {
                        //permission was granted
                        takePicture();
                    }
                    else
                    {
                        Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
                    }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAG_PICK_CODE){
            // set image to image view
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                bitmap = ARGBBitmap(bitmap);
                runOCR(bitmap);
            }catch (Exception e){
                Log.d("Error", "onActivityResult: Problem converting image");
                }
        }
        else if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST){
            // set image to image view
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            runOCR(bitmap);
        }
    }

    private Bitmap ARGBBitmap(Bitmap img) {
        return img.copy(Bitmap.Config.ARGB_8888,true);
    }




}
