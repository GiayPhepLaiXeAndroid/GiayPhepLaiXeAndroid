package com.example.thibanglaixe.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.thibanglaixe.R;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TipsRememberActivity extends AppCompatActivity {
    TextView tv_TipsRemember_content;
    final String url = "https://docs.google.com/document/d/1bDaPa97pLueWLjPskuDnrbPP7M4CrayYTUDm91a3RXw/edit?tab=t.0#heading=h.rbovp2r9lcs7";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tips_remember);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tv_TipsRemember_content = findViewById(R.id.tv_tips_remember_content);
        String content = readFileFromAssets("tips_remember.docx");
        tv_TipsRemember_content.setText(readFormattedDocx("tips_remember.docx"));
//        fetchTextFromUrl(url);
    }

    public void fetchTextFromUrl(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    final String html = response.body().string();
                    Document documet = Jsoup.parse(html);
                    String text = documet.text();

                    // Chạy trên UI thread để cập nhật giao diện
                    new android.os.Handler(Looper.getMainLooper()).post(() -> {
                        tv_TipsRemember_content.setText(text); // Cập nhật UI
                    });
                }
            }

        });
    }
    
    private String readFileFromAssets(String fileName) {
        StringBuilder content = new StringBuilder();
        try {
            InputStream inputStream = getAssets().open(fileName);
            XWPFDocument document = new XWPFDocument(inputStream);
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                content.append(paragraph.getText()).append("\n");
            }
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content.toString();
    }

    private SpannableString readFormattedDocx (String fileName) {
        StringBuilder content = new StringBuilder();
        SpannableString spannableString = new SpannableString("");
        try {
            InputStream inputStream = getAssets().open(fileName);
            XWPFDocument document = new XWPFDocument(inputStream);
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String runText = run.text();
                    if (runText.isEmpty()) continue;
                    int start = content.length();
                    content.append(runText).append(" ");

                    if(run.isBold()) {
                        spannableString = new SpannableString(content.toString());
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, start + runText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                    if(run.isItalic()) {
                        spannableString = new SpannableString(content.toString());
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, start + runText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                content.append("\n");
            }
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return spannableString;
    }
}