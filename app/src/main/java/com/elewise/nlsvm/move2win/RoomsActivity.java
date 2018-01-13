package com.elewise.nlsvm.move2win;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.elewise.nlsvm.move2win.models.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomsActivity extends AppCompatActivity {

    private DatabaseReference instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        instance = FirebaseDatabase.getInstance().getReference().child("android");

        instance.orderByChild("Status").equalTo(Room.Status.Empty.ordinal()).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        long childrenCount = dataSnapshot.getChildrenCount();
                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                        ArrayList<Room> rooms = new ArrayList<>();

                        for(DataSnapshot ds: children){
                            rooms.add(ds.getValue(Room.class));
                        }

                        Log.d("stef", "onDataChange: count "+rooms.size());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

    }

}
