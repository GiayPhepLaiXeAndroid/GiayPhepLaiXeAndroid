package com.example.thibanglaixe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    LinearLayout signup_layout_login;
    TextView signup_et_email, signup_et_password, signup_et_confirmpassword, signup_et_phone;
    Button signup_btn_signup;
    Boolean flagEmail = true, flagPassword = true, flagConfirmPassword = true, flagPhone = true;
    SharedPreferences sharedPreferences;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signu_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signup_layout_login = findViewById(R.id.signup_layout_login);
        signup_layout_login.setOnClickListener(viewLogin -> {
            Intent viewLoginActivity = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(viewLoginActivity);
        });

        signup_et_email = findViewById(R.id.signup_et_email);
        signup_et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    String email = signup_et_email.getText().toString();
                    if(email.isEmpty()) {
                        signup_et_email.setError("Email không được để trống");
                        flagEmail = false;
                        return;
                    } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        signup_et_email.setError("Email không hợp lệ");
                        flagEmail = false;
                        return;
                    }

                    firebaseFirestore.collection(Constants.PREF_COLLECTION_USER)
                            .whereEqualTo(Constants.PREF_EMAIL, email)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                         signup_et_email.setError("Email đã tồn tại");
                                         flagEmail = false;
                                    }
                                    else {
                                        signup_et_email.setError(null);
                                        flagEmail = true;
                                    }
                                }
                            });
                }
            }
        });

        signup_et_password = findViewById(R.id.signup_et_password);
        signup_et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    String password = signup_et_password.getText().toString();
                    if(password.isEmpty()) {
                        signup_et_password.setError("Password không được để trống");
                        flagPassword = false;
                    } else if(password.length() < 8) {
                        signup_et_password.setError("Password từ 8 kí tự");
                        flagPassword = false;
                    } else {
                        signup_et_password.setError(null);
                        flagPassword = true;
                    }
                }
            }
        });

        signup_et_confirmpassword = findViewById(R.id.signup_et_confirm_password);
        signup_et_confirmpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    String confirmPassword = signup_et_confirmpassword.getText().toString();
                    String password = signup_et_password.getText().toString();
                    if(confirmPassword.isEmpty()) {
                        signup_et_confirmpassword.setError("Mật khẩu không được để trống");
                        flagConfirmPassword = false;
                    } else if(!confirmPassword.equals(password)) {
                        signup_et_confirmpassword.setError("Mật khẩu không khớp");
                        flagConfirmPassword = false;
                    } else {
                        signup_et_confirmpassword.setError(null);
                        flagConfirmPassword = true;
                    }
                }
            }
        });

        signup_et_phone = findViewById(R.id.signup_et_phone);
        signup_et_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    String phone = signup_et_phone.getText().toString();
                    if(phone.isEmpty()) {
                        signup_et_phone.setError("Số điện thoại không được để trống");
                        flagPhone = false;
                    } else if(!Patterns.PHONE.matcher(phone).matches()) {
                        signup_et_phone.setError("Số điện thoại không hợp lệ");
                        flagPhone = false;
                    } else {
                        signup_et_phone.setError(null);
                        flagPhone = true;
                    }
                }
            }
        });


        sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        firebaseFirestore = FirebaseFirestore.getInstance();

        signup_btn_signup = findViewById(R.id.signup_btn_signup);
        signup_btn_signup.setOnClickListener(SignUpActivity -> {
            if(validation()) {
                String email = signup_et_email.getText().toString();
                String password = signup_et_password.getText().toString();
                String confirmPassword = signup_et_confirmpassword.getText().toString();
                String phone = signup_et_phone.getText().toString();
                Map<String, Object> user = new HashMap<>();
                user.put(Constants.PREF_EMAIL, email);
                user.put(Constants.PREF_PASSWORD, password);
                user.put(Constants.PREF_PHONE, phone);
                firebaseFirestore.collection(Constants.PREF_COLLECTION_USER)
                        .add(user)
                        .addOnSuccessListener(documentReference -> {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Constants.PREF_EMAIL, email);
                            editor.putString(Constants.PREF_PASSWORD, password);
                            editor.putString(Constants.PREF_PHONE, phone);
                            editor.putBoolean(Constants.PREF_IS_LOGIN, true);
                            editor.apply();
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getApplicationContext(), "Có lỗi xảy ra, vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                        });
            }
            else {
                Toast.makeText(getApplicationContext(), "Vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Boolean validation() {
        if(flagEmail && flagPassword && flagConfirmPassword && flagPhone) {
            return true;
        }
        return false;
    }
}