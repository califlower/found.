package com.example.cal13.found;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kishan.askpermission.AskPermission;
import com.kishan.askpermission.PermissionCallback;

public class home extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "AnonymousAuth";
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        new AskPermission.Builder(this)
                .setPermissions(android.Manifest.permission.GET_ACCOUNTS, Manifest.permission.INTERNET)
                .setCallback(new PermissionCallback()
                {
                    @Override
                    public void onPermissionsGranted(int requestCode)
                    {

                    }

                    @Override
                    public void onPermissionsDenied(int requestCode)
                    {

                    }
                })
                .request(20);


        mAuth = FirebaseAuth.getInstance();


        FloatingActionButton found = (FloatingActionButton) findViewById(R.id.found_fab);
        FloatingActionButton lost = (FloatingActionButton) findViewById(R.id.lost_fab);

        Button settings = (Button) findViewById(R.id.settings);

        found.setOnClickListener(foundListener());

        lost.setOnClickListener(foundListener());

        settings.setOnClickListener(settingsListener());

        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        signInAnonymously();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null)
        {
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
    private View.OnClickListener foundListener()
    {
        View.OnClickListener v = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(home.this, found.class);
                View s = findViewById(R.id.foundText);
                String t = findViewById(R.id.foundText).getTransitionName();

                View f = findViewById(R.id.found_fab);
                String tf = findViewById(R.id.found_fab).getTransitionName();


                Pair<View, String> p1 = Pair.create(f, tf);
                Pair<View, String> p2 = Pair.create(s, t);


                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(home.this, p1, p2);
                startActivity(i, transitionActivityOptions.toBundle());
            }
        };

        return v;
    }
    private View.OnClickListener lostListener()
    {
        View.OnClickListener v = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(home.this, lost.class);

                View s = findViewById(R.id.lostText);
                String t = findViewById(R.id.lostText).getTransitionName();

                View f = findViewById(R.id.lost_fab);
                String tf = findViewById(R.id.lost_fab).getTransitionName();


                Pair<View, String> p1 = Pair.create(f, tf);
                Pair<View, String> p2 = Pair.create(s, t);


                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(home.this, p1, p2);
                startActivity(i, transitionActivityOptions.toBundle());
            }
        };

        return v;
    }
    private View.OnClickListener settingsListener()
    {
        View.OnClickListener v = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(home.this, settings.class);
                Bundle b = ActivityOptionsCompat.makeSceneTransitionAnimation(home.this).toBundle();
                startActivity(i, b);
            }
        };
        return v;
    }

}
