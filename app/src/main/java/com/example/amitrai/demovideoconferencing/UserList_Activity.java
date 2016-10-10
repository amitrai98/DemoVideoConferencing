package com.example.amitrai.demovideoconferencing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.amitrai.demovideoconferencing.adapters.UserListAdapter;
import com.example.amitrai.demovideoconferencing.listeners.RecyclerviewItemclickListener;
import com.example.amitrai.demovideoconferencing.modal.UserBin;

import java.util.ArrayList;
import java.util.List;

public class UserList_Activity extends AppCompatActivity implements RecyclerviewItemclickListener{

    private RecyclerView recycler_users = null;
    private List<UserBin> list_users = new ArrayList<>();
    private UserListAdapter adapter = null;
    private RecyclerviewItemclickListener listener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        init();
    }

    /**
     * initialzing view elements
     */
    private void init(){
        recycler_users = (RecyclerView) findViewById(R.id.recycler_users);
        list_users.add(new UserBin(" user 1", AppConstants.TOKEN, AppConstants.SESSION_ID));
        list_users.add(new UserBin(" user 2", AppConstants.TOKEN_U2, AppConstants.SESSION_ID_U2));
        list_users.add(new UserBin(" user 3", AppConstants.TOKEN_U3, AppConstants.SESSION_ID_U3));
        list_users.add(new UserBin(" user 4", AppConstants.TOKEN_U4, AppConstants.SESSION_ID_U4));
        list_users.add(new UserBin(" user 5", AppConstants.TOKEN_U5, AppConstants.SESSION_ID_U5));

        listener = this;
        adapter = new UserListAdapter(list_users, listener);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        recycler_users.setLayoutManager(manager);
        recycler_users.setAdapter(adapter);
    }

    @Override
    public void onItemClickListener(int position) {
        Intent i = new Intent(this, VideoConfrence_Activity.class);
        i.putExtra(VideoConfrence_Activity.CURRENT_TOKEN, list_users.get(position).getTOKEN());
        i.putExtra(VideoConfrence_Activity.CURRENT_SESSION_ID, list_users.get(position).getSESSION_ID());
        startActivity(i);
    }
}
