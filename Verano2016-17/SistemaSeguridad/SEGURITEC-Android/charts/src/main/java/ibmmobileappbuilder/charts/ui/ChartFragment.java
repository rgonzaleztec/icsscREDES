/*
 * Copyright 2016.
 * This code is part of IBM Mobile UI Builder
 */

package ibmmobileappbuilder.charts.ui;

import java.util.List;

public interface ChartFragment {
    void setChartTitle(String title);
    void addSeries(List<Number> series, int seriesColor, String label);
    void drawChart();
}
