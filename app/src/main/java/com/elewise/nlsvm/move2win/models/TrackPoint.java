package com.elewise.nlsvm.move2win.models;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by lucenko on 12.01.2018.
 */

@IgnoreExtraProperties
public class TrackPoint {

    @PropertyName("Point")
    public Position position;

    @PropertyName("Timestamp")
    public long timestamp;

    public TrackPoint() {
    }

    public TrackPoint(Position position, long timestamp) {
        this.position = position;
        this.timestamp = timestamp;
    }
}
