package com.pentavalue.tomato.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.model.Supermarket;
import com.pentavalue.tomato.utils.LanguageUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.shoppingLyt)
    LinearLayout shoppingLyt;
    @Bind(R.id.profileLyt)
    LinearLayout profileLyt;
    @Bind(R.id.contactUsLyt)
    LinearLayout contactUsLyt;
    @Bind(R.id.aboutUsLyt)
    LinearLayout aboutUsLyt;
    @Bind(R.id.rightImg)
    ImageView rightImg;


    private Supermarket supermarket = new Supermarket();


    public static Intent getActivityIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        shoppingLyt.setOnClickListener(this);
        profileLyt.setOnClickListener(this);
        contactUsLyt.setOnClickListener(this);
        aboutUsLyt.setOnClickListener(this);
        rightImg.setOnClickListener(this);

        if (LanguageUtils.getInstance().getLanguage().equals(LanguageUtils.LANGUAGE_ENGLISH))
            rightImg.setImageResource(R.drawable.ar);
        else
            rightImg.setImageResource(R.drawable.eng);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shoppingLyt:
                startActivity(BrowseSupermarketsActivity.getActivityIntent(this));
                break;
            case R.id.profileLyt:
                startActivity(ProfileActivity.getActivityIntent(this));
                break;
            case R.id.contactUsLyt:
                startActivity(ContactUsActivity.getActivityIntent(this));
                break;
            case R.id.aboutUsLyt:
                startActivity(AboutUsActivity.getActivityIntent(this));
                break;
            case R.id.rightImg:
                String languageCode = LanguageUtils.LANGUAGE_ENGLISH;
                if (LanguageUtils.getInstance().getLanguage().equals(LanguageUtils.LANGUAGE_ENGLISH)) {
                    rightImg.setImageResource(R.drawable.ar);
                    languageCode = LanguageUtils.LANGUAGE_arabic;
                } else {
                    rightImg.setImageResource(R.drawable.eng);
                }

                // Change application language
                LanguageUtils.changeApplicationLanguage(languageCode, this, true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
