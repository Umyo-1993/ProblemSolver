package com.example.problemsolver;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StartPageActivity1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    Button btn1,btn2;
    ImageView im1;
    TextView Expert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page1);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        androidx.appcompat.widget.SearchView searchView;
        ListView listView;
        ArrayList<String> list;
        ArrayAdapter<String > adapter;
        btn1=findViewById(R.id.item_1);
        btn2=findViewById(R.id.item_2);
        im1=findViewById(R.id.expert1);
        Expert=findViewById(R.id.experttext);
        listView = (ListView) findViewById(R.id.listview);
        searchView =  findViewById(R.id.search);
        list = new ArrayList<>();
        list.add("Android");
        list.add("Python");
        list.add("MySql");
        list.add("Firebase");
        Expert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StartPageActivity1.this,ExpertProfileActivity.class);
                startActivity(intent);
            }
        });
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(StartPageActivity1.this, "worked", Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference userdata= FirebaseDatabase.getInstance().getReference().child(uid).child("Users");

        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView usernamehome=headerView.findViewById(R.id.user_profile_name);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        userdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    modelUpload2 modelUpload2=postSnapshot.getValue(com.example.problemsolver.modelUpload2.class);
                    //  Toast.makeText(images.this, ""+postSnapshot.toString(), Toast.LENGTH_SHORT).show();
                    //    Toast.makeText(images.this, ""+modelUpload2.toString(), Toast.LENGTH_SHORT).show();
                    usernamehome.setText(modelUpload2.getFullname());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(StartPageActivity1.this,Selectingsub.class);
              //  startActivity(intent);
                Toast.makeText(StartPageActivity1.this, "Hi there", Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(StartPageActivity1.this,UsersActivity2.class);
            startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StartPageActivity1.this, "Hi I am btn2", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(StartPageActivity1.this,UsersActivity2.class);
                startActivity(intent);
            }
        });

  searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
          if(list.contains(query))
          {
              adapter.getFilter().filter(query);
              listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                  @Override
                  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                      Intent intent=new Intent(StartPageActivity1.this,UsersActivity2.class);
                      startActivity(intent);
                  }
              });
          }else
          {
              Toast.makeText(StartPageActivity1.this, "No Match found",Toast.LENGTH_LONG).show();
          }
          return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
          return false;
      }
  });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_home_drawer, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.nav_item_one)
        {
            return true;
        }
        else if(id==R.id.nav_item_two)
        {
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_item_one:
                Toast.makeText(this, "Nav one", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_item_two :
                Intent intent1 = new Intent(StartPageActivity1.this, Login.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                finish();
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Logged out.....", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}