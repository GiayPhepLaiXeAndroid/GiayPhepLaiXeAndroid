package com.example.thibanglaixe.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thibanglaixe.Adapter.PracticeTestAdapter;
import com.example.thibanglaixe.R;
import com.example.thibanglaixe.object.Exam;
import com.example.thibanglaixe.untilities.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;

public class PracticeTestActivity extends AppCompatActivity {
    ArrayList<String> practiceTestTitle = new ArrayList<>(Arrays.asList("Đề 1", "Đề 2", "Đề 3", "Đề 4", "Đề 5", "Đề 6"));
    ArrayList<String> practiceTestCode = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6"));
    RecyclerView PracticeTest_rv_Practice;
    PracticeTestAdapter adapter;
    ArrayList<Exam> listExam;
    TextView tv_title_practice;
    ConstraintLayout btn_back_practice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_practice_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tv_title_practice = findViewById(R.id.tv_title_practice);
        tv_title_practice.setText(getIntent().getStringExtra("title"));
        btn_back_practice = findViewById(R.id.btn_back);
        btn_back_practice.setOnClickListener(view -> finish());

        PracticeTest_rv_Practice = findViewById(R.id.PracticeTest_rv_Practice);
        listExam = new ArrayList<>();
        for (int i = 0; i < 22; i++) {
            Exam exam = new Exam(Integer.toString(i), "Đề " + Integer.toString(i));
            listExam.add(exam);
        }
        adapter = new PracticeTestAdapter(getApplicationContext(), listExam);
        int spanCount = 3;
        int itemPx = dpToPx(getApplicationContext(),120);
        PracticeTest_rv_Practice.setLayoutManager(new GridLayoutManager(getApplicationContext(), spanCount));
        PracticeTest_rv_Practice.addItemDecoration(new GridSpacingItemDecoration(spanCount, itemPx));
        PracticeTest_rv_Practice.setAdapter(adapter);
    }

    private int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()
        );
    }
}