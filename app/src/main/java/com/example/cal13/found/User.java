package com.example.cal13.found;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class User {

    private String name;
    private String email;
    private String number;
    private int distance;
    private long latitude;
    private long longitude;

    private DatabaseReference database;
    private FirebaseAuth authToken;

    public User()
    {

    }

    User(DatabaseReference newDatabase, FirebaseAuth newAuthToken)
    {
        this.name = "";
        this.email = "";
        this.number = "";
        this.distance = 5;
        this.latitude = 0;
        this.longitude =0;

        database = newDatabase;
        authToken = newAuthToken;
    }

    void setName(String newName)
    {
        name = newName;
    }

    void setEmail(String newEmail)
    {
        email = newEmail;
    }

    void setNumber(String newNumber)
    {
        number = newNumber;
    }

    void setDistance(int newDistance)
    {
        distance = newDistance;
    }

    void setDatabase(DatabaseReference newDatabase)
    {
        database = newDatabase;
    }

    void setAuthToken(FirebaseAuth newAuthToken)
    {
        authToken = newAuthToken;
    }

    String getName()
    {
        return name;
    }

    String getEmail()
    {
        return email;
    }

    int getDistance()
    {
        return distance;
    }

    void setLatitude(long latitude)
    {
        this.latitude = latitude;
    }

    void setLongitude(long longitude)
    {
        this.longitude = longitude;
    }

    long getLatitude()
    {
        return latitude;
    }

    long getLongitude()
    {
        return longitude;
    }

    String getNumber()
    {
        return number;
    }

    boolean writeToDatabase()
    {

        if (authToken.getCurrentUser() == null)
            return false;
        else
        {
            database.child("users").child(authToken.getCurrentUser().getUid()).setValue(this);
            return true;
        }

    }



}

