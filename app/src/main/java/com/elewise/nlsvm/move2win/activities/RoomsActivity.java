package com.elewise.nlsvm.move2win.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.elewise.nlsvm.move2win.R;
import com.elewise.nlsvm.move2win.adapters.RoomsAdapter;
import com.elewise.nlsvm.move2win.models.Room;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class RoomsActivity extends AppCompatActivity implements RoomsAdapter.ItemClickListener {

    private RoomsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        RecyclerView view = findViewById(R.id.rooms_recycler);

        findViewById(R.id.fab).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(RoomsActivity.this, CreateRoomActivity.class));
                    }
                }
        );

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("android")
                .orderByChild("Status")
                .equalTo(Room.Status.Empty.ordinal());

        FirebaseRecyclerOptions<Room> options = new FirebaseRecyclerOptions.Builder<Room>()
                        .setQuery(query, Room.class)
                        .build();

        adapter = new RoomsAdapter(options);

        adapter.setOnClickListener(this);

        LinearLayoutManager layout = new LinearLayoutManager(this);

        view.setLayoutManager(layout);

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

    @Override
    public void onItemClick(Room room) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("room_name", room.name);
        startActivity(intent);
    }
}
