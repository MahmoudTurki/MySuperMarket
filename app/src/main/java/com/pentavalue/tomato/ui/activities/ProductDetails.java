package com.pentavalue.tomato.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.model.Deals;
import com.pentavalue.tomato.storage.IntentConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductDetails extends AppCompatActivity {

    @Bind(R.id.titleTxt)
    TextView titleTxt;
    @Bind(R.id.leftImg)
    ImageView leftImg;

    @Bind(R.id.itemImg)
    ImageView itemImg;
    @Bind(R.id.itemTitleTxt)
    TextView itemTitleTxt;
    @Bind(R.id.itemAmountTxt)
    TextView itemAmountTxt;
    @Bind(R.id.favouriteImg)
    ImageView favouriteImg;

    @Bind(R.id.itemPriceTxt)
    TextView itemPriceTxt;
    @Bind(R.id.itemDescTxt)
    TextView itemDescTxt;

    @Bind(R.id.countTxt)
    TextView countTxt;

    @Bind(R.id.addToCartImg)
    ImageView addToCartImg;

    public static Intent getActivityIntent(Context context, Deals item) {
        return new Intent(context, ProfileActivity.class).putExtra(IntentConstants.PRODUCT_DETAILS, item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
