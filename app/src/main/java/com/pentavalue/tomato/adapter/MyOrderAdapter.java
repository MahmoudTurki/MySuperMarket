package com.pentavalue.tomato.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pentavalue.tomato.R;
import com.pentavalue.tomato.data.constants.Params;
import com.pentavalue.tomato.model.Orders;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Mahmoud Turki
 */
public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.CustomViewHolder> {

    private List<Orders> list;
    private static Context context;

    public MyOrderAdapter(Context context, List<Orders> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        final Orders entity = (Orders) list.get(position);
        switch (entity.getOrderStatus()) {
            case Params.Order.ONGOING:
                holder.deliveryTimeTxt.setText(R.string.order_expected_delivery_time);
                holder.orderStatusTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                break;
            case Params.Order.DELIVERED:
                holder.deliveryTimeTxt.setText(R.string.order_delivery_time);
                holder.orderStatusTxt.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.myorder_deliverd, 0, 0);
                holder.orderStatusTxt.setText(R.string.delivered);
                break;
            case Params.Order.NO_ACTION:
                holder.deliveryTimeTxt.setText(R.string.order_delivery_time);
                holder.orderStatusTxt.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.myorder_no_action, 0, 0);
                holder.orderStatusTxt.setText(R.string.no_action);
                break;
        }

        holder.orderNumberValueTxt.setText(entity.getOrderNumber());
        holder.orderTotalPriceValueTxt.setText(entity.getOrderPrice());
        holder.orderDeliveryTimeValueTxt.setText(entity.getDeliveryTime());

    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_orders, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.orderStatusTxt)
        TextView orderStatusTxt;
        @Bind(R.id.orderNumberValueTxt)
        TextView orderNumberValueTxt;
        @Bind(R.id.deliveryTimeTxt)
        TextView deliveryTimeTxt;

        @Bind(R.id.orderDeliveryTimeValueTxt)
        TextView orderDeliveryTimeValueTxt;

        @Bind(R.id.orderTotalPriceValueTxt)
        TextView orderTotalPriceValueTxt;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
