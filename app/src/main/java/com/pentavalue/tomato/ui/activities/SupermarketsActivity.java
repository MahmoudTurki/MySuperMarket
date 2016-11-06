package com.pentavalue.tomato.ui.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.adapter.SupermarketAdapter;
import com.pentavalue.tomato.data.constants.Params;
import com.pentavalue.tomato.data.parsing.BuildingStaticData;
import com.pentavalue.tomato.model.Location;
import com.pentavalue.tomato.model.Supermarket;
import com.pentavalue.tomato.storage.IntentConstants;
import com.pentavalue.tomato.ui.custom.DividerItemDecoration;
import com.pentavalue.tomato.utils.ValidatorUtils;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class SupermarketsActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.titleTxt)
    TextView titleTxt;
    @Bind(R.id.descTxt)
    TextView descTxt;
    @Bind(R.id.leftImg)
    ImageView leftImg;
    @Bind(R.id.rightImg)
    ImageView rightImg;

    @Bind(R.id.searchView)
    SearchView searchView;


    public static Intent getActivityIntent(Context context, List<Supermarket> list, Location location) {
        return new Intent(context, SupermarketsActivity.class)
                .putExtra(IntentConstants.SUPERMARKETS, (Serializable) list)
                .putExtra(IntentConstants.LOCATION, location);
    }

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, SupermarketsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermarkets);
        init();
    }

    /**
     * Initialization for all views
     **/
    private void init() {
        ButterKnife.bind(this);
        Location location = (Location) getIntent().getSerializableExtra(IntentConstants.LOCATION);
        leftImg.setImageResource(R.drawable.ic_arrow_back);
        searchView.setVisibility(View.VISIBLE);
        setUpSearchView();

//        titleTxt.setText(location.getName());
//        descTxt.setText(location.getAreaList().get(0).getName());
        setupRecyclerView(recyclerView, BuildingStaticData.getSuperMarketList());
    }

    private void setUpSearchView() {
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(id);
        textView.setTextColor(Color.WHITE);
        textView.setHintTextColor(Color.WHITE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Change the back button.
                    titleTxt.setVisibility(View.GONE);
                }
            });
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    titleTxt.setVisibility(View.VISIBLE);
                    return false;
                }
            });
            //*** setOnQueryTextFocusChangeListener ***
            searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    // TODO Auto-generated method stub
                }
            });
            //*** setOnQueryTextListener ***
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    // TODO Auto-generated method stub
                    startActivity(SupermarketSectionsActivity.getActivityIntent(SupermarketsActivity.this));
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // TODO Auto-generated method stub
                    return false;
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchView:
                break;
        }
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Supermarket> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(Params.NO_LAYOUT));
        setListItems(recyclerView, list);
    }

    private void setListItems(RecyclerView recyclerView, List<Supermarket> list) {
        if (!ValidatorUtils.isEmpty(list)) {
            SupermarketAdapter adapter = new SupermarketAdapter(this, list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
