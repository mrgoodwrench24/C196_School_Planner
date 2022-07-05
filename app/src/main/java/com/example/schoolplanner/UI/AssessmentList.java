package com.example.schoolplanner.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.schoolplanner.Database.Repository;
import com.example.schoolplanner.Entity.Assessment;
import com.example.schoolplanner.R;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class AssessmentList extends AppCompatActivity {

    private int courseID;
    private Repository repository;
    private List<Assessment> courseAssessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        courseID = getIntent().getIntExtra("courseID", -1);
        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAssessments);
        courseAssessments = setCourseAssessments(courseID);
        final AssessmentsAdapter adapter = new AssessmentsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(courseAssessments);
    }

    public void onActionAddAssessment(View view) {
        Intent intent = new Intent(this, AssessmentUpdate.class);
        intent.putExtra("courseID", courseID);
        startActivity(intent);
    }

    public List setCourseAssessments(int courseID){
        List<Assessment> allAssessments = repository.getAllAssessments();
        try{
            List<Assessment> courseAssessments = new ArrayList<>();
                    for(Assessment search : allAssessments){
                        if(search.getCourseID() != courseID){
                            courseAssessments.add(search);

                        }
            }
                    allAssessments.removeAll(courseAssessments);

        }catch(ConcurrentModificationException e){
            e.printStackTrace();
        }
        return allAssessments;
    }
}