package android.example.autoattendie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SubjectInfo extends AppCompatActivity {

    static String id;
    static JSONObject jsonObject = new JSONObject();
    static JSONArray jsonArray = new JSONArray();
    int present = 0;
    double percentage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_info);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Subject Info");
        setSupportActionBar(toolbar);
        TextView subject = findViewById(R.id.subject);
        TextView del = findViewById(R.id.del);
        TextView attend = findViewById(R.id.attend);
        TextView percent = findViewById(R.id.percent);
        id = getIntent().getStringExtra("id");
        try {
            jsonArray = (JSONArray) jsonObject.get(id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        subject.setText("Subject: " + id);
        del.setText("Lectures Delivered: " + jsonArray.length());
        for(int j = 0;j<jsonArray.length();j++) {
            try {
                if(jsonArray.get(j).toString().equals("Present")) {
                    present++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        percentage = ((double) present / jsonArray.length()) * 100;
        attend.setText("Lectures Attended: " + present);
        percent.setText("Your Attendance: " + percentage + ".0%");
    }
    public static void initJsonObject(JSONObject jsonObject1) {

            jsonObject = jsonObject1;
    }
}
