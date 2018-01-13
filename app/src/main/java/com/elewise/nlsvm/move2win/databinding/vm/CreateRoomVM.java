package com.elewise.nlsvm.move2win.databinding.vm;

import android.databinding.Observable;
import android.databinding.ObservableField;

import com.elewise.nlsvm.move2win.models.Position;

import java.util.Locale;

/**
 * Created by lucenko on 13.01.2018.
 */

public class CreateRoomVM {

    public ObservableField<String> roomName = new ObservableField<>("");

    public ObservableField<String> driverName = new ObservableField<>("");

    public ObservableField<Position> start = new ObservableField<>();

    public ObservableField<Position> finish = new ObservableField<>();

    public ObservableField<String> startStr = new ObservableField<>("");

    public ObservableField<String> finishStr = new ObservableField<>("");

    public CreateRoomVM() {
        
        start.addOnPropertyChangedCallback(
                new Observable.OnPropertyChangedCallback() {
                    @Override
                    public void onPropertyChanged(Observable observable, int i) {
                        Position point = ((ObservableField<Position>) observable).get();

                        if(point!=null) {
                            String format = String.format(Locale.ENGLISH,"широта: %f, долгота: %f", point.lat, point.lng);
                            startStr.set(format);
                        } else {
                            startStr.set("");
                        }
                    }
                }
        );

        finish.addOnPropertyChangedCallback(
                new Observable.OnPropertyChangedCallback() {
                    @Override
                    public void onPropertyChanged(Observable observable, int i) {
                        Position point = ((ObservableField<Position>) observable).get();

                        if(point!=null) {
                            String format = String.format(Locale.ENGLISH,"широта: %f, долгота: %f", point.lat, point.lng);
                            finishStr.set(format);
                        } else {
                            finishStr.set("");
                        }
                    }
                }
        );
    }

    public boolean isValid() {
        return start.get()!=null &&
                finish.get()!=null &&
                !roomName.get().trim().isEmpty() &&
                !driverName.get().trim().isEmpty();
    }
}
