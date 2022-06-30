package com.example.schoolplanner.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolplanner.Database.Repository;
import com.example.schoolplanner.Entity.Course;
import com.example.schoolplanner.R;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseViewHolder> {
    public CoursesAdapter(List<Course> mCourses, Context context, LayoutInflater mInflater) {
        this.mCourse = mCourses;
        this.context = context;
        this.mInflater = mInflater;
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseName;
        private final TextView courseStatus;

        public CourseViewHolder(View itemview) {
            super(itemview);
            courseName = itemview.findViewById(R.id.textViewCourseName);
            courseStatus = itemview.findViewById(R.id.textCourseProgress);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourse.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("id", current.getCourseID());
                    context.startActivity(intent);

                }
            });
        }
    }

    private List<Course> mCourse;
    private final Context context;
    private final LayoutInflater mInflater;
    private Repository repository;

    public CoursesAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public CoursesAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.course_view_row, parent, false);

        return new CoursesAdapter.CourseViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CoursesAdapter.CourseViewHolder holder, int position) {
        holder.courseName.setText(mCourse.get(position).getTitle());
        holder.courseStatus.setText(mCourse.get(position).getStatus());

    }

    public void setCourses(List<Course> courses){
        if (courses.size() != 0){
            mCourse = courses;
            notifyDataSetChanged();
        }
        else{
            mCourse = null;
            notifyDataSetChanged();
        }

    }

    @Override
    public int getItemCount() {

        if(mCourse!=null){
            return mCourse.size();
        }
        else return 0;
    }
}
