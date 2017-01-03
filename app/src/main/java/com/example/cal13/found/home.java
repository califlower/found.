package com.example.cal13.found;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FloatingActionButton found = (FloatingActionButton) findViewById(R.id.found_fab);
        FloatingActionButton lost = (FloatingActionButton) findViewById(R.id.lost_fab);

        Button settings= (Button) findViewById(R.id.settings);

        found.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(home.this, found.class);
                View s = findViewById(R.id.foundText);
                String t = findViewById(R.id.foundText).getTransitionName();

                View f = findViewById(R.id.found_fab);
                String tf= findViewById(R.id.found_fab).getTransitionName();


                Pair<View, String> p1 = Pair.create(f, tf);
                Pair<View, String> p2 = Pair.create(s, t);


                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(home.this,p1, p2);
                startActivity(i, transitionActivityOptions.toBundle());
            }
        });

        lost.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(home.this, lost.class);

                View s = findViewById(R.id.lostText);
                String t = findViewById(R.id.lostText).getTransitionName();

                View f = findViewById(R.id.lost_fab);
                String tf= findViewById(R.id.lost_fab).getTransitionName();


                Pair<View, String> p1 = Pair.create(f, tf);
                Pair<View, String> p2 = Pair.create(s, t);


                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(home.this,p1, p2);
                startActivity(i, transitionActivityOptions.toBundle());
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(home.this, settings.class);

                Fade fade = new Fade();
                fade.setDuration(1000);
                getWindow().setExitTransition(fade);

                Bundle b = ActivityOptions.makeSceneTransitionAnimation(home.this).toBundle();
                startActivity(i,b);
            }
        });

    }

}
