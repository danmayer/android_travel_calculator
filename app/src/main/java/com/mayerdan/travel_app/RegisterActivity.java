package com.mayerdan.travel_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mayerdan.travel_app.tasks.RegisterTask;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class RegisterActivity extends BaseActivity {

    private SharedPreferences mPreferences;
    @InjectView(R.id.userEmail) EditText userEmailField;
    private String mUserEmail;
    @InjectView(R.id.userName) EditText userNameField;
    private String mUserName;
    @InjectView(R.id.userPassword) EditText userPasswordField;
    private String mUserPassword;
    @InjectView(R.id.userPasswordConfirmation) EditText userPasswordConfirmationField;
    private String mUserPasswordConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);

        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
    }

    public void registerNewAccount(View button) {
        mUserEmail = userEmailField.getText().toString();
        mUserName = userNameField.getText().toString();
        mUserPassword = userPasswordField.getText().toString();
        mUserPasswordConfirmation = userPasswordConfirmationField.getText().toString();

        if (mUserEmail.length() == 0 || mUserName.length() == 0 || mUserPassword.length() == 0 || mUserPasswordConfirmation.length() == 0) {
            // input fields are empty
            Toast.makeText(this, "Please complete all the fields",
                    Toast.LENGTH_LONG).show();
            return;
        } else {
            if (!mUserPassword.equals(mUserPasswordConfirmation)) {
                // password doesn't match confirmation
                Toast.makeText(this, "Your password doesn't match confirmation, check again",
                        Toast.LENGTH_LONG).show();
                return;
            } else {
                RegisterTask
                    registerTask = new RegisterTask(RegisterActivity.this, mPreferences, mUserEmail, mUserName, mUserPassword, mUserPasswordConfirmation, this);
                registerTask.execute();
            }
        }
    }

}
