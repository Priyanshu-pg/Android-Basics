package com.example.priyanshu.loginpage.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.example.priyanshu.loginpage.R;
import com.example.priyanshu.loginpage.helpers.InputValidation;
import com.example.priyanshu.loginpage.sql.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = MainActivity.this;
    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextUsername;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;
    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {
        textInputLayoutUsername = (TextInputLayout) findViewById(R.id.textInputLayout_username);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayout_password);

        textInputEditTextUsername = (TextInputEditText) findViewById(R.id.textInputEditText_username);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditText_password);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.button_login);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.text_link_register);

    }

    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                verifyFromSQLite();
                break;
            case R.id.text_link_register:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextUsername,
                                        textInputLayoutUsername,
                                        getString(R.string.error_missing_username))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword,
                                        textInputLayoutPassword,
                                        getString(R.string.error_missing_password))) {
            return;
        }
        if (!databaseHelper.isUserNameValid(textInputEditTextUsername.getText().toString().trim())){
            textInputLayoutUsername.setError(getString(R.string.error_invalid_username));
            return;
        }

        if (databaseHelper.areCredentialsValid(textInputEditTextUsername.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {
            Intent loggedInIntent = new Intent(activity, LoggedIn.class);
            emptyInputEditText();
            startActivity(loggedInIntent);
        }
        else{
            Toast.makeText(this,R.string.error_invalid_password,
                    Toast.LENGTH_SHORT).show();
        }

}

    private void emptyInputEditText() {
        textInputEditTextUsername.setText(null);
        textInputEditTextPassword.setText(null);
    }


}
