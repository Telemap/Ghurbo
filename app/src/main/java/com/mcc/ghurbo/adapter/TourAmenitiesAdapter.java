package com.mcc.ghurbo.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.model.AmenityModel;

import java.util.ArrayList;

public class TourAmenitiesAdapter extends RecyclerView.Adapter<TourAmenitiesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<AmenityModel> arrayList;

    public TourAmenitiesAdapter(Context context, ArrayList<AmenityModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;

        public ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tour_amenity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.title.setText(arrayList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}