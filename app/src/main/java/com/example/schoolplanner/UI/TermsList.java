package com.example.schoolplanner.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.schoolplanner.Database.Repository;
import com.example.schoolplanner.Entity.Term;
import com.example.schoolplanner.R;

import java.util.List;

public class TermsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_terms_list);
        Repository repo = new Repository(getApplication());
        List<Term> terms = repo.getmAllTerms();
        RecyclerView recyclerView=findViewById(R.id.termRecyclerView);
        final TermsAdapter adapter = new TermsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);

    }

    public void goToTermUpdate(View view){
        Intent intent = new Intent(this, TermUpdate.class);
        startActivity(intent);
    }
}