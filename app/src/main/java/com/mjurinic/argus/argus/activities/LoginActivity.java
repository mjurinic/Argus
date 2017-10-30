package com.mjurinic.argus.argus.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mjurinic.argus.argus.R;
import com.mjurinic.argus.argus.helpers.SharedPrefsHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {

    private static final String FACEBOOK_TOKEN = "facebook_token";

    @BindView(R.id.login_button)
    LoginButton loginButton;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        callbackManager = CallbackManager.Factory.create();

        if (!isLoggedIn()) initFacebookLogin();
        else startActivity(new Intent(getApplicationContext(), CameraActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isLoggedIn() {
        return AccessToken.getCurrentAccessToken() != null;
    }

    private void initFacebookLogin() {
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                SharedPrefsHelper.storeString(getApplicationContext(), FACEBOOK_TOKEN, loginResult.getAccessToken().getToken());
                startActivity(new Intent(getApplicationContext(), CameraActivity.class));
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Timber.d(error.getMessage());
            }
        });
    }
}
