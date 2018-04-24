package com.mcc.ghurbo.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mcc.ghurbo.R;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.listener.ItemClickListener;
import com.mcc.ghurbo.model.MyBookingModel;

import java.util.ArrayList;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.ViewHolder> {

    private Context context;

    private ItemClickListener itemClickListener;
    private ArrayList<MyBookingModel> arrayList;

    public MyBookingAdapter(Context context, ArrayList<MyBookingModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView icon;
        private TextView title, subtitle, price, checkin,
                checkout, tourDate;
        private LinearLayout tourDatePanel, hotelDatePanel;
        private CardView cardView;

        public ViewHolder(View view) {
            super(view);

            icon = (ImageView) view.findViewById(R.id.icon);
            title = (TextView) view.findViewById(R.id.title);
            subtitle = (TextView) view.findViewById(R.id.subtitle);
            price = (TextView) view.findViewById(R.id.price);
            checkin = (TextView) view.findViewById(R.id.checkin);
            checkout = (TextView) view.findViewById(R.id.checkout);
            tourDatePanel = (LinearLayout) view.findViewById(R.id.tour_date_panel);
            hotelDatePanel = (LinearLayout) view.findViewById(R.id.hotel_date_panel);
            cardView = (CardView) view.findViewById(R.id.cardView);
            tourDate = (TextView) view.findViewById(R.id.tour_date);

            itemView.setOnClickListener(this);
            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(getAdapterPosition(), view);
            }
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_booking_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Glide.with(context)
                .load(arrayList.get(position).getPhoto())
                .error(R.color.placeholder)
                .placeholder(R.color.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.icon);

        String type = arrayList.get(position).getType();
        if(type.equals(AppConstants.TYPE_HOTELS)) {
            holder.hotelDatePanel.setVisibility(View.VISIBLE);
            holder.tourDatePanel.setVisibility(View.GONE);
            holder.title.setText(arrayList.get(position).getHotelName());
        } else {
            holder.title.setText(arrayList.get(position).getTourName());
            holder.hotelDatePanel.setVisibility(View.GONE);
            holder.tourDatePanel.setVisibility(View.VISIBLE);
        }

        holder.subtitle.setText(arrayList.get(position).getLocation());
        String price = arrayList.get(position).getTotalPrice();

        if(price != null && !price.isEmpty()) {
            holder.price.setText(AppConstants.CURRENCY + price);
        } else {
            holder.price.setVisibility(View.INVISIBLE);
        }
        holder.checkin.setText(arrayList.get(position).getCheckin());
        holder.checkout.setText(arrayList.get(position).getCheckout());
        holder.tourDate.setText(arrayList.get(position).getCheckin());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}