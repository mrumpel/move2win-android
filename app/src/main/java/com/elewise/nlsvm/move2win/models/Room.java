package com.elewise.nlsvm.move2win.models;

import com.google.firebase.database.PropertyName;

/**
 * Created by lucenko on 12.01.2018.
 */

public class Room {

    @PropertyName("Driver1")
    public Driver driver1;

    @PropertyName("Driver2")
    public Driver driver2;

    @PropertyName("Start")
    public Position start;

    @PropertyName("Finish")
    public Position finish;

    @PropertyName("Name")
    public String name;

    @PropertyName("Status")
    public int status;

    @PropertyName("Timestamp")
    public long timestamp;

}
