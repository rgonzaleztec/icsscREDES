package ibmmobileappbuilder.charts.formatter;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

public class CustomYAxisValueFormatter implements YAxisValueFormatter {
    public CustomYAxisValueFormatter() {}

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        if(value % 1 == 0) {
            return String.format("%.0f", value);
        } else {
            return String.format("%.1f", value);
        }
    }
}
