package com.pentavalue.tomato.ui.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.listeneres.OnProfileListener;
import com.pentavalue.tomato.managers.UserManager;
import com.pentavalue.tomato.model.User;
import com.pentavalue.tomato.storage.IntentConstants;
import com.pentavalue.tomato.utils.AlertUtils;
import com.pentavalue.tomato.utils.DateUtils;
import com.pentavalue.tomato.utils.ValidatorUtils;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class EditProfileActivity extends BaseActivity implements View.OnClickListener, OnProfileListener {

    @Bind(R.id.firstNameET)
    EditText firstNameET;
    @Bind(R.id.lastNameET)
    EditText lastNameET;
    @Bind(R.id.phoneNumberET)
    EditText phoneNumberET;

    @Bind(R.id.radioSexGroup)
    RadioGroup radioSexGroup;
    RadioButton radioSexBtn;

    @Bind(R.id.birthDateTxt)
    TextView birthDateTxt;
    @Bind(R.id.birthDateLyt)
    LinearLayout birthDateLyt;

    @Bind(R.id.saveTxt)
    TextView saveTxt;

    @Bind(R.id.titleTxt)
    TextView titleTxt;

    @Bind(R.id.leftImg)
    ImageView leftImg;


    private String firstName, lastName, phoneNumber, gender, birthDate;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day, selectedId;
    private boolean setDateFlag = false;
    private User user = new User();

    public static Intent getActivityIntent(Context context, User user) {
        return new Intent(context, EditProfileActivity.class)
                .putExtra(IntentConstants.USER, user);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        user = (User) getIntent().getSerializableExtra(IntentConstants.USER);
        titleTxt.setText(R.string.edit_profile);
        leftImg.setImageResource(R.drawable.ic_arrow_back);
        birthDateLyt.setOnClickListener(this);
        leftImg.setOnClickListener(this);
        saveTxt.setOnClickListener(this);
        if (ValidatorUtils.isEmpty(user))
            return;
        firstNameET.setText(user.getFirstName());
        lastNameET.setText(user.getLastName());
        birthDateTxt.setText(user.getBirthDate());
        phoneNumberET.setText(user.getPhoneNumber());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.birthDateLyt:
                openDatePicker(v);
                break;
            case R.id.saveTxt:
                getValue();
                if (checkIfValidValues()) {
                    // TODO: call user manager to perform login
                    UserManager.getInstance(this).performEditProfileRequest(getUserObj());
                }
                break;
            case R.id.leftImg:
                this.onBackPressed();
                break;
        }
    }

    @SuppressWarnings("deprecation")
    public void openDatePicker(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, myDateListener, year, month, day);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return datePickerDialog;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            // TODO Auto-generated method stub
            setDateFlag = true;
            showDate(selectedYear, selectedMonth + 1, selectedDay);
        }
    };

    private void showDate(int year, int month, int day) {
        birthDateTxt.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }

    private void getValue() {
        firstName = firstNameET.getText().toString();
        lastName = lastNameET.getText().toString();
        phoneNumber = phoneNumberET.getText().toString();
        birthDate = birthDateTxt.getText().toString();

        // find the radio button by returned id "get selected radio button from radioGroup"
        radioSexBtn = (RadioButton) findViewById(radioSexGroup.getCheckedRadioButtonId());
        gender = radioSexBtn.getText().toString();
    }

    private User getUserObj() {
        User user = UserManager.getInstance(this).getUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setBirthDate(birthDate);
        user.setGender(gender);
        return user;
    }

    private boolean checkIfValidValues() {
        if (ValidatorUtils.isEmpty(firstName)) {
            AlertUtils.showError(this, getString(R.string.enter_firstname));
            return false;
        } else if (ValidatorUtils.isEmpty(lastName)) {
            AlertUtils.showError(this, getString(R.string.enter_lastname));
            return false;
        } else if (ValidatorUtils.isEmpty(phoneNumber)) {
            AlertUtils.showError(this, getString(R.string.enter_phonenumber));
            return false;
        } else if (!ValidatorUtils.isValidLength(phoneNumber, 7)) {
            AlertUtils.showError(this, getString(R.string.valid_phone));
            return false;
        } else if (ValidatorUtils.isEmpty(birthDate) && !setDateFlag) {
            AlertUtils.showError(this, getString(R.string.enter_birthdate));
            return false;
        }
        return true;
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
    public void onSuccess(User obj) {
        hideLoadingDialog();
        UserManager.getInstance(this).saveUserProfile(obj);
    }

    @Override
    public void onException(Exception ex) {
        hideLoadingDialog();
        AlertUtils.showToast(this, ex.getMessage());
    }
}
