package android.example.autoattendie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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
}
