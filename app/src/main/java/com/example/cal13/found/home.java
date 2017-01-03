package com.example.cal13.found;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class home extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "AnonymousAuth";



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();


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

        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
        signInAnonymously();

    }

    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void signInAnonymously()
    {

        // [START signin_anonymously]
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInAnonymously", task.getException());
                            Toast.makeText(home.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END signin_anonymously]
    }





}
