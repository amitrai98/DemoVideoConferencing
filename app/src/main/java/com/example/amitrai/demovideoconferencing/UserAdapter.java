package com.example.amitrai.demovideoconferencing;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amitrai.demovideoconferencing.modal.UserBin;

import java.util.List;

/**
 * Created by amitrai on 29/9/16.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserCell>{

    private List<UserBin> list_userbin;
    private String TAG = getClass().getSimpleName();

    public class UserCell extends RecyclerView.ViewHolder{
        public TextView txt_username;


        public UserCell(View itemView) {
            super(itemView);

            txt_username = (TextView) itemView.findViewById(R.id.txt_username);
        }
    }


    public UserAdapter(List<UserBin> list_userbin){
        this.list_userbin = list_userbin;
    }


    @Override
    public UserCell onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_cell, parent, false);
        return new UserCell(view);
    }

    @Override
    public void onBindViewHolder(final UserAdapter.UserCell holder, int position) {
        UserBin userBin = list_userbin.get(position);

        if(userBin.getUsername() != null && !userBin.getUsername().isEmpty())
            holder.txt_username.setText(userBin.getUsername());


        holder.txt_username.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e(TAG, "user name clicked"+holder.txt_username.getText());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_userbin.size();
    }



}
