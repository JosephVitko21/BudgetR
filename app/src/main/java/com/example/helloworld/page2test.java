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
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

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
        scanDisplayImage = (ImageView) findViewById(R.id.scan_display_image);
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    scanDisplayImage.setImageBitmap(bitmap);
                    displayMessage("Image taken successfully loaded");
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    scanDisplayImage.setImageBitmap(bitmap);
                    displayMessage("Image selected successfully loaded");
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

}
