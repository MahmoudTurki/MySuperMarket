package com.pentavalue.tomato.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.adapter.AddressAdapter;
import com.pentavalue.tomato.data.constants.Params;
import com.pentavalue.tomato.listeneres.OnProfileListener;
import com.pentavalue.tomato.managers.UserManager;
import com.pentavalue.tomato.model.ShippingAddresses;
import com.pentavalue.tomato.model.User;
import com.pentavalue.tomato.storage.IntentConstants;
import com.pentavalue.tomato.ui.custom.DividerItemDecoration;
import com.pentavalue.tomato.utils.AlertUtils;
import com.pentavalue.tomato.utils.DateUtils;
import com.pentavalue.tomato.utils.ValidatorUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class ProfileActivity extends BaseActivity implements View.OnClickListener, OnProfileListener {

    @Bind(R.id.userNameTxt)
    TextView userNameTxt;
    @Bind(R.id.birthDateTxt)
    TextView birthDateTxt;
    @Bind(R.id.phoneNumberTxt)
    TextView phoneNumberTxt;
    @Bind(R.id.emailTxt)
    TextView emailTxt;
    @Bind(R.id.titleTxt)
    TextView titleTxt;
    @Bind(R.id.leftImg)
    ImageView leftImg;
    @Bind(R.id.rightImg)
    ImageView rightImg;
    @Bind(R.id.userImg)
    ImageView userImg;
    @Bind(R.id.addAddressTxt)
    TextView addAddressTxt;
    @Bind(R.id.changePasswordTxt)
    TextView changePasswordTxt;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private boolean isProfileReturned = false;
    private User user = new User();

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, ProfileActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        titleTxt.setText(R.string.profile);
        rightImg.setImageResource(R.drawable.profile_edit_profile);
        rightImg.setVisibility(View.VISIBLE);
        leftImg.setVisibility(View.GONE);
        addAddressTxt.setOnClickListener(this);
        changePasswordTxt.setOnClickListener(this);
        rightImg.setOnClickListener(this);
        setupRecyclerView(recyclerView, getAddressList());
    }

    private void setItemsFromServer(User user) {
        if (ValidatorUtils.isEmpty(user)) {
            AlertUtils.showToast(this, getString(R.string.profile_empty));
            return;
        }
        this.user = user;
        userNameTxt.setText(user.getFirstName() + " " + user.getLastName());
        emailTxt.setText(user.getEmail());
        phoneNumberTxt.setText(user.getPhoneNumber());
        birthDateTxt.setText(user.getBirthDate());

        setupRecyclerView(recyclerView, user.getShippingAddresses() /*getAddressList(user)*/);
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<ShippingAddresses> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(Params.NO_LAYOUT));
        setListItems(recyclerView, list);
    }

    private void setListItems(RecyclerView recyclerView, List<ShippingAddresses> list) {
        if (!ValidatorUtils.isEmpty(list)) {
            AddressAdapter adapter = new AddressAdapter(this, list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private List<ShippingAddresses> getAddressList(User user) {
        List<ShippingAddresses> list = new ArrayList<ShippingAddresses>();
        if (isProfileReturned)
            list = user.getShippingAddresses();
        return list;
    }

    private List<ShippingAddresses> getAddressList() {
        List<ShippingAddresses> list = new ArrayList<ShippingAddresses>();
        ShippingAddresses obj = new ShippingAddresses(getString(R.string.address_name), getString(R.string.address_description));
        for (int i = 0; i < 7; i++) {
            list.add(obj);
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addAddressTxt:
                startActivity(DeliveryAddressActivity.getActivityIntent(this, null, IntentConstants.ADD_ADDRESS, null));
                break;
            case R.id.changePasswordTxt:
                startActivity(ChangePasswordActivity.getActivityIntent(this));
                break;
            case R.id.rightImg:
                startActivity(EditProfileActivity.getActivityIntent(this, user));
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        UserManager.getInstance(this).removeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        UserManager.getInstance(this).addListener(this);
        showLoadingDialog();
        UserManager.getInstance(this).performProfileRequest();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onSuccess(User obj) {
        hideLoadingDialog();
        setItemsFromServer(obj);
        isProfileReturned = true;
    }

    @Override
    public void onException(Exception ex) {
        hideLoadingDialog();
        AlertUtils.showToast(this, ex.getMessage());
    }
}