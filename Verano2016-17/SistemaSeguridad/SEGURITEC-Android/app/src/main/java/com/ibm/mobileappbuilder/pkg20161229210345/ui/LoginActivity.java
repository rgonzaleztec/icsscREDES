
package com.ibm.mobileappbuilder.pkg20161229210345.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.ibm.mobileappbuilder.pkg20161229210345.MyApplication;
import com.ibm.mobileappbuilder.pkg20161229210345.R;

import java.util.Map;

import ibmmobileappbuilder.services.LoginService;
import ibmmobileappbuilder.services.DefaultLoginService;
import ibmmobileappbuilder.ui.BaseLoginActivity;
import ibmmobileappbuilder.util.ConnectivityUtils;
import ibmmobileappbuilder.util.LoginUtils;

public class LoginActivity extends BaseLoginActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //set up the SharedPreference to store the login process
        setSharedPreferences(((MyApplication) getApplication()).getSecureSharedPreferences());

        super.onCreate(savedInstanceState);
    }

    //Customize this method to use another Login Service or change its behaviour
    @Override
    public LoginService createLoginService() {

        return new DefaultLoginService(getString(R.string.login_url)){
            @Override
            protected void onPostExecute(Map<String, String> loginParams) {

                String expirationTimeString = loginParams.get(LoginUtils.EXPIRATION_TIME);
                if (expirationTimeString != null) {
                    SharedPreferences.Editor editor = getSharedPreferences().edit();
                    Long expirationTime = Long.parseLong(expirationTimeString);
                    // store the login info
                    editor.putLong(LoginUtils.EXPIRATION_TIME, expirationTime)
                        .putString(LoginUtils.LAST_USER, getEmail())
                        .putLong(LoginUtils.SUSPENDED_DATE, System.currentTimeMillis())
                        .putString(LoginUtils.TOKEN, loginParams.get(LoginUtils.TOKEN))
                        .commit();

                    // redirect to main activity
                    Intent goToMainActivity = new Intent(getApplicationContext(), SEGURITECMainActivity.class);
                    startActivity(goToMainActivity);
                    finish();

                } else {
                    showProgress(false);
                    String error = "";
                    if(ConnectivityUtils.isConnected(getBaseContext())){
                        error = getResources().getString(R.string.error_login);
                    }else{
                        error = getResources().getString(R.string.error_network_unavailable);
                    }
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
                }
                setLoginTaskRunning(false);
            }
        };
    }

    @Override
    public boolean isEmailValid(String email) {
        //Default = true; Replace with your own logic
        return super.isEmailValid(email);
    }

    @Override
    public boolean isPasswordValid(String password) {
        //Default = true; Replace with your own logic
        return super.isPasswordValid(password);
    }
}

