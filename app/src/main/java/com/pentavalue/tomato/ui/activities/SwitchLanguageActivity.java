package com.pentavalue.tomato.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.utils.LanguageUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class SwitchLanguageActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.englishTxt)
    TextView englishTxt;

    @Bind(R.id.arabicTxt)
    TextView arabicTxt;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, SwitchLanguageActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_language);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        englishTxt.setOnClickListener(this);
        arabicTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.englishTxt:
                switchLanguage(LanguageUtils.LANGUAGE_ENGLISH);
                break;
            case R.id.arabicTxt:
                switchLanguage(LanguageUtils.LANGUAGE_arabic);
                break;
        }
    }

    private void switchLanguage(String languageCode) {
        // Change application language
        LanguageUtils.changeApplicationLanguage(languageCode, this, true);
        startActivity(LoginActivity.getActivityIntent(this));
        finish();
    }
}
