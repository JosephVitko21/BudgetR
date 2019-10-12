package com.example.helloworld;

import androidx.fragment.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.widget.TextView;

public class FourthFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab4, container, false);

        TextView tv = (TextView) v.findViewById(R.id.tvFragFourth);
        tv.setText(getArguments().getString("msg"));

        return v;
    }

    public static FourthFragment newInstance(String text) {

        FourthFragment f = new FourthFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}