package com.example.straydog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터 베이스 서버 연결
    private EditText mEtName, mEtPass, mEtPassck , mEtEmail,mEtRegion,mEtPhoneNo;  //회원가입 입력필드
    Button  btn_login;
    TextView btn_register, btn_findId, btn_findPw;
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("straydog");


        mEtPass = findViewById(R.id.et_pass);
        mEtEmail = findViewById(R.id.et_email);



        btn_login = findViewById(R.id.login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = mEtEmail.getText().toString();
                String strPass = mEtPass.getText().toString();

                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //로그인 성공
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish(); //현재 액티비티 파괴
                        }else{
                            Toast.makeText(Login.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btn_register = findViewById(R.id.register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 화면으로 이동
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
        btn_findId = findViewById(R.id.finding_id);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //아이디 찾기 화면으로 이동
                Intent intent = new Intent(Login.this, finding_id.class);
                startActivity(intent);
            }
        });
        btn_findPw = findViewById(R.id.finding_pw);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //비번 찾기 화면으로 이동
                Intent intent = new Intent(Login.this, Finding_pw.class);
                startActivity(intent);
            }
        });
    }
}