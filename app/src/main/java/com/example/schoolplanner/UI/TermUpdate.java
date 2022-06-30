package com.example.schoolplanner.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import com.example.schoolplanner.Database.Repository;
import com.example.schoolplanner.Entity.Term;
import com.example.schoolplanner.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TermUpdate extends AppCompatActivity {
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    private TextView title;
    private EditText termName;
    private EditText termStart;
    private EditText termEnd;
    private String name;
    private String start;
    private  String end;
    private int termID;
    Term workingTerm;
    DatePickerDialog.OnDateSetListener editTermStartDate;
    DatePickerDialog.OnDateSetListener editTermEndDate;
    private Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_update);
        repository = new Repository(getApplication());

        title = findViewById(R.id.updateTitle);
        termName = findViewById(R.id.updateTermName);
        termStart = findViewById(R.id.editTermStartDate);
        termEnd = findViewById(R.id.editTermEndDate);
        termID = getIntent().getIntExtra("id", -1);
        workingTerm = workingTerm(termID);
        if(termID == -1){
            title.setText("New Term");
        }
        else{
            name = workingTerm.getTermName();
            termName.setText(name);
            start = workingTerm.getStartDate();
            termStart.setText(start);
            end = workingTerm.getEndDate();
            termEnd.setText(end);
            title.setText("Term " + termID);
            termName.setText(name);
        }
        termStart = findViewById(R.id.editTermStartDate);
        editTermStartDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                // TODO Auto-generated method stub

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                termStart.setText(start);
                updateStartLabel();

            }
        };

        termStart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //TODO Auto-generated method stub
                new DatePickerDialog(TermUpdate.this, editTermStartDate, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        termEnd = findViewById(R.id.editTermEndDate);
        editTermEndDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                // TODO Auto-generated method stub

                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                termEnd.setText(end);
                updateEndLabel();

            }
        };

        termEnd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //TODO Auto-generated method stub
                new DatePickerDialog(TermUpdate.this, editTermEndDate, myCalendarEnd.get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




    }


    private void updateStartLabel(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        termStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateEndLabel(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        termEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }


    public void termUpdateSave(View view){
        Term updateTerm;
        if(termID == -1){
            int newID = repository.getmAllTerms().get(repository.getmAllTerms().size()-1).getTermID() + 1;
            updateTerm = new Term(newID, termName.getText().toString(),termStart.getText().toString(),termEnd.getText().toString());
            repository.insert(updateTerm);
            Intent intent = new Intent(this, TermsList.class);
            startActivity(intent);
        }
        else{
            updateTerm = new Term(termID, termName.getText().toString(),termStart.getText().toString(),termEnd.getText().toString());
            repository.update(updateTerm);
            Intent intent = new Intent(this, TermDetails.class);
            intent.putExtra("id",termID);
            repository = new Repository(getApplication());
            startActivity(intent);
        }

    }

    public Term workingTerm(int termID){
        List<Term> allTerms = repository.getmAllTerms();
        for(Term search : allTerms){
            if(search.getTermID() == termID){
                return search;
            }
        }
        return null;
    }
}