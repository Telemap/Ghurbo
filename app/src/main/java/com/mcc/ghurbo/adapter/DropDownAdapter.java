package com.mcc.ghurbo.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.listener.ItemClickListener;
import com.mcc.ghurbo.model.LocationModel;

import java.util.ArrayList;

public class DropDownAdapter extends RecyclerView.Adapter<DropDownAdapter.ViewHolder> {

    private ItemClickListener itemClickListener;
    private ArrayList<LocationModel> arrayList;

    public DropDownAdapter(ArrayList<LocationModel> arrayList) {
        this.arrayList = arrayList;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titleView;

        public ViewHolder(View view) {
            super(view);

            titleView = (TextView) view.findViewById(R.id.title);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_location, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.titleView.setText(arrayList.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}