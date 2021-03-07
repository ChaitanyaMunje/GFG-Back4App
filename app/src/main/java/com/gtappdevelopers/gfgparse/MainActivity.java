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
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

    //creating variables for our edit text
    private EditText courseNameEdt, courseDurationEdt, courseDescriptionEdt;
    //creating variable for button
    private Button submitCourseBtn, readCourseBtn;
    //creating a strings for storing our values from edittext fields.
    private String courseName, courseDuration, courseDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializing our edittext and buttons
        readCourseBtn = findViewById(R.id.idBtnReadCourse);
        courseNameEdt = findViewById(R.id.idEdtCourseName);
        courseDescriptionEdt = findViewById(R.id.idEdtCourseDescription);
        courseDurationEdt = findViewById(R.id.idEdtCourseDuration);
        submitCourseBtn = findViewById(R.id.idBtnSubmitCourse);
        readCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
        submitCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting data from edittext fields.
                courseName = courseNameEdt.getText().toString();
                courseDescription = courseDescriptionEdt.getText().toString();
                courseDuration = courseDurationEdt.getText().toString();

                //validating the text fileds if empty or not.
                if (TextUtils.isEmpty(courseName)) {
                    courseNameEdt.setError("Please enter Course Name");

                } else if (TextUtils.isEmpty(courseDescription)) {
                    courseDescriptionEdt.setError("Please enter Course Description");
                } else if (TextUtils.isEmpty(courseDuration)) {
                    courseDurationEdt.setError("Please enter Course Duration");
                } else {
                    //calling method to add data to Firebase Firestore.
                    addDataToDatabase(courseName, courseDescription, courseDuration);
                }
            }
        });
    }

    private void addDataToDatabase(String courseName, String courseDescription, String courseDuration) {
        // Configure Query
        ParseObject courseList = new ParseObject("courses");
        //on below line we are adding our data with their key value in our object.
        courseList.put("courseName", courseName);
        courseList.put("courseDescription", courseDescription);
        courseList.put("courseDuration", courseDuration);
        //after adding all data we are calling a method to save our data in background.
        courseList.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                //inside on done method we are checking if the error is null or not.
                if (e == null) {
                    //if the error is null we are displaying a simple toast message.
                    Toast.makeText(MainActivity.this, "Data has been successfully added to Database", Toast.LENGTH_SHORT).show();
                    //on below line we are setting our edit text fields to empty value.
                    courseNameEdt.setText("");
                    courseDescriptionEdt.setText("");
                    courseDurationEdt.setText("");
                } else {
                    //if the error is not null we will be displaying an error message to our user.
                    Toast.makeText(
                            getApplicationContext(),
                            e.getMessage().toString(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

    }
}