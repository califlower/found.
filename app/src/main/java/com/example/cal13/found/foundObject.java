package com.example.cal13.found;

import android.media.Image;

import com.google.android.gms.location.places.Place;

import java.util.Calendar;

public class foundObject
{
    private String title;
    private Calendar c;
    private Image i;
    private Place p;
    private String desc;

    public foundObject()
    {

    }
    public foundObject(String title, String desc, Place p, Image i, Calendar c)
    {
        this.title=title;
        this.desc=desc;
        this.p=p;
        this.i=i;
        this.c=c;

    }



}
