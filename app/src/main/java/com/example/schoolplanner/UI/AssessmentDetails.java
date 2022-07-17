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
import android.widget.TextView;
import com.example.schoolplanner.Database.Repository;
import com.example.schoolplanner.Entity.Assessment;
import com.example.schoolplanner.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {

    private TextView textAssessmentName;
    private TextView textStartDate;
    private TextView textEndDate;
    private TextView textType;
    private TextView textStatus;
    private String assessmentName;
    private String startDate;
    private String endDate;
    private String type;
    private String status;
    private int assessmentID;
    private int courseID;
    private Assessment workingAssessment;
    private Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        repository = new Repository(getApplication());
        textAssessmentName = findViewById(R.id.textViewAUTitle);
        textStartDate = findViewById(R.id.textViewADStartDate);
        textEndDate = findViewById(R.id.editTextAUEnd);
        textType = findViewById(R.id.spinnerAUType);
        textStatus = findViewById(R.id.spinnerAUStatus);
        assessmentID = getIntent().getIntExtra("id", -1);
        workingAssessment = findAssessment(assessmentID);
        courseID = workingAssessment.getCourseID();
        assessmentName = workingAssessment.getAssessmentTitle();
        startDate = workingAssessment.getStartDate();
        endDate = workingAssessment.getEndDate();
        type = workingAssessment.getType();
        status = workingAssessment.getResult();
        textAssessmentName.setText(assessmentName);
        textStartDate.setText(startDate);
        textEndDate.setText(endDate);
        textType.setText(type);
        textStatus.setText(status);


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_assessmentdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.assessmentsMenuEdit:
                Intent edit = new Intent(this, AssessmentUpdate.class);
                    edit.putExtra("id", workingAssessment.getAssessmentID());
                startActivity(edit);
                return true;
            case R.id.assessmentsMenuDelete:
                int courseID = workingAssessment.getCourseID();
                repository.delete(workingAssessment);
                Intent delete = new Intent(this, CourseDetails.class);
                delete.putExtra("courseID", courseID);
                startActivity(delete);
                return true;
            case R.id.notifcationStart:
                String startDateSelected = textStartDate.getText().toString();
                Date notifyStartDate = null;
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                try{
                    notifyStartDate=sdf.parse(startDateSelected);
                } catch(ParseException e) {
                    e.printStackTrace();
                }
                Long startTrigger = notifyStartDate.getTime();
                Intent notify = new Intent(AssessmentDetails.this, MyReceiver.class);
                notify.putExtra("key", workingAssessment.getAssessmentTitle() + " starts today!");
                PendingIntent sender=PendingIntent.getBroadcast(AssessmentDetails.this,MainActivity.numAlert++,notify,PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,startTrigger,sender);
                return true;
            case R.id.notifcationEnd:
                String endDateSelected = textEndDate.getText().toString();
                Date notifyEndDate = null;
                String myFormatEnd = "MM/dd/yy";
                SimpleDateFormat sdfEnd = new SimpleDateFormat(myFormatEnd, Locale.US);
                try{
                    notifyEndDate=sdfEnd.parse(endDateSelected);
                } catch(ParseException e) {
                    e.printStackTrace();
                }
                Long endTrigger = notifyEndDate.getTime();
                Intent notifyEnd = new Intent(AssessmentDetails.this, MyReceiver.class);
                notifyEnd.putExtra("key", workingAssessment.getAssessmentTitle() + " ends today!");
                PendingIntent senderEnd=PendingIntent.getBroadcast(AssessmentDetails.this,MainActivity.numAlert++,notifyEnd,PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManagerEnd = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManagerEnd.set(AlarmManager.RTC_WAKEUP,endTrigger,senderEnd);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private Assessment findAssessment(int assessmentID) {
        Assessment search;
        List<Assessment> allAssessments = repository.getAllAssessments();
        for(int i = 0; i < allAssessments.size(); i++){
            if(allAssessments.get(i).getAssessmentID() == assessmentID){
                search = allAssessments.get(i);
                return search;
            }

        }
        return null;
    }

    public void onClickBack(View view) {
        Intent back = new Intent(this, AssessmentList.class);
        back.putExtra("courseID", courseID);
        startActivity(back);
    }
}