package com.example.helloworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import me.relex.circleindicator.CircleIndicator;

public class TutorialSwipeAdapter extends PagerAdapter {
    private int[] imageResources =  {R.drawable.tutorial_label1, R.drawable.tutorial_label2, R.drawable.tutorial_label3};
    private Context ctx;
    private LayoutInflater layoutInflater;

    public TutorialSwipeAdapter(Context ctx){
        this.ctx=ctx;
    }
    @Override
    public int getCount() {
        return imageResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return (view==(LinearLayout)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.swipe_layout,container,false);
        ImageView imageView = (ImageView)itemView.findViewById(R.id.imageView);
        imageView.setImageResource(imageResources[position]);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
