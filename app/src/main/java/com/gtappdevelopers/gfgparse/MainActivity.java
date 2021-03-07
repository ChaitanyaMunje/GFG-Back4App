package com.gtappdevelopers.gfgparse;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    //creating variables for our edit text and buttons.
    private EditText userNameEdt, passwordEdt;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializing our edit text  and buttons.
        userNameEdt = findViewById(R.id.idEdtUserName);
        passwordEdt = findViewById(R.id.idEdtPassword);
        loginBtn = findViewById(R.id.idBtnLogin);
        //adding on click listener for our button.
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line we are getting data from our edit text.
                String userName = userNameEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                //checking if the entered text is empty or not.
                if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
                }
                //calling a method to login our user.
                loginUser(userName, password);
            }
        });
    }

    private void loginUser(String userName, String password) {
        //calling a method to login a user.
        ParseUser.logInInBackground(userName, password, (parseUser, e) -> {
            //after login checking if the user is null or not.
            if (parseUser != null) {
                //if the user is not null then we will display a toast message with user login and passing that user to new activity.
                Toast.makeText(this, "Login Successful ", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                i.putExtra("username", userName);
                startActivity(i);
            } else {
                //display an toast message when user logout of the app.
                ParseUser.logOut();
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}