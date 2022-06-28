package com.example.schoolplanner.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.schoolplanner.Database.Repository;
import com.example.schoolplanner.Entity.Course;
import com.example.schoolplanner.R;

public class CourseUpdate extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String name;
    private String start;
    private String end;
    private String cStatus;
    private String cMentorName;
    private String cMentorPhone;
    private String cMentorEmail;
    private EditText courseName;
    private EditText startDate;
    private EditText endDate;
    private EditText ciName;
    private EditText ciPhone;
    private EditText ciEmail;
    private TextView cUpdatetitle;
    private int courseID;
    private static int termID;

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_update);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        repository = new Repository(getApplication());
        courseID = getIntent().getIntExtra("id", -1);
        courseName = findViewById(R.id.editTextCourseName);
        startDate = findViewById(R.id.editTextCourseStart);
        endDate = findViewById(R.id.editTextCourseEnd);
        Spinner spinner = findViewById(R.id.spinnerCourseStatus);
        ciName = findViewById(R.id.editTextCDInstructorName);
        ciPhone = findViewById(R.id.editTextCDInstructorPhone);
        ciEmail = findViewById(R.id.editTextCDInstructorEmail);
        cUpdatetitle = findViewById(R.id.textViewCourseUpdate);
        termID = TermDetails.getTermIDCourse();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.course_status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cStatus = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });

        if(courseID == -1){
            cUpdatetitle.setText("Add New Course");
        }
        else {
            cUpdatetitle.setText("Update Course");
            name = getIntent().getStringExtra("name");
            courseName.setText(name);
            start = getIntent().getStringExtra("start");
            startDate.setText(start);
            end = getIntent().getStringExtra("end");
            endDate.setText(end);
            cStatus = getIntent().getStringExtra("status");
            setSpinner(spinner, cStatus);
            cMentorName = getIntent().getStringExtra("CIName");
            ciName.setText(cMentorName);
            cMentorPhone = getIntent().getStringExtra("CIphone");
            ciPhone.setText(cMentorPhone);
            cMentorEmail = getIntent().getStringExtra("CIemail");
            ciEmail.setText(cMentorEmail);

        }



    }



    public void clickSaveCourse(View view) {
        Course updateCourse;
        if(courseID == -1){
            int newID = repository.getmAllCourses().get(repository.getmAllCourses().size()-1).getCourseID() + 1;
            updateCourse = new Course(newID, courseName.getText().toString(), startDate.getText().toString(),endDate.getText().toString(), cStatus,ciName.getText().toString(),ciPhone.getText().toString(),ciEmail.getText().toString(), "Testing", termID);
            repository.insert(updateCourse);
            Intent intent = new Intent(this, CourseDetails.class);
            //intent.putExtra("id", courseID);
            repository = new Repository(getApplication());
            startActivity(intent);
        }
        else{
            updateCourse = new Course(courseID, courseName.getText().toString(), startDate.getText().toString(),endDate.getText().toString(), cStatus,ciName.getText().toString(),ciPhone.getText().toString(),ciEmail.getText().toString(), "Testing", termID);
            repository.update(updateCourse);
            Intent intent = new Intent(this, CourseDetails.class);
            intent.putExtra("id", termID);
            repository = new Repository(getApplication());
            startActivity(intent);

        }
    }

    public void setSpinner(Spinner spinner, String search){
        for(int i = 0; i < spinner.getAdapter().getCount(); i++){
            if(spinner.getAdapter().getItem(i).toString().contains(search)){
                spinner.setSelection(i);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        cStatus = adapterView.getItemAtPosition(i).toString();
    }


    public void clickCancelChanges(View view) {
        Intent intent = new Intent(this, CourseDetails.class);
        intent.putExtra("id", courseID);
        repository = new Repository(getApplication());
        startActivity(intent);
    }
}