package com.example.schoolplanner.Database;

import android.app.Application;

import com.example.schoolplanner.DAO.AssessmentDAO;
import com.example.schoolplanner.DAO.CourseDAO;
import com.example.schoolplanner.DAO.TermDAO;
import com.example.schoolplanner.Entity.Assessment;
import com.example.schoolplanner.Entity.Course;
import com.example.schoolplanner.Entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
        private AssessmentDAO mAssessmentDAO;
        private CourseDAO mCourseDAO;
        private TermDAO mTermDAO;

        private List<Assessment> mAllAssessments;
        private List<Course> mAllCourses;
        private List<Term> mAllTerms;

        private static int NUMBER_OF_THREADS=4;
        static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        public Repository(Application application){
                SchoolDatabaseBuilder db = SchoolDatabaseBuilder.getDatabase(application);
                mAssessmentDAO = db.assessmentDAO();
                mCourseDAO = db.courseDAO();
                mTermDAO = db.termDAO();
        }

        public List<Assessment>getAllAssessments(){
                databaseExecutor.execute(()->{
                        mAllAssessments=mAssessmentDAO.getAllAssessments();
                });

                try {
                        Thread.sleep(1000);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                return mAllAssessments;
        }

        public void insert(Assessment assessment){
                databaseExecutor.execute(()->{
                        mAssessmentDAO.insert(assessment);
                });

                try{
                        Thread.sleep(1000);
                }catch(InterruptedException e) {
                        e.printStackTrace();
                }
        }

        public void update(Assessment assessment){
                databaseExecutor.execute(()->{
                        mAssessmentDAO.update(assessment);
                });

                try{
                        Thread.sleep(1000);
                }catch(InterruptedException e) {
                        e.printStackTrace();
                }
        }

        public void delete(Assessment assessment){
                databaseExecutor.execute(()->{
                        mAssessmentDAO.delete(assessment);
                });

                try{
                        Thread.sleep(1000);
                }catch(InterruptedException e) {
                        e.printStackTrace();
                }
        }

        public List<Course>getmAllCourses(){
                databaseExecutor.execute(()->{
                        mAllCourses=mCourseDAO.getAllCourses();
                });

                try {
                        Thread.sleep(1000);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                return mAllCourses;
        }

        public void insert(Course course){
                databaseExecutor.execute(()->{
                        mCourseDAO.insert(course);
                });

                try{
                        Thread.sleep(1000);
                }catch(InterruptedException e) {
                        e.printStackTrace();
                }
        }

        public void update(Course course){
                databaseExecutor.execute(()->{
                        mCourseDAO.update(course);
                });

                try{
                        Thread.sleep(1000);
                }catch(InterruptedException e) {
                        e.printStackTrace();
                }
        }

        public void delete(Course course){
                databaseExecutor.execute(()->{
                        mCourseDAO.delete(course);
                });

                try{
                        Thread.sleep(1000);
                }catch(InterruptedException e) {
                        e.printStackTrace();
                }
        }

        public List<Term>getmAllTerms(){
                databaseExecutor.execute(()->{
                        mAllTerms=mTermDAO.getAllTerms();
                });

                try {
                        Thread.sleep(1000);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                return mAllTerms;
        }

        public void insert(Term term){
                databaseExecutor.execute(()->{
                        mTermDAO.insert(term);
                });

                try{
                        Thread.sleep(1000);
                }catch(InterruptedException e) {
                        e.printStackTrace();
                }
        }

        public void update(Term term){
                databaseExecutor.execute(()->{
                        mTermDAO.update(term);
                });

                try{
                        Thread.sleep(1000);
                }catch(InterruptedException e) {
                        e.printStackTrace();
                }
        }

        public void delete(Term term){
                databaseExecutor.execute(()->{
                        mTermDAO.delete(term);
                });

                try{
                        Thread.sleep(1000);
                }catch(InterruptedException e) {
                        e.printStackTrace();
                }
        }
}
