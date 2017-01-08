package com.example.cal13.found;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class User {

    private String name;
    private String email;
    private String number;
    private String location;
    private String distance;

    private DatabaseReference database;
    private FirebaseAuth authToken;

    public User()
    {

    }

    public User(DatabaseReference newDatabase, FirebaseAuth newAuthToken)
    {
        this.name = "";
        this.email = "";
        this.number = "";
        this.location = "";
        this.distance = "";

        database = newDatabase;
        authToken = newAuthToken;
    }

    public void setName(String newName)
    {
        name = newName;
    }

    public void setEmail(String newEmail)
    {
        email = newEmail;
    }

    public void setNumber(String newNumber)
    {
        number = newNumber;
    }

    public void setLocation(String newLocation)
    {
        location = newLocation;
    }

    public void setDistance(String newDistance)
    {
        distance = newDistance;
    }

    public void setDatabase(DatabaseReference newDatabase)
    {
        database = newDatabase;
    }
    public void setAuthToken(FirebaseAuth newAuthToken)
    {
        authToken = newAuthToken;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getDistance()
    {
        return distance;
    }

    public String getLocation()
    {
        return location;
    }

    public String getNumber()
    {
        return number;
    }

    public boolean writeToDatabase()
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

