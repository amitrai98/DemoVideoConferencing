package com.example.amitrai.demovideoconferencing.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.amitrai.demovideoconferencing.R;
import com.example.amitrai.demovideoconferencing.modal.Audiance;

import java.util.List;

/**
 * Created by amitrai on 4/10/16.
 */

public class ViewerAdapter extends RecyclerView.Adapter<ViewerAdapter.AudianceViewHolder>{

    public List<Audiance> list_audiance;

    public ViewerAdapter(List<Audiance> list_audiance){
        this.list_audiance = list_audiance;
    }

    @Override
    public AudianceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.user_cell, parent, false);

        return new AudianceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AudianceViewHolder holder, int position) {
        Audiance audiance = list_audiance.get(position);

    }

    @Override
    public int getItemCount() {
        return list_audiance.size();
    }

    public class AudianceViewHolder extends RecyclerView.ViewHolder{
        TextView txt_username;
        FrameLayout layout_user;
        public AudianceViewHolder(View itemView) {
            super(itemView);

            layout_user = (FrameLayout) itemView.findViewById(R.id.layout_user);
            txt_username = (TextView) itemView.findViewById(R.id.txt_username);
        }
    }
}
