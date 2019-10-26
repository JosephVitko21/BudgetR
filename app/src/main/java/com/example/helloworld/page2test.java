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
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;



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

        enterManualButton = (ImageButton) dialogView.findViewById(R.id.enter_manual_button);
        enterManualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogS.dismiss();

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

        dialogS.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


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
        scanDisplayImage.setImageBitmap(bitmap);
//        switch(requestCode) {
//            case 0:
//                if(resultCode == RESULT_OK){
//                    bitmap = (Bitmap)data.getExtras().get("data");
//                    scanDisplayImage.setImageBitmap(bitmap);
//                    displayMessage("Image successfully loaded");
//                }
//
//                break;
//            case 1:
//                if(resultCode == RESULT_OK){
//                    bitmap = (Bitmap)data.getExtras().get("data");
//                    scanDisplayImage.setImageBitmap(bitmap);
//                    displayMessage("Image successfully loaded");
//                }
//                break;
//        }
    }
}
