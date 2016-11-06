package com.pentavalue.tomato.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.utils.IntentShareUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class ContactUsActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.callTxt)
    TextView callTxt;
    @Bind(R.id.emailTxt)
    TextView emailTxt;
    @Bind(R.id.websiteTxt)
    TextView websiteTxt;
    @Bind(R.id.facebookTxt)
    TextView facebookTxt;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, ContactUsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        callTxt.setOnClickListener(this);
        emailTxt.setOnClickListener(this);
        websiteTxt.setOnClickListener(this);
        facebookTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.callTxt:
                startActivity(IntentShareUtils.openCall("01063878525"));
                break;
            case R.id.emailTxt:
                startActivity(IntentShareUtils.openEmail("info@toamto.com"));
                break;
            case R.id.websiteTxt:
            case R.id.facebookTxt:
                startActivity(IntentShareUtils.openURL("www.google.com"));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
