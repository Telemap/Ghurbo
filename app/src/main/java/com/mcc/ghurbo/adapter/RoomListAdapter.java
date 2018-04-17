package com.mcc.ghurbo.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mcc.ghurbo.R;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.listener.ItemClickListener;
import com.mcc.ghurbo.model.HotelDetailsModel;
import com.mcc.ghurbo.model.HotelModel;
import com.mcc.ghurbo.model.RoomDetailsModel;

import java.util.ArrayList;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.ViewHolder> {

    private Context context;

    private ItemClickListener itemClickListener;
    private ArrayList<RoomDetailsModel> arrayList;

    public RoomListAdapter(Context context, ArrayList<RoomDetailsModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView icon;
        private TextView title, subtitle, price;

        public ViewHolder(View view) {
            super(view);

            icon = (ImageView) view.findViewById(R.id.icon);
            title = (TextView) view.findViewById(R.id.title);
            subtitle = (TextView) view.findViewById(R.id.subtitle);
            price = (TextView) view.findViewById(R.id.price);

            itemView.setOnClickListener(this);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Glide.with(context)
                .load(arrayList.get(position).getThumbnailImage())
                .error(R.color.placeholder)
                .placeholder(R.color.placeholder)
                .into(holder.icon);

        holder.title.setText(arrayList.get(position).getTitle());
        holder.subtitle.setText(Html.fromHtml(arrayList.get(position).getDesc()));
        holder.price.setText(AppConstants.CURRENCY + arrayList.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}