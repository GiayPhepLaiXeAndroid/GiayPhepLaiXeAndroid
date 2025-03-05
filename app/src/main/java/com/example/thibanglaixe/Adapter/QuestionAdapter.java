package com.example.thibanglaixe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thibanglaixe.R;
import com.example.thibanglaixe.object.Question;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {
    Context context;
    ArrayList<Question> listQuestion;

    public QuestionAdapter(Context context, ArrayList<Question> listQuestion) {
        this.context = context;
        this.listQuestion = listQuestion;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Question getQuestion = listQuestion.get(position);
        holder.numberQuestion.setText(getQuestion.getNumberQuestion());
        holder.titleQuestion.setText(getQuestion.getTitle());
        holder.answer1.setText(getQuestion.getChoice1());
        holder.answer1.setText(getQuestion.getChoice1());
        holder.answer1.setText(getQuestion.getChoice1());
        holder.answer1.setText(getQuestion.getChoice1());
    }

    @Override
    public int getItemCount() {
        return listQuestion.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView numberQuestion, correct_answer, wrong_answer, titleQuestion;
        RadioButton answer1, answer2, answer3, answer4;
        RadioGroup radioGroup_question;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            numberQuestion = itemView.findViewById(R.id.numberQuestion);
            titleQuestion = itemView.findViewById(R.id.titleQuestion);
            correct_answer = itemView.findViewById(R.id.correct_answer);
            wrong_answer = itemView.findViewById(R.id.wrong_answer);
            answer1 = itemView.findViewById(R.id.answer1);
            answer2 = itemView.findViewById(R.id.answer2);
            answer3 = itemView.findViewById(R.id.answer3);
            answer4 = itemView.findViewById(R.id.answer4);
        }
    }
}
