package com.pentavalue.tomato.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.adapter.LocationsAdapter;
import com.pentavalue.tomato.data.constants.Params;
import com.pentavalue.tomato.listeneres.OnBrowseSupermarketsListener;
import com.pentavalue.tomato.listeneres.OnLocationsListener;
import com.pentavalue.tomato.managers.UserManager;
import com.pentavalue.tomato.model.Area;
import com.pentavalue.tomato.model.Location;
import com.pentavalue.tomato.storage.IntentConstants;
import com.pentavalue.tomato.ui.custom.DividerItemDecoration;
import com.pentavalue.tomato.utils.AlertUtils;
import com.pentavalue.tomato.utils.ValidatorUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class BrowseSupermarketsActivity extends BaseActivity implements View.OnClickListener, OnLocationsListener, LocationsAdapter.OnCitySelectListener, OnBrowseSupermarketsListener {

    @Bind(R.id.leftImg)
    ImageView leftImg;

    @Bind(R.id.cityTxt)
    TextView cityTxt;
    @Bind(R.id.areaTxt)
    TextView areaTxt;

    @Bind(R.id.fabBtn)
    FloatingActionButton fabBtn;
    @Bind(R.id.floatingBtnRLyt)
    RelativeLayout floatingBtnRLyt;
    @Bind(R.id.showSupermarketsTxt)
    TextView showSupermarketsTxt;

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

    @Bind(R.id.fpFrameLyt)
    FrameLayout fpFrameLyt;


    private int count = 1;
    private List<Location> locationList = new ArrayList<Location>();
    private boolean isCitySelected = false;
    private int citySelectedIndex = 0;
    private int areaSelectedIndex = 0;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, BrowseSupermarketsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_supermarkets);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        leftImg.setVisibility(View.GONE);
        cityTxt.setOnClickListener(this);
        fabBtn.setOnClickListener(this);
        areaTxt.setOnClickListener(this);
        showSupermarketsTxt.setOnClickListener(this);
        homeMenu.setOnClickListener(this);
        contactUsMenu.setOnClickListener(this);
        aboutUsMenu.setOnClickListener(this);
        profileMenu.setOnClickListener(this);
        shoppingMenu.setOnClickListener(this);
        notificationMenu.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        UserManager.getInstance(this).addListener(this);
//        showLoadingDialog();
//        UserManager.getInstance(this).performLocationRequest();
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
    public void onSuccess(List<Location> list) {
        hideLoadingDialog();
        if (!ValidatorUtils.isEmpty(list))
            locationList = list;
    }

    @Override
    public void onSuccess() {
        startActivity(SupermarketsActivity.getActivityIntent(this, UserManager.getInstance(this).getSupermarketsList(), getCityAndArea()));
    }

    @Override
    public void onException(Exception ex) {
        hideLoadingDialog();
        AlertUtils.showToast(this, ex.getMessage());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cityTxt:
                // TODO: call perform location request
                showDialog(getString(R.string.city), IntentConstants.CITY);
                break;
            case R.id.areaTxt:
                showDialog(getString(R.string.location), IntentConstants.AREA);
                break;
            case R.id.showSupermarketsTxt:
                // TODO: call perform supermarkets request
//                showLoadingDialog();
//                UserManager.getInstance(this).performBrowseSupermarketsRequest(getCityAndArea());
                startActivity(SupermarketsActivity.getActivityIntent(this));

                break;
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
        }
    }

    private void showDialog(String title, int flag) {

        /** Initialize of dialog **/
        final Dialog dialog = new Dialog(this);

        /** remove dialog title **/
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        /** Include dialog.xml file **/
        dialog.setContentView(R.layout.location_dialog);

        /** set dialog background transparent to use my own view instead **/
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        /** set dialog gravity **/
        dialog.getWindow().setGravity(Gravity.CENTER);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final LinearLayout searchLyt = (LinearLayout) dialog.findViewById(R.id.searchLyt);
        final SearchView searchView = (SearchView) dialog.findViewById(R.id.searchView);
        final int searchCloseButtonId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) searchView.findViewById(searchCloseButtonId);
        // Set on click listener
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setVisibility(View.GONE);
                searchLyt.setVisibility(View.VISIBLE);
            }
        });

        searchLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setVisibility(View.VISIBLE);
                searchLyt.setVisibility(View.GONE);
            }
        });

        TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
        dialogTitle.setText(title);

        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
        if (flag == IntentConstants.CITY)
            setupRecyclerView(recyclerView, flag, getCityList());
        else
            setupRecyclerView(recyclerView, flag, getAreaList());

        /** Dismiss dialog on Cancel button pressed **/
        ImageView closeImg = (ImageView) dialog.findViewById(R.id.closeImg);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        /** Show dialog **/
        dialog.show();
    }

    private void setupRecyclerView(RecyclerView recyclerView, int flag, List<?> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(R.layout.list_order_spacer));
        setListItems(recyclerView, list, flag);
    }

    private void setListItems(RecyclerView recyclerView, List<?> list, int flag) {
        if (!ValidatorUtils.isEmpty(list)) {
            LocationsAdapter adapter = new LocationsAdapter(this, list, flag, this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private List<Location> getCityList() {
        if (locationList.size() > 0)
            return locationList;
        // TODO: clear the next statments as they are dummy data for test ONLY
        List<Location> cityList = new ArrayList<Location>();
        List<Area> areaList = new ArrayList<Area>();
        areaList.add(new Area("1", "Area"));
        Location obj = new Location("1", "City", areaList);
        for (int i = 0; i < 100; i++) {
            cityList.add(obj);
        }
        return cityList;
    }

    private List<Area> getAreaList() {
        if (isCitySelected)
            return locationList.get(citySelectedIndex).getAreaList();
        else
            AlertUtils.showToast(this, getString(R.string.select_city_first));
        // TODO: clear the next statments as they are dummy data for test ONLY
        List<Area> areaList = new ArrayList<Area>();
        Area obj = new Area("1", "Area");
        for (int i = 0; i < 100; i++) {
            areaList.add(obj);
        }
        return areaList;
    }

    private Location getCityAndArea() {
        ArrayList<Area> list = new ArrayList<Area>(1);
        if (ValidatorUtils.isEmpty(locationList))
            return null;

        list.add(locationList.get(citySelectedIndex).getAreaList().get(areaSelectedIndex));
        Location location = locationList.get(citySelectedIndex);
        location.setAreaList(list);
        return location;
    }

    @Override
    public void onCitySelected(int index) {
        citySelectedIndex = index;
    }

    @Override
    public void onAreaSelected(int index) {
        areaSelectedIndex = index;
    }
}
