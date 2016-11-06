package com.pentavalue.tomato.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.model.Notification;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.CustomViewHolder> {

    private List<?> list;
    private Context context;

    public NotificationsAdapter(Context context, List<?> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        Notification entity = (Notification) list.get(position);
        holder.titleTxt.setText(entity.getName());
        holder.dateTxt.setText(entity.getDate());
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notifications, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.titleTxt)
        TextView titleTxt;
        @Bind(R.id.dateTxt)
        TextView dateTxt;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
