package com.example.thibanglaixe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.thibanglaixe.MainActivity;
import com.example.thibanglaixe.R;
import com.example.thibanglaixe.untilities.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    LinearLayout login_layout_signup;
    EditText login_et_email, login_et_password;
    CheckBox login_cb_saveInf;
    Button login_btn_login;
    TextView login_tv_forgotPassword;
    Boolean flagEmail = true, flagPassword = true;
    SharedPreferences sharedPreferences;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        login_layout_signup = findViewById(R.id.login_layout_signup);
        login_layout_signup.setOnClickListener(viewSignUp -> {
            Intent SignUp = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(SignUp);
        });

        login_et_email = findViewById(R.id.login_et_email);
        login_et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    String email = login_et_email.getText().toString();
                    if(email.isEmpty()) {
                        login_et_email.setError("Email không được để trống");
                        flagEmail = false;
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        login_et_email.setError("Email không hợp lệ");
                        flagEmail = false;
                    } else {
                        login_et_email.setError(null);
                        flagEmail = true;
                    }
                }
            }
        });

        login_et_password = findViewById(R.id.login_et_password);
        login_et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    String password = login_et_password.getText().toString();
                    if(password.isEmpty()) {
                        login_et_password.setError("Mật khẩu không được để trống");
                        flagPassword = false;
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(password).matches()) {
                        login_et_password.setError("Mật khẩu không hợp lệ");
                        flagPassword = false;
                    } else {
                        login_et_password.setError(null);
                        flagPassword = true;
                    }
                }
            }
        });

        login_btn_login = findViewById(R.id.login_btn_login);
        login_btn_login.setOnClickListener(login -> {
            if(validation()) {
                String email = login_et_email.getText().toString();
                String password = login_et_password.getText().toString();
                sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
                firebaseFirestore = FirebaseFirestore.getInstance();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                firebaseFirestore.collection(Constants.PREF_COLLECTION_USER)
                        .whereEqualTo(Constants.PREF_EMAIL, email)
                        .whereEqualTo(Constants.PREF_PASSWORD, password)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Kiểm tra thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                                }else {
                                    Intent viewMain = new Intent(LoginActivity.this, MainActivity.class);
                                    viewMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(viewMain);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Vui lòng thử lại sau",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    public boolean validation() {
        if(flagEmail && flagPassword) return true;
        return false;
    }
}