package com.pentavalue.tomato.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hrules.horizontalnumberpicker.HorizontalNumberPicker;
import com.pentavalue.tomato.R;
import com.pentavalue.tomato.model.Deals;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.CustomViewHolder> {

    private List<Deals> list;
    private static Context context;

    public DealsAdapter(Context context, List<Deals> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        final Deals entity = (Deals) list.get(position);
        holder.itemTitleTxt.setText(entity.getName());
        holder.itemDescTxt.setText(entity.getDescription());
        holder.itemPriceTxt.setText(entity.getPrice());

        Picasso.with(context)
                .load("http://s22.postimg.org/xhxnsizrl/abc2.jpg")
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.itemImg, new Callback() {
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

            }
        });
        holder.itemDealTxt.setText(entity.getDeal());
        holder.subtractImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = entity.getCount();
                if (count != 0)
                    holder.countTxt.setText("" + --count);
                entity.setCount(count);
            }
        });
        holder.addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = entity.getCount();
                holder.countTxt.setText("" + ++count);
                entity.setCount(count);
            }
        });

        holder.countTxt.setText("" + entity.getCount());
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_deals, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.itemImg)
        ImageView itemImg;
        @Bind(R.id.loadingPanel)
        RelativeLayout loadingPanel;


        @Bind(R.id.itemTitleTxt)
        TextView itemTitleTxt;
        @Bind(R.id.itemDescTxt)
        TextView itemDescTxt;
        @Bind(R.id.itemPriceTxt)
        TextView itemPriceTxt;

        @Bind(R.id.targetLyt)
        RelativeLayout targetLyt;

        @Bind(R.id.countTxt)
        TextView countTxt;
        @Bind(R.id.itemDealTxt)
        TextView itemDealTxt;

        @Bind(R.id.subtractImg)
        ImageView subtractImg;
        @Bind(R.id.addImg)
        ImageView addImg;

        @Bind(R.id.numberPicker)
        HorizontalNumberPicker numberPicker;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            numberPicker.getButtonMinusView().setTextColor(context.getResources().getColor(R.color.colorLightGreen));
            numberPicker.getButtonMinusView().setTextColor(context.getResources().getColor(R.color.colorLightGreen));

        }

    }
}
