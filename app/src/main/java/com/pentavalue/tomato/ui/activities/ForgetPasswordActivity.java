package com.pentavalue.tomato.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.listeneres.OnForgotPasswordListener;
import com.pentavalue.tomato.managers.UserManager;
import com.pentavalue.tomato.utils.AlertUtils;
import com.pentavalue.tomato.utils.ValidatorUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener, OnForgotPasswordListener {

    @Bind(R.id.emailET)
    EditText emailET;
    @Bind(R.id.generateTxt)
    TextView generateTxt;

    private String email;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, ForgetPasswordActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        generateTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginTxt:
                getValue();
                if (checkIfValidValues()) {
                    // TODO: call user manager to perform generate password
                    UserManager.getInstance(this).performForgotPasswordRequest();
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
    public void onSuccess() {
        hideLoadingDialog();
    }

    @Override
    public void onException(Exception ex) {
        hideLoadingDialog();
        AlertUtils.showToast(this, ex.getMessage());
    }

    private void getValue() {
        email = emailET.getText().toString();
    }

    private boolean checkIfValidValues() {
        if (ValidatorUtils.isEmpty(email)) {
            AlertUtils.showError(this, getString(R.string.enter_email));
            return false;
        }
        return true;
    }
}
