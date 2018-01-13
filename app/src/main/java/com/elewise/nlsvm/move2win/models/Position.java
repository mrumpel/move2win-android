package com.elewise.nlsvm.move2win.models;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by lucenko on 12.01.2018.
 */

@IgnoreExtraProperties
public class Position {

    @PropertyName("X")
    public double lat;

    @PropertyName("Y")
    public double lng;
}
