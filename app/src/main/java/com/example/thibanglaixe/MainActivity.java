package com.example.thibanglaixe;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thibanglaixe.Adapter.MainactivityFuncAdapter;
import com.example.thibanglaixe.untilities.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ArrayList<Integer> listIdIcon, listIdColor;
    ArrayList<String> listNameFunc;
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
        int px = dpToPx(this, 190);
        main_rv_func = findViewById(R.id.main_rv_func);
        //main_rv_func.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        main_rv_func.setLayoutManager(new GridLayoutManager(getApplicationContext(), spanCount));
        main_rv_func.addItemDecoration(new GridSpacingItemDecoration(spanCount, px));
        listIdColor = new ArrayList<>(Arrays.asList(R.color.background_default, R.color.brown, R.color.green, R.color.red, R.color.violet, R.color.black, R.color.yellow, R.color.blue));
        listIdIcon = new ArrayList<>(Arrays.asList(R.drawable.baseline_access_time_34, R.drawable.baseline_article_34, R.drawable.baseline_error_outline_34,
                R.drawable.baseline_menu_book_34, R.drawable.baseline_traffic_34, R.drawable.baseline_label_34,
                R.drawable.baseline_shield_34, R.drawable.baseline_assessment_34));
        listNameFunc = new ArrayList<>(Arrays.asList("Đề ngẫu nhiên", "Thi theo đề", "Các câu bị sai", "On tập câu hỏi", "Các biển báo", "Mẹo ghi nhớ", "Câu hỏi điểm liệt", "Top 50 câu bị sai"));
        adapter = new MainactivityFuncAdapter(this, listIdColor, listNameFunc, listIdIcon);
        main_rv_func.setAdapter(adapter);
    }

    private int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()
        );
    }
}