package android.example.autoattendie;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class FeedBack extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("FeedBack");
        setSupportActionBar(toolbar);
    }

    public void submit_req(View view) {


        EditText cust_msg = (EditText) findViewById(R.id.message);
        String value = cust_msg.getText().toString();

        RadioButton bugcheck = (RadioButton) findViewById(R.id.bug_id);
        boolean bugss = bugcheck.isChecked();

        RadioButton suggest = (RadioButton) findViewById(R.id.suggestion_id);
        boolean suggs = suggest.isChecked();

        String subj = checkv(bugss, suggs);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,subj);
        intent.putExtra(Intent.EXTRA_TEXT, value);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public String checkv(boolean bugss,boolean suggs) {
        String m = "Hello, There is a ";
        if(bugss) {
            m =  m + "Bug";
        }
        else if(suggs) {
            m =  m + "Suggestion";
        }
        if(bugss && suggs) {
            m = m + "Bug and Suggestion";
        }
        return m;
    }
}
