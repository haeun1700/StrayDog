package com.example.straydog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터 베이스 서버 연결
    private EditText mEtName, mEtPass, mEtPassck , mEtEmail,mEtRegion,mEtPhoneNo;  //회원가입 입력필드
    private Button validateButton, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("straydog");

        mEtName = findViewById(R.id.et_name);
        mEtPass = findViewById(R.id.et_pass);
        mEtPassck = findViewById(R.id.et_passck);

        mEtEmail = findViewById(R.id.et_email);
        mEtRegion = findViewById(R.id.et_region);
        mEtPhoneNo = findViewById(R.id.et_phoneNo);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Joinsuccess.class);
                startActivity(intent);
                //회원가입 처리 시작
                String strName = mEtName.getText().toString();
                String strPass = mEtPass.getText().toString();
                String strPassck = mEtPassck.getText().toString();
                String strEmail = mEtEmail.getText().toString();
                String strRegion = mEtRegion.getText().toString();
                int intPhone = Integer.parseInt(mEtPhoneNo.getText().toString());

                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // 가입이 성공했을 때 처리할 코드
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setEmail(firebaseUser.getEmail());
                            account.setIdToken(firebaseUser.getUid());
                            account.setPass(strPass);

                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
                            Toast.makeText(Register.this, "회원가입 성공!", Toast.LENGTH_SHORT);
                        } else {
                            // 가입이 실패했을 때 처리할 코드
                            Toast.makeText(Register.this, "회원가입 실패!", Toast.LENGTH_SHORT);
                        }
                    }
                });

            }
        });
    }
}