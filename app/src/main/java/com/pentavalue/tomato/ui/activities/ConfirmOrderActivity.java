package com.pentavalue.tomato.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.adapter.ConfirmOrderAdapter;
import com.pentavalue.tomato.adapter.ItemsAdapter;
import com.pentavalue.tomato.data.constants.Params;
import com.pentavalue.tomato.data.parsing.BuildingStaticData;
import com.pentavalue.tomato.model.Items;
import com.pentavalue.tomato.model.Notification;
import com.pentavalue.tomato.ui.custom.DividerItemDecoration;
import com.pentavalue.tomato.utils.ValidatorUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConfirmOrderActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.noItemTxt)
    TextView noItemTxt;
    @Bind(R.id.titleTxt)
    TextView titleTxt;

    @Bind(R.id.confirmOrderTxt)
    TextView confirmOrderTxt;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, ConfirmOrderActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
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
        ButterKnife.bind(this);
        titleTxt.setText(R.string.checkout);
        setupRecyclerView(recyclerView, BuildingStaticData.getItemsList());
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Items> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setListItems(recyclerView, list);
    }

    private void setListItems(RecyclerView recyclerView, List<Items> list) {
        if (!ValidatorUtils.isEmpty(list)) {
            ConfirmOrderAdapter adapter = new ConfirmOrderAdapter(this, list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private void handleEmptyList(List<Notification> list) {
        if (ValidatorUtils.isEmpty(list)) {
            noItemTxt.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noItemTxt.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
