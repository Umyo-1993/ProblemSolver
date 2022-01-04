package com.example.problemsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Admin_Message_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_message);
        Intent intent=getIntent();
        Toast.makeText(Admin_Message_Activity.this, ""+intent.getStringExtra("adminuserid"), Toast.LENGTH_SHORT).show();
        Toast.makeText(Admin_Message_Activity.this, ""+intent.getStringExtra("adminusername"), Toast.LENGTH_SHORT).show();
    }
}