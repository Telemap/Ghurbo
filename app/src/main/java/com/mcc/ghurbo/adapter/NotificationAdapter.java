package com.mcc.ghurbo.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.listener.ItemClickListener;
import com.mcc.ghurbo.model.NotificationModel;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context mContext;

    private ArrayList<NotificationModel> dataList;

    private ItemClickListener itemClickListener;

    public NotificationAdapter(Context context, ArrayList<NotificationModel> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle, tvSubTitle;
        private ImageView imgVw;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            itemView.setOnClickListener(this);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvSubTitle = (TextView) itemView.findViewById(R.id.tvSubTitle);
            imgVw = (ImageView) itemView.findViewById(R.id.imgView);

        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(getLayoutPosition(), view);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view, viewType);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String title = dataList.get(position).getTitle();
        String message = dataList.get(position).getBody();

        if (dataList.get(position).getStatus().equals(AppConstants.STATUS_SEEN)) {
            holder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        } else {
            holder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.appColor));
        }

        holder.tvTitle.setText(title);
        holder.tvSubTitle.setText(message);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
