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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    EditText emailtv,passwordtv,fullname,username,mail,phonenumber;
    Button signup,finalsignup;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailtv=findViewById(R.id.emailed);
        passwordtv=findViewById(R.id.passworded);
        fullname=findViewById(R.id.fullnameed);
        mail=findViewById(R.id.emailed);
        username=findViewById(R.id.usernameet);
        phonenumber=findViewById(R.id.phonenumbered);
        finalsignup=findViewById(R.id.buttonfinal);
        mAuth=FirebaseAuth.getInstance();

        Intent intent=getIntent();
        String mailstr=intent.getStringExtra("mail");
        String password=intent.getStringExtra("password");

        emailtv.setText(mailstr);
        passwordtv.setText(password);

        finalsignup.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                final String fullnamest=fullname.getText().toString();
                final String usernamest=username.getText().toString();
                final String mailst=emailtv.getText().toString();
                final String phonenumberst=phonenumber.getText().toString();


                //    FirebaseUser currentuser=mAuth.getCurrentUser();
                //    String Id=currentuser.getUid();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();

                final DatabaseReference userref= FirebaseDatabase.getInstance().getReference().child(uid);

                userref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.child("Users").child(usernamest).exists())
                        {
                            HashMap<String, Object> userdataMap = new HashMap<>();
                            userdataMap.put("username", usernamest);
                            userdataMap.put("mail", mailst);
                            //  userdataMap.put("location",finaaddress);
                            userdataMap.put("fullname",fullnamest);
                            userdataMap.put("phonenumber",phonenumberst);

                            userref.child("Users").child(usernamest).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful() && usernamest!=null && mailst!=null && fullnamest!=null && phonenumberst!=null)
                                    {
                                        Toast.makeText(Register.this, "You have been registered successfully..", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(Register.this,Login.class);

                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(Register.this, "Network error .or change username...Try again later..", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    private void registerNewUser() {
        String email,password;
        email=emailtv.getText().toString();
        password=passwordtv.getText().toString();
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