package com.pentavalue.tomato.ui.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pentavalue.tomato.R;

/**
 * @author Mahmoud Turki
 */
public class BaseActivity extends AppCompatActivity {

    private ImageView rightImg, leftImg, cartImg, notificationImg, profileImg, contactUsImg, aboutUsImg;
    private TextView titleTxt;
    private FrameLayout contentLayout;
    private RelativeLayout topBarRelativeLayout;
    private LinearLayout bottomBarLinearLayout;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        init();*/
    }


    private void init() {
        titleTxt = (TextView) findViewById(R.id.titleTxt);
        contentLayout = (FrameLayout) findViewById(R.id.baseActivityContent);

        topBarRelativeLayout = (RelativeLayout) findViewById(R.id.topBarRlyt);
        bottomBarLinearLayout = (LinearLayout) findViewById(R.id.bottomBarLLyt);

        rightImg = (ImageView) findViewById(R.id.rightImg);
        leftImg = (ImageView) findViewById(R.id.leftImg);
        cartImg = (ImageView) findViewById(R.id.cartImg);
        notificationImg = (ImageView) findViewById(R.id.notificationImg);
        profileImg = (ImageView) findViewById(R.id.profileImg);
        contactUsImg = (ImageView) findViewById(R.id.contactUsImg);
        aboutUsImg = (ImageView) findViewById(R.id.aboutUsImg);
    }

    /* @Override
     public void setContentView(int layoutResID) {
         View content = LayoutInflater.from(this).inflate(layoutResID, contentLayout, false);
         contentLayout.addView(content);
     }

     @Override
     public void setContentView(View view) {
         contentLayout.addView(view);
     }*/

    /**
     * This method is used to show a progress dialog for the screen.
     *
     * @return True if the progress dialog was actually shown due this call.
     */
    public boolean showLoadingDialog() {
        if (mDialog == null && this != null) {
            mDialog = new ProgressDialog(this);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setMessage(getString(R.string.loading));
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setIndeterminate(true);
            mDialog.setCancelable(false);
            mDialog.getWindow().setGravity(Gravity.CENTER);
        }

        if (mDialog != null && !mDialog.isShowing()) {
            try {
                mDialog.show();
            } catch (Exception e) {
                // Nothing
            }
            return true;
        }
        return false;
    }

    /**
     * This method is used to hide the progress dialog off this screen.
     *
     * @return True if the dialog was actually dismissed due this call.
     */
    public boolean hideLoadingDialog() {
        if (this != null && !this.isFinishing() && mDialog != null && mDialog.isShowing()) {
            try {
                mDialog.dismiss();
            } catch (Exception e) {
                // Nothing
            }
            return true;
        }
        return false;
    }

    public void showLeftBtn() {
        leftImg.setVisibility(View.VISIBLE);
    }

    public void showRightBtn() {
        rightImg.setVisibility(View.VISIBLE);
    }

    public void showTitle() {
        titleTxt.setVisibility(View.VISIBLE);
    }

    public void showTopBar() {
        topBarRelativeLayout.setVisibility(View.VISIBLE);
    }

    public void showBottomBar() {
        bottomBarLinearLayout.setVisibility(View.VISIBLE);
    }

    public void hideLeftBtn() {
        leftImg.setVisibility(View.GONE);
    }

    public void hideRightBtn() {
        rightImg.setVisibility(View.GONE);
    }

    public void hideTitle() {
        titleTxt.setVisibility(View.GONE);
    }

    public void hideTopBar() {
        topBarRelativeLayout.setVisibility(View.GONE);
    }

    public void hideBottomBar() {
        bottomBarLinearLayout.setVisibility(View.GONE);
    }

    public ImageView getRightImg() {
        return rightImg;
    }

    public ImageView getLeftImg() {
        return leftImg;
    }

    public ImageView getCartImg() {
        return cartImg;
    }

    public ImageView getNotificationImg() {
        return notificationImg;
    }

    public ImageView getProfileImg() {
        return profileImg;
    }

    public ImageView getContactUsImg() {
        return contactUsImg;
    }

    public ImageView getAboutUsImg() {
        return aboutUsImg;
    }

    public TextView getTitleTxt() {
        return titleTxt;
    }

    public FrameLayout getContentLayout() {
        return contentLayout;
    }

    public RelativeLayout getTopBarRelativeLayout() {
        return topBarRelativeLayout;
    }

    public LinearLayout getBottomBarLinearLayout() {
        return bottomBarLinearLayout;
    }
}
