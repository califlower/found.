package com.example.cal13.found;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

class foundObject
{
    String title;
    long epochPosted;
    String objectImage;
    double objectLong;
    double objectLat;
    String objectDescription;

    DatabaseReference database;
    FirebaseAuth authToken;

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
        this.objectImage = "";

    }




}
