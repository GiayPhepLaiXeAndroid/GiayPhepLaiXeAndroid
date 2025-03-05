package com.example.thibanglaixe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thibanglaixe.R;
import com.example.thibanglaixe.activity.ViewExamActivity;
import com.example.thibanglaixe.object.Exam;

import java.util.ArrayList;

public class PracticeTestAdapter extends RecyclerView.Adapter<PracticeTestAdapter.MyViewHolder> {
    Context context;
    ArrayList<Exam> listExaml;

    public PracticeTestAdapter(Context context, ArrayList<Exam> listExaml) {
        this.context = context;
        this.listExaml = listExaml;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_practice_test, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Exam getExam = listExaml.get(position);
        holder.title.setText(getExam.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewExam = new Intent(holder.itemView.getContext(), ViewExamActivity.class);
                holder.itemView.getContext().startActivity(viewExam);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listExaml.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_tv_practiceTest);
        }
    }
}
