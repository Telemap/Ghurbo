package com.mcc.ghurbo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mcc.ghurbo.R;
import com.mcc.ghurbo.listener.ItemClickListener;

import java.util.ArrayList;

public class MultiZoomImageAdapter extends PagerAdapter {

    private ArrayList<String> images;
    private LayoutInflater inflater;
    private Context mContext;

    // Listener
    private ItemClickListener mListener;

    public MultiZoomImageAdapter(Context context, ArrayList<String> images) {
        this.mContext = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup view, final int position) {

        View imageLayout = inflater.inflate(R.layout.item_multi_zoom_image, view, false);
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);

        Glide.with(mContext)
                .load(images.get(position))
                .placeholder(R.color.placeholder)
                .into(imageView);

        view.addView(imageLayout);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null) {
                    mListener.onItemClick(position, view);
                }
            }
        });

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    public void setItemClickListener(ItemClickListener mListener){
        this.mListener = mListener;
    }


}
