package com.pentavalue.tomato.utils;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pentavalue.tomato.R;

/**
 * @author Mahmoud Turki
 */
public class AlertUtils {

    /**
     * Static method to display error whenever it happened to user as AlertDialog
     *
     * @param context
     * @param errorMessage
     */
    public static void showError(Context context, String errorMessage) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.error)
                .setMessage(errorMessage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO excuate action you wanna
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Static method to display messages as toast to user
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackbar(Context context, String message, View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private static Drawable getValidationIcon(Context context) {
        Drawable icon = context.getResources().getDrawable(R.drawable.mandatory_field);
        if (icon != null) {
            icon.setBounds(0, 0,
                    icon.getIntrinsicWidth(),
                    icon.getIntrinsicHeight());
        }
        return icon;
    }

    public static void setValidationBackground(Context context, EditText editText, int message) {
//        editText.setBackground(context.getResources().getDrawable(R.drawable.line_yellow));
        editText.setError(context.getString(message), getValidationIcon(context));
    }

    public static void setValidationBackground(Context context, TextView textView, int message) {
//        textView.setBackground(context.getResources().getDrawable(R.drawable.line_yellow));
        textView.setError(context.getString(message), getValidationIcon(context));
    }

}
