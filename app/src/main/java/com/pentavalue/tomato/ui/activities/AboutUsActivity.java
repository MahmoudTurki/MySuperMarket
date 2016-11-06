package com.pentavalue.tomato.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.pentavalue.tomato.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class AboutUsActivity extends BaseActivity {

    @Bind(R.id.visionTxt)
    TextView visionTxt;
    @Bind(R.id.goalsTxt)
    TextView goalsTxt;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, AboutUsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
