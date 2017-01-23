
package com.ibm.mobileappbuilder.pkg20161229210345.ui;

import android.support.v4.app.Fragment;
import android.util.SparseArray;

import ibmmobileappbuilder.ui.DrawerActivity;

import ibmmobileappbuilder.actions.StartActivityAction;
import ibmmobileappbuilder.util.Constants;
import com.ibm.mobileappbuilder.pkg20161229210345.R;

public class SEGURITECMainActivity extends DrawerActivity {

    private final SparseArray<Class<? extends Fragment>> sectionFragments = new SparseArray<>();
    {
            sectionFragments.append(R.id.entry0, StoresMapFragment.class);
            sectionFragments.append(R.id.entry1, ReviewsFragment.class);
            sectionFragments.append(R.id.entry2, RankingFragment.class);
            sectionFragments.append(R.id.entry3, GraficoFragment.class);
            sectionFragments.append(R.id.entry4, AcercaFragment.class);
            sectionFragments.append(R.id.entry5, LogoutFragment.class);
    }

    @Override
    public SparseArray<Class<? extends Fragment>> getSectionFragmentClasses() {
      return sectionFragments;
    }

}
