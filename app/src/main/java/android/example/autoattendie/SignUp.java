package android.example.autoattendie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity {
    Button singUp;
    EditText Id;
    EditText password1;
    EditText password2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        singUp=(Button) findViewById(R.id.signup);
        Id=(EditText)findViewById(R.id.enter_id);
        password1=(EditText)findViewById(R.id.enter_password1);
        password2=(EditText)findViewById(R.id.enter_password2);
        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Id.getText().toString().isEmpty() && !password1.getText().toString().isEmpty() &&
                        !password2.getText().toString().isEmpty())
                {
                    String p1=password1.getText().toString().trim();
                    String p2=password2.getText().toString().trim();
                    if(p1.equals(p2))
                    {
                        LoginDB db= new LoginDB(SignUp.this);
                        db.open();
                            if(!db.checkForDuplicate(Id.getText().toString().trim())) {
                                db.createEntry(Id.getText().toString().trim(), password1.getText().toString());
                                Toast.makeText(SignUp.this, "Account created", Toast.LENGTH_SHORT).show();
                                Toast.makeText(SignUp.this, "Account created", Toast.LENGTH_SHORT).show();
                                login(v);
                            }
                            else
                            {
                                Toast.makeText(SignUp.this, "Id Already Exists", Toast.LENGTH_SHORT).show();
                            }
                        db.close();

                    }
                    else
                        Toast.makeText(SignUp.this, "Password Does not match", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(SignUp.this, "All Fields are Required", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void login(View view) {
        Intent login = new Intent(this, Login.class);
        startActivity(login);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
