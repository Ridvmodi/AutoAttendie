package android.example.autoattendie;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        Intent login = new Intent(this, Login.class);
        startActivity(login);
    }

    public void signup(View view) {
        Intent signup = new Intent(this, SignUp.class);
        startActivity(signup);
    }

    @Override
    public void onBackPressed() {
        if (exit) {
//            finish(); // finish activity
            ActivityCompat.finishAffinity(MainActivity.this);
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
        }

    }
}
