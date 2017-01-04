package com.example.cal13.found;

import android.content.Context;
import android.content.Intent;
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

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class settings extends AppCompatActivity
{

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        setupWindowAnimations();

        final EditText name= (EditText) findViewById(R.id.setting_name);
        final EditText email= (EditText) findViewById(R.id.setting_email);
        final EditText number = (EditText) findViewById(R.id.setting_number);
        final EditText location = (EditText) findViewById(R.id.setting_location);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        TextWatcher t= new TextWatcher()
        {
            int c=0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

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
            public void afterTextChanged(Editable s)
            {

            }
        };

        name.addTextChangedListener(t);
        email.addTextChangedListener(t);
        number.addTextChangedListener(t);


        /*
            Saves data to FIREBASE database if possible. Displays toast with status
         */
        final Button s = (Button) findViewById(R.id.save);
        s.setOnClickListener(new View.OnClickListener()
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

                    s.setVisibility(View.GONE);

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

        Button b = (Button) findViewById(R.id.back);
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        location.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try
                {
                    startActivityForResult(builder.build(settings.this), PLACE_PICKER_REQUEST);


                }
                catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e)
                {
                    e.printStackTrace();
                }
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PLACE_PICKER_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {
                Place place = PlacePicker.getPlace(getApplicationContext(),data);
                EditText location = (EditText) findViewById(R.id.setting_location);
                location.setText(place.getAddress());
            }
        }
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
