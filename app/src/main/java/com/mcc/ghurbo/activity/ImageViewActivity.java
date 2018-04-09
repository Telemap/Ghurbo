package com.mcc.ghurbo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.adapter.MultiZoomImageAdapter;
import com.mcc.ghurbo.data.constant.AppConstants;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class ImageViewActivity extends BaseActivity {

    private ArrayList<String> images;

    private ViewPager mPager;
    private MultiZoomImageAdapter multiZoomImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariables();
        initView();
        loadPhotos(images);
    }

    private void initVariables() {
        Intent intent = getIntent();

        if(intent.hasExtra(AppConstants.BUNDLE_MULTI_IMAGE)) {
            images = intent.getStringArrayListExtra(AppConstants.BUNDLE_MULTI_IMAGE);
        }

    }

    private void initView() {
        setContentView(R.layout.activity_image_view);

        mPager = (ViewPager) findViewById(R.id.pager);

    }

    private void loadPhotos(final ArrayList<String> images) {

        if (images != null && !images.isEmpty()) {
            multiZoomImageAdapter = new MultiZoomImageAdapter(getApplicationContext(), images);
            mPager.setAdapter(multiZoomImageAdapter);
            CircleIndicator indicator = (CircleIndicator) findViewById(R.id.sliderIndicator);
            indicator.setViewPager(mPager);
        }
    }
}
