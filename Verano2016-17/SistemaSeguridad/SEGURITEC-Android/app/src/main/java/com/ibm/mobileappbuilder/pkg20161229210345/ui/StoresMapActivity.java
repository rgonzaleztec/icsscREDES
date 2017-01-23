

package com.ibm.mobileappbuilder.pkg20161229210345.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ibm.mobileappbuilder.pkg20161229210345.R;

import ibmmobileappbuilder.ui.BaseListingActivity;
/**
 * StoresMapActivity list activity
 */
public class StoresMapActivity extends BaseListingActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        if(isTaskRoot()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        setTitle(getString(R.string.storesMapActivity));
    }

    @Override
    protected Class<? extends Fragment> getFragmentClass() {
        return StoresMapFragment.class;
    }

}
