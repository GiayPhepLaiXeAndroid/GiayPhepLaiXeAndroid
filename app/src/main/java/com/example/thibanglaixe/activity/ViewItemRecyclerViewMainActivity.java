package com.example.thibanglaixe.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thibanglaixe.Adapter.QuestionAdapter;
import com.example.thibanglaixe.R;
import com.example.thibanglaixe.object.DocxReader;
import com.example.thibanglaixe.object.Question;

import java.util.ArrayList;

public class ViewItemRecyclerViewMainActivity extends AppCompatActivity {
    RecyclerView rv_questions;
    ArrayList<Question> listQuestion;
    QuestionAdapter questionAdapter;
    ConstraintLayout btn_back;
    TextView viewTime, tv_viewItem_title;
    CountDownTimer countDownTimer;
    long timeLeftInMillis = 1800000;
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

        tv_viewItem_title = findViewById(R.id.tv_viewItem_title);
        tv_viewItem_title.setText(getIntent().getStringExtra("title"));

        rv_questions = findViewById(R.id.rv_questions);
        rv_questions.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        listQuestion = new ArrayList<>();
//        for(int i = 0; i < 4; i++) {
//            Question question = new Question("Question " + Integer.toString(i + 1), "Title question " + Integer.toString(i + 1),"choice1", "choice2", "choice3", "choice4", i % 4 + 1);
//            listQuestion.add(question);
//        }
        listQuestion = DocxReader.readDocxQuestions(getApplicationContext(), "test_data.docx");
        int num_question = 24;
        // Lấy một phần danh sách và tạo một danh sách mới từ đó
        ArrayList<Question> questions = new ArrayList<>(listQuestion.subList(0, num_question)); // ✅ Đúng
        //Toast.makeText(this, Integer.toString(listQuestion.size()), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, listQuestion.get(1).getChoice3(), Toast.LENGTH_SHORT).show();
        questionAdapter = new QuestionAdapter(getApplicationContext(), questions);
        rv_questions.setAdapter(questionAdapter);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(btn_back -> finish());

        viewTime = findViewById(R.id.viewTime);
        if(savedInstanceState != null) {
            timeLeftInMillis = savedInstanceState.getLong("timeLeftInMillis", 1800000);
            String timeFormatted = formatTime(timeLeftInMillis);
            viewTime.setText(timeFormatted);
        }
        startCountdown();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("timeLeftInMillis", timeLeftInMillis);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Long timeLeftInMillis = savedInstanceState.getLong("timeLeftInMillis");
        String timeFormatted = formatTime(timeLeftInMillis);
        viewTime.setText(timeFormatted);
    }

    private void startCountdown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTime();
            }
            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateTime();
            }
        }.start();
    }

    private void updateTime() {
        String timeFormatted = formatTime(timeLeftInMillis);
        viewTime.setText(timeFormatted);
    }

    public String formatTime(long timeInMillis) {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        // Định dạng thành "MM:SS"
        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        return timeFormatted;
    }
}