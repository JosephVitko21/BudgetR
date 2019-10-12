package com.example.helloworld;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class DemoFragment extends Fragment {

    string name;
    int budgetNumber;

    EditText nameInput;
    EditText budgetInput;

    Button beginButton;


    private TextView textView;


    public DemoFragment() {

        nameInput = (EditText) findViewById(R.idea.nameInput);
        budgetInput = (EditText) findViewById(R.idea.budgetInput);

        beginButton = (Button) findViewById(R.id.begin);
        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameInput.getText().toString();
                budgetNumber = Integer.valueOf(budgetInput.getText().toString());

            }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_demo, container, false);
        textView = view.findViewById(R.id.txt_display);
        textView.setText(getArguments().getString("message"));
        return view;
    }

}
