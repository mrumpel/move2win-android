package com.elewise.nlsvm.move2win.databinding.vm;

import android.databinding.ObservableField;

import com.elewise.nlsvm.move2win.models.Room;

/**
 * Created by lucenko on 13.01.2018.
 */

public class RoomVM {

    public ObservableField<String> name = new ObservableField<>("");


    public RoomVM(Room room) {
        this.name.set(room.name);
    }

    public void update(Room room){
        this.name.set(room.name);
    }
}
