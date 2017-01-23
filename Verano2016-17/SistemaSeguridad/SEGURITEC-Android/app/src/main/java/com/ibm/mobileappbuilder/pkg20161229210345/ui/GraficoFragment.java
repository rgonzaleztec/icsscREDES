
package com.ibm.mobileappbuilder.pkg20161229210345.ui;
import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.util.ColorUtils;
import ibmmobileappbuilder.util.StringUtils;

import ibmmobileappbuilder.charts.ui.LineChartFragment;
import android.widget.TextView;
import ibmmobileappbuilder.util.StringUtils;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.ibm.mobileappbuilder.pkg20161229210345.ds.StoresDSItem;
import com.ibm.mobileappbuilder.pkg20161229210345.ds.StoresDS;
import static ibmmobileappbuilder.analytics.injector.PageViewBehaviorInjector.pageViewBehavior;

import static ibmmobileappbuilder.analytics.injector.PageViewBehaviorInjector.pageViewBehavior;

public class GraficoFragment extends LineChartFragment<StoresDSItem> {

    private Datasource<StoresDSItem> datasource;
    private SearchOptions searchOptions;

    public static GraficoFragment newInstance(Bundle args){
        GraficoFragment fragment = new GraficoFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public GraficoFragment(){
        super();
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        addBehavior(pageViewBehavior("Grafico"));
    }

    @Override
      protected Datasource<StoresDSItem> getDatasource() {
        if (datasource != null) {
          return datasource;
        }
          searchOptions = SearchOptions.Builder.searchOptions().build();
         datasource = StoresDS.getInstance(searchOptions);
            return datasource;
      }

    @Override
    public void loadChart(List<StoresDSItem> items) {
        if (items.size() != 0) {
            setChartTitle("Horizontal Axis");

            List<String> XAxisValues = new ArrayList<String>();
            for (StoresDSItem item : items) {
                XAxisValues.add((item.rating != null) ? StringUtils.intToString(item.rating) : "");
            }
            setXAxisValues(XAxisValues);

            List<Number> series;
            
            series = new ArrayList<Number>();
            for (StoresDSItem item : items){
            	Number value = StringUtils.StringToNumber(StringUtils.intToString(item.rating));
            	
            	if(value != null) {
            		series.add(value);
            	}	
            }
            addSeries(series, Color.parseColor("#e59062"), "Legend Entry 1");
        }
    }
}


