package com.example.cal13.found;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class lost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
        setupEnterAnimation();
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
                animateRevealShow(findViewById(R.id.activity_lost));
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
        AnimationUtilities.animateRevealShow(this, viewRoot, findViewById(R.id.lost_fab).getWidth() / 2, R.color.colorPrimary,
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
        findViewById(R.id.activity_lost_container).startAnimation(animation);
        findViewById(R.id.activity_lost_container).setVisibility(View.VISIBLE);
        findViewById(R.id.lost_fab).setVisibility(View.GONE);


    }
}
