package android.example.autoattendie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class FeedBack extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("FeedBack");
        setSupportActionBar(toolbar);
    }
}
