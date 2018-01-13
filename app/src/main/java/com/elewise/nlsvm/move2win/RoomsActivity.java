package com.elewise.nlsvm.move2win;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RoomsActivity extends AppCompatActivity {

    private FirebaseDatabase instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        instance = FirebaseDatabase.getInstance();

    }

}
