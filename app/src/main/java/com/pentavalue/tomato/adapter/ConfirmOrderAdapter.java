package com.pentavalue.tomato.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.model.Items;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class ConfirmOrderAdapter extends RecyclerView.Adapter<ConfirmOrderAdapter.CustomViewHolder> {

    private List<Items> list;
    private static Context context;

    public ConfirmOrderAdapter(Context context, List<Items> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        final Items entity = (Items) list.get(position);
        holder.itemTitleTxt.setText(entity.getName());
        holder.itemCountTxt.setText(entity.getCount());
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_confirm_order, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.itemTitleTxt)
        TextView itemTitleTxt;
        @Bind(R.id.itemCountTxt)
        TextView itemCountTxt;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
