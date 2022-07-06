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
import com.example.schoolplanner.Entity.Assessment;
import com.example.schoolplanner.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AssessmentUpdate extends AppCompatActivity {
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    private int assessmentID;
    private int courseID;
    private String assessmentName;
    private String startDate;
    private String endDate;
    private String type;
    private String status;
    private TextView textViewTitle;
    private EditText editName;
    private EditText editStart;
    private EditText editEnd;
    Assessment workingAssessment;
    DatePickerDialog.OnDateSetListener editAssessmentStartDate;
    DatePickerDialog.OnDateSetListener editAssessmentEndDate;

    private Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_update);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repository = new Repository(getApplication());
        assessmentID = getIntent().getIntExtra("id", -1);

        textViewTitle = findViewById(R.id.textViewAUTitle);
        editName = findViewById(R.id.editTextAUName);
        editStart = findViewById(R.id.editTextAUStart);
        editEnd = findViewById(R.id.editTextAUEnd);
        Spinner typeSpinner = findViewById(R.id.spinnerAUType);
        Spinner statusSpinner = findViewById(R.id.spinnerAUStatus);
        ArrayAdapter<CharSequence> typeSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.assessment_type, android.R.layout.simple_spinner_item);
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeSpinnerAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = typeSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> statusSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.assessment_status, android.R.layout.simple_spinner_item);
        statusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusSpinnerAdapter);
        Spinner finalStatusSpinner = statusSpinner;
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                status = finalStatusSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(assessmentID == -1){
            textViewTitle.setText("Add New Assessment");
        }
        else {
            workingAssessment = findAssessment(assessmentID);
            assessmentName = workingAssessment.getAssessmentTitle();
            courseID = workingAssessment.getCourseID();
            editName.setText(assessmentName);
            startDate = workingAssessment.getStartDate();
            editStart.setText(startDate);
            endDate = workingAssessment.getEndDate();
            editEnd.setText(endDate);
            type = workingAssessment.getType();
            setSpinnerType(typeSpinner, type);
            status = workingAssessment.getResult();
            setSpinnerStatus(statusSpinner, status);


            editAssessmentStartDate = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    myCalendarStart.set(Calendar.YEAR, year);
                    myCalendarStart.set(Calendar.MONTH, month);
                    myCalendarStart.set(Calendar.DAY_OF_MONTH, day);
                    String myFormat = "MM/dd/yy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    editStart.setText(startDate);
                    updateStartLabel();
                }

                private void updateStartLabel() {
                    String myFormat = "MM/dd/yy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    editStart.setText(sdf.format(myCalendarStart.getTime()));
                }
            };

            editStart.setOnClickListener(new View.OnClickListener() {



                @Override
                public void onClick(View view) {
                    //TODO Auto-generated method stub
                    new DatePickerDialog(AssessmentUpdate.this, editAssessmentStartDate, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            editAssessmentEndDate = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    // TODO Auto-generated method stub

                    myCalendarEnd.set(Calendar.YEAR, year);
                    myCalendarEnd.set(Calendar.MONTH, month);
                    myCalendarEnd.set(Calendar.DAY_OF_MONTH, day);
                    String myFormat = "MM/dd/yy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    editEnd.setText(endDate);
                    updateEndLabel();
                }
            };

            editEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO Auto-generated method stub
                    new DatePickerDialog(AssessmentUpdate.this, editAssessmentEndDate,myCalendarEnd.get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

        }

    }

    private void updateEndLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }

    private void setSpinnerType(Spinner typeSpinner, String type) {
        for(int i = 0; i < typeSpinner.getAdapter().getCount(); i++){
            if(typeSpinner.getAdapter().getItem(i).toString().contains(type)){
                typeSpinner.setSelection(i);
            }
        }
    }

    private void setSpinnerStatus(Spinner statusSpinner, String status) {
        for(int i = 0; i < statusSpinner.getAdapter().getCount(); i++){
            if(statusSpinner.getAdapter().getItem(i).toString().contains(status)){
                statusSpinner.setSelection(i);
            }
        }

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

    public void onClickSaveAssessment(View view) {
        Assessment updateAssessment;
        if(assessmentID == -1){
            int newID;
            if(repository.getAllAssessments().size() == 0){
                newID = 1;
            }
            else{
                newID = repository.getAllAssessments().get(repository.getAllAssessments().size()-1).getAssessmentID() + 1;
            }
            courseID = getIntent().getIntExtra("courseID", -1);
            updateAssessment = new Assessment(newID, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), type, status, courseID);
            repository.insert(updateAssessment);
            Intent intent = new Intent(this, AssessmentList.class);
            intent.putExtra("id", assessmentID);
            repository = new Repository(getApplication());
            startActivity(intent);
        }
        else{
            updateAssessment = new Assessment(assessmentID,editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), type, status, courseID);
            repository.update(updateAssessment);
            Intent intent = new Intent(this, AssessmentList.class);
            intent.putExtra("courseID", updateAssessment.getCourseID());
            repository = new Repository(getApplication());
            startActivity(intent);
        }
    }

    public void clickCancelChanges(View view) {
        Intent intent = new Intent(this, AssessmentList.class);
        intent.putExtra("courseID", courseID);
        repository = new Repository(getApplication());
        startActivity(intent);
    }
}