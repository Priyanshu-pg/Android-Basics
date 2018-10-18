package com.example.priyanshu.loginpage;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String username, password;
    Button button_login;
    int attempt_counter=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button_login = (Button)findViewById(R.id.button_login);
    }

    public void checkCredentials(View view) {
        username = ((EditText)findViewById(R.id.value_username)).getText().toString();
        password = ((EditText)findViewById(R.id.value_password)).getText().toString();
        if(username.equals("priyanshu")&& password.equals("abc@123"))
        {
            Intent intent = new Intent(this, LoggedIn.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,"Username and password is NOT correct",
                    Toast.LENGTH_SHORT).show();
            attempt_counter--;
            if(attempt_counter==5)
                button_login.setEnabled(false);

        }
    }
}
