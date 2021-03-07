package com.gtappdevelopers.gfgparse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {

    //creating a variable for our text view..
    private TextView userNameTV;
    //button for logout
    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logoutBtn = findViewById(R.id.idBtnLogout);
        //initializing our variables
        userNameTV = findViewById(R.id.idTVUserName);
        //getting data from intent.
        String name = getIntent().getStringExtra("username");
        //setting data to our text view.
        userNameTV.setText(name);
        //initializing click listner for logout button
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling a method to logout our user.
                ParseUser.logOutInBackground(e -> {
                    if (e == null) {
                        Toast.makeText(HomeActivity.this, "User Logged Out", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                });
            }
        });
    }
}