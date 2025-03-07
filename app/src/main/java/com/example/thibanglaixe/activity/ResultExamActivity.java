package com.example.thibanglaixe.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.thibanglaixe.MainActivity;
import com.example.thibanglaixe.R;
import com.example.thibanglaixe.untilities.CustomPieChart;

import java.util.Arrays;

public class ResultExamActivity extends AppCompatActivity {

    TextView correct_percent, wrong_percent;
    Button btn_to_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result_exam);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        correct_percent = findViewById(R.id.correct_percent);
        wrong_percent = findViewById(R.id.wrong_percent);
        CustomPieChart pieChart = findViewById(R.id.pieChart);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int countQuestion = bundle.getInt("countQuestion");
            int countCorrect = bundle.getInt("countCorrectAnswer");
            Toast.makeText(getApplicationContext(), Integer.toString(countQuestion) + " " + Integer.toString(countCorrect), Toast.LENGTH_SHORT).show();
            int countWrong = countQuestion - countCorrect;
            float percentCorrect = Math.round((float) countCorrect / countQuestion * 100f);
            float percentWrong = Math.round((float) (1 - percentCorrect / 100f) * 100f);
            pieChart.setData(Arrays.asList(percentCorrect, percentWrong),
                    Arrays.asList(getColor(R.color.green), getColor(R.color.red)));

            correct_percent.setText(Integer.toString(countCorrect) + " " + "(" + Float.toString(percentCorrect) + "%)");
            wrong_percent.setText(Integer.toString(countWrong) + " " + "(" + Float.toString(percentWrong) + "%)");

            btn_to_home = findViewById(R.id.btn_to_home);
            btn_to_home.setOnClickListener(v -> {
                Intent toMainAtvt = new Intent(this, MainActivity.class);
                toMainAtvt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(toMainAtvt);
            });
        }

    }
}