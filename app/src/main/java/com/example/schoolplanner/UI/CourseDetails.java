package com.example.schoolplanner.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
            case R.id.notifcationStart:
                String startDateSelected = startDate.getText().toString();
                Date notifyStartDate = null;
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                try{
                    notifyStartDate=sdf.parse(startDateSelected);
                } catch(ParseException e) {
                    e.printStackTrace();
                }
                Long startTrigger = notifyStartDate.getTime();
                Intent notify = new Intent(CourseDetails.this, MyReceiver.class);
                notify.putExtra("key", workingCourse.getTitle() + " starts today!");
                PendingIntent sender=PendingIntent.getBroadcast(CourseDetails.this,MainActivity.numAlert++,notify,PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,startTrigger,sender);
                return true;
            case R.id.notifcationEnd:
                String endDateSelected = endDate.getText().toString();
                Date notifyEndDate = null;
                String myFormatEnd = "MM/dd/yy";
                SimpleDateFormat sdfEnd = new SimpleDateFormat(myFormatEnd, Locale.US);
                try{
                    notifyEndDate=sdfEnd.parse(endDateSelected);
                } catch(ParseException e) {
                    e.printStackTrace();
                }
                Long endTrigger = notifyEndDate.getTime();
                Intent notifyEnd = new Intent(CourseDetails.this, MyReceiver.class);
                notifyEnd.putExtra("key", workingCourse.getTitle() + " ends today!");
                PendingIntent senderEnd=PendingIntent.getBroadcast(CourseDetails.this,MainActivity.numAlert++,notifyEnd,PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManagerEnd = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManagerEnd.set(AlarmManager.RTC_WAKEUP,endTrigger,senderEnd);
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