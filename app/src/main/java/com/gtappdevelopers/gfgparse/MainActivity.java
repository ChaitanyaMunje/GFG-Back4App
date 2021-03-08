package com.gtappdevelopers.gfgparse;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {
    //creating variables for our edit text and buttons.
    private EditText userNameEdt, passwordEdt, userEmailEdt;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializing our edit text  and buttons.
        userNameEdt = findViewById(R.id.idEdtUserName);
        passwordEdt = findViewById(R.id.idEdtPassword);
        userEmailEdt = findViewById(R.id.idEdtEmail);
        registerBtn = findViewById(R.id.idBtnRegister);
        //adding on click listener for our button
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line we are getting data from our edit text.
                String userName = userNameEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                String email = userEmailEdt.getText().toString();
                //checking if the entered text is empty or not.
                if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password) && TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
                }
                //calling a method to register a user.
                registerUser(userName, password, email);
            }
        });

    }

    private void registerUser(String userName, String password, String email) {
        //on below line we are creating a new user using parse user.
        ParseUser user = new ParseUser();
        // Set the user's username, user email and password, which can be obtained from edit text
        user.setUsername(userName);
        user.setEmail(email);
        user.setPassword(password);
        //calling a method to register the user.
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                //on user registration checking if the error is null or not.
                if (e == null) {
                    //if the error is null we are displaying a toast message and redirecting our user to login activity and passing the user name.
                    Toast.makeText(MainActivity.this, "User Registered successfully \n Please verify your email", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    i.putExtra("usereName", userName);
                    i.putExtra("password", password);
                    startActivity(i);
                } else {
                    //if we get any erro then we are logging out our user and displaying an error message
                    ParseUser.logOut();
                    Toast.makeText(MainActivity.this, "Fail to Register User..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}