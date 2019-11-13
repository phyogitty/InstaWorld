package com.pk.instaworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    EditText etUsername;
    EditText etPassword;
    EditText etConfirmPassword;
    Button btnCompleteSingUp;
    Button btnCancelSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnCompleteSingUp = findViewById(R.id.btnCompleteSignUp);
        btnCancelSignUp = findViewById(R.id.btnCancelSignUp);



        // Set custom properties
        // Invoke signUpInBackground
        btnCompleteSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String userName = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                String email = etEmail.getText().toString();
                if (isNotBlank(firstName) && isNotBlank(lastName) && isNotBlank(userName)
                 && isNotBlank(password) && isNotBlank(email) && isNotBlank(confirmPassword)) {
                    if (password != confirmPassword) {
                        Toast.makeText(SignUpActivity.this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
                    } else {
                        final ParseUser user = new ParseUser();
                        // Set core properties
                        user.put("firstName", firstName);
                        user.put("lastName", lastName);
                        user.setUsername(userName);
                        user.setPassword(password);
                        user.setEmail(email);
                        user.signUpInBackground(new SignUpCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    // Hooray! Let them use the app now.
                                } else {
                                    // Sign up didn't succeed. Look at the ParseException
                                    // to figure out what went wrong
                                }
                            }
                        });
                    }
                }

            }
        });


    }

    private boolean isNotBlank(String str) {
        return str != "";

    }
}
