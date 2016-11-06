package com.pentavalue.tomato.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.model.Special;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class SpecialAdapter extends RecyclerView.Adapter<SpecialAdapter.CustomViewHolder> {

    private List<Special> list;
    private Context context;

    public SpecialAdapter(Context context, List<Special> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        Special entity = (Special) list.get(position);
        holder.titleTxt.setText(entity.getName());

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
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sepcial, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.titleTxt)
        TextView titleTxt;
        @Bind(R.id.itemImg)
        ImageView itemImg;

        @Bind(R.id.loadingPanel)
        RelativeLayout loadingPanel;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
