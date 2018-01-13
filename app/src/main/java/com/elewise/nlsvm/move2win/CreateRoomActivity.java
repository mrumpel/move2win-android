package com.elewise.nlsvm.move2win;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.elewise.nlsvm.move2win.databinding.ActivityCreateRoomBinding;
import com.elewise.nlsvm.move2win.databinding.vm.CreateRoomHandler;
import com.elewise.nlsvm.move2win.databinding.vm.CreateRoomVM;

public class CreateRoomActivity extends AppCompatActivity implements CreateRoomHandler {

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
        Toast.makeText(this, "Create clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickCreateStart(View view) {

    }

    @Override
    public void onClickCreateFinish(View view) {

    }
}
