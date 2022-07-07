package com.example.schoolplanner.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.schoolplanner.Database.Repository;
import com.example.schoolplanner.Entity.Course;
import com.example.schoolplanner.Entity.Term;
import com.example.schoolplanner.R;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class TermDetails extends AppCompatActivity {
    private TextView title;
    private TextView termName;
    private TextView termStart;
    private TextView termEnd;
    private String name;
    private String start;
    private String end;
    private int termID;
    private static int termIDCourse;
    private Repository repository;
    protected List<Course> termCourses;
    Term workingTerm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        repository = new Repository(getApplication());
        title = findViewById(R.id.textViewAUTitle);
        termName = findViewById(R.id.detailTermName);
        termStart = findViewById(R.id.detailStartDate);
        termEnd = findViewById(R.id.detailEndDate);
        termID = getIntent().getIntExtra("id", -1);
        workingTerm = workingTerm(termID);
        name = workingTerm.getTermName();
        start = workingTerm.getStartDate();
        end = workingTerm.getEndDate();
        title.setText("Term " + termID);
        termName.setText(name);
        termStart.setText(start);
        termEnd.setText(end);
        termIDCourse = termID;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView=findViewById(R.id.courseRecyclerView);
        termCourses = setTermCourses(termID);
        final CoursesAdapter adapter = new CoursesAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(termCourses);
    }

    public void clickUpdateTerm(View view){


    }


    public List setTermCourses(int termID){
        List<Course> allCourses = repository.getmAllCourses();
        try {

            List<Course> termCourse = new ArrayList<Course>();
            for (Course course : allCourses) {
                if (course.getTermID() != termID) {
                    termCourse.add(course);
                }
            }
            allCourses.removeAll(termCourse);
        }catch(ConcurrentModificationException e){
            e.printStackTrace();
        }

        return allCourses;


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_termdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.deleteTerm:
                repository.delete(workingTerm);
                Intent delete = new Intent(this, TermsList.class);
                startActivity(delete);
                return true;
            case R.id.editTerm:
                Intent intent=new Intent(this, TermUpdate.class);
                if (!name.isEmpty() && !start.isEmpty() && !end.isEmpty()) {
                    intent.putExtra("id",termID);
                }
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);

    }




    public static int getTermIDCourse() {
        return termIDCourse;
    }

    public void clickAddCourse(View view) {
        Intent intent = new Intent(this, CourseUpdate.class);
        startActivity(intent);
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