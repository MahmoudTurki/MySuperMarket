package com.pentavalue.tomato.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.listeneres.OnLoginListener;
import com.pentavalue.tomato.managers.UserManager;
import com.pentavalue.tomato.model.User;
import com.pentavalue.tomato.utils.AlertUtils;
import com.pentavalue.tomato.utils.ValidatorUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, OnLoginListener {

    @Bind(R.id.usernameET)
    EditText userNameET;

    @Bind(R.id.passwordET)
    EditText passwordET;
    @Bind(R.id.forgotPasswordTxt)
    TextView forgotPasswordTxt;
    @Bind(R.id.loginTxt)
    TextView loginTxt;
    @Bind(R.id.signUpTxt)
    TextView signUpTxt;
    @Bind(R.id.skipLoginTxt)
    TextView skipLoginTxt;

    private String userName, password;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        forgotPasswordTxt.setOnClickListener(this);
        loginTxt.setOnClickListener(this);
        signUpTxt.setOnClickListener(this);
        skipLoginTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgotPasswordTxt:
                startActivity(ForgetPasswordActivity.getActivityIntent(this));
                break;
            case R.id.loginTxt:
                getValue();
                if (checkIfValidValues()) {
                    // TODO: call user manager to perform login
                    showLoadingDialog();
                    UserManager.getInstance(this).performLoginRequest(new User(userName, password));
                }
                break;
            case R.id.signUpTxt:
                startActivity(SignUpActivity.getActivityIntent(this));
                break;
            case R.id.skipLoginTxt:
                startActivity(HomeActivity.getActivityIntent(this));
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
    public void onSuccess(User obj) {
        hideLoadingDialog();
        startActivity(HomeActivity.getActivityIntent(this));
    }

    @Override
    public void onException(Exception ex) {
        hideLoadingDialog();
        AlertUtils.showToast(this, ex.getMessage());
    }

    private void getValue() {
        userName = userNameET.getText().toString();
        password = passwordET.getText().toString();
    }

    private boolean checkIfValidValues() {
        if (ValidatorUtils.isEmpty(userName)) {
            AlertUtils.setValidationBackground(this, userNameET, R.string.mandatory_field);
            return false;
        } else if (ValidatorUtils.isEmpty(password)) {
            AlertUtils.setValidationBackground(this, passwordET, R.string.mandatory_field);
            return false;
        }
        return true;
    }
}
