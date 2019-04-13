package android.example.autoattendie;
import java.util.ArrayList;
import android.os.Bundle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import android.app.Activity;
import android.support.annotation.Nullable;

public class AttendanceChart extends Activity {
    BarChart chart;
    ArrayList<BarEntry> entries = new ArrayList<>();;
    ArrayList<String> BarEntryLabels = new ArrayList<>();;
    BarDataSet Bardataset;
    BarData data;
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_chart);
        chart = findViewById(R.id.chart1);
        AddValuesToBARENTRY();
        AddValuesToBarEntryLabels();
        Bardataset = new BarDataSet(entries, "Subjects");
        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        data = new BarData(Bardataset);
        data.setBarWidth(0.9f);
        chart.setData(data);
        chart.setFitBars(true);
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(BarEntryLabels));
        xAxis.setDrawGridLines(false);
        chart.getDescription().setEnabled(false);
        chart.setScaleEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.animateY(3000);
        chart.invalidate();
    }
    public void AddValuesToBARENTRY () {

        entries.add(new BarEntry(0f, 25));
        entries.add(new BarEntry(1f, 50));
        entries.add(new BarEntry(2f, 80));
        entries.add(new BarEntry(3f, 65));
        entries.add(new BarEntry(4f, 90));

    }
    public void AddValuesToBarEntryLabels () {

        BarEntryLabels.add("Se");
        BarEntryLabels.add("Dbms");
        BarEntryLabels.add("Epics");
        BarEntryLabels.add("Apc");
        BarEntryLabels.add("Web");

    }
}