package android.example.autoattendie;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
    EditText id;
    EditText password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        id=(EditText)findViewById(R.id.enter_id);
        password=(EditText)findViewById(R.id.enter_password);
        Button login = findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginDB db=new LoginDB(Login.this);
                db.open();
                    if(id.getText().toString().isEmpty() && password.getText().toString().isEmpty())
                        Toast.makeText(Login.this, "Wrong Id or Password", Toast.LENGTH_SHORT).show();
                    else
                    {
                        Person n=db.checkLonginDetails(id.getText().toString().trim(),password.getText().toString().trim());
                        if(n!=null) {
                            Intent intent = new Intent(Login.this, Home.class);
                            startActivity(intent);
                        }
                    }
                db.close();

            }
        });

    }

    public void signup(View view) {
        Intent signUp = new Intent(this, SignUp.class);
        startActivity(signUp);
    }

    public void forgetPass(View view) {
        Intent forget = new Intent(this, ForgetPassWord.class);
        startActivity(forget);
    }

}
