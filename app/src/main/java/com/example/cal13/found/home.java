package com.example.cal13.found;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(home.this,s , t);
                startActivity(i, transitionActivityOptions.toBundle());
            }
        });

    }

}
