package com.example.cal13.found;

import android.media.Image;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class foundObject
{
    private String title;
    private long epochPosted;
    private Image objectImage;
    private double objectLong;
    private double objectLat;
    private String objectDescription;

    private DatabaseReference database;
    private FirebaseAuth authToken;

    foundObject()
    {

    }
    foundObject(DatabaseReference newDatabase, FirebaseAuth newAuthToken)
    {
        this.title="";
        this.epochPosted = System.currentTimeMillis()/1000;
        this.objectLong = 0;
        this.objectLat = 0;
        this.objectDescription = "";

    }




}
