package android.example.autoattendie;
import java.util.ArrayList;
import android.os.Bundle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AttendanceChart extends AppCompatActivity {
    BarChart chart;
    static ArrayList<BarEntry> entries = new ArrayList<>();
    ArrayList<String> BarEntryLabels = new ArrayList<>();
    BarDataSet Bardataset;
    BarData data;
    static JSONArray jsonArray;
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_chart);
        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Attendance Chart");
        setSupportActionBar(toolbar);
        chart = findViewById(R.id.chart1);
//        AddValuesToBARENTRY(attendance);
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
    public static void AddValuesToBARENTRY(JSONObject jsonObject) throws JSONException {

        int present = 0, per = 0;
        jsonArray = (JSONArray) jsonObject.get("se");
        for(int j = 0;j<jsonArray.length();j++) {
            if(jsonArray.get(j).toString().equals("Present")) {
                present++;
            }
        }
        per = (present * 100/ jsonArray.length());
        entries.add(new BarEntry(0f, per));
        present = 0;
        jsonArray = (JSONArray) jsonObject.get("dbms");
        for(int j = 0;j<jsonArray.length();j++) {
            if(jsonArray.get(j).toString().equals("Present")) {
                present++;
            }
        }
        per = (present * 100/ jsonArray.length());
        entries.add(new BarEntry(1f, per));
        present = 0;
        jsonArray = (JSONArray) jsonObject.get("epics");
        for(int j = 0;j<jsonArray.length();j++) {
            if(jsonArray.get(j).toString().equals("Present")) {
                present++;
            }
        }
        per = (present * 100/ jsonArray.length());
        entries.add(new BarEntry(2f, per));
        present = 0;
        jsonArray = (JSONArray) jsonObject.get("apc");
        for(int j = 0;j<jsonArray.length();j++) {
            if(jsonArray.get(j).toString().equals("Present")) {
                present++;
            }
        }
        per = (present * 100/ jsonArray.length());
        entries.add(new BarEntry(3f, per));
        present = 0;
        jsonArray = (JSONArray) jsonObject.get("web");
        for(int j = 0;j<jsonArray.length();j++) {
            if(jsonArray.get(j).toString().equals("Present")) {
                present++;
            }
        }
        per = (present * 100/ jsonArray.length());
        entries.add(new BarEntry(4f, per));

    }
    public void AddValuesToBarEntryLabels () {

        BarEntryLabels.add("Se");
        BarEntryLabels.add("Dbms");
        BarEntryLabels.add("Epics");
        BarEntryLabels.add("Apc");
        BarEntryLabels.add("Web");

    }
}