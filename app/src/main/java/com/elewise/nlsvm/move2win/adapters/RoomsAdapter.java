package com.elewise.nlsvm.move2win.adapters;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elewise.nlsvm.move2win.R;
import com.elewise.nlsvm.move2win.databinding.ItemRoomBinding;
import com.elewise.nlsvm.move2win.databinding.vm.RoomVM;
import com.elewise.nlsvm.move2win.models.Room;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

/**
 * Created by lucenko on 13.01.2018.
 */

public class RoomsAdapter extends FirebaseRecyclerAdapter<Room, RoomsAdapter.RoomViewHolder>{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RoomsAdapter(@NonNull FirebaseRecyclerOptions<Room> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RoomViewHolder holder, int position, @NonNull Room model) {
        holder.bind(model);
    }

    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(itemView);
    }

    //<editor-fold desc="View Holder">
    public static class RoomViewHolder extends RecyclerView.ViewHolder{

        private final TextView viewById;

        public RoomViewHolder(View itemView) {
            super(itemView);
            viewById = itemView.findViewById(R.id.room_name);
        }

        public void bind(Room room){
            viewById.setText(room.name);
        }

    }
    //</editor-fold>
}

