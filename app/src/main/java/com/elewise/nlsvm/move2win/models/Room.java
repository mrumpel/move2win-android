package com.elewise.nlsvm.move2win.models;

import com.google.firebase.database.PropertyName;

/**
 * Created by lucenko on 12.01.2018.
 */

public class Room {

    public enum Status {
        Empty, //только создатель комнаты, ожидающий соперника
        Full, //создатель и соперник
        Start, //гонка началась
        End3, //гонка закончилась
        Break, //гонка отменена
        Processed //ставки обработаны
    }

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

    public boolean isDriversReady(){
        return driver1!=null && driver2!=null && driver2.ready && driver1.ready;
    }
}
