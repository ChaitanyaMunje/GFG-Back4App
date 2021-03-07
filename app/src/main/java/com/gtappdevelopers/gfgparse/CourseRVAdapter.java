package com.gtappdevelopers.gfgparse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CourseModal> courseModalArrayList;

    //creating a constructor class.
    public CourseRVAdapter(Context context, ArrayList<CourseModal> courseModalArrayList) {
        this.context = context;
        this.courseModalArrayList = courseModalArrayList;
    }

    @NonNull
    @Override
    public CourseRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.course_rv_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CourseRVAdapter.ViewHolder holder, int position) {
        //setting data to our text views from our modal class.
        CourseModal courses = courseModalArrayList.get(position);
        holder.courseNameTV.setText(courses.getCourseName());
        holder.courseDurationTV.setText(courses.getCourseDuration());
        holder.courseDescTV.setText(courses.getCourseDescription());
    }

    @Override
    public int getItemCount() {
        return courseModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //creating variables for our text views.
        private final TextView courseNameTV;
        private final TextView courseDurationTV;
        private final TextView courseDescTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //initializing our text views.
            courseNameTV = itemView.findViewById(R.id.idTVCourseName);
            courseDurationTV = itemView.findViewById(R.id.idTVCourseDuration);
            courseDescTV = itemView.findViewById(R.id.idTVCourseDescription);
        }
    }
}
