package com.example.schoolplanner.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private EditText saveNote;
    private int courseID;
    private String name;
    private String start;
    private String end;
    private String cStatus;
    private String cMentorName;
    private String cMentorPhone;
    private String cMentorEmail;
    private String note;
    private Repository repository;
    Course workingCourse;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        repository = new Repository(getApplication());
        courseName = findViewById(R.id.textViewAUTitle);
        startDate = findViewById(R.id.textViewADStartDate);
        endDate = findViewById(R.id.editTextAUEnd);
        status = findViewById(R.id.spinnerAUType);
        ciName = findViewById(R.id.spinnerAUStatus);
        ciPhone = findViewById(R.id.editTextCDInstructorPhone);
        ciEmail = findViewById(R.id.editTextCDInstructorEmail);
        saveNote = findViewById(R.id.editTextNote);
        courseID = getIntent().getIntExtra("id" , -1);
        workingCourse = findCourse(courseID);
        name = workingCourse.getTitle();
        start = workingCourse.getStartDate();
        end = workingCourse.getEndDate();
        cStatus = workingCourse.getStatus();
        cMentorName = workingCourse.getMentorName();
        cMentorPhone = workingCourse.getMentorPhone();
        cMentorEmail = workingCourse.getMentorEmail();
        note = workingCourse.getNote();
        courseName.setText(name);
        startDate.setText(start);
        endDate.setText(end);
        status.setText(cStatus);
        ciName.setText(cMentorName);
        ciPhone.setText(cMentorPhone);
        ciEmail.setText(cMentorEmail);
        saveNote.setText(note);

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
                return true;
            case R.id.editCourse:
                Intent edit = new Intent(this, CourseUpdate.class);
                if(!name.isEmpty()){
                    edit.putExtra("id", courseID);
                }
                startActivity(edit);
                return true;
            case R.id.assessmentsMenuButton:
                Intent assessments = new Intent(this, AssessmentList.class);
                assessments.putExtra("courseID", courseID);
                startActivity(assessments);
                return true;
            case R.id.shareNote:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, saveNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Share Note");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;

        }
        return super.onOptionsItemSelected(item);

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

    public void onActionSaveNote(View view) {
        String newNote = saveNote.getText().toString();
        workingCourse.setNote(newNote);
        repository.update(workingCourse);
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Note Saved", Toast.LENGTH_LONG);
        toast.show();


    }

    public void onClickBack(View view) {
        Intent back = new Intent(this, TermDetails.class);
        back.putExtra("id", workingCourse.getTermID());
        startActivity(back);
    }
}