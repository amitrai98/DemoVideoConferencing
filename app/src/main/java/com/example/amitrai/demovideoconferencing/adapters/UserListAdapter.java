package com.example.amitrai.demovideoconferencing.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amitrai.demovideoconferencing.R;
import com.example.amitrai.demovideoconferencing.listeners.RecyclerviewItemclickListener;
import com.example.amitrai.demovideoconferencing.modal.UserBin;

import java.util.List;

/**
 * Created by amitrai on 10/10/16.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder>{

public List<UserBin> list_users;
private RecyclerviewItemclickListener listener;

    public UserListAdapter(List<UserBin> list_users , RecyclerviewItemclickListener listener){
        this.list_users = list_users;
        this.listener = listener;
    }

    @Override
    public UserListAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.usercell, parent, false);

        return new UserListAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserListAdapter.UserViewHolder holder, int position) {
        holder.txt_username.setText(list_users.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return list_users.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder{
    TextView txt_username;
    public UserViewHolder(View itemView) {
        super(itemView);

        txt_username = (TextView) itemView.findViewById(R.id.txt_username);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)
                    listener.onItemClickListener(getAdapterPosition());
            }
        });
    }
}
}
