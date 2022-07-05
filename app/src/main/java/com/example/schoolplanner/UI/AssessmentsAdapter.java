package com.example.schoolplanner.UI;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolplanner.Database.Repository;
import com.example.schoolplanner.Entity.Assessment;
import com.example.schoolplanner.R;

import java.util.List;

public class AssessmentsAdapter extends RecyclerView.Adapter<AssessmentsAdapter.AssessmentViewHolder> {
    public AssessmentsAdapter(List<Assessment> mAssessments, Context context, LayoutInflater mInflater){
        this.mAssessment = mAssessments;
        this.context = context;
        this.mInflater = mInflater;
    }



    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentName;
        private final TextView assessmentEndDate;

        public AssessmentViewHolder(View itemview){
            super(itemview);
            assessmentName = itemview.findViewById(R.id.textViewAssessmentName);
            assessmentEndDate = itemview.findViewById(R.id.textAssessmentEndDate);
            itemview.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessment.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("id", current.getAssessmentID());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Assessment> mAssessment;
    private final Context context;
    private final LayoutInflater mInflater;
    private Repository repository;

    public AssessmentsAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentsAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.assessments_view_row,parent,false);

        return new AssessmentsAdapter.AssessmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentsAdapter.AssessmentViewHolder holder, int position) {
        holder.assessmentName.setText(mAssessment.get(position).getAssessmentTitle());
        holder.assessmentEndDate.setText(mAssessment.get(position).getEndDate());

    }

    public void setAssessments(List<Assessment> assessments){
        if(assessments.size()!=0){
            mAssessment = assessments;
            notifyDataSetChanged();
        }
        else{
            mAssessment = null;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if(mAssessment!=null){
            return mAssessment.size();
        }
        else return 0;
    }
}
