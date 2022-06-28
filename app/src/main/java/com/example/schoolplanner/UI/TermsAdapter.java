package com.example.schoolplanner.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolplanner.Entity.Term;
import com.example.schoolplanner.R;

import java.util.List;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.TermViewHolder> {
    class TermViewHolder extends RecyclerView.ViewHolder{
        private final TextView termName;
        private final TextView termStart;
        private final TextView termEnd;
        public TermViewHolder(View itemView) {
            super(itemView);
            termName = itemView.findViewById(R.id.textViewDetailName);
            termStart = itemView.findViewById(R.id.textViewStart);
            termEnd = itemView.findViewById(R.id.textViewEnd);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position=getAdapterPosition();
                    final Term current=mTerms.get(position);
                    Intent intent=new Intent(context,TermDetails.class);
                    intent.putExtra("id", current.getTermID());
                    intent.putExtra("name", current.getTermName());
                    intent.putExtra("start", current.getStartDate());
                    intent.putExtra("end", current.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    public TermsAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }




    @NonNull
    @Override
    public TermsAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.term_view_row,parent,false);

        return new TermsAdapter.TermViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermsAdapter.TermViewHolder holder, int position) {
        holder.termName.setText(mTerms.get(position).getTermName());
        holder.termStart.setText("Start Date: " + mTerms.get(position).getStartDate());
        holder.termEnd.setText("End Date: " + mTerms.get(position).getEndDate());

    }

    public void setTerms(List<Term> terms){
        mTerms=terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mTerms!=null){
            return mTerms.size();
        }
        else return 0;
    }
}
