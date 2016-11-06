package com.pentavalue.tomato.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.data.constants.Params;
import com.pentavalue.tomato.model.Supermarket;
import com.pentavalue.tomato.ui.activities.SupermarketSectionsActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class SupermarketAdapter extends RecyclerView.Adapter<SupermarketAdapter.CustomViewHolder> {

    private List<Supermarket> list;
    private Context context;

    public SupermarketAdapter(Context context, List<Supermarket> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        final Supermarket entity = (Supermarket) list.get(position);
        holder.supermarketTitleTxt.setText(entity.getName());
        if (entity.getAvailabilityStatus().equalsIgnoreCase(Params.Supermarket.CLOSED))
            holder.availabilityStatusTxt.setBackgroundResource(R.drawable.supermarket_cloosed);
        else
            holder.availabilityStatusTxt.setBackgroundResource(R.drawable.supermarket_open);
        holder.availabilityStatusTxt.setText(entity.getAvailabilityStatus());
        holder.openingTimeTxt.setText(entity.getOpeningTime());
        holder.closingTimeTxt.setText(entity.getClosingTime());
        holder.minOrderTxt.setText("" + entity.getMinimumOrder() + Params.Supermarket.AED);
        holder.deliveryFeesTxt.setText("" + entity.getDeliveryCharges() + Params.Supermarket.AED);
        holder.deliveryTimeTxt.setText(entity.getDeliveryTime());
//        holder.supermarketPaymentTxt.setText(entity.getPaymentOptions());
//        holder.supermarketRBar.setRating(Float.parseFloat(entity.getRating()));

        Picasso.with(context)
                .load("http://s22.postimg.org/xhxnsizrl/abc2.jpg")
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.supermarketImg, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.loadingPanel.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.loadingPanel.setVisibility(View.GONE);
                    }
                });

        holder.targetLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(SupermarketSectionsActivity.getActivityIntent(context, entity));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_supermarkets, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.supermarketImg)
        ImageView supermarketImg;
        @Bind(R.id.loadingPanel)
        RelativeLayout loadingPanel;
        @Bind(R.id.dealsTxt)
        TextView dealsTxt;
        @Bind(R.id.newArrivalsTxt)
        TextView newArrivalsTxt;

        @Bind(R.id.supermarketRBar)
        RatingBar supermarketRBar;
        @Bind(R.id.supermarketTitleTxt)
        TextView supermarketTitleTxt;
        @Bind(R.id.availabilityStatusTxt)
        TextView availabilityStatusTxt;
        @Bind(R.id.openingTimeTxt)
        TextView openingTimeTxt;
        @Bind(R.id.closingTimeTxt)
        TextView closingTimeTxt;

        @Bind(R.id.minOrderTxt)
        TextView minOrderTxt;
        @Bind(R.id.supermarketPaymentTxt)
        TextView supermarketPaymentTxt;
        @Bind(R.id.deliveryFeesTxt)
        TextView deliveryFeesTxt;
        @Bind(R.id.deliveryTimeTxt)
        TextView deliveryTimeTxt;

        @Bind(R.id.targetLyt)
        LinearLayout targetLyt;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
