package com.example.problemsolver;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Tentative_Registration extends AppCompatActivity {
    EditText emailt,passwordt;
    Button proceed;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentative_registration);
        emailt=findViewById(R.id.mailtent);
        passwordt=findViewById(R.id.passwordtent);
        proceed=findViewById(R.id.proceed);
        mAuth=FirebaseAuth.getInstance();

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                registerNewUser();
                if(emailt.getText().toString().isEmpty())
                {
                    Toast.makeText(Tentative_Registration.this, "Enter a valid Email", Toast.LENGTH_SHORT).show();

                }
                if(passwordt.getText().toString().isEmpty())
                {
                    Toast.makeText(Tentative_Registration.this, "Enter password", Toast.LENGTH_SHORT).show();
                }
                if(!emailt.getText().toString().isEmpty() && ! passwordt.getText().toString().isEmpty()) {
                    Intent intent = new Intent(Tentative_Registration.this, Register.class);
                    String mailst = String.valueOf(intent.putExtra("mail", emailt.getText().toString()));
                    String passwordst = String.valueOf(intent.putExtra("password", passwordt.getText().toString()));
                    startActivity(intent);
                }


            }
        });


    }

    private void registerNewUser() {
        String email,password;
        email=emailt.getText().toString();
        password=passwordt.getText().toString();
        //checking email input empty or not
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        //checking password input empty or not
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        //create a new USer now
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "Registration successful!",
                            Toast.LENGTH_LONG)
                            .show();

                } else {

                    // Registration failed
                    Toast.makeText(
                            getApplicationContext(),
                            "Registration failed!!"
                                    + " Please try again later",
                            Toast.LENGTH_LONG)
                            .show();

                    // hide the progress bar

                }
            }
        });
    }


}