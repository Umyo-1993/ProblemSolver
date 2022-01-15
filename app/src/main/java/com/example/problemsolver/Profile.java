package com.example.problemsolver;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class Profile extends AppCompatActivity {


    TextView name, mail;
    Button logout,home;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        home = findViewById(R.id.home);
        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);
        logout=findViewById(R.id.logout1);
        mAuth = FirebaseAuth.getInstance();



        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            name.setText(signInAccount.getDisplayName());
            mail.setText(signInAccount.getEmail());
        }


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  FirebaseAuth.getInstance().signOut();
               // Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                DatabaseReference dfr= FirebaseDatabase.getInstance().getReference().child("EmailQueue").child(uid);
                HashMap<String,Object> hashMap=new HashMap<>();
                hashMap.put("emailqueue",signInAccount.getEmail());//use uid in child
                hashMap.put("uid",uid);
                dfr.setValue(hashMap);
                Toast.makeText(Profile.this, "email and uid added", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Profile.this,StartPageActivity1.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),GoogleLoginActivity.class);
                startActivity(intent);

            }
        });


    }
}