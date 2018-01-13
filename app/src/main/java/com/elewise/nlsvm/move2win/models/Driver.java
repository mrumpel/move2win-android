package com.elewise.nlsvm.move2win.models;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.List;

/**
 * Created by lucenko on 12.01.2018.
 */

@IgnoreExtraProperties
public class Driver {

    @PropertyName("Track")
    public List<TrackPoint> points;

    @PropertyName("Name")
    public String name;

    @PropertyName("Ready")
    public boolean ready;

}
