package com.pentavalue.tomato.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.pentavalue.tomato.R;
import com.pentavalue.tomato.adapter.NotificationsAdapter;
import com.pentavalue.tomato.data.constants.Params;
import com.pentavalue.tomato.listeneres.OnNotificationListener;
import com.pentavalue.tomato.managers.UserManager;
import com.pentavalue.tomato.model.Notification;
import com.pentavalue.tomato.services.GCMRegistrationIntentService;
import com.pentavalue.tomato.ui.custom.DividerItemDecoration;
import com.pentavalue.tomato.utils.AlertUtils;
import com.pentavalue.tomato.utils.ValidatorUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class NotificationActivity extends BaseActivity implements OnNotificationListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.titleTxt)
    TextView titleTxt;
    @Bind(R.id.leftImg)
    ImageView leftImg;

    @Bind(R.id.noNotificationTxt)
    TextView noNotificationTxt;


    //Creating a broadcast receiver for gcm registration
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, NotificationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        init();
        initPushNotification();
    }

    private void initPushNotification() { //Initializing our broadcast receiver
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            //When the broadcast received, We are sending the broadcast from GCMRegistrationIntentService
            @Override
            public void onReceive(Context context, Intent intent) {
                //If the broadcast has received with success
                //that means device is registered successfully
                if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)) {
                    //Getting the registration token from the intent
                    String token = intent.getStringExtra("token");
                    //Displaying the token as toast
                    AlertUtils.showToast(NotificationActivity.this, "Registration token:" + token);
                    //if the intent is not with success then displaying error messages
                } else if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)) {
                    AlertUtils.showToast(NotificationActivity.this, "GCM registration error!");
                } else {
                    AlertUtils.showToast(NotificationActivity.this, "Error occurred");
                }
            }
        };

        //Checking play service is available or not
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        //if play service is not available
        if (ConnectionResult.SUCCESS != resultCode) {
            //If play service is supported but not installed
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                //Displaying message that play service is not installed
                AlertUtils.showToast(NotificationActivity.this, "Google Play Service is not install/enabled in this device!");
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());

                //If play service is not supported, Displaying an error message
            } else {
                AlertUtils.showToast(NotificationActivity.this, "This device does not support for Google Play Service!");
            }
            //If play service is available
        } else {
            //Starting intent to register device
            Intent itent = new Intent(this, GCMRegistrationIntentService.class);
            startService(itent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * Initialization for all views
     **/
    private void init() {
        ButterKnife.bind(this);
        titleTxt.setText(R.string.notifications);
        leftImg.setVisibility(View.GONE);
        setupRecyclerView(recyclerView, getNotificationsList());
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<?> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(Params.NO_LAYOUT));
        setListItems(recyclerView, list);
    }

    private void setListItems(RecyclerView recyclerView, List<?> list) {
        if (!ValidatorUtils.isEmpty(list)) {
            NotificationsAdapter adapter = new NotificationsAdapter(this, list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private List<Notification> getNotificationsList() {
        List<Notification> list = new ArrayList<Notification>();
        Notification obj = new Notification("1", getString(R.string.test), "22 June, 2015");
        for (int i = 0; i < 50; i++) {
            list.add(obj);
        }
        return list;
    }

    @Override
    public void onResume() {
        super.onResume();
        UserManager.getInstance(this).addListener(this);
        Log.w("MainActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }

    @Override
    public void onPause() {
        super.onPause();
        UserManager.getInstance(this).removeListener(this);
        Log.w("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    @Override
    public void onSuccess(List<Notification> list) {
        hideLoadingDialog();
        handleEmptyList(list);
    }

    @Override
    public void onException(Exception ex) {
        hideLoadingDialog();
        AlertUtils.showToast(this, ex.getMessage());
    }

    private void handleEmptyList(List<Notification> list) {
        if (ValidatorUtils.isEmpty(list)) {
            noNotificationTxt.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noNotificationTxt.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}

