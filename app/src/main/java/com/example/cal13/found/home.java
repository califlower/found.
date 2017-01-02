package com.example.cal13.found;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static android.R.attr.transitionName;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FloatingActionButton found = (FloatingActionButton) findViewById(R.id.found_fab);

        found.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(home.this, found.class);
                View s = findViewById(R.id.homeBottomText);
                String t = findViewById(R.id.homeBottomText).getTransitionName();

                View f = findViewById(R.id.found_fab);
                String tf= findViewById(R.id.found_fab).getTransitionName();


                Pair<View, String> p1 = Pair.create(f, tf);
                Pair<View, String> p2 = Pair.create(s, t);


                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(home.this,p1, p2);
                startActivity(i, transitionActivityOptions.toBundle());
            }
        });

    }

}
