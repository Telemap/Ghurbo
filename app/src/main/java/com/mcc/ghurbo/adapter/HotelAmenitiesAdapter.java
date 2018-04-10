package com.mcc.ghurbo.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mcc.ghurbo.R;
import com.mcc.ghurbo.model.AmenityModel;

import java.util.ArrayList;

public class HotelAmenitiesAdapter extends RecyclerView.Adapter<HotelAmenitiesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<AmenityModel> arrayList;

    public HotelAmenitiesAdapter(Context context, ArrayList<AmenityModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private ImageView icon;

        public ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            icon = (ImageView) view.findViewById(R.id.icon);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel_amenity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.title.setText(arrayList.get(position).getTitle());

        Glide.with(context)
                .load(arrayList.get(position).getIcon())
                .error(R.color.placeholder)
                .placeholder(R.color.placeholder)
                .into(holder.icon);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}