
package com.ibm.mobileappbuilder.pkg20161229210345.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ibm.mobileappbuilder.pkg20161229210345.R;

import ibmmobileappbuilder.ui.BaseDetailActivity;
/**
 * GraficoActivity chart screen
 */
public class GraficoActivity extends BaseDetailActivity {


    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);        
        
        if(isTaskRoot()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
		
        setTitle(getString(R.string.graficoActivity));
    }   

    // DetailActivity interface

    @Override
    protected Class<? extends Fragment> getFragmentClass() {
        return GraficoFragment.class;
    }

}

