package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompatExtras;
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

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import static java.security.AccessController.getContext;

public class OcrTestActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button photoButton;
    private Button galleryButton;
    private TextView resultText;
    private TextView blankText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_test);



        imageView = findViewById(R.id.ocr_test_image);
        photoButton = findViewById(R.id.ocr_test_button);
        galleryButton = findViewById(R.id.ocr_test_gallery_button);
        resultText = findViewById(R.id.ocr_test_result);
        blankText = findViewById(R.id.ocr_test_blank_text);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker = new ImagePicker(new ImagePicker.GetImage() {
                    @Override
                    public void setGalleryImage(Uri imageUri) {

                        Log.i("ImageURI", imageUri + "");

                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                        assert cursor != null;
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        mediaPath = cursor.getString(columnIndex);
                        // Set the Image in ImageView for Previewing the Media
                        imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                        cursor.close();

                    }

                    @Override
                    public void setCameraImage(String filePath) {

                        mediaPath =filePath;
                        Glide.with(getContext()).load(filePath).into(imageView);

                    }

                    @Override
                    public void setImageFile(File file) {

                        cameraImage = file;

                    }
                }, true);
                imagePicker.show(getActivity().getSupportFragmentManager(), imagePicker.getTag());
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
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


    private void selectImage() {



        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };




        AlertDialog.Builder builder = new AlertDialog.Builder(OcrTestActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                        takePicture();
                }

                else if (options[item].equals("Choose from Gallery"))

                {

                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 2);



                }

                else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

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

    private File savebitmap(Bitmap bmp) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        // String temp = null;
        File file = new File(extStorageDirectory, "temp.png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, "temp.png");

        }

        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    private void displayMessage(String message){

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            runOCR(photo);
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//        displayMessage(Integer.toString(requestCode));
//
//        if (resultCode == RESULT_OK) {
//
//            if (requestCode == 1) {
//
//                //h=0;
//                File f = new File(Environment.getExternalStorageDirectory().toString());
//
//                for (File temp : f.listFiles()) {
//
//                    if (temp.getName().equals("temp.jpg")) {
//
//                        f = temp;
//                        File photo = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
//                        //pic = photo;
//                        break;
//
//                    }
//
//                }
//
//                try {
//
//                    Bitmap bitmap;
//
//                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//
//
//                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
//
//                            bitmapOptions);
//
//
//                    imageView.setImageBitmap(bitmap);
//
//
//                    String path = android.os.Environment
//
//                            .getExternalStorageDirectory()
//
//                            + File.separator
//
//                            + "Phoenix" + File.separator + "default";
//                    //p = path;
//
//                    f.delete();
//
//                    OutputStream outFile = null;
//
//                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
//
//                    try {
//
//                        outFile = new FileOutputStream(file);
//
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
//                        //pic=file;
//                        outFile.flush();
//
//                        outFile.close();
//
//
//                    } catch (FileNotFoundException e) {
//
//                        e.printStackTrace();
//
//                    } catch (IOException e) {
//
//                        e.printStackTrace();
//
//                    } catch (Exception e) {
//
//                        e.printStackTrace();
//
//                    }
//
//                } catch (Exception e) {
//
//                    e.printStackTrace();
//
//                }
//
//            } else if (requestCode == 2) {
//
//
//                Uri selectedImage = data.getData();
//                // h=1;
//                //imgui = selectedImage;
//                String[] filePath = {MediaStore.Images.Media.DATA};
//
//                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
//
//                c.moveToFirst();
//
//                int columnIndex = c.getColumnIndex(filePath[0]);
//
//                String picturePath = c.getString(columnIndex);
//
//                c.close();
//
//                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//
//
//                Log.w("path", picturePath + "");
//
//
//                imageView.setImageBitmap(thumbnail);
//
//            }
//
//        }else{
//
//            displayMessage("Problem with result code" + resultCode);
//
//        }
//    }



}
