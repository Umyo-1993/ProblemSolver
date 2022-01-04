package com.example.problemsolver;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin_User_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    adminpersonAdapter personadapter;
    ArrayList<Users2> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);
        recyclerView = findViewById(R.id.recycler1);
        database = FirebaseDatabase.getInstance().getReference("EmailQueue");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();

        personadapter = new adminpersonAdapter(Admin_User_Activity.this,list);
        recyclerView.setAdapter(personadapter);



        database.addValueEventListener(new ValueEventListener() {
            int i=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Users2 user = dataSnapshot.getValue(Users2.class);
                    i++;

                    list.add(user);

                    String a=dataSnapshot.toString();
                    //only one value added to the list

                }
                personadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        System.out.println(list.size());
    }
}