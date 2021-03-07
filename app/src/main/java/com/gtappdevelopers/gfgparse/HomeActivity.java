package com.gtappdevelopers.gfgparse;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    //creating variables for our recycler view, progressbar, array list and adapter class.
    private RecyclerView coursesRV;
    private ProgressBar loadingPB;
    private ArrayList<CourseModal> courseModalArrayList;
    private CourseRVAdapter courseRVAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //initializing our views.
        loadingPB = findViewById(R.id.idProgressBar);
        coursesRV = findViewById(R.id.idRVCourses);
        courseModalArrayList = new ArrayList<>();
        //calling a method to load recycler view.
        prepareCourseRV();
        //calling a method to get the data from database.
        getDataFromServer();
    }

    private void getDataFromServer() {
        // Configure Query with our query.
        ParseQuery<ParseObject> query = ParseQuery.getQuery("courses");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                //in done method checking if the error is null or not.
                if (e == null){
                    // Adding objects into the Array
                    //if error is not null we are getting data from our object and passing it to our array list.
                    for(int i= 0 ; i < objects.size(); i++){
                        //on below line we are extracting our data and adding it ot our array list
                        String courseName = objects.get(i).getString("courseName");
                        String courseDescription = objects.get(i).getString("courseDescription");
                        String courseDuration = objects.get(i).getString("courseDuration");
                        //on below line we are adding data to our array list.
                        courseModalArrayList.add(new CourseModal(courseName,courseDescription,courseDuration));
                    }
                    //notifying adapter class on adding new data.
                    courseRVAdapter.notifyDataSetChanged();
                    loadingPB.setVisibility(View.GONE);
                } else {
                    //handling error if we get any error.
                    Toast.makeText(HomeActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void prepareCourseRV(){
        coursesRV.setHasFixedSize(true);
        coursesRV.setLayoutManager(new LinearLayoutManager(this));
        //adding our array list to our recycler view adapter class.
        courseRVAdapter = new CourseRVAdapter( this,courseModalArrayList);
        //setting adapter to our recycler view.
        coursesRV.setAdapter(courseRVAdapter);
    }
}