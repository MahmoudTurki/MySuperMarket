package com.pentavalue.tomato.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.listeneres.OnChangePasswordListener;
import com.pentavalue.tomato.managers.UserManager;
import com.pentavalue.tomato.model.User;
import com.pentavalue.tomato.utils.AlertUtils;
import com.pentavalue.tomato.utils.ValidatorUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener, OnChangePasswordListener {

    @Bind(R.id.titleTxt)
    TextView titleTxt;
    @Bind(R.id.changeTxt)
    TextView changeTxt;
    @Bind(R.id.oldPasswordET)
    EditText oldPasswordET;
    @Bind(R.id.confirmPasswordET)
    EditText confirmPasswordET;
    @Bind(R.id.newPasswordET)
    EditText newPasswordET;

    @Bind(R.id.leftImg)
    ImageView leftImg;

    private String newPassword, oldPassword, confirmPassword;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, ChangePasswordActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        titleTxt.setText(R.string.change_password);
        changeTxt.setOnClickListener(this);
        leftImg.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeTxt:
                getValue();
                if (checkIfValidValues()) {
                    // TODO: call user manager to perform change password
                    showLoadingDialog();
                    UserManager.getInstance(this).performChangePasswordRequest(getUserObj());
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UserManager.getInstance(this).addListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        UserManager.getInstance(this).removeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onSuccess(String obj) {
        hideLoadingDialog();
        finish();
    }

    @Override
    public void onException(Exception ex) {
        hideLoadingDialog();
        AlertUtils.showToast(this, ex.getMessage());
    }

    private void getValue() {
        oldPassword = oldPasswordET.getText().toString();
        newPassword = newPasswordET.getText().toString();
        confirmPassword = confirmPasswordET.getText().toString();
    }

    private User getUserObj() {
        return new User(oldPassword, newPassword, confirmPassword);
    }

    private boolean checkIfValidValues() {
        if (ValidatorUtils.isEmpty(oldPassword)) {
            AlertUtils.setValidationBackground(this, oldPasswordET, R.string.mandatory_field);
            return false;
        } else if (ValidatorUtils.isEmpty(newPassword)) {
            AlertUtils.setValidationBackground(this, newPasswordET, R.string.mandatory_field);
            return false;
        } else if (ValidatorUtils.isEmpty(confirmPassword)) {
            AlertUtils.setValidationBackground(this, confirmPasswordET, R.string.mandatory_field);
            return false;
        } else if (!confirmPassword.equals(newPassword)) {
            AlertUtils.setValidationBackground(this, confirmPasswordET, R.string.password_and_confirm_notEqual);
            return false;
        }
        return true;
    }
}
