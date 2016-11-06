package com.pentavalue.tomato.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.adapter.ViewPagerAdapter;
import com.pentavalue.tomato.model.Supermarket;
import com.pentavalue.tomato.storage.IntentConstants;
import com.pentavalue.tomato.ui.fragments.MyOrderFragment;
import com.pentavalue.tomato.ui.fragments.ScanBarcodeFragment;
import com.pentavalue.tomato.ui.fragments.DealsFragment;
import com.pentavalue.tomato.ui.fragments.FavoriteItemsFragment;
import com.pentavalue.tomato.ui.fragments.NewArrivalsFragment;
import com.pentavalue.tomato.ui.fragments.AllItemsFragment;
import com.pentavalue.tomato.ui.fragments.SpecialFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SupermarketSectionsActivity extends BaseActivity implements View.OnClickListener,
        ScanBarcodeFragment.OnFragmentInteractionListener, DealsFragment.OnFragmentInteractionListener,
        FavoriteItemsFragment.OnFragmentInteractionListener, NewArrivalsFragment.OnFragmentInteractionListener,
        MyOrderFragment.OnFragmentInteractionListener, AllItemsFragment.OnFragmentInteractionListener,
        SpecialFragment.OnFragmentInteractionListener {

    @Bind(R.id.fabBtn)
    FloatingActionButton fabBtn;
    @Bind(R.id.floatingBtnRLyt)
    RelativeLayout floatingBtnRLyt;

    @Bind(R.id.profileMenu)
    FloatingActionButton profileMenu;
    @Bind(R.id.contactUsMenu)
    FloatingActionButton contactUsMenu;
    @Bind(R.id.aboutUsMenu)
    FloatingActionButton aboutUsMenu;
    @Bind(R.id.homeMenu)
    FloatingActionButton homeMenu;
    @Bind(R.id.notificationMenu)
    FloatingActionButton notificationMenu;
    @Bind(R.id.shoppingMenu)
    FloatingActionButton shoppingMenu;


    @Bind(R.id.rightImg)
    ImageView rightImg;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.titleTxt)
    TextView titleTxt;

    @Bind(R.id.fpFrameLyt)
    RelativeLayout fpFrameLyt;

    @Bind(R.id.rateLyt)
    LinearLayout rateLyt;

    private int count = 1;
    private Supermarket supermarket = new Supermarket();

    public static Intent getActivityIntent(Context context, Supermarket supermarket) {
        return new Intent(context, SupermarketSectionsActivity.class)
                .putExtra(IntentConstants.SUPERMARKETS, supermarket);
    }

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, SupermarketSectionsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermarket_sections);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        //this.supermarket = (Supermarket) getIntent().getSerializableExtra(IntentConstants.SUPERMARKETS);

        setSupportActionBar(toolbar);
        //titleTxt.setText(supermarket.getName());
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        fabBtn.setOnClickListener(this);
        homeMenu.setOnClickListener(this);
        contactUsMenu.setOnClickListener(this);
        aboutUsMenu.setOnClickListener(this);
        profileMenu.setOnClickListener(this);
        shoppingMenu.setOnClickListener(this);
        notificationMenu.setOnClickListener(this);
        rateLyt.setOnClickListener(this);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(AllItemsFragment.newInstance(), getString(R.string.all_items));
        adapter.addFragment(ScanBarcodeFragment.newInstance(), getString(R.string.scan_barcode));
        adapter.addFragment(SpecialFragment.newInstance(), getString(R.string.specials));
        adapter.addFragment(DealsFragment.newInstance(), getString(R.string.deals));
        adapter.addFragment(NewArrivalsFragment.newInstance(), getString(R.string.new_arrivals));
        adapter.addFragment(FavoriteItemsFragment.newInstance(), getString(R.string.favorites_items));
        adapter.addFragment(MyOrderFragment.newInstance(), getString(R.string.my_orders));

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabBtn:
                count++;
                if (count % 2 == 0) {
                    floatingBtnRLyt.setVisibility(View.VISIBLE);
                    fabBtn.setImageResource(R.drawable.appmenu_close);
                    fpFrameLyt.setBackgroundColor(getResources().getColor(R.color.opacityWhite));
                } else {
                    floatingBtnRLyt.setVisibility(View.GONE);
                    fabBtn.setImageResource(R.drawable.shopping_menu);
                    fpFrameLyt.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
                break;
            case R.id.homeMenu:
                startActivity(HomeActivity.getActivityIntent(this));
                break;
            case R.id.profileMenu:
                startActivity(ProfileActivity.getActivityIntent(this));
                break;
            case R.id.contactUsMenu:
                startActivity(ContactUsActivity.getActivityIntent(this));
                break;
            case R.id.aboutUsMenu:
                startActivity(AboutUsActivity.getActivityIntent(this));
                break;
            case R.id.notificationMenu:
                startActivity(NotificationActivity.getActivityIntent(this));
                break;
            case R.id.shoppingMenu:
                startActivity(BrowseSupermarketsActivity.getActivityIntent(this));
                finish();
                break;
            case R.id.rateLyt:
                showDialog();
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void showDialog() {

        /** Initialize of dialog **/
        final Dialog dialog = new Dialog(this);

        /** remove dialog title **/
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        /** Include dialog.xml file **/
        dialog.setContentView(R.layout.rate_dialog);

        /** set dialog background transparent to use my own view instead **/
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        /** set dialog gravity **/
        dialog.getWindow().setGravity(Gravity.CENTER);

        /** Dismiss dialog on Cancel button pressed **/
        TextView rateTxt = (TextView) dialog.findViewById(R.id.rateTxt);
        rateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });

        /** Dismiss dialog on Cancel button pressed **/
        TextView closeTxt = (TextView) dialog.findViewById(R.id.closeTxt);
        rateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });

        /** Show dialog **/
        dialog.show();
    }
}
