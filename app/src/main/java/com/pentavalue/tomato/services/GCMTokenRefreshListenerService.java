package com.pentavalue.tomato.services;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;



/**
 * @author Mahmoud Turki
 */
public class GCMTokenRefreshListenerService extends InstanceIDListenerService {

    //If the token is changed registering the device again
    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, GCMRegistrationIntentService.class);
        startService(intent);
    }
}
