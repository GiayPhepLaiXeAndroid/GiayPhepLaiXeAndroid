package com.example.thibanglaixe;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thibanglaixe.Adapter.MainactivityFuncAdapter;
import com.example.thibanglaixe.object.DocxReader;
import com.example.thibanglaixe.object.Question;
import com.example.thibanglaixe.untilities.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ArrayList<Integer> listIdIcon, listIdColor;
    ArrayList<String> listNameFunc, listTag;
    RecyclerView main_rv_func;
    MainactivityFuncAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int spanCount = 2;
        int px = dpToPx(this, 180);
        main_rv_func = findViewById(R.id.main_rv_func);
        //main_rv_func.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        main_rv_func.setLayoutManager(new GridLayoutManager(getApplicationContext(), spanCount));
        main_rv_func.addItemDecoration(new GridSpacingItemDecoration(spanCount, px));
        listIdColor = new ArrayList<>(Arrays.asList(R.drawable.background_default_color, R.drawable.background_brown_color, R.drawable.background_green_color,
                R.drawable.background_red_color, R.drawable.background_violet_color, R.drawable.background_black_color, R.drawable.background_yellow_color,
                R.drawable.background_blue_color));
        listIdIcon = new ArrayList<>(Arrays.asList(R.drawable.baseline_access_time_34, R.drawable.baseline_article_34, R.drawable.baseline_error_outline_34,
                R.drawable.baseline_menu_book_34, R.drawable.baseline_traffic_34, R.drawable.baseline_label_34,
                R.drawable.baseline_shield_34, R.drawable.baseline_assessment_34));
        listNameFunc = new ArrayList<>(Arrays.asList("Đề ngẫu nhiên", "Thi theo đề", "Các câu bị sai", "Ôn tập câu hỏi", "Các biển báo", "Mẹo ghi nhớ", "Câu hỏi điểm liệt", "Top 50 câu bị sai"));
        listTag = new ArrayList<>(Arrays.asList("random", "test", "wrong", "practice", "news", "note", "point", "top"));
        adapter = new MainactivityFuncAdapter(this, listIdColor, listIdIcon, listNameFunc, listTag);
        main_rv_func.setAdapter(adapter);

        ArrayList<Question> listQuestion = DocxReader.readDocxQuestions(getApplicationContext(), "test_data.docx");
        Toast.makeText(this, Integer.toString(listQuestion.size()), Toast.LENGTH_SHORT).show();
    }

    private int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()
        );
    }
}