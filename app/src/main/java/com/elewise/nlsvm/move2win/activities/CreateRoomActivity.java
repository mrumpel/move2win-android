package com.elewise.nlsvm.move2win.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.elewise.nlsvm.move2win.databinding.ActivityCreateRoomBinding;
import com.elewise.nlsvm.move2win.databinding.vm.CreateRoomHandler;
import com.elewise.nlsvm.move2win.databinding.vm.CreateRoomVM;
import com.elewise.nlsvm.move2win.models.Driver;
import com.elewise.nlsvm.move2win.models.Position;
import com.elewise.nlsvm.move2win.models.Room;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateRoomActivity extends AppCompatActivity implements CreateRoomHandler {

    public static final int RC_GET_START = 0x123;
    public static final int RC_GET_FINISH = 0x124;
    private CreateRoomVM model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new CreateRoomVM();
        
        ActivityCreateRoomBinding inflate = ActivityCreateRoomBinding.inflate(getLayoutInflater());

        inflate.setHandler(this);
        inflate.setModel(model);
        setContentView(inflate.getRoot());
    }

    @Override
    public void onClickCreateRoom(View view) {
        if(model.isValid()){
            Room room = new Room();
            room.name = model.roomName.get();
            room.start = model.start.get();
            room.finish = model.finish.get();
            room.driver1 = new Driver();
            room.driver1.name = model.driverName.get();
            room.status = Room.Status.Empty.ordinal();
            Map<String, Object> map = new HashMap<>();
            map.put(room.name, room);
            FirebaseDatabase.getInstance().getReference().child("android").updateChildren(map);
            finish();
        } else {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickCreateStart(View view) {
        Intent intent = new Intent(this, SelectPositionActivity.class);
        startActivityForResult(intent, RC_GET_START);
    }

    @Override
    public void onClickCreateFinish(View view) {
        Intent intent = new Intent(this, SelectPositionActivity.class);
        startActivityForResult(intent, RC_GET_FINISH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_GET_FINISH && resultCode == RESULT_OK){
            Position position = data.getParcelableExtra(Position.BUNDEL_KEY);
            if(position!=null){
                Log.d("stef", "onActivityResult: finish position: "+position);
                model.finish.set(position);
            }
        }

        if(requestCode == RC_GET_START && resultCode ==  RESULT_OK){
            Position position = data.getParcelableExtra(Position.BUNDEL_KEY);
            if(position!=null){
                Log.d("stef", "onActivityResult: start position: "+position);
                model.start.set(position);
            }
        }
    }
}
