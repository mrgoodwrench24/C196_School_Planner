package com.example.schoolplanner.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.schoolplanner.DAO.AssessmentDAO;
import com.example.schoolplanner.DAO.CourseDAO;
import com.example.schoolplanner.DAO.TermDAO;
import com.example.schoolplanner.Entity.Assessment;
import com.example.schoolplanner.Entity.Course;
import com.example.schoolplanner.Entity.Term;

@Database(entities = {Assessment.class, Course.class, Term.class}, version = 1, exportSchema = false)
public abstract class SchoolDatabaseBuilder extends RoomDatabase {
    public abstract AssessmentDAO assessmentDAO();
    public abstract CourseDAO courseDAO();
    public abstract TermDAO termDAO();

    private static volatile SchoolDatabaseBuilder INSTANCE;

    static SchoolDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (SchoolDatabaseBuilder.class) {
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SchoolDatabaseBuilder.class, "SchoolDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }



}
