package com.mcc.ghurbo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.mcc.ghurbo.R;
import com.mcc.ghurbo.data.preference.AppPreference;
import com.mcc.ghurbo.data.preference.PrefKey;
import com.mcc.ghurbo.login.BaseLoginActivity;
import com.mcc.ghurbo.login.LoginModel;
import com.mcc.ghurbo.utility.ActivityUtils;
import com.mcc.ghurbo.utility.Utils;

public class LoginActivity extends BaseLoginActivity {

    private Button btnLoginSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initFunctionality();
        initListener();

    }

    private void initView() {
        setContentView(R.layout.activity_login);
        btnLoginSkip = (Button) findViewById(R.id.btn_skip_login);
    }

    private void initFunctionality() {
        initFbLogin((LoginButton) findViewById(R.id.btn_login_facebook));
        initGoogleLogin((SignInButton) findViewById(R.id.btn_login_google));
        setGoogleButtonText((SignInButton) findViewById(R.id.btn_login_google), getString(R.string.google_cap));
        initPhoneLogin((Button) findViewById(R.id.btn_login_phone));
        initEmailLogin((Button) findViewById(R.id.btn_email_login));
    }

    private void initListener() {
        btnLoginSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onLoginResponse(LoginModel loginModel) {

        if(loginModel.getUserId() != null) {
            loginGhurbo(loginModel);
        }

    }

    private void loginGhurbo(LoginModel loginModel) {
        AppPreference.getInstance(getApplicationContext()).setBoolean(PrefKey.LOGIN, true);


        AppPreference.getInstance(getApplicationContext()).setString(PrefKey.USER_ID, loginModel.getUserId());
        AppPreference.getInstance(getApplicationContext()).setString(PrefKey.EMAIL, loginModel.getEmail());
        AppPreference.getInstance(getApplicationContext()).setString(PrefKey.PHONE, loginModel.getPhone());
        AppPreference.getInstance(getApplicationContext()).setString(PrefKey.NAME, loginModel.getName());
        AppPreference.getInstance(getApplicationContext()).setString(PrefKey.PROFILE_PIC, loginModel.getProfilePic());

        ActivityUtils.getInstance().invokeActivity(LoginActivity.this, MainActivity.class, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.noInternetWarning(btnLoginSkip, getApplicationContext());
    }
}
