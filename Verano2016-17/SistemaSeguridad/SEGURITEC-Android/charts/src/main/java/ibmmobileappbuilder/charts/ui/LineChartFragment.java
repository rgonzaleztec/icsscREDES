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
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

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

public abstract class LineChartFragment<T> extends BaseFragment
        implements ChartFragment, Filterable, Refreshable {

    private static String BUNDLE_XY_SERIES_MAP = "xy_series_map";
    private static String BUNDLE_XY_VALUES = "xy_values";
    private static String BUNDLE_SERIES_NAME = "series_name";
    private static String BUNDLE_LINE_TITLE = "line_title";

    protected LineChart lineChart;
    private Datasource<T> datasource;
    private List<String> xAxisValues;
    private Map<List<Number>, Integer> xySeriesMap;
    private TextView lineTitle;

    private View lineChartContainer;
    private View progressContainer;

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

        View view = inflater.inflate(R.layout.fragment_line_chart, container, false);

        lineChart = (LineChart) view.findViewById(R.id.line_chart);
        lineChartContainer = view.findViewById(R.id.line_chart_container);
        progressContainer = view.findViewById(R.id.progressContainer);
        lineTitle = (TextView) view.findViewById(R.id.line_title);

        textColor = getResources().getColor(R.color.textColor);

        // initialize chart
        lineChartSetup();

        return view;
    }

    private void lineChartSetup() {
        lineChart.setDescription("");
        lineChart.setNoDataText(getString(R.string.chart_no_info));
        Paint p = lineChart.getPaint(Chart.PAINT_INFO);
        p.setColor(textColor);

        lineChart.setExtraOffsets(0, 0, 0, 0);

        lineChart.setDragDecelerationFrictionCoef(0.95f);

        lineChart.setMaxVisibleValueCount(60);
        lineChart.setPinchZoom(false);

        lineChart.setDrawGridBackground(false);
        lineChart.setHighlightPerTapEnabled(true);
        lineChart.animateY(getResources().getInteger(R.integer.animation_duration), Easing.EasingOption.EaseInOutCirc);
        lineChart.setScaleEnabled(true);

        Legend legend = lineChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setXEntrySpace(4f);
        legend.setWordWrapEnabled(true);
        legend.setTextSize(getResources().getDimension(R.dimen.legend_text_font_size));
        legend.setTextColor(textColor);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setFormSize(getResources().getDimension(R.dimen.legend_form_size));

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(textColor);
        xAxis.setTextSize(getResources().getDimension(R.dimen.x_axis_domain_label_font_size));

        YAxis left = lineChart.getAxisLeft();
        left.setSpaceTop(0.15f);
        left.setAxisMinValue(0.0f);
        left.setValueFormatter(new CustomYAxisValueFormatter());
        left.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        left.setTextColor(textColor);
        left.setTextSize(getResources().getDimension(R.dimen.y_axis_domain_label_font_size));

        YAxis right = lineChart.getAxisRight();
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
            lineTitle.setText(savedInstanceState.getString(BUNDLE_LINE_TITLE));

            if(xySeriesMap != null && xAxisValues != null && seriesName != null && lineTitle != null) {
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
			
			if(lineTitle != null && lineTitle.getText() != null) {
				savedInstanceState.putString(BUNDLE_LINE_TITLE, lineTitle.getText().toString());
			}	
        }
    }

    private void loadData() {
        //set up the listener for charts
        Datasource.Listener dsListener = new Datasource.Listener<List<T>>() {
            @Override
            public void onSuccess(List<T> ts) {
                if (lineChart != null && ts.size() != 0) {
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
        lineChart = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        datasource = null;
    }

    @Override
    public void setChartTitle(String title) {
        lineTitle.setText(title);
    }

    public void setXAxisValues(List<String> values) {
        xAxisValues = values;
    }

    @Override
    public void addSeries(List<Number> series, int seriesColor, String label) {
        if(series != null) {
            xySeriesMap.put(series, seriesColor);
            seriesName.add(label);
        }
    }

    @Override
    public void drawChart() {
        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        ArrayList<Entry> entries;

        int index = 0;
        for(List<Number> series : xySeriesMap.keySet()) {
            entries = new ArrayList<>();

            for(int i=0;i<series.size();i++) {
                entries.add(new Entry((float) series.get(i), i));
            }

            LineDataSet lineDataSet = new LineDataSet(entries, seriesName.get(index));
            lineDataSet.setColor(xySeriesMap.get(series));
            lineDataSet.setDrawValues(false);
            lineDataSet.setLineWidth(2f);
            lineDataSet.setCircleColor(xySeriesMap.get(series));
            lineDataSet.setDrawCircleHole(false);
            lineDataSet.setCircleRadius(4f);

            dataSets.add(lineDataSet);
            index++;
        }

        LineData data = new LineData(xAxisValues, dataSets);
        lineChart.setData(data);
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
        lineChart.animateY(getResources().getInteger(R.integer.animation_duration), Easing.EasingOption.EaseInOutCirc);
    }

    private void setListShown(boolean shown) {
        if (progressContainer != null && lineChartContainer != null) {
            progressContainer.setVisibility(shown ? View.GONE : View.VISIBLE);
            lineChartContainer.setVisibility(shown ? View.VISIBLE : View.GONE);
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

    protected abstract Datasource<T> getDatasource();

    public abstract void loadChart(List<T> items);
}