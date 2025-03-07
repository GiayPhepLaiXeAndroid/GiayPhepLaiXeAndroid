package com.example.thibanglaixe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thibanglaixe.R;
import com.example.thibanglaixe.activity.ResultExamActivity;
import com.example.thibanglaixe.object.Question;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {
    Context context;
    ArrayList<Question> listQuestion;
    int countQuestion = 0, countCorrectAnswer = 0;

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
        holder.answer2.setText(getQuestion.getChoice2());
        if(!getQuestion.getChoice3().isEmpty()) holder.answer3.setText(getQuestion.getChoice3());
        else holder.answer3.setVisibility(View.GONE);
        if(!getQuestion.getChoice4().isEmpty()) holder.answer4.setText(getQuestion.getChoice4());
        else holder.answer4.setVisibility(View.GONE);

        holder.radioGroup_question.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int yourAnswer = holder.radioGroup_question.getCheckedRadioButtonId();
                int answer = getQuestion.getAnswer();
                if (yourAnswer != -1) { // Nếu có radio button được chọn
                    countQuestion ++;
                    int index = 0;
                    // Duyệt qua tất cả các radio button trong radio group
                    for (int i = 0; i < holder.radioGroup_question.getChildCount(); i++) {
                        View child = holder.radioGroup_question.getChildAt(i);
                        if (child instanceof RadioButton) { // Chỉ xét các RadioButton
                            index++;
                            if (child.getId() == yourAnswer) { // Nếu ID khớp với button được chọn
                                Toast.makeText(context, "Your answer: " + Integer.toString(yourAnswer) + " Answer: " + Integer.toString(answer), Toast.LENGTH_SHORT).show();
                                if (index == answer) {
                                    holder.correct_answer.setVisibility(View.VISIBLE);
                                    holder.wrong_answer.setVisibility(View.GONE);
                                    countCorrectAnswer ++;
                                } else {
                                    holder.wrong_answer.setVisibility(View.VISIBLE);
                                    holder.correct_answer.setVisibility(View.GONE);
                                }
                                //Toast.makeText(context, "Thứ tự: " + index, Toast.LENGTH_SHORT).show();
//                                break;
                            }
                            else{
                                child.setEnabled(false);
                            }
                        }
                    }
                }

                if (countQuestion == listQuestion.size()) {
                    Intent viewResult = new Intent(context, ResultExamActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("countQuestion", countQuestion);
                    bundle.putInt("countCorrectAnswer", countCorrectAnswer);
                    viewResult.putExtras(bundle);
                    holder.itemView.getContext().startActivity(viewResult);
                }
            }
        });

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
            radioGroup_question = itemView.findViewById(R.id.radioGroup_question);
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
