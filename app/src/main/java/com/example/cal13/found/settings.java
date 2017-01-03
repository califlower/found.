package com.example.cal13.found;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class settings extends AppCompatActivity
{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupWindowAnimations();

        final EditText name= (EditText) findViewById(R.id.setting_name);
        final EditText email= (EditText) findViewById(R.id.setting_email);
        final EditText number = (EditText) findViewById(R.id.setting_number);



        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();



        TextWatcher t= new TextWatcher()
        {
            int c=0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (c>2)
                {

                    Button b = (Button) findViewById(R.id.save);
                    b.setVisibility(View.VISIBLE);
                }
                else
                    c++;

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        name.addTextChangedListener(t);
        email.addTextChangedListener(t);
        number.addTextChangedListener(t);


        /*
            Saves data to FIREBASE database if possible. Displays toast with status
         */
        final Button b = (Button) findViewById(R.id.save);
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                View f = settings.this.getCurrentFocus();

                //Hides keyboard so that it doesn't block things when saving

                if (f != null)
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
                }

                //Makes sure data is able to be saved

                if (mAuth!=null && mAuth.getCurrentUser()!=null)
                {
                    writeNewUser(
                            mAuth.getCurrentUser().getUid(),
                            name.getText().toString(),
                            email.getText().toString(),
                            number.getText().toString(),
                            "",
                            "");
                    Context context = getApplicationContext();
                    CharSequence text = "Data saved successfully";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    b.setVisibility(View.GONE);

                }
                else
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Data could not be saved";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }


            }
        });

        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                User u = dataSnapshot.getValue(User.class);

                if (u!=null)
                {
                    if (name.getText().toString().compareTo(u.name)!=0)
                        name.setText(u.name);

                    if (email.getText().toString().compareTo(u.email)!=0)
                        email.setText(u.email);

                    if (number.getText().toString().compareTo(u.number)!=0)
                        number.setText(u.number);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });


    }


    private void setupWindowAnimations()
    {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        Slide slide = new Slide();
        slide.setDuration(500);
        slide.setSlideEdge(Gravity.TOP);
        getWindow().setEnterTransition(slide);

    }

    private void writeNewUser(String userId, String name, String email, String number, String location, String distance)
    {
        User user = new User(name, email, number, location, distance);
        mDatabase.child("users").child(userId).setValue(user);
    }
}
