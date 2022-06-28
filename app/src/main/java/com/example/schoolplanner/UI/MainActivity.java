package com.example.schoolplanner.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.schoolplanner.Database.Repository;
import com.example.schoolplanner.Entity.Course;
import com.example.schoolplanner.Entity.Term;
import com.example.schoolplanner.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openPlanner(View view) {
        Intent intent = new Intent(this, TermsList.class);
        startActivity(intent);
        Repository repo = new Repository(getApplication());
        Term term = new Term(1, "Term 1", "01/01/2018", "06/30/2018");
        repo.insert(term);
        Term term2 = new Term(2, "Term 2", "07/01/2018", "12/31/2018");
        repo.insert(term2);
        Course course = new Course(1,"Introduction to IT = C182", "01/01/2018", "06/30/2018","Completed","Jeff Gordon",null,null, "I passed!", 1);
        repo.insert(course);

    }
}