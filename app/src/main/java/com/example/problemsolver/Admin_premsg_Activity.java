package com.example.problemsolver;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Admin_premsg_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_premsg);
        Button proceed=findViewById(R.id.btnproceed);
        TextView tv=findViewById(R.id.textView);
        EditText et=findViewById(R.id.adminname);

        Intent intent=getIntent();
        String userref=intent.getStringExtra("adminuserid");
        tv.setText(userref.toString());
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(Admin_premsg_Activity.this, Admin_Message_Activity.class);
                Toast.makeText(Admin_premsg_Activity.this, ""+userref, Toast.LENGTH_SHORT).show();
                Toast.makeText(Admin_premsg_Activity.this, ""+et.getText().toString(), Toast.LENGTH_SHORT).show();
                intent.putExtra("adminuserid", userref);
                intent.putExtra("adminusername", et.getText().toString());
                 startActivity(intent);
            }
        });

    }
}