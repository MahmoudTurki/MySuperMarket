package com.pentavalue.tomato.ui.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.utils.AlertUtils;
import com.pentavalue.tomato.utils.PermissionUtils;

/**
 * @author Mahmoud Turki
 */
public class MainActivity extends AppCompatActivity implements PermissionUtils.PermissionListener {
    boolean isSelected = false;
    private int count = 1;
    RelativeLayout floatingBtnRLyt;
    Button permissionBtn;
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        final FloatingActionMenu fab = (FloatingActionMenu) findViewById(R.id.fabMenu);
//        final FloatingActionButton fabBtn = (FloatingActionButton) findViewById(R.id.fab);
        permissionBtn = (Button) findViewById(R.id.permissionBtn);
        permissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils.checkPermission(MainActivity.this, permissionBtn,
                        PermissionUtils.PERMISSIONS_CAMERA, MainActivity.this);
            }
        });

        floatingBtnRLyt = (RelativeLayout) findViewById(R.id.floatingBtnRLyt);

        // fab.setBackgroundResource(R.drawable.shopping_menu);
       /* fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count % 2 == 0) {
                    floatingBtnRLyt.setVisibility(View.VISIBLE);
                    fabBtn.setImageResource(R.drawable.appmenu_close);
                } else {

                    floatingBtnRLyt.setVisibility(View.GONE);
                    fabBtn.setImageResource(R.drawable.shopping_menu);
                }

            }
        });*/

    }

    @Override
    public void OnPermissionGranted() {
        AlertUtils.showToast(this, "OnPermissionGranted()");
    }

    @Override
    public void OnPermissionDenied() {
        AlertUtils.showToast(this, "OnPermissionDenied()");
    }

    @Override
    public void OnPermissionGranted(String requiredPermission) {
        AlertUtils.showToast(this, "OnPermissionGranted(): " + requiredPermission);
    }

    @Override
    public void OnPermissionDenied(String requiredPermission) {
        AlertUtils.showToast(this, "OnPermissionDenied(): " + requiredPermission);
    }

    @Override
    public void shouldShowRequestPermissionRationale(final String requiredPermission, final int requestCode) {
        AlertUtils.showToast(this, "shouldShowRequestPermissionRationale(): " + requiredPermission);
        Snackbar.make(permissionBtn, R.string.permission_camera_rationale, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{requiredPermission}, requestCode);
                    }
                })
                .show();
    }

    @Override
    public void requestPermissionDirectly(String requiredPermission, int requestCode) {
        AlertUtils.showToast(this, "requestPermissionDirectly(): " + requiredPermission);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{requiredPermission}, requestCode);
    }


    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == PermissionUtils.REQUEST_CAMERA) {
            // BEGIN_INCLUDE(permission_result)
            // Received permission result for camera permission.
            Log.i(TAG, "Received response for Camera permission request.");

            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission has been granted, preview can be displayed
                Log.i(TAG, "CAMERA permission has now been granted. Showing preview.");
                Snackbar.make(permissionBtn, R.string.permision_available_camera,
                        Snackbar.LENGTH_SHORT).show();
            } else {
                Log.i(TAG, "CAMERA permission was NOT granted.");
                Snackbar.make(permissionBtn, R.string.permissions_not_granted,
                        Snackbar.LENGTH_SHORT).show();

            }
            // END_INCLUDE(permission_result)

        } else if (requestCode == PermissionUtils.REQUEST_CONTACTS) {
            Log.i(TAG, "Received response for contact permissions request.");

            // We have requested multiple permissions for contacts, so all of them need to be
            // checked.
            if (PermissionUtils.verifyPermissions(grantResults, this)) {
                // All required permissions have been granted, display contacts fragment.
                Snackbar.make(permissionBtn, R.string.permision_available_contacts,
                        Snackbar.LENGTH_SHORT)
                        .show();
            } else {
                Log.i(TAG, "Contacts permissions were NOT granted.");
                Snackbar.make(permissionBtn, R.string.permissions_not_granted,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
