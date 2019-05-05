package android.example.autoattendie;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AllSubjects extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_subjects);
        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("All Subjects");
        setSupportActionBar(toolbar);
    }
    public void changePage(View v) {
        String id = getResources().getResourceEntryName((v.getId()));
        Intent intent = new Intent(this, SubjectInfo.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
