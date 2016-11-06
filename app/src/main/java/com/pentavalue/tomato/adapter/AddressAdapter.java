package com.pentavalue.tomato.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.model.ShippingAddresses;
import com.pentavalue.tomato.storage.IntentConstants;
import com.pentavalue.tomato.ui.activities.DeliveryAddressActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.CustomViewHolder> {

    private List<ShippingAddresses> list;
    private Context context;

    public AddressAdapter(Context context, List<ShippingAddresses> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        ShippingAddresses entity = (ShippingAddresses) list.get(position);
        holder.addressNameTxt.setText(entity.getAddressName());
        holder.addressDescriptionTxt.setText(entity.getExtraDirections());
        holder.editAddressImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(DeliveryAddressActivity.getActivityIntent(context, null, IntentConstants.EDIT_ADDRESS, list.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_address, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.addressNameTxt)
        TextView addressNameTxt;
        @Bind(R.id.addressDescriptionTxt)
        TextView addressDescriptionTxt;
        @Bind(R.id.editAddressImg)
        ImageView editAddressImg;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
