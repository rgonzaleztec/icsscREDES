/*
 * Copyright 2016.
 * This code is part of IBM Mobile UI Builder
 */

package ibmmobileappbuilder.charts.ui;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ibmmobileappbuilder.charts.R;
import ibmmobileappbuilder.charts.formatter.CustomYAxisValueFormatter;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.Pagination;
import ibmmobileappbuilder.ds.filter.Filter;
import ibmmobileappbuilder.ui.BaseFragment;
import ibmmobileappbuilder.ui.Filterable;
import ibmmobileappbuilder.ui.Refreshable;
import ibmmobileappbuilder.util.ColorUtils;

public abstract class BarChartFragment<T> extends BaseFragment
        implements ChartFragment, Filterable, Refreshable {

    private static String BUNDLE_XY_SERIES_MAP = "xy_series_map";
    private static String BUNDLE_XY_VALUES = "xy_values";
    private static String BUNDLE_SERIES_NAME = "series_name";
    private static String BUNDLE_BAR_TITLE = "bar_title";

    private BarChart barChart;
    private Datasource<T> datasource;
    private List<String> xAxisValues;
    private Map<List<Number>, Integer> xySeriesMap;
    private TextView barTitle;

    protected View barChartContainer;
    protected View progressContainer;

    private List<String> seriesName = new ArrayList<String>();

    private int textColor;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        //use LinkedHashMap for ordered access
        xySeriesMap = new LinkedHashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            datasource = getDatasource();
        }

        View view = inflater.inflate(R.layout.fragment_bar_chart, container, false);

        barChart = (BarChart) view.findViewById(R.id.bar_chart);
        barChartContainer = view.findViewById(R.id.bar_chart_container);
        progressContainer = view.findViewById(R.id.progressContainer);
        barTitle = (TextView) view.findViewById(R.id.bar_title);

        textColor = getResources().getColor(R.color.textColor);

        // initialize chart
        barChartSetup();

        return view;
    }

    private void barChartSetup() {
        barChart.setDescription("");
        barChart.setNoDataText(getString(R.string.chart_no_info));
        Paint p = barChart.getPaint(Chart.PAINT_INFO);
        p.setColor(textColor);

        barChart.setExtraOffsets(0, 0, 0, 0);

        barChart.setDragDecelerationFrictionCoef(0.95f);

        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);

        barChart.setDrawGridBackground(false);
        barChart.setHighlightPerTapEnabled(true);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.animateY(getResources().getInteger(R.integer.animation_duration), Easing.EasingOption.EaseInOutCirc);
        barChart.setScaleEnabled(true);

        Legend legend = barChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setXEntrySpace(4f);
        legend.setWordWrapEnabled(true);
        legend.setTextSize(getResources().getDimension(R.dimen.legend_text_font_size));
        legend.setTextColor(textColor);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setFormSize(getResources().getDimension(R.dimen.legend_form_size));

        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(textColor);
        xAxis.setTextSize(getResources().getDimension(R.dimen.x_axis_domain_label_font_size));

        YAxis left = barChart.getAxisLeft();
        left.setSpaceTop(0.15f);
        left.setAxisMinValue(0.0f);
        left.setValueFormatter(new CustomYAxisValueFormatter());
        left.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        left.setTextColor(textColor);
        left.setTextSize(getResources().getDimension(R.dimen.y_axis_domain_label_font_size));

        YAxis right = barChart.getAxisRight();
        right.setSpaceTop(0.15f);
        right.setAxisMinValue(0.0f);
        right.setValueFormatter(new CustomYAxisValueFormatter());
        right.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        right.setTextColor(textColor);
        right.setTextSize(getResources().getDimension(R.dimen.y_axis_domain_label_font_size));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState == null) {
            loadData();
        } else {
            xySeriesMap = (Map<List<Number>, Integer>) savedInstanceState.getSerializable(BUNDLE_XY_SERIES_MAP);
            xAxisValues = savedInstanceState.getStringArrayList(BUNDLE_XY_VALUES);
            seriesName = savedInstanceState.getStringArrayList(BUNDLE_SERIES_NAME);
            barTitle.setText(savedInstanceState.getString(BUNDLE_BAR_TITLE));

            if(xySeriesMap != null && xAxisValues != null && seriesName != null && barTitle != null) {
                drawChart();
            } else {
                loadData();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        if(xySeriesMap != null) {
            savedInstanceState.putSerializable(BUNDLE_XY_SERIES_MAP, (Serializable) xySeriesMap);
            savedInstanceState.putStringArrayList(BUNDLE_XY_VALUES, (ArrayList<String>) xAxisValues);
            savedInstanceState.putStringArrayList(BUNDLE_SERIES_NAME, (ArrayList<String>) seriesName);
			
			if(barTitle != null && barTitle.getText() != null) {
				savedInstanceState.putString(BUNDLE_BAR_TITLE, barTitle.getText().toString());
			}	
        }
    }

    private void loadData() {
        //set up the listener for charts
        Datasource.Listener dsListener = new Datasource.Listener<List<T>>() {
            @Override
            public void onSuccess(List<T> ts) {
                if (barChart != null && ts.size() != 0) {
                    loadChart(ts);
                    drawChart();
                }
                setListShown(true);
            }

            @Override
            public void onFailure(Exception e) {
                setListShown(true);
            }
        };

        if (datasource != null) {
            setListShown(false);
            if (datasource instanceof Pagination) {
                ((Pagination) datasource).getItems(0, dsListener);
            } else {
                datasource.getItems(dsListener);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        barChart = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        datasource = null;
    }

    @Override
    public void setChartTitle(String title) {
        barTitle.setText(title);
    }

    @Override
    public void addSeries(List<Number> series, int seriesColor, String label) {
        if(series != null) {
            xySeriesMap.put(series, seriesColor);
            seriesName.add(label);
        }
    }

    public void setXAxisValues(List<String> values) {
        xAxisValues = values;
    }

    @Override
    public void drawChart() {
        List<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        ArrayList<BarEntry> entries;

        int index = 0;
        for(List<Number> series : xySeriesMap.keySet()) {
            entries = new ArrayList<>();

            for(int i=0;i<series.size();i++) {
                entries.add(new BarEntry((float) series.get(i), i));
            }

            BarDataSet barDataSet = new BarDataSet(entries, seriesName.get(index));
            barDataSet.setColor(xySeriesMap.get(series));
            barDataSet.setDrawValues(false);

            dataSets.add(barDataSet);
            index++;
        }

        BarData data = new BarData(xAxisValues, dataSets);
        barChart.setData(data);
    }

    @Override
    public void onSearchTextChanged(String s) {
        datasource.onSearchTextChanged(s);
        refresh();
    }

    @Override
    public void addFilter(Filter filter) {
        datasource.addFilter(filter);
    }

    @Override
    public void clearFilters() {
        datasource.clearFilters();
    }

    @Override
    public void refresh() {
        loadData();
        barChart.animateY(getResources().getInteger(R.integer.animation_duration), Easing.EasingOption.EaseInOutCirc);
    }

    private void setListShown(boolean shown) {
        if (progressContainer != null && barChartContainer != null) {
            progressContainer.setVisibility(shown ? View.GONE : View.VISIBLE);
            barChartContainer.setVisibility(shown ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.refresh_menu, menu);

        MenuItem item = menu.findItem(R.id.action_refresh);
        ColorUtils.tintIcon(item, R.color.textBarColor, getActivity());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh){
            refresh();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Delegates
    protected abstract Datasource<T> getDatasource();

    protected abstract void loadChart(List<T> items);
}
