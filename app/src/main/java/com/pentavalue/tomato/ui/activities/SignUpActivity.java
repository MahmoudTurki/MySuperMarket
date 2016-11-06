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
import com.pentavalue.tomato.model.User;
import com.pentavalue.tomato.storage.IntentConstants;
import com.pentavalue.tomato.utils.AlertUtils;
import com.pentavalue.tomato.utils.ValidatorUtils;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.firstNameET)
    EditText firstNameET;
    @Bind(R.id.lastNameET)
    EditText lastNameET;
    @Bind(R.id.emailET)
    EditText emailET;
    @Bind(R.id.passwordET)
    EditText passwordET;
    @Bind(R.id.confirmPasswordET)
    EditText confirmPasswordET;
    @Bind(R.id.phoneNumberET)
    EditText phoneNumberET;

    @Bind(R.id.radioSexGroup)
    RadioGroup radioSexGroup;
    RadioButton radioSexBtn;

    @Bind(R.id.birthDateTxt)
    TextView birthDateTxt;
    @Bind(R.id.birthDateLyt)
    LinearLayout birthDateLyt;

    @Bind(R.id.addDeliverAddressTxt)
    TextView addDeliverAddressTxt;

    @Bind(R.id.titleTxt)
    TextView titleTxt;
    @Bind(R.id.leftImg)
    ImageView leftImg;


    private String firstName, lastName, email, password, confirmPassword, phoneNumber, gender, birthDate;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day, selectedId;
    private boolean setDateFlag = false;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        titleTxt.setText(R.string.sign_up);
        leftImg.setImageResource(R.drawable.ic_arrow_back);
        leftImg.setOnClickListener(this);
        birthDateLyt.setOnClickListener(this);
        addDeliverAddressTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.birthDateLyt:
                openDatePicker(v);
                break;
            case R.id.leftImg:
                this.onBackPressed();
                break;
            case R.id.addDeliverAddressTxt:
                getValue();
                if (!checkIfValidValues()) {
                    // TODO: call user manager to perform login
                    startActivity(DeliveryAddressActivity.getActivityIntent(this, getUserObj(), IntentConstants.FIRST_SIGN_UP, null));
                }
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
        email = emailET.getText().toString();
        password = passwordET.getText().toString();
        confirmPassword = confirmPasswordET.getText().toString();
        phoneNumber = phoneNumberET.getText().toString();
        birthDate = birthDateTxt.getText().toString();

        // find the radio button by returned id "get selected radio button from radioGroup"
        radioSexBtn = (RadioButton) findViewById(radioSexGroup.getCheckedRadioButtonId());
        gender = radioSexBtn.getText().toString();
    }

    private User getUserObj() {
        return new User(firstName, lastName, email, password, confirmPassword, phoneNumber, gender, birthDate);
    }

    private boolean checkIfValidValues() {
        if (ValidatorUtils.isEmpty(firstName)) {
            AlertUtils.setValidationBackground(this, firstNameET, R.string.mandatory_field);
            return false;
        } else if (ValidatorUtils.isEmpty(lastName)) {
            AlertUtils.setValidationBackground(this, lastNameET, R.string.mandatory_field);
            return false;
        } else if (ValidatorUtils.isEmpty(email)) {
            AlertUtils.setValidationBackground(this, emailET, R.string.mandatory_field);
            return false;
        } else if (!ValidatorUtils.isValidEmail(email)) {
            AlertUtils.setValidationBackground(this, emailET, R.string.valid_email);
            return false;
        } else if (ValidatorUtils.isEmpty(password)) {
            AlertUtils.setValidationBackground(this, passwordET, R.string.mandatory_field);
            return false;
        } else if (ValidatorUtils.isEmpty(confirmPassword)) {
            AlertUtils.setValidationBackground(this, confirmPasswordET, R.string.mandatory_field);
            return false;
        } else if (!confirmPassword.equals(password)) {
            AlertUtils.setValidationBackground(this, confirmPasswordET, R.string.password_and_confirm_notEqual);
            return false;
        } else if (ValidatorUtils.isEmpty(phoneNumber)) {
            AlertUtils.setValidationBackground(this, phoneNumberET, R.string.mandatory_field);
            return false;
        } else if (!ValidatorUtils.isValidLength(password, 7)) {
            AlertUtils.setValidationBackground(this, passwordET, R.string.valid_password);
            return false;
        } else if (!ValidatorUtils.isValidLength(phoneNumber, 8)) {
            AlertUtils.setValidationBackground(this, phoneNumberET, R.string.valid_phone);
            return false;
        } else if (ValidatorUtils.isEmpty(birthDate) && !setDateFlag) {
            AlertUtils.setValidationBackground(this, birthDateTxt, R.string.mandatory_field);
            return false;
        }
        return true;
    }
}
