package com.example.problemsolver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    EditText emailed,passworded;
    TextView returnsignup;
    Button login;
    private FirebaseAuth mAuth;
    CheckBox rememberme;
    TextView forgetpw;
    TextView adminlogin;
    ImageView googlelogo;


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        emailed = findViewById(R.id.selected);
        passworded = findViewById(R.id.selectedpw);
        login = findViewById(R.id.signin);
        returnsignup=findViewById(R.id.textbottom2);
        rememberme=findViewById(R.id.checkbox);
        adminlogin=findViewById(R.id.adminlogintext);
        googlelogo=findViewById(R.id.googlelogo);
        sharedPreferences=getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        editor=sharedPreferences.edit();
        String email, password;
        email = emailed.getText().toString();
        password = passworded.getText().toString();
        forgetpw=findViewById(R.id.forgetpassword);


        //return to Registeractivity
        if(email.equals(""))
        {
            emailed.setText(email.toString());
        }
        if(password.equals(""))
        {
            passworded.setText(password.toString());

        }
        emailed.setText(sharedPreferences.getString("email",""));
        passworded.setText(sharedPreferences.getString("password",""));

        returnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Tentative_Registration.class);

                startActivity(intent);
            }
        });
        adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Admin_User_Activity.class);
                startActivity(intent);
            }
        });
        googlelogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,GoogleLoginActivity.class);
                startActivity(intent);
            }
        });
        // validations for input email and password
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUserAccount();
            }
        });
        forgetpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailf = emailed.getText().toString();

                if (TextUtils.isEmpty(emailf)) {
                    Toast.makeText(getApplicationContext(), "Enter your email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.sendPasswordResetEmail(emailf)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Check email to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Login.this, "Fail to send reset password email!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }

    private void loginUserAccount()
    {

        // show the visibility of progress bar to show loading


        // Take the value of two edit texts in Strings
        String email, password;

        email = emailed.getText().toString();
        password = passworded.getText().toString();


        // validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // signin existing user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Login successful!!",
                                    Toast.LENGTH_LONG)
                                    .show();

                            //firebase email list
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = user.getUid();
                            DatabaseReference dfr=FirebaseDatabase.getInstance().getReference().child("EmailQueue").child(uid);
                            HashMap<String,Object>hashMap=new HashMap<>();
                            hashMap.put("emailqueue",email);//use uid in child
                            hashMap.put("uid",uid);
                            dfr.setValue(hashMap);



                            // hide the progress bar
                            Intent intent=new Intent(Login.this,StartPageActivity1.class);
                            //    Intent intent=new Intent(Login.this,UsersActivity2.class);
                            startActivity(intent);

                            if(rememberme.isChecked() ){
                                Toast.makeText(Login.this, "Checked", Toast.LENGTH_SHORT).show();
                                editor.putString("email",email);
                                editor.putString("password",password);

                                editor.commit();


                            }else{
                                Toast.makeText(Login.this, "Not checked...", Toast.LENGTH_SHORT).show();
                            }
                            // if sign-in is successful
                            // intent to home activity

                        }

                        else {

                            // sign-in failed
                            Toast.makeText(getApplicationContext(),
                                    "Login failed!!",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar

                        }
                    }



                });



    }


}