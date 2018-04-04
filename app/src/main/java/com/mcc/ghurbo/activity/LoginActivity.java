package com.mcc.ghurbo.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.mcc.ghurbo.R;
import com.mcc.ghurbo.api.helper.RequestLogin;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.data.preference.AppPreference;
import com.mcc.ghurbo.data.preference.PrefKey;
import com.mcc.ghurbo.login.BaseLoginActivity;
import com.mcc.ghurbo.login.LoginModel;
import com.mcc.ghurbo.utility.ActivityUtils;
import com.mcc.ghurbo.utility.DialogUtils;
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
            loginGhurbo(loginModel);
        }

    }

    private void loginGhurbo(LoginModel loginModel) {

        final ProgressDialog dialogUtils = DialogUtils.showProgressDialog(LoginActivity.this, getString(R.string.loading), false);

        RequestLogin requestLogin = new RequestLogin(getApplicationContext());
        requestLogin.buildParams(loginModel.getUserId(), loginModel.getEmail(), loginModel.getPhone(), loginModel.getName(), loginModel.getProfilePic(), loginModel.getType());
        requestLogin.setResponseListener(new ResponseListener() {
            @Override
            public void onResponse(Object data) {

                if (data != null) {
                    LoginModel responseModel = (LoginModel) data;

                    AppPreference.getInstance(getApplicationContext()).setBoolean(PrefKey.LOGIN, true);

                    AppPreference.getInstance(getApplicationContext()).setString(PrefKey.USER_ID, responseModel.getUserId());
                    AppPreference.getInstance(getApplicationContext()).setString(PrefKey.EMAIL, responseModel.getEmail());
                    AppPreference.getInstance(getApplicationContext()).setString(PrefKey.PHONE, responseModel.getPhone());
                    AppPreference.getInstance(getApplicationContext()).setString(PrefKey.NAME, responseModel.getName());
                    AppPreference.getInstance(getApplicationContext()).setString(PrefKey.PROFILE_PIC, responseModel.getProfilePic());

                    ActivityUtils.getInstance().invokeActivity(LoginActivity.this, MainActivity.class, true);
                } else {
                    Utils.showToast(getApplicationContext(), getString(R.string.login_failed));
                }

                DialogUtils.dismissProgressDialog(dialogUtils);
            }
        });
        requestLogin.execute();





    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.noInternetWarning(btnLoginSkip, getApplicationContext());
    }
}
