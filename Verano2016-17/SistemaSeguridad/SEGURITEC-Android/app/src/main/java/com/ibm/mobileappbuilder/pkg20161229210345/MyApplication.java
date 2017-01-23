

package com.ibm.mobileappbuilder.pkg20161229210345;

import android.app.Application;
import ibmmobileappbuilder.injectors.ApplicationInjector;
import android.support.multidex.MultiDexApplication;
import ibmmobileappbuilder.analytics.injector.AnalyticsReporterInjector;
import android.app.Activity;
import android.os.Bundle;
import ibmmobileappbuilder.util.SecurePreferences;
import com.ibm.mobileappbuilder.pkg20161229210345.ui.LoginActivity;
import ibmmobileappbuilder.util.LoginUtils;
import ibmmobileappbuilder.push.BluemixPushWrapper;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;


/**
 * You can use this as a global place to keep application-level resources
 * such as singletons, services, etc.
 */
public class MyApplication extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {
    private SecurePreferences mSharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationInjector.setApplicationContext(this);
        AnalyticsReporterInjector.analyticsReporter(this).init(this,
                getString(R.string.analyticsApiKey),
                getString(R.string.bmdBluemixRegion),
                getString(R.string.app_name));
        mSharedPreferences = new SecurePreferences(this);
        registerActivityLifecycleCallbacks(this);

        mSharedPreferences.edit().putLong(LoginUtils.EXPIRATION_TIME, LoginUtils.SESSION_EXPIRED).apply();
        BluemixPushWrapper bluemixPushWrapper = new BluemixPushWrapper();
        bluemixPushWrapper.register(this,
            getString(R.string.bmdBluemixRegion),
            getString(R.string.pushAppGuid),
            getString(R.string.pushClientSecret)
        );
    }

    public SecurePreferences getSecureSharedPreferences() {
        return mSharedPreferences;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        boolean splashShown = false;
        if(!splashShown && !(activity instanceof LoginActivity) ){
            LoginUtils.checkLoggedStatus(mSharedPreferences, LoginActivity.class, activity);
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if(!(activity instanceof LoginActivity) ) {
            LoginUtils.storeLastActiveStatus(mSharedPreferences);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }


}
