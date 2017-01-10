package com.example.cal13.found;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;


class AnimationUtilities
{


    static void animateRevealShow(final Context ctx, final View view, final int startRadius,
                                         @ColorRes final int color, int x, int y, final OnRevealAnimationListener listener)
    {
        float finalRadius = (float) Math.hypot(view.getWidth(), view.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, finalRadius);
        anim.setDuration(ctx.getResources().getInteger(R.integer.animation_duration));
        anim.setStartDelay(100);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());

        anim.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                view.setBackgroundColor(ContextCompat.getColor(ctx, color));
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                view.setVisibility(View.VISIBLE);
                listener.onRevealShow();
            }
        });
        anim.start();
    }

}

