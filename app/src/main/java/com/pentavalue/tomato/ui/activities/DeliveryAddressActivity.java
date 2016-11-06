package com.pentavalue.tomato.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.listeneres.OnSignUpListener;
import com.pentavalue.tomato.managers.UserManager;
import com.pentavalue.tomato.model.ShippingAddresses;
import com.pentavalue.tomato.model.User;
import com.pentavalue.tomato.storage.IntentConstants;
import com.pentavalue.tomato.utils.AlertUtils;
import com.pentavalue.tomato.utils.ValidatorUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class DeliveryAddressActivity extends BaseActivity implements View.OnClickListener, OnSignUpListener {

    @Bind(R.id.shippingAddressNameET)
    EditText shippingAddressNameET;
    @Bind(R.id.blockNumberET)
    EditText blockNumberET;
    @Bind(R.id.streetNameET)
    EditText streetNameET;
    @Bind(R.id.buildingNumberET)
    EditText buildingNumberET;
    @Bind(R.id.floorET)
    EditText floorET;
    @Bind(R.id.apartmentNumberET)
    EditText apartmentNumberET;
    @Bind(R.id.extraDirectionET)
    EditText extraDirectionET;

    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    RadioButton radioBtn;

    @Bind(R.id.signUpTxt)
    TextView signUpTxt;
    @Bind(R.id.titleTxt)
    TextView titleTxt;

    @Bind(R.id.leftImg)
    ImageView leftImg;
    @Bind(R.id.rightImg)
    ImageView rightImg;

    private String shippingAddressName, blockNumber, streetName, buildingNumber, floor, apartmentNumber, extraDirection, addressType;
    private int flag;
    private ShippingAddresses address = new ShippingAddresses();

    public static Intent getActivityIntent(Context context, User user, int flag, ShippingAddresses address) {
        return new Intent(context, DeliveryAddressActivity.class)
                .putExtra(IntentConstants.USER, user)
                .putExtra(IntentConstants.ADD_ADDRESS_TYPE, flag)
                .putExtra(IntentConstants.ADDRESS, address);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        signUpTxt.setOnClickListener(this);
        leftImg.setOnClickListener(this);

        leftImg.setImageResource(R.drawable.ic_arrow_back);
        flag = getIntent().getIntExtra(IntentConstants.ADD_ADDRESS_TYPE, IntentConstants.FIRST_SIGN_UP);
        address = (ShippingAddresses) getIntent().getSerializableExtra(IntentConstants.ADDRESS);

        switch (flag) {
            case IntentConstants.FIRST_SIGN_UP:
                titleTxt.setText(R.string.delivery_address);
                rightImg.setVisibility(View.GONE);

                break;
            case IntentConstants.EDIT_ADDRESS:
                signUpTxt.setText(R.string.save);
                titleTxt.setText(R.string.edit_address);
                rightImg.setVisibility(View.VISIBLE);
                rightImg.setImageResource(R.drawable.edit_address_delete);
                if (ValidatorUtils.isEmpty(address))
                    return;
                shippingAddressNameET.setText(address.getAddressName());
                blockNumberET.setText(address.getBlockNumber());
                streetNameET.setText(address.getStreetName());
                buildingNumberET.setText(address.getBuildingNumber());
                floorET.setText(address.getFloor());
                apartmentNumberET.setText(address.getAppartmentNumber());
                extraDirectionET.setText(address.getExtraDirections());
                break;
            case IntentConstants.ADD_ADDRESS:
                titleTxt.setText(R.string.edit_address);
                signUpTxt.setText(R.string.save);
                rightImg.setVisibility(View.VISIBLE);
                rightImg.setImageResource(R.drawable.edit_address_delete);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpTxt:
                getValue();
                if (!checkIfValidValues()) {
                    showLoadingDialog();
                    if (flag == IntentConstants.FIRST_SIGN_UP) {
                        // TODO: call user manager to perform sign up
                        UserManager.getInstance(this).performSignUpRequest(getUserObj());
//                    startActivity(LoginActivity.getActivityIntent(this));
                    } else {
                        // TODO: call user manager to perform edit profile
                        UserManager.getInstance(this).performEditProfileRequest(getUserObj());
                    }
                }
                break;
            case R.id.rightImg:
                // TODO: perform delete address action
                break;
            case R.id.leftImg:
                this.onBackPressed();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UserManager.getInstance(this).addListener(this);
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
    public void onSuccess(String obj) {
        hideLoadingDialog();
        AlertUtils.showToast(this, obj);
        showDialog();
        startActivity(LoginActivity.getActivityIntent(this));
    }

    @Override
    public void onException(Exception ex) {
        hideLoadingDialog();
        AlertUtils.showToast(this, ex.getMessage());
    }

    private void getValue() {
        shippingAddressName = shippingAddressNameET.getText().toString();
        blockNumber = blockNumberET.getText().toString();
        streetName = streetNameET.getText().toString();
        buildingNumber = buildingNumberET.getText().toString();
        floor = floorET.getText().toString();
        apartmentNumber = apartmentNumberET.getText().toString();
        extraDirection = extraDirectionET.getText().toString();

        // find the radio button by returned id "get selected radio button from radioGroup"
        radioBtn = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        addressType = radioBtn.getText().toString();
    }

    private User getUserObj() {
        User user = new User();
        ArrayList<ShippingAddresses> list = new ArrayList<ShippingAddresses>();
        ShippingAddresses newAddress = new ShippingAddresses(shippingAddressName, addressType, blockNumber, streetName, buildingNumber, floor, apartmentNumber, extraDirection);

        if (flag == IntentConstants.FIRST_SIGN_UP) {
            list.add(newAddress);
            user = (User) getIntent().getSerializableExtra(IntentConstants.USER);
            user.setShippingAddresses(list);
        } else if (flag == IntentConstants.ADD_ADDRESS) {
            list.add(newAddress);
            user = UserManager.getInstance(this).getUser();
            user.setShippingAddresses(list);
        } else if (flag == IntentConstants.EDIT_ADDRESS) {
            newAddress.setId(address.getId());
            user = UserManager.getInstance(this).getUser();
            ArrayList<ShippingAddresses> savedAddressList = (ArrayList<ShippingAddresses>) user.getShippingAddresses();
            savedAddressList.remove(newAddress);
            savedAddressList.add(newAddress);
            user.setShippingAddresses(savedAddressList);
        }
        return user;
    }

    private boolean checkIfValidValues() {
        if (ValidatorUtils.isEmpty(shippingAddressName)) {
            AlertUtils.setValidationBackground(this, shippingAddressNameET, R.string.mandatory_field);
            return false;
        } else if (ValidatorUtils.isEmpty(streetName)) {
            AlertUtils.setValidationBackground(this, streetNameET, R.string.mandatory_field);
            return false;
        } else if (ValidatorUtils.isEmpty(buildingNumber)) {
            AlertUtils.setValidationBackground(this, buildingNumberET, R.string.mandatory_field);
            return false;
        }
        return true;
    }

    private void showDialog() {

        /** Initialize of dialog **/
        final Dialog dialog = new Dialog(this);

        /** remove dialog title **/
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        /** Include dialog.xml file **/
        dialog.setContentView(R.layout.alert_dialog);

        /** set dialog background transparent to use my own view instead **/
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        /** set dialog gravity **/
        dialog.getWindow().setGravity(Gravity.CENTER);

        /** Dismiss dialog on Cancel button pressed **/
        TextView okTxt = (TextView) dialog.findViewById(R.id.okBtn);
        okTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        /** Show dialog **/
        dialog.show();
    }
}
