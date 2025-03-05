package com.example.thibanglaixe.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thibanglaixe.Adapter.QuestionAdapter;
import com.example.thibanglaixe.R;
import com.example.thibanglaixe.object.Question;

import java.util.ArrayList;

public class ViewItemRecyclerViewMainActivity extends AppCompatActivity {
    RecyclerView rv_questions;
    ArrayList<Question> listQuestion;
    QuestionAdapter questionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_item_recycler_view_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rv_questions = findViewById(R.id.rv_questions);
        rv_questions.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        listQuestion = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            Question question = new Question(1, "choice1", "choice2", "choice3", "choice4", "Question " + Integer.toString(i + 1), (i % 4 + 1), "Title question " + Integer.toString(i + 1));
            listQuestion.add(question);
        }
        Toast.makeText(this, Integer.toString(listQuestion.size()), Toast.LENGTH_SHORT).show();
        questionAdapter = new QuestionAdapter(getApplicationContext(), listQuestion);
        rv_questions.setAdapter(questionAdapter);
    }
}