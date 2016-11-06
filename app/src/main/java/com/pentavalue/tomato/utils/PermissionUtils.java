package com.pentavalue.tomato.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.pentavalue.tomato.R;

/**
 * Utility class that wraps access to the runtime permissions API in M and provides basic helper
 * methods.
 *
 * @author Mahmoud Turki
 */

public class PermissionUtils {

    public static final String TAG = "PermissionUtils";

    /**
     * Id to identify a camera permission request.
     */
    public static final int REQUEST_CAMERA = 0;

    /**
     * Id to identify a contacts permission request.
     */
    public static final int REQUEST_CONTACTS = 1;

    /**
     * Permissions required to access camera. Used by the {@link BaseActivity}.
     */
    public static String PERMISSIONS_CAMERA = Manifest.permission.CAMERA;

    /**
     * Permissions required to read and write contacts. Used by the {@link BaseActivity}.
     */
    public static String[] PERMISSIONS_CONTACT = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS};
    /**
     * Permissions required to perform calls. Used by the {@link BaseActivity}.
     */
    public static String[] PERMISSIONS_CALL = {Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_PHONE_STATE};
    /**
     * Permissions required to perform internet. Used by the {@link BaseActivity}.
     */
    private static String[] PERMISSIONS_INTERNET = {Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE};

    public interface PermissionListener {
        public void OnPermissionGranted();

        public void OnPermissionDenied();

        public void shouldShowRequestPermissionRationale(String requiredPermission, int requestCode);

        public void requestPermissionDirectly(String requiredPermission, int requestCode);

        public void OnPermissionGranted(String requiredPermission);

        public void OnPermissionDenied(String requiredPermission);
    }

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     *
     * @see Activity#onRequestPermissionsResult(int, String[], int[])
     */
    public static boolean verifyPermissions(int[] grantResults, PermissionListener listenere) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Called when the 'show camera' button is clicked.
     * Callback is defined in resource layout definition.
     */
    public static void checkPermission(Activity activity, View view, String requiredPermission,
                                       PermissionListener listenere) {
        Log.i(TAG, "Show camera button pressed. Checking permission.");
        // BEGIN_INCLUDE(required_permission)
        // Check if the required permission is already available.
        if (ActivityCompat.checkSelfPermission(activity, requiredPermission) != PackageManager.PERMISSION_GRANTED) {
            // Required permission has not been granted.
            requestRequiredPermission(activity, view, requiredPermission, listenere);
        } else {
            // Required permissions is already available, show the required preview.
            Log.i(TAG, "CAMERA permission has already been granted. Displaying camera preview.");
//            showCameraPreview();
            listenere.OnPermissionGranted();

        }
        // END_INCLUDE(required_permission)
    }

    /**
     * Requests the required permission.
     * If the permission has been denied previously, a SnackBar will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private static void requestRequiredPermission(final Activity activity, View view,
                                                  final String requiredPermission, final PermissionListener listenere) {
        Log.i(TAG, "Required permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(required_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requiredPermission)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Log.i(TAG, "Displaying required permission rationale to provide additional context.");
            Snackbar.make(view, R.string.permission_camera_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(activity, new String[]{requiredPermission}, REQUEST_CAMERA);
                            listenere.OnPermissionGranted();
                        }
                    })
                    .show();
            listenere.shouldShowRequestPermissionRationale(requiredPermission, REQUEST_CAMERA);
        } else {
            // Required permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(activity, new String[]{requiredPermission}, REQUEST_CAMERA);
            listenere.OnPermissionDenied();
            listenere.requestPermissionDirectly(requiredPermission, REQUEST_CAMERA);
        }
        // END_INCLUDE(required_permission_request)
    }
}
