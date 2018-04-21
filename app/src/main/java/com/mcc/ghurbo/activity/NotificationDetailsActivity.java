package com.mcc.ghurbo.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.widget.TextView;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.data.sqlite.DbConstants;
import com.mcc.ghurbo.model.NotificationModel;

public class NotificationDetailsActivity extends BaseActivity{

    private NotificationModel notificationModel;

    private TextView tvTitle, tvBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVar();
        initView();
        initFunctionality();
        invokeMessenger();
    }

    private void initVar() {
        Intent intent = getIntent();

        if(intent.hasExtra(AppConstants.BUNDLE_NOTI_DETAILS)) {
            notificationModel = intent.getParcelableExtra(AppConstants.BUNDLE_NOTI_DETAILS);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_notification_details);
        initToolbar();
        enableBackButton();
        initLoader();
        setToolbarTitle(getString(R.string.notifications));

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvBody = (TextView) findViewById(R.id.tv_body);
    }

    private void initFunctionality() {
        tvTitle.setText(notificationModel.getTitle());
        tvBody.setText(notificationModel.getBody());

        updateNotificationStatus(notificationModel.getId());
    }

    public void updateNotificationStatus(int id) {

        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_STATUS, AppConstants.STATUS_SEEN);

        // Which row to update, based on the ID
        String selection = DbConstants._ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        getContentResolver().update(DbConstants.NOTI_CONTENT_URI, values, selection, selectionArgs);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
