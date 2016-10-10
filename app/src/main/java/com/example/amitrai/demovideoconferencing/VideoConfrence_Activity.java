package com.example.amitrai.demovideoconferencing;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.amitrai.demovideoconferencing.adapters.ViewerAdapter;
import com.example.amitrai.demovideoconferencing.listeners.RecyclerviewItemclickListener;
import com.example.amitrai.demovideoconferencing.modal.Audiance;
import com.example.amitrai.demovideoconferencing.modal.UserBin;
import com.opentok.android.BaseVideoRenderer;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.amitrai.demovideoconferencing.R.id.layout_selected_user;


public class VideoConfrence_Activity extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks,
        Publisher.PublisherListener,
        Session.SessionListener, RecyclerviewItemclickListener {

    private RelativeLayout layout_selected, layout_me;
    private ImageView img_swipe, img_end_call, img_mute;
    private RecyclerView recycle_viewers = null;

    private ViewerAdapter adapter = null;
    private List<Audiance> list_audiance = new ArrayList<>();
    private String TAG = getClass().getSimpleName();

    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;

    private Session mSession;
    private Publisher mPublisher;

    private ArrayList<Subscriber> mSubscribers = new ArrayList<Subscriber>();
    private HashMap<Stream, Subscriber> mSubscriberStreams = new HashMap<Stream, Subscriber>();

    private RelativeLayout mPublisherViewContainer;

    private RecyclerviewItemclickListener listener = null;

    private UserBin userBin = null;

    public static String CURRENT_TOKEN = "current_token";
    public static String CURRENT_SESSION_ID = "current_session_id";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_view);

        if(getIntent().hasExtra(CURRENT_TOKEN)){
            String token = getIntent().getStringExtra(CURRENT_TOKEN);
            String session_id = getIntent().getStringExtra(CURRENT_SESSION_ID);
            userBin = new UserBin("user", token, session_id);

            init();
        }else
            finish();
    }


    /**
     * initialize view elements
     */
    private void init(){
        layout_selected = (RelativeLayout) findViewById(R.id.layout_selected);
        mPublisherViewContainer = (RelativeLayout) findViewById(R.id.layout_me);
        img_swipe = (ImageView) findViewById(R.id.img_swipe);
        img_end_call = (ImageView) findViewById(R.id.img_end_call);
        img_mute = (ImageView) findViewById(R.id.img_mute);
        recycle_viewers = (RecyclerView) findViewById(R.id.recycle_viewers);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false);
        recycle_viewers.setLayoutManager(manager);

        listener = this;


        img_swipe.setOnClickListener(this);
        img_end_call.setOnClickListener(this);
        img_mute.setOnClickListener(this);
//        layout_me.setOnClickListener(this);
        mPublisherViewContainer.setOnClickListener(this);


//        list_audiance.add(new Audiance("user 1", AppConstants.API_KEY, AppConstants.SESSION_ID, AppConstants.TOKEN_U1));
//        list_audiance.add(new Audiance("user 2", AppConstants.API_KEY, AppConstants.SESSION_ID, AppConstants.TOKEN_U2));
//        list_audiance.add(new Audiance("user 3", AppConstants.API_KEY, AppConstants.SESSION_ID, AppConstants.TOKEN_U3));
//        list_audiance.add(new Audiance("user 4", AppConstants.API_KEY, AppConstants.SESSION_ID, AppConstants.TOKEN_U4));
//        list_audiance.add(new Audiance("user 5", AppConstants.API_KEY, AppConstants.SESSION_ID, AppConstants.TOKEN_U5));


        adapter = new ViewerAdapter(list_audiance, listener);
        recycle_viewers.setAdapter(adapter);


        requestPermissions();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.img_end_call:
                endCall();
                break;

            case R.id.img_mute:
                muteCall();
                break;

            case R.id.img_swipe:
                swipeCamera();
                break;

            case R.id.layout_me:

                break;

            case layout_selected_user:

                break;
        }
    }

    /**
     * disconnects the on going call
     */
    private void endCall(){
        if(mSession != null)
            mSession.disconnect();
        finish();
    }

    /**
     * swipes the camera from front to rare and vice versa
     */
    private void swipeCamera(){
        try {
            if (mPublisher == null) {
                return;
            }
            mPublisher.swapCamera();
        }catch (Exception exp){
            exp.printStackTrace();
        }

    }

    /**
     * mutes the on going call
     */
    private void muteCall(){
        try {
            if (mPublisher == null) {
                return;
            }
            if (mPublisher.getPublishAudio()) {
                img_mute.setImageResource(R.drawable.mic_no);
                mPublisher.setPublishAudio(false);
            } else {
                img_mute.setImageResource(R.drawable.mic);
                mPublisher.setPublishAudio(true);
            }
        }catch (Exception exp){
            exp.printStackTrace();
        }

    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, getString(R.string.rationale_ask_again))
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setPositiveButton(getString(R.string.setting))
                    .setNegativeButton(getString(R.string.cancel), null)
                    .setRequestCode(RC_SETTINGS_SCREEN_PERM)
                    .build()
                    .show();
        }
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = {
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        };
        if (EasyPermissions.hasPermissions(this, perms)) {
            mSession = new Session(VideoConfrence_Activity.this, AppConstants.API_KEY, userBin.getSESSION_ID());
            mSession.setSessionListener(this);
            if(userBin != null && userBin.getTOKEN() != null)
                mSession.connect(userBin.getTOKEN());
            else{
                View view = findViewById(R.id.layout_parent);
                Snackbar snackbar = Snackbar
                        .make(view, "Invalid Token.", Snackbar.LENGTH_LONG);

                snackbar.show();
            }

        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_video_app), RC_VIDEO_APP_PERM, perms);
        }
    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        Log.e(TAG, "onStreamCreated: Own stream " + stream.getStreamId() + " created");
    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        Log.d(TAG, "onStreamDestroyed: Own stream " + stream.getStreamId() + " destroyed");
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        Log.d(TAG, "onError: Error (" + opentokError.getMessage() + ") in publisher");

        Toast.makeText(this, "Session error. See the logcat please.", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onConnected(Session session) {
        Log.d(TAG, "onConnected: Connected to session " + session.getSessionId());

        mPublisher = new Publisher(VideoConfrence_Activity.this, "publisher");

        mPublisher.setPublisherListener(this);
        mPublisher.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);

        mPublisherViewContainer.addView(mPublisher.getView());

        mSession.publish(mPublisher);
    }

    @Override
    public void onDisconnected(Session session) {
        Log.d(TAG, "onDisconnected: disconnected from session " + session.getSessionId());

        mSession = null;

        finish();
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.d(TAG, "onStreamReceived: New stream " + stream.getStreamId() + " in session " + session.getSessionId());

//        if (mSubscribers.size() + 1 > MAX_NUM_SUBSCRIBERS) {
//            Toast.makeText(this, "New subscriber ignored. MAX_NUM_SUBSCRIBERS limit reached.", Toast.LENGTH_LONG).show();
//            return;
//        }

        final Subscriber subscriber = new Subscriber(VideoConfrence_Activity.this, stream);
        mSession.subscribe(subscriber);
        mSubscribers.add(subscriber);
        mSubscriberStreams.put(stream, subscriber);

        list_audiance.add(new Audiance("user 1", subscriber));

        if(list_audiance.size() == 1){
            selectUser(list_audiance.get(0).getSubscriber());
        }

        adapter.notifyDataSetChanged();



//        id = getResources().getIdentifier("toggleAudioSubscriber" + (new Integer(position)).toString(), "id", VideoConfrence_Activity.this.getPackageName());
//        final ToggleButton toggleAudio = (ToggleButton) findViewById(id);
//        toggleAudio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    subscriber.setSubscribeToAudio(true);
//                } else {
//                    subscriber.setSubscribeToAudio(false);
//                }
//            }
//        });
//        toggleAudio.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.d(TAG, "onStreamDropped: Stream " + stream.getStreamId() + " dropped from session " + session.getSessionId());

        Subscriber subscriber = mSubscriberStreams.get(stream);
        if (subscriber == null) {
            return;
        }

        for (int i = 0; i<list_audiance.size(); i++){
            if(list_audiance.get(i).getSubscriber().getStream().getStreamId().equalsIgnoreCase(stream.getStreamId())){
                list_audiance.remove(i);
            }
        }

        adapter.notifyDataSetChanged();

//        int position = mSubscribers.indexOf(subscriber);
//        int id = getResources().getIdentifier("subscriberview" + (new Integer(position)).toString(), "id", VideoConfrence_Activity.this.getPackageName());
//
//        mSubscribers.remove(subscriber);
//        mSubscriberStreams.remove(stream);
//
//        RelativeLayout subscriberViewContainer = (RelativeLayout) findViewById(id);
//        subscriberViewContainer.removeView(subscriber.getView());
//
//        id = getResources().getIdentifier("toggleAudioSubscriber" + (new Integer(position)).toString(), "id", VideoConfrence_Activity.this.getPackageName());
//        final ToggleButton toggleAudio = (ToggleButton) findViewById(id);
//        toggleAudio.setOnCheckedChangeListener(null);
//        toggleAudio.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.d(TAG, "onError: Error (" + opentokError.getMessage() + ") in session " + session.getSessionId());

        Toast.makeText(this, "Session error. See the logcat please.", Toast.LENGTH_LONG).show();
        finish();
    }



    private void disconnectSession() {
        if (mSession == null) {
            return;
        }

        if (list_audiance.size() > 0) {
            for (Subscriber subscriber : mSubscribers) {
                if (subscriber != null) {
                    mSession.unsubscribe(subscriber);
                    subscriber.destroy();
                }
            }
        }

        if (mPublisher != null) {
            mPublisherViewContainer.removeView(mPublisher.getView());
            mSession.unpublish(mPublisher);
            mPublisher.destroy();
            mPublisher = null;
        }
        mSession.disconnect();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

//        mSession.disconnect();
    }

    @Override
    public void onItemClickListener(int position) {
        Log.e(TAG, "item click received at "+position);
        if(list_audiance.get(position).getSubscriber() != null)
            selectUser(list_audiance.get(position).getSubscriber());
    }

    /**
     * selects user to the main view.
     */
    private void selectUser(@NonNull Subscriber subscriber){
        try {
            subscriber.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
            ViewGroup view = (ViewGroup) subscriber.getView().getParent();
            ViewGroup viewGroup = (ViewGroup) layout_selected.getParent();

            if (view != null) {
                ViewGroup parent = (ViewGroup) view.getParent();
                if (parent != null) {
                    parent.removeView(view);
                }
            }
            if(view != null){
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                layout_selected.addView(view);
                layout_selected.setLayoutParams(lp);
                view.setLayoutParams(lp);

            }
        }catch (Exception exp){
            exp.printStackTrace();
        }
    }
}
