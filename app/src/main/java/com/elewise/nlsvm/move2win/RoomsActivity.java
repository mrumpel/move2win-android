package com.elewise.nlsvm.move2win;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.elewise.nlsvm.move2win.adapters.RoomsAdapter;
import com.elewise.nlsvm.move2win.models.Room;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class RoomsActivity extends AppCompatActivity {

    private DatabaseReference instance;
    private RoomsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        RecyclerView view = findViewById(R.id.rooms_recycler);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("android")
                .orderByChild("Status")
                .equalTo(Room.Status.Empty.ordinal());

        FirebaseRecyclerOptions<Room> options = new FirebaseRecyclerOptions.Builder<Room>()
                        .setQuery(query, Room.class)
                        .build();

        adapter = new RoomsAdapter(options);

        view.setLayoutManager(new LinearLayoutManager(this));

        view.setAdapter(adapter);

        adapter.startListening();

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.startListening();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.stopListening();
    }
}
