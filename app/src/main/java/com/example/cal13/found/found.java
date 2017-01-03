package com.example.cal13.found;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class found extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);
        setupEnterAnimation();

        Button settings = (Button) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(found.this, settings.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(found.this).toBundle();
                startActivity(i,b);
            }
        });

    }




    private void setupEnterAnimation()
    {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
        transition.setDuration(300);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                animateRevealShow(findViewById(R.id.activity_found));
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void animateRevealShow(final View viewRoot)
    {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        AnimationUtilities.animateRevealShow(this, viewRoot, findViewById(R.id.found_fab).getWidth() / 2, R.color.colorAccent,
                cx, cy, new OnRevealAnimationListener()
                {
                    @Override
                    public void onRevealHide()
                    {

                    }

                    @Override
                    public void onRevealShow()
                    {
                        initViews();
                    }
                });
    }

    private void initViews()
    {
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        animation.setDuration(300);
        findViewById(R.id.activity_found_container).startAnimation(animation);
        findViewById(R.id.activity_found_container).setVisibility(View.VISIBLE);
        findViewById(R.id.found_fab).setVisibility(View.GONE);


    }

}
