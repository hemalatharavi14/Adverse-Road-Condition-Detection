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

public class Signup extends AppCompatActivity {

    EditText email,pass;
    Button signupbtn;
    TextView signin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FirebaseApp.initializeApp(this);
        mAuth=FirebaseAuth.getInstance();
        email=(EditText)findViewById(R.id.emailId);
        pass=(EditText)findViewById(R.id.password);
        signupbtn=(Button)findViewById(R.id.signupbtn);
        signin=(TextView)findViewById(R.id.signintext);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this,Login.class));
            }
        });

    }
    private void createUser(){
        String mail=email.getText().toString();
        String password=pass.getText().toString();

        if(TextUtils.isEmpty(mail)){
            Toast.makeText(Signup.this,"Enter EmailId",Toast.LENGTH_LONG).show();
            email.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(Signup.this,"Enter Password",Toast.LENGTH_LONG).show();
            pass.requestFocus();
            return;
        }
        else if(password.length()<6){
            Toast.makeText(Signup.this,"Password should have minimum 6 characters",Toast.LENGTH_LONG).show();
            pass.requestFocus();
            return;
        }
        else {
            //IMPORTANT -- Make sure to enable email login in firebase authentication or else the app will crash

            mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            Toast.makeText(Signup.this, "Success", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Signup.this, MainActivity.class));

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(Signup.this, "Failed..try later", Toast.LENGTH_LONG).show();
                        }
                    });
            //****************************************************************
        }

    }
}
