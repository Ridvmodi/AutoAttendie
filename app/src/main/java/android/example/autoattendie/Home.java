package android.example.autoattendie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

import androidx.annotation.RequiresApi;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {
    public static TextView loginname ;
    static CompactCalendarView compactCalendarView;
    static JSONObject jsonObject = new JSONObject();
    static JSONArray jsonArray;
    private Boolean exit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String id=getIntent().getStringExtra("id");
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        fetchData details = new fetchData(id);
        details.execute();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Notified Your Teacher", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        // Set first day of week to Monday, defaults to Monday so calling setFirstDayOfWeek is not necessary
        // Use constants provided by Java Calendar class
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Spinner spinner = findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("se");
        categories.add("dbms");
        categories.add("apc");
        categories.add("web");
        categories.add("epics");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        try {
            markAttendance(jsonObject, item);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (exit) {
                ActivityCompat.finishAffinity(Home.this);
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(Home.this, AttendanceChart.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(Home.this, AllSubjects.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(this, FeedBack.class);
            startActivity(intent);
        } else if(id == R.id.logout) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    static void initJsonObject(JSONObject jsonObject1){
        jsonObject = jsonObject1;
        try {
            markAttendance(jsonObject, "se");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    static void markAttendance(JSONObject jsonObject, String subject) throws JSONException {
        compactCalendarView.removeAllEvents();
        String d = "";
        jsonArray = (JSONArray)jsonObject.get(subject);
        for(int i = 0;i<jsonArray.length();i++) {
            if(i < 10) {
                d = "0";
            }
            d += String.valueOf(i+1) + "/05/2019";
            if(jsonArray.get(i).toString().equals("Present")) {
                changeToLongDate(d, 1);
            } else {
                changeToLongDate(d, 0);
            }
        }
    }
    static void changeToLongDate(String dateString, int flag)
    {
        try {

             SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
             Date date = sdf.parse(dateString);
             Event ev1;
             if(flag == 1) {
                 ev1 = new Event(Color.BLACK,date.getTime(), "Some extra data that I want to store.");
             } else {
                 ev1 = new Event(Color.RED, date.getTime(), "Some extra data that I want to store.");
             }
             compactCalendarView.addEvent(ev1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
