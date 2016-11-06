package com.pentavalue.tomato.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.pentavalue.tomato.R;

/**
 * @author Mahmoud Turki
 */
public class ItemDetailsActivity extends BaseActivity {

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, ItemDetailsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
    }
}
