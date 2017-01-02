package com.example.cal13.found;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Gravity;

public class settings extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupWindowAnimations();
    }
    private void setupWindowAnimations() {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        Slide slide = new Slide();
        slide.setDuration(500);
        slide.setSlideEdge(Gravity.TOP);
        getWindow().setEnterTransition(slide);
    }
}
