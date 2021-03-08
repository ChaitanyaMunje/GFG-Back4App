package com.gtappdevelopers.gfgparse;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

public class UpdateCourseActivity extends AppCompatActivity {

    //creating variables for our edit text
    private EditText courseNameEdt, courseDurationEdt, courseDescriptionEdt;
    //creating variable for button
    private Button updateCourseBtn, deleteCourseBtn;
    //creating a strings for storing our values from edittext fields.
    private String courseName, courseDuration, courseDescription, originalCourseName, objectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);
        //initializing our edittext and buttons
        deleteCourseBtn = findViewById(R.id.idBtnDelete);
        updateCourseBtn = findViewById(R.id.idBtnUpdate);
        courseNameEdt = findViewById(R.id.idEdtCourseName);
        courseDescriptionEdt = findViewById(R.id.idEdtCourseDescription);
        courseDurationEdt = findViewById(R.id.idEdtCourseDuration);
        //on below line we are setting data to our edit text field.
        courseNameEdt.setText(getIntent().getStringExtra("courseName"));
        courseDescriptionEdt.setText(getIntent().getStringExtra("courseDescription"));
        courseDurationEdt.setText(getIntent().getStringExtra("courseDuration"));
        originalCourseName = getIntent().getStringExtra("courseName");
        //Adding on click listner for delete button
        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling a method to delete a course.
                deleteCourse(originalCourseName);
            }
        });
        updateCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    //calling method to update data.
                    updateData(originalCourseName, courseName, courseDescription, courseDuration);
                }

            }
        });
    }

    private void deleteCourse(String courseName) {
        // Configure Query with our query.
        ParseQuery<ParseObject> query = ParseQuery.getQuery("courses");
        //adding a condition where our course name must be equal to the original course name
        query.whereEqualTo("courseName", courseName);
        //on below line we are finding the course with the course name.
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                //if the error is null.
                if (e == null) {
                    //on below line we are getting the first course and calling a delete method to delete this course.
                    objects.get(0).deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            //inside done method checking if the error is null or not.
                            if (e == null) {
                                //if the error is not null then we are displaying a toast message and opening our home activity.
                                Toast.makeText(UpdateCourseActivity.this, "Course Deleted..", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(UpdateCourseActivity.this, LoginActivity.class);
                                startActivity(i);
                            } else {
                                //if we get error we are displaying it in below line.
                                Toast.makeText(UpdateCourseActivity.this, "Fail to delete course..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    //if we donot get the data in our database then we are displaying below message.
                    Toast.makeText(UpdateCourseActivity.this, "Fail to get the object..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void updateData(String originalCourseName, String courseName, String courseDescription, String courseDuration) {
        // Configure Query with our query.
        ParseQuery<ParseObject> query = ParseQuery.getQuery("courses");
        //adding a condition where our course name must be equal to the original course name
        query.whereEqualTo("courseName", originalCourseName);
        //in below method we are getting the unique id of the course which we have to make update.
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                //inside done method we check if the error is null or not.
                if (e == null) {
                    //if the error is null then we are getting our object id in below line.
                    objectID = object.getObjectId().toString();
                    //after getting our object id we will move towards updating our course.
                    //calling below method to update our course.
                    query.getInBackground(objectID, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            //in this method we are getting the object which we have to update.
                            if (e == null) {
                                //in below line we are adding new data to the object which we get from its id.
                                //on below line we are adding our data with their key value in our object.
                                object.put("courseName", courseName);
                                object.put("courseDescription", courseDescription);
                                object.put("courseDuration", courseDuration);
                                //after adding new data then we are calling a method to save this data
                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        //inside on done method we are checking if the error is null or not.
                                        if (e == null) {
                                            //if the error is null our data has been updated.
                                            //we are displaying a toast message and redirecting our user to home activity where we are displaying course list.
                                            Toast.makeText(UpdateCourseActivity.this, "Course Updated..", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(UpdateCourseActivity.this, LoginActivity.class);
                                            startActivity(i);
                                        } else {
                                            //below line is for error handling.
                                            Toast.makeText(UpdateCourseActivity.this, "Fail to update data " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                //on below line we are displaying a message if we don't get the object from its id.
                                Toast.makeText(UpdateCourseActivity.this, "Fail to update course " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    //this is error handling if we don't get the id for our object
                    Toast.makeText(UpdateCourseActivity.this, "Fail to get object ID..", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}