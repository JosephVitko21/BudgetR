package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;
import android.graphics.BitmapFactory;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.NumberFormat;
import java.util.Calendar;


public class page2test extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.house,
            R.drawable.speech_bubble_7,
            R.drawable.plus,
            R.drawable.bar_chart,
            R.drawable.settings
    };
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton addButton;
    private ImageView scanDisplayImage;
    private LayoutInflater inflater;
    private View dialogView;
    private View scanLayout;
    private View manualLayout;
    private ImageButton enterManualButton;
    private ImageButton scanReceiptButton;
    private ImageButton cameraButton;
    private ImageButton photoButton;
    private TextView manualEntryLabel;
    private EditText manualEntryInput;
    private Button manualNextButton;
    private int counter;
    private String inputText;
    private String line;
    private String tempCostString;
    private AutoCompleteTextView manualAutoComplete;
    private TextView messageView;
    private Button clearFileButton;
    Bitmap image;
    private TessBaseAPI mTess;
    String datapath = "";
    private Button confirmInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2test);


        toolbar=findViewById(R.id.toolBar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        viewPager = findViewById(R.id.tabbedPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        menuItem.setChecked(true);
                        viewPager.setCurrentItem(0);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_expenses:
                        menuItem.setChecked(true);
                        viewPager.setCurrentItem(1);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_data:
                        menuItem.setChecked(true);
                        viewPager.setCurrentItem(2);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_add:
                        menuItem.setChecked(true);
                        viewPager.setCurrentItem(3);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_settings:
                        menuItem.setChecked(true);
                        displayMessage("Settings Selected...");
                        drawerLayout.closeDrawers();
                        return true;
                }

                return false;
            }
        });

        addButton = findViewById(R.id.add_expense_button);
        View.OnClickListener addListener;
        addListener = new View.OnClickListener()
        {
            public void onClick(View v)
            {
                addExpense();
            }
        };
        addButton.setOnClickListener(addListener);

//        clearFileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clearFile();
//            }
//        });
        image = BitmapFactory.decodeResource(getResources(), R.drawable.budgetr_home_logo);
        String language = "eng";
        datapath = getFilesDir()+ "/tesseract/";
        mTess = new TessBaseAPI();
        checkFile(new File(datapath + "tessdata/"));
        mTess.init(datapath, language);


    }

    private void displayMessage(String message){

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[3]);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addExpense(){

//        ExampleDialog exampleDialog = new ExampleDialog();
//        exampleDialog.show(getSupportFragmentManager(), "example dialog");

        Dialog dialog = onCreateDialog();
        dialog.show();



    }

    public Dialog onCreateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final AlertDialog dialogS = builder.create();

        inflater = this.getLayoutInflater();
        dialogView = inflater.inflate(R.layout.custom_dialog_layout, null);
        dialogS.setView(dialogView);
        dialogS.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        enterManualButton = (ImageButton) dialogView.findViewById(R.id.enter_manual_button);
        enterManualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manualLayout = inflater.inflate(R.layout.manual_entry_dialog, null);
                dialogS.setContentView(manualLayout);
                dialogS.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                manualNextButton = (Button) manualLayout.findViewById(R.id.manual_next_button);
                manualEntryInput = (EditText) manualLayout.findViewById(R.id.manual_entry_input);
                manualEntryLabel = (TextView) manualLayout.findViewById(R.id.manual_entry_label);
                manualAutoComplete = (AutoCompleteTextView) manualLayout.findViewById(R.id.manual_auto_complete);

                manualEntryInput.clearFocus();
                BaseInputConnection  mInputConnection = new BaseInputConnection(manualLayout, true);
                mInputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                manualEntryInput.getText().clear();
                manualEntryLabel.setText("Enter the Location of your Expense");
                manualEntryInput.requestFocus();
                mInputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));

                StringBuilder sb = new StringBuilder();

                final String[] labels = new String[4];
                labels[0] = "Enter the Location or Website of your Expense";
                labels[1] = "Enter the Total Cost of your Expense";
                labels[2] = "Enter the Date of your Expense";
                labels[3] = "Category (Food, Transportation, Personal, Entertainment, Other);";
                counter = 0;

                line = "";

                final TextWatcher tw = new TextWatcher() {
                    private String current = "";
                    private String mmddyyyy = "MMDDYYYY";
                    private Calendar cal = Calendar.getInstance();

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!s.toString().equals(current)) {
                            String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                            String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                            int cl = clean.length();
                            int sel = cl;
                            for (int i = 2; i <= cl && i < 6; i += 2) {
                                sel++;
                            }
                            //Fix for pressing delete next to a forward slash
                            if (clean.equals(cleanC)) sel--;

                            if (clean.length() < 8){
                                clean = clean + mmddyyyy.substring(clean.length());
                            }else{
                                //This part makes sure that when we finish entering numbers
                                //the date is correct, fixing it otherwise
                                int day  = Integer.parseInt(clean.substring(2,4));
                                int mon  = Integer.parseInt(clean.substring(0,2));
                                int year = Integer.parseInt(clean.substring(4,8));

                                mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                                cal.set(Calendar.MONTH, mon-1);
                                year = (year<1900)?1900:(year>2100)?2100:year;
                                cal.set(Calendar.YEAR, year);
                                // ^ first set year for the line below to work correctly
                                //with leap years - otherwise, date e.g. 29/02/2012
                                //would be automatically corrected to 28/02/2012

                                day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                                clean = String.format("%02d%02d%02d",mon, day, year);
                            }

                            clean = String.format("%s/%s/%s", clean.substring(0, 2),
                                    clean.substring(2, 4),
                                    clean.substring(4, 8));

                            sel = sel < 0 ? 0 : sel;
                            current = clean;
                            manualEntryInput.setText(current);
                            manualEntryInput.setSelection(sel < current.length() ? sel : current.length());
                        }
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void afterTextChanged(Editable s) {}
                };
                final TextWatcher tw2 = new TextWatcher() {

                    private String current = "";
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(!s.toString().equals(current)){
                            manualEntryInput.removeTextChangedListener(this);

                            String cleanString = s.toString().replaceAll("[$,.]", "");

                            double parsed = Double.parseDouble(cleanString);
                            String formatted = NumberFormat.getCurrencyInstance().format((parsed/100));

                            current = formatted;
                            manualEntryInput.setText(formatted);
                            manualEntryInput.setSelection(formatted.length());

                            manualEntryInput.addTextChangedListener(this);
                            tempCostString = manualEntryInput.getText().toString();
                        }
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };


                manualNextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        counter = counter + 1;
                        if(counter == 4){
                            String inputText = manualEntryInput.getText().toString();
                            line = buildString(line, inputText, counter);
                            displayMessage(line);
                            try{
                                commitToFile(line);
                                readMessage();
                            }catch (IOException e){
                            }
                            dialogS.dismiss();
                        }else if(counter==1) {

                            manualEntryInput.addTextChangedListener(tw2);
                            String inputText = manualEntryInput.getText().toString();
                            line = buildString(line, inputText, counter);
                            displayMessage(line);
                            manualEntryInput.getText().clear();
                            manualEntryLabel.setText(labels[counter]);

                        }else if(counter==2){
                            manualEntryInput.removeTextChangedListener(tw2);
                            manualEntryInput.addTextChangedListener(tw);
                            String inputText = manualEntryInput.getText().toString().substring(1);
                            line = buildString(line, inputText, counter);
                            displayMessage(line);
                            manualEntryInput.getText().clear();
                            manualEntryLabel.setText(labels[counter]);

                        }else{
                            manualEntryInput.removeTextChangedListener(tw);
                            manualEntryInput.removeTextChangedListener(tw2);
                            manualEntryInput.setInputType(InputType.TYPE_CLASS_TEXT);
                            String inputText = manualEntryInput.getText().toString();
                            line = buildString(line, inputText, counter);
                            displayMessage(line);
                            manualEntryInput.getText().clear();
                            manualEntryLabel.setText(labels[counter]);
                        }




                    }
                });


            }
        });
        scanReceiptButton = (ImageButton) dialogView.findViewById(R.id.scan_receipt_button);
        scanReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanLayout = inflater.inflate(R.layout.scan_add_layout, null);
                dialogS.setContentView(scanLayout);

                cameraButton = (ImageButton) scanLayout.findViewById(R.id.camera_button);
                photoButton = (ImageButton) scanLayout.findViewById(R.id.photo_button);

                cameraButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        camera(dialogS);
                    }
                });

                photoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gallery(dialogS);
                    }
                });



            }
        });


        return dialogS;
    }


    public void camera(Dialog dialog){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, 0);//zero can be replaced with any action code (called requestCode)
        dialog.dismiss();

    }

    public void gallery(Dialog dialog){

        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
        dialog.dismiss();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Dialog dialog = confirmDialog();
                    dialog.show();
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Dialog dialog = confirmDialog();
                    dialog.show();
                }
                break;
        }
    }

    public String buildString(String originalString, String input, int lineNumber){
        if(lineNumber==1){
            StringBuilder sb = new StringBuilder();
            sb.append(originalString);
            sb.append(input.toLowerCase());
            String updatedString = sb.toString();
            return updatedString;
        }else{
            StringBuilder sb = new StringBuilder();
            sb.append(originalString);
            sb.append(",");
            sb.append(input.toLowerCase());
            String updatedString = sb.toString();
            return updatedString;
        }



    }

    private void commitToFile(String expenseString) throws IOException {

        String message = expenseString + "\n";
        String fileName = "hello_file";
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_APPEND);
            fileOutputStream.write(message.getBytes());
            fileOutputStream.close();
            displayMessage("Message Saved");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }


//        FileOutputStream fOut = openFileOutput("savedData.csv",
//                MODE_APPEND);
//        OutputStreamWriter osw = new OutputStreamWriter(fOut);
//        osw.write(expenseString);
//        osw.write("\n");
//        osw.flush();
//        osw.close();


    }

    public void readMessage(){
        try {
            String message;
            FileInputStream fileInputStream = openFileInput("hello_file");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            message = bufferedReader.readLine();
            stringBuffer.append(message + "\n");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void clearFile(View view){

        String message = "";
        String fileName = "hello_file";
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            fileOutputStream.write(message.getBytes());
            fileOutputStream.close();
            displayMessage("File Cleared");
            readMessage();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
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

    public void runOCR(View view){
        String OCRresult = null;
        mTess.setImage(image);
        OCRresult = mTess.getUTF8Text();
        messageView.setText(OCRresult);
    }

    public Dialog confirmDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final AlertDialog dialog = builder.create();

        inflater = this.getLayoutInflater();
        dialogView = inflater.inflate(R.layout.confirm_dialog_layout, null);

        dialog.setView(dialogView);

        confirmInfoButton = (Button) dialogView.findViewById(R.id.ConfirmInfoButton);
        confirmInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                displayMessage("Data successfully enetered!");

            }
        });

        return dialog;


    }



}
