package com.pentavalue.tomato.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.model.Area;
import com.pentavalue.tomato.model.Location;
import com.pentavalue.tomato.storage.IntentConstants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.CustomViewHolder> {

    private List<?> list;
    private Context context;
    private int flag;
    private OnCitySelectListener onCitySelectListener;

    public static interface OnCitySelectListener {
        void onCitySelected(int index);
        void onAreaSelected(int index);
    }

    public LocationsAdapter(Context context, List<?> list, int flag, OnCitySelectListener onCitySelectListener) {
        this.context = context;
        this.list = list;
        this.flag = flag;
        this.onCitySelectListener = onCitySelectListener;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        String name;
        if (flag == IntentConstants.CITY) {
            Location entity = (Location) list.get(position);
            name = entity.getName();
        } else {
            Area entity = (Area) list.get(position);
            name = entity.getName();
        }

        holder.radioLocation.setText(name);

        holder.radioLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == IntentConstants.CITY)
                    onCitySelectListener.onCitySelected(position);
                else if(flag == IntentConstants.AREA)
                    onCitySelectListener.onAreaSelected(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_locations, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.radioLocation)
        RadioButton radioLocation;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
