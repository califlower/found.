package com.example.cal13.found;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
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

import java.io.IOException;
import java.util.Locale;

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

        final EditText name = (EditText) findViewById(R.id.setting_name);
        final EditText email = (EditText) findViewById(R.id.setting_email);
        final EditText number = (EditText) findViewById(R.id.setting_number);
        final EditText location = (EditText) findViewById(R.id.setting_location);
        final Button b = (Button) findViewById(R.id.back);
        final Button saveButton = (Button) findViewById(R.id.save);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        name.addTextChangedListener(saveVisibility(saveButton));
        email.addTextChangedListener(saveVisibility(saveButton));
        number.addTextChangedListener(saveVisibility(saveButton));


        /*
            Saves data to FIREBASE database if possible. Displays toast with status
         */

        saveButton.setOnClickListener(saveButtonListener(saveButton, name, email, number));
        location.setOnClickListener(locationListener(location));


        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(databaseListener(name, email,number, location));
        b.setOnClickListener(backListener());

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PLACE_PICKER_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {
                Place place = PlacePicker.getPlace(getApplicationContext(), data);
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

    private TextWatcher saveVisibility(final Button save)
    {
        TextWatcher t = new TextWatcher()
        {
            int c = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (c > 2)
                {
                    save.setVisibility(View.VISIBLE);
                } else
                    c++;

            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        };

        return t;
    }

    private View.OnClickListener saveButtonListener(final Button save, final EditText name, final EditText email, final EditText number)
    {
        View.OnClickListener v = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                View f = settings.this.getCurrentFocus();

                //Hides keyboard so that it doesn't block things when saving

                if (f != null)
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
                }

                //Makes sure data is able to be saved

                if (mAuth != null && mAuth.getCurrentUser() != null)
                {
                    User u = new User(mDatabase, mAuth);
                    u.setName(name.getText().toString());
                    u.setEmail(email.getText().toString());
                    u.setNumber(number.getText().toString());

                    u.writeToDatabase();

                    Context context = getApplicationContext();
                    CharSequence text = "Data saved successfully";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    save.setVisibility(View.GONE);

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

        };

        return v;
    }

    private View.OnClickListener locationListener(final EditText location)
    {
        View.OnClickListener v = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try
                {
                    startActivityForResult(builder.build(settings.this), PLACE_PICKER_REQUEST);


                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e)
                {
                    e.printStackTrace();
                }
            }
        };

        return v;
    }
    private ValueEventListener databaseListener(final EditText name, final EditText email, final EditText number, final EditText location)
    {
        ValueEventListener v = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                User u = dataSnapshot.getValue(User.class);

                if (u != null)
                {
                    if (name.getText().toString().compareTo(u.getName()) != 0)
                        name.setText(u.getName());

                    if (email.getText().toString().compareTo(u.getEmail()) != 0)
                        email.setText(u.getEmail());


                    if (number.getText().toString().compareTo(u.getNumber()) != 0)
                        number.setText(u.getNumber());

                    Geocoder g = new Geocoder(getApplicationContext(), Locale.getDefault());

                    try
                    {
                        if (g.getFromLocation(u.getLatitude(),u.getLongitude(),1).isEmpty())
                            location.setText("Your Location");

                        else if (location.getText().toString().compareTo(g.getFromLocation(u.getLatitude(),u.getLongitude(),1).get(0).getPostalCode())!=0)
                            location.setText(g.getFromLocation(u.getLatitude(),u.getLongitude(),1).get(0).getPostalCode());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                        location.setText("Location not found");
                    }



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }

        };

        return v;
    }

    private View.OnClickListener backListener()
    {
        View.OnClickListener v = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        };

        return v;
    }

}
