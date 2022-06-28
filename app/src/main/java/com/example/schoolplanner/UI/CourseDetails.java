package com.example.schoolplanner.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.schoolplanner.Database.Repository;
import com.example.schoolplanner.Entity.Course;
import com.example.schoolplanner.R;

import java.util.List;

public class CourseDetails extends AppCompatActivity {

    private TextView courseName;
    private TextView startDate;
    private TextView endDate;
    private TextView status;
    private TextView ciName;
    private TextView ciPhone;
    private TextView ciEmail;
    private int courseID;
    private String name;
    private String start;
    private String end;
    private String cStatus;
    private String cMentorName;
    private String cMentorPhone;
    private String cMentorEmail;
    private Repository repository;
    Course workingCourse;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        repository = new Repository(getApplication());

        courseName = findViewById(R.id.textViewCDName);
        startDate = findViewById(R.id.textViewCDStartDate);
        endDate = findViewById(R.id.texViewCDEndDate);
        status = findViewById(R.id.textViewCDStatus);
        ciName = findViewById(R.id.editTextCDInstructorName);
        ciPhone = findViewById(R.id.editTextCDInstructorPhone);
        ciEmail = findViewById(R.id.editTextCDInstructorEmail);
        if(getIntent().hasExtra("id")){
            courseID = getIntent().getIntExtra("id" , -1);
        }
        else{
        }
        workingCourse = findCourse(courseID);
        name = workingCourse.getTitle();
        start = workingCourse.getStartDate();
        end = workingCourse.getEndDate();
        cStatus = workingCourse.getStatus();
        cMentorName = workingCourse.getMentorName();
        cMentorPhone = workingCourse.getMentorPhone();
        cMentorEmail = workingCourse.getMentorEmail();
        courseName.setText(name);
        startDate.setText(start);
        endDate.setText(end);
        status.setText(cStatus);
        ciName.setText(cMentorName);
        ciPhone.setText(cMentorPhone);
        ciEmail.setText(cMentorEmail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_coursedetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.deleteCourse:
                int termID = workingCourse.getTermID();
                repository.delete(workingCourse);
                Intent intent = new Intent(this, TermDetails.class);
                intent.putExtra("termID", termID);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    public void clickCourseUpdate(View view) {
        Intent intent = new Intent(this, CourseUpdate.class);
        if(!name.isEmpty()){
            intent.putExtra("id", courseID);
            intent.putExtra("name", name);
            intent.putExtra("start", start);
            intent.putExtra("end", end);
            intent.putExtra("status", cStatus);
            intent.putExtra("CIName", cMentorName);
            intent.putExtra("CIphone", cMentorPhone);
            intent.putExtra("CIemail", cMentorEmail);
        }
        startActivity(intent);


    }

    private Course findCourse(int id){
        Course search;
        List<Course> allCourses = repository.getmAllCourses();
        for(int i = 0; i < allCourses.size(); i++){
            if(allCourses.get(i).getCourseID() == id){
                search = allCourses.get(i);
                return search;
            }
        }
        return null;
    }
}