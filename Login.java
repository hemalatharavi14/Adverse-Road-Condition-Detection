package com.example.prayaan2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText user,pwd;
    Button login;
    TextView signup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        mAuth=FirebaseAuth.getInstance();
        user=(EditText)findViewById(R.id.userId);
        pwd=(EditText)findViewById(R.id.userPass);
        login=(Button)findViewById(R.id.loginbtn);
        signup=(TextView)findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Signup.class));
            }
        });
    }
    private void loginUser(){
        String email=user.getText().toString();
        String pass=pwd.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(Login.this,"Enter EmailId",Toast.LENGTH_LONG).show();
            user.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(Login.this,"Enter Password",Toast.LENGTH_LONG).show();
            pwd.requestFocus();
            return;
        }
        else if(pass.length()<6){
            Toast.makeText(Login.this,"Password should have minimum 6 characters",Toast.LENGTH_LONG).show();
            pwd.requestFocus();
            return;
        }
        else {

            //IMPORTANT -- Make sure to enable email login in firebase authentication or else the app will crash
            mAuth.signInWithEmailAndPassword(user.getText().toString(),pwd.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this, "Log in failed.. Try later", Toast.LENGTH_LONG).show();
                        }
                    });
            // **************************************************************
        }

    }
}
