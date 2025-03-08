package com.example.thibanglaixe.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thibanglaixe.Adapter.CertificateAdapter;
import com.example.thibanglaixe.R;

import java.util.ArrayList;
import java.util.Arrays;

public class CertificateActivity extends AppCompatActivity {

    ConstraintLayout btn_back;
    RecyclerView rv_certificate;
    ArrayList<String> listCertificate, listDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_certificate);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(view -> finish());

        rv_certificate = findViewById(R.id.rv_certificate);
        rv_certificate.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        listCertificate = new ArrayList<>(Arrays.asList("A1", "A2", "B1", "B2", "C1", "C2"));
        listDesc = new ArrayList<>(Arrays.asList("Chuẩn bằng A1", "Chuẩn bằng A2", "Chuẩn bằng B1", "Chuẩn bằng B2", "Chuẩn bằng C1", "Chuẩn bằng C2"));
        CertificateAdapter adapter = new CertificateAdapter(this, listCertificate, listDesc);
        rv_certificate.setAdapter(adapter);
    }
}