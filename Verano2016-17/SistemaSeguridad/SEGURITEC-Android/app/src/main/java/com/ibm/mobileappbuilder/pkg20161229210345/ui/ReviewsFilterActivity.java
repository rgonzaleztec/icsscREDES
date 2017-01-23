

package com.ibm.mobileappbuilder.pkg20161229210345.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Arrays;

import com.ibm.mobileappbuilder.pkg20161229210345.R;

import ibmmobileappbuilder.ui.BaseFragment;
import ibmmobileappbuilder.ui.FilterActivity;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;

import ibmmobileappbuilder.views.DateRangePicker;
import java.util.Date;

/**
 * ReviewsFilterActivity filter activity
 */
public class ReviewsFilterActivity extends FilterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isTaskRoot()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // set title
        setTitle(R.string.reviewsFilterActivity);
    }

    @Override
    protected Fragment getFragment() {
        return new PlaceholderFragment();
    }

    public static class PlaceholderFragment extends BaseFragment {
        private SearchOptions.Builder searchOptionsBuilder = SearchOptions.Builder.searchOptions();
        private SearchOptions searchOptions;

        
        long reviewedon_min = -1;
        long reviewedon_max = -1;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.reviews_filter, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            // Get saved values
            Bundle bundle = savedInstanceState;
            if(bundle == null) {
                bundle = getArguments();
            }
            searchOptions = bundle.getParcelable("search_options");

            // get initial data
            
            reviewedon_max = bundle.getLong("reviewedon_max", -1);
            reviewedon_min = bundle.getLong("reviewedon_min", -1);
            // bind pickers
            
            final DateRangePicker reviewedon_view = (DateRangePicker) view.findViewById(R.id.reviewedon_filter);
            reviewedon_view.setMinDate(reviewedon_min != -1? new Date(reviewedon_min) : null)
                .setMaxDate(reviewedon_max != -1? new Date(reviewedon_max) : null)
                .setRangeSelectedListener(new DateRangePicker.DateRangeSelectedListener() {
                @Override
                public void onRangeSelected(Date dateMin, Date dateMax) {
                    reviewedon_min = dateMin != null ? dateMin.getTime() : -1;
                    reviewedon_max = dateMax != null ? dateMax.getTime() : -1;
                }
            });
            // Bind buttons
            Button okBtn = (Button) view.findViewById(R.id.ok);
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();

                    // send filter result back to caller
                                        
                    intent.putExtra("reviewedon_min", reviewedon_min);
                    intent.putExtra("reviewedon_max", reviewedon_max);

                    getActivity().setResult(RESULT_OK, intent);
                    getActivity().finish();
                }
            });

            Button cancelBtn = (Button) view.findViewById(R.id.reset);
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Reset values
                                        
                    reviewedon_view.setMinDate(null);
                    reviewedon_min = -1;
                    reviewedon_view.setMaxDate(null);
                    reviewedon_max = -1;
                }
            });
        }

        @Override
        public void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);

            // save current status
            bundle.putParcelable("search_options", searchOptions);
            
            bundle.putLong("reviewedon_max", reviewedon_max);
            bundle.putLong("reviewedon_min", reviewedon_min);
        }
    }

}
