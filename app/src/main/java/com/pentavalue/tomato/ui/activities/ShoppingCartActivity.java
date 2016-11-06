package com.pentavalue.tomato.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pentavalue.tomato.R;

import butterknife.ButterKnife;

public class ShoppingCartActivity extends AppCompatActivity {

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, ShoppingCartActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void init() {
        ButterKnife.unbind(this);
    }
}
