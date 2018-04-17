package com.mcc.ghurbo.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mcc.ghurbo.R;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.listener.ItemClickListener;
import com.mcc.ghurbo.model.HotelModel;
import com.mcc.ghurbo.model.TourModel;

import java.util.ArrayList;

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.ViewHolder> {

    private Context context;

    private ItemClickListener itemClickListener;
    private ArrayList<HotelModel> arrayList;

    public HotelListAdapter(Context context, ArrayList<HotelModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView icon;
        private TextView title, subtitle, price;
        private RatingBar rating;

        public ViewHolder(View view) {
            super(view);

            icon = (ImageView) view.findViewById(R.id.icon);
            title = (TextView) view.findViewById(R.id.title);
            subtitle = (TextView) view.findViewById(R.id.subtitle);
            price = (TextView) view.findViewById(R.id.price);
            rating = (RatingBar) view.findViewById(R.id.rating);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Glide.with(context)
                .load(arrayList.get(position).getThumbnailImage())
                .error(R.color.placeholder)
                .placeholder(R.color.placeholder)
                .into(holder.icon);

        holder.title.setText(arrayList.get(position).getHotelTitle());
        holder.subtitle.setText(Html.fromHtml(arrayList.get(position).getHotelDescription()));
        holder.price.setText(AppConstants.CURRENCY + arrayList.get(position).getBasicPrice());

        float rate = arrayList.get(position).getHotelStars();
        if (rate == 0) {
            holder.rating.setVisibility(View.GONE);
        } else {
            holder.rating.setVisibility(View.VISIBLE);
            holder.rating.setRating(rate);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}