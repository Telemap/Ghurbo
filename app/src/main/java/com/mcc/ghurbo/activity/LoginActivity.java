package com.mcc.ghurbo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.mcc.ghurbo.R;
import com.mcc.ghurbo.api.helper.RequestLogin;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.data.preference.AppPreference;
import com.mcc.ghurbo.data.preference.PrefKey;
import com.mcc.ghurbo.login.BaseLoginActivity;
import com.mcc.ghurbo.login.LoginModel;
import com.mcc.ghurbo.model.SearchHotelModel;
import com.mcc.ghurbo.model.SearchTourModel;
import com.mcc.ghurbo.utility.ActivityUtils;
import com.mcc.ghurbo.utility.DialogUtils;
import com.mcc.ghurbo.utility.Utils;

public class LoginActivity extends BaseLoginActivity {

    private SearchTourModel searchTourModel;
    private SearchHotelModel searchHotelModel;
    private boolean fromBooking;

    private Button btnLoginSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVars();
        initView();
        initFunctionality();
        initListener();

    }

    private void initVars() {
        Intent intent = getIntent();

        if (intent.hasExtra(AppConstants.BUNDLE_KEY_HOTEL_MODEL)) {
            searchHotelModel = intent.getParcelableExtra(AppConstants.BUNDLE_KEY_HOTEL_MODEL);
        }

        if (intent.hasExtra(AppConstants.BUNDLE_KEY_TOUR_MODEL)) {
            searchTourModel = intent.getParcelableExtra(AppConstants.BUNDLE_KEY_TOUR_MODEL);
        }

        if (intent.hasExtra(AppConstants.BUNDLE_FROM_BOOKING)) {
            fromBooking = intent.getBooleanExtra(AppConstants.BUNDLE_FROM_BOOKING, false);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_login);
        btnLoginSkip = (Button) findViewById(R.id.btn_skip_login);

    }

    private void initFunctionality() {
        initFbLogin((LoginButton) findViewById(R.id.btn_login_facebook));
        initGoogleLogin((SignInButton) findViewById(R.id.btn_login_google));
        setGoogleButtonText((SignInButton) findViewById(R.id.btn_login_google), getString(R.string.google_cap));

        if(fromBooking) {
            btnLoginSkip.setVisibility(View.INVISIBLE);
        }
    }

    private void initListener() {
        btnLoginSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPreference.getInstance(getApplicationContext()).setBoolean(PrefKey.SKIPPED, true);
                ActivityUtils.getInstance().invokeActivity(LoginActivity.this, MainActivity.class, true);
            }
        });
    }

    @Override
    public void onLoginResponse(LoginModel loginModel) {

        if(loginModel.getUserId() != null) {
            if(fromBooking) {
                ActivityUtils.getInstance().invokeProfilingActivity(LoginActivity.this, loginModel, searchTourModel, searchHotelModel);
            } else {
                ActivityUtils.getInstance().invokeProfilingActivity(LoginActivity.this, loginModel);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.noInternetWarning(btnLoginSkip, getApplicationContext());
    }
}
