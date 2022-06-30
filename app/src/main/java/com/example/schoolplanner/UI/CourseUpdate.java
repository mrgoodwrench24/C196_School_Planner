package com.example.schoolplanner.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.schoolplanner.Database.Repository;
import com.example.schoolplanner.Entity.Course;
import com.example.schoolplanner.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CourseUpdate extends AppCompatActivity implements AdapterView.OnItemClickListener {

    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    private String name;
    private String start;
    private String end;
    private String cStatus;
    private String cMentorName;
    private String cMentorPhone;
    private String cMentorEmail;
    private String note;
    private EditText courseName;
    private EditText startDate;
    private EditText endDate;
    private EditText ciName;
    private EditText ciPhone;
    private EditText ciEmail;
    private TextView cUpdatetitle;
    private int courseID;
    private static int termID;
    Course course;
    DatePickerDialog.OnDateSetListener editCourseStartDate;
    DatePickerDialog.OnDateSetListener editCourseEndDate;

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_update);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.course_status, android.R.layout.simple_spinner_item);
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

        if (courseID == -1) {
            cUpdatetitle.setText("Add New Course");
        } else {
            course = findCourse(courseID);
            name = course.getTitle();
            courseName.setText(name);
            start = course.getStartDate();
            startDate.setText(start);
            end = course.getEndDate();
            endDate.setText(end);
            cStatus = course.getStatus();
            setSpinner(spinner, cStatus);
            cMentorName = course.getMentorName();
            ciName.setText(cMentorName);
            cMentorPhone = course.getMentorEmail();
            ciPhone.setText(cMentorPhone);
            cMentorEmail = course.getMentorEmail();
            ciEmail.setText(cMentorEmail);
            note = course.getNote();

        }

        editCourseStartDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                // TODO Auto-generated method stub

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                startDate.setText(start);
                updateStartLabel();

            }

        };

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CourseUpdate.this, editCourseStartDate, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editCourseEndDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                // TODO Auto-generated method stub

                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                endDate.setText(end);
                updateEndLabel();

            }

        };

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CourseUpdate.this, editCourseEndDate, myCalendarEnd.get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    private void updateEndLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        endDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateStartLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startDate.setText(sdf.format(myCalendarStart.getTime()));
    }


    public void clickSaveCourse(View view) {
        Course updateCourse;
        if(courseID == -1){
            int newID = repository.getmAllCourses().get(repository.getmAllCourses().size()-1).getCourseID() + 1;
            updateCourse = new Course(newID, courseName.getText().toString(), startDate.getText().toString(),endDate.getText().toString(), cStatus,ciName.getText().toString(),ciPhone.getText().toString(),ciEmail.getText().toString(), null, termID);
            repository.insert(updateCourse);
            Intent intent = new Intent(this, CourseDetails.class);
            //intent.putExtra("id", courseID);
            repository = new Repository(getApplication());
            startActivity(intent);
        }
        else{
            updateCourse = new Course(courseID, courseName.getText().toString(), startDate.getText().toString(),endDate.getText().toString(), cStatus,ciName.getText().toString(),ciPhone.getText().toString(),ciEmail.getText().toString(), note, termID);
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