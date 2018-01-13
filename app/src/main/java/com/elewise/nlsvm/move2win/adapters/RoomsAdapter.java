package com.elewise.nlsvm.move2win.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elewise.nlsvm.move2win.R;
import com.elewise.nlsvm.move2win.models.Room;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

/**
 * Created by lucenko on 13.01.2018.
 */

public class RoomsAdapter extends FirebaseRecyclerAdapter<Room, RoomsAdapter.RoomViewHolder>{

    private RoomViewHolder roomViewHolder;
    private ItemClickListener listener;

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
        roomViewHolder = new RoomViewHolder(itemView);

        itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(roomViewHolder.getModel());
                    }
                }
        );

        return roomViewHolder;
    }

    public void setOnClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    public interface ItemClickListener{
        void onItemClick(Room room);
    }

    //<editor-fold desc="View Holder">
    public static class RoomViewHolder extends RecyclerView.ViewHolder{

        private final TextView roomName;
        private final TextView driverName;
        private Room model;

        public RoomViewHolder(View itemView) {
            super(itemView);
            roomName = itemView.findViewById(R.id.room_name);
            driverName = itemView.findViewById(R.id.driver_name);
        }

        public void bind(Room room){
            driverName.setText(room.driver1.name);
            roomName.setText(room.name);
            this.model = room;
        }

        public Room getModel(){
            return model;
        }
    }
    //</editor-fold>
}

