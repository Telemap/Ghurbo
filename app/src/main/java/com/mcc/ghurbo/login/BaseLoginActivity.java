package com.mcc.ghurbo.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mcc.ghurbo.R;

import org.json.JSONObject;

public abstract class BaseLoginActivity extends AppCompatActivity {

    private static final int FB_REQUEST_CODE = 99,
            PERMISSION_REQ = 100,
            GOOGLE_REQUEST_CODE = 101;
    private CallbackManager callbackManager;

    //private SignInButton btnLoginGPlus;
    private GoogleApiClient mGoogleApiClient;

    private boolean grantPermission() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.RECEIVE_SMS}, PERMISSION_REQ);

            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PERMISSION_REQ) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initPhoneLogin();
            } else {
                Toast.makeText(BaseLoginActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void initPhoneLogin(Button btnPhoneLogin) {
        if (btnPhoneLogin != null) {
            btnPhoneLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initPhoneLogin();
                }
            });
        }
    }

    private void initPhoneLogin() {
        if (grantPermission()) {
            final Intent intent = new Intent(BaseLoginActivity.this, AccountKitActivity.class);
            AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                    new AccountKitConfiguration.AccountKitConfigurationBuilder(
                            LoginType.PHONE,
                            AccountKitActivity.ResponseType.TOKEN);


            // enable receive auto sms
            configurationBuilder.setReadPhoneStateEnabled(true);
            configurationBuilder.setReceiveSMS(true);
            configurationBuilder.setDefaultCountryCode("BD");

            intent.putExtra(
                    AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                    configurationBuilder.build());
            startActivityForResult(intent, FB_REQUEST_CODE);
        }
    }

    public void initEmailLogin(Button btnEmailLogin) {
        if (btnEmailLogin != null) {

            btnEmailLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Intent intent = new Intent(BaseLoginActivity.this, AccountKitActivity.class);
                    AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                            new AccountKitConfiguration.AccountKitConfigurationBuilder(
                                    LoginType.EMAIL,
                                    AccountKitActivity.ResponseType.TOKEN);
                    intent.putExtra(
                            AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                            configurationBuilder.build());
                    startActivityForResult(intent, FB_REQUEST_CODE);

                }
            });
        }
    }

    public void initFbLogin(LoginButton loginButton) {

        if(loginButton != null) {
            loginButton.setVisibility(View.VISIBLE);
            loginButton.setReadPermissions("email");

            callbackManager = CallbackManager.Factory.create();

            // Callback registration
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    getUserInfo(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {
                    Toast.makeText(BaseLoginActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(BaseLoginActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void initGoogleLogin(SignInButton btnLoginGPlus) {
        if(btnLoginGPlus != null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {
                            Toast.makeText(BaseLoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

            btnLoginGPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, GOOGLE_REQUEST_CODE);
                }
            });
        }

    }

    public void setGoogleButtonText(SignInButton btnLoginGPlus, String buttonText) {
        if (btnLoginGPlus != null) {
            for (int i = 0; i < btnLoginGPlus.getChildCount(); i++) {
                View v = btnLoginGPlus.getChildAt(i);

                if (v instanceof TextView) {
                    TextView tv = (TextView) v;
                    tv.setText(buttonText);
                    return;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FB_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (loginResult.getError() != null) {
                Toast.makeText(BaseLoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            } else if (loginResult.wasCancelled()) {
                Toast.makeText(BaseLoginActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                getUserInfo();
            }
        } else if (requestCode == GOOGLE_REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                getUserInfo(result);
            } else {
                Toast.makeText(BaseLoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getUserInfo(GoogleSignInResult result) {
        GoogleSignInAccount account = result.getSignInAccount();

        Uri uri = account.getPhotoUrl();
        String photo = "";
        if (uri != null) {
            photo = uri.toString();
        }
        onLoginResponse(new LoginModel(account.getId(), account.getDisplayName(), photo, account.getEmail(), null, LoginModel.TYPE_GOOGLE));
    }

    private void getUserInfo() {
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                String accountKitId = account.getId();

                // Get phone number
                PhoneNumber phoneNumber = account.getPhoneNumber();
                String phoneNumberString = null;
                if (phoneNumber != null) {
                    phoneNumberString = phoneNumber.toString();
                    Log.e("AccKit", "Phone: " + phoneNumberString);
                }

                // Get email
                String email = account.getEmail();

                onLoginResponse(new LoginModel(accountKitId, null, null, email, phoneNumberString, LoginModel.TYPE_ACCOUNT_KIT));

            }

            @Override
            public void onError(final AccountKitError error) {
                Log.e("AccKit", "Error: " + error.toString());
            }
        });
    }

    private void getUserInfo(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String email = object.getString("email");
                            String profilePic = "https://graph.facebook.com/" + id + "/picture?width=200&height=150";

                            onLoginResponse(new LoginModel(id, name, profilePic, email, null, LoginModel.TYPE_FACEBOOK));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void logout() {
        AccountKit.logOut();
    }

    protected abstract void onLoginResponse(LoginModel loginModel);

}