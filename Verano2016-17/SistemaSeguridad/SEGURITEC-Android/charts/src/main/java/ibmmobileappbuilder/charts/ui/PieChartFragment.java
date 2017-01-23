/*
 * Copyright 2016.
 * This code is part of IBM Mobile UI Builder
 */

package ibmmobileappbuilder.charts.ui;

import android.graphics.Color;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.List;

import ibmmobileappbuilder.charts.R;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.Pagination;
import ibmmobileappbuilder.ds.filter.Filter;
import ibmmobileappbuilder.ui.BaseFragment;
import ibmmobileappbuilder.ui.Filterable;
import ibmmobileappbuilder.ui.Refreshable;
import ibmmobileappbuilder.util.ColorUtils;

public abstract class PieChartFragment<T> extends BaseFragment
        implements ChartFragment, Filterable, Refreshable {

    private static String BUNDLE_SERIES = "series";
    private static String BUNDLE_LABELS = "labels";
    private static String BUNDLE_RANDOM_COLORS = "random_colors";
    private static String BUNDLE_PIE_TITLE = "pie_title";

    private PieChart pieChart;
    private Datasource<T> datasource;
    private TextView pieTitle;

    protected View pieChartContainer;
    protected View progressContainer;

    protected List<Number> series = new ArrayList<Number>();
    protected List<String> labels = new ArrayList<String>();
    protected ArrayList<Integer> randomColors = new ArrayList<Integer>();

    private int textColor, backgroundColor;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState == null) {
            datasource = getDatasource();
        }

        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);

        pieChart = (PieChart) view.findViewById(R.id.pie_chart);
        pieChartContainer = view.findViewById(R.id.pie_chart_container);
        progressContainer = view.findViewById(R.id.progressContainer);
        pieTitle = (TextView) view.findViewById(R.id.pie_title);

        textColor = getResources().getColor(R.color.textColor);
        backgroundColor = getResources().getColor(R.color.window_background);

        // initialize chart
        pieChartSetup();

        return view;
    }

    private void pieChartSetup() {
        pieChart.setUsePercentValues(false);
        pieChart.setDescription("");
        pieChart.setNoDataText(getString(R.string.chart_no_info));
        Paint p = pieChart.getPaint(Chart.PAINT_INFO);
        p.setColor(textColor);

        pieChart.setExtraOffsets(15, 0, 15, 0);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setCenterText("");
        pieChart.setCenterTextColor(textColor);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(48f);
        pieChart.setTransparentCircleRadius(51f);
        pieChart.setDrawCenterText(true);
        pieChart.setDrawSliceText(false);
        pieChart.setHoleColor(backgroundColor);

        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.animateY(getResources().getInteger(R.integer.animation_duration), Easing.EasingOption.EaseInOutCirc);

        Legend legend = pieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setXEntrySpace(4f);
        legend.setWordWrapEnabled(true);
        legend.setTextSize(getResources().getDimension(R.dimen.legend_text_font_size));
        legend.setTextColor(textColor);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setFormSize(getResources().getDimension(R.dimen.legend_form_size));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState == null) {
            loadData();
        } else {
            float[] serieValues = savedInstanceState.getFloatArray(BUNDLE_SERIES);
            labels = savedInstanceState.getStringArrayList(BUNDLE_LABELS);
            randomColors = savedInstanceState.getIntegerArrayList(BUNDLE_RANDOM_COLORS);
            pieTitle.setText(savedInstanceState.getString(BUNDLE_PIE_TITLE));

            if (serieValues != null && labels != null && randomColors != null && pieTitle != null) {
                series = new ArrayList<Number>();

                for (int i = 0; i < serieValues.length; i++) {
                    series.add(serieValues[i]);
                }

                drawChart();
            } else {
                loadData();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        if(series != null) {
            float serieValues[] = new float[series.size()];
            for(int i=0;i<series.size();i++) {
                serieValues[i] = (Float) series.get(i);
            }

            savedInstanceState.putFloatArray(BUNDLE_SERIES, serieValues);
            savedInstanceState.putStringArrayList(BUNDLE_LABELS, (ArrayList<String>) labels);
            savedInstanceState.putIntegerArrayList(BUNDLE_RANDOM_COLORS, randomColors);
			
			if(pieTitle != null && pieTitle.getText() != null) {
				savedInstanceState.putString(BUNDLE_PIE_TITLE, pieTitle.getText().toString());
			}
        }
    }

    private void loadData() {
        //set up the listener for charts
        Datasource.Listener dsListener = new Datasource.Listener<List<T>>() {
            @Override
            public void onSuccess(List<T> ts) {
                if (pieChart != null && ts.size() != 0) {
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
        pieChart = null;
    }

    @Override
    public void setChartTitle(String title) {
        pieTitle.setText(title);
    }

    @Override
    public void addSeries(List<Number> series, int seriesColor, String label) {
        if(series != null && !series.isEmpty()) {
            this.series.add(series.get(0));
            this.labels.add(label);
            this.randomColors.add(seriesColor);
        }
    }

    @Override
    public void drawChart() {
        List<Entry> entries = new ArrayList<Entry>();

        for(int i=0;i<series.size();i++) {
            entries.add(new Entry((float) series.get(i), i));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setColors(randomColors);

        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(labels, dataSet);

        data.setValueTextSize(getResources().getDimension(R.dimen.pie_value_font_size));
        data.setValueTextColor(textColor);

        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();
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
        pieChart.animateY(getResources().getInteger(R.integer.animation_duration), Easing.EasingOption.EaseInOutCirc);
    }

    private void setListShown(boolean shown) {
        if (progressContainer != null && pieChartContainer != null) {
            progressContainer.setVisibility(shown ? View.GONE : View.VISIBLE);
            pieChartContainer.setVisibility(shown ? View.VISIBLE : View.GONE);
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