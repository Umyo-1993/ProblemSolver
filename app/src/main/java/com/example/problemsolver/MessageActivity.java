package com.example.problemsolver;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MessageActivity extends AppCompatActivity {
    ImageView imageView;
    ImageView imageupload;
    FirebaseUser fuser;
    DatabaseReference reference;
    DatabaseReference referencech;
    Intent intent;
    Button sendbtn;
    EditText text_send;
    TextView username;
    RecyclerView recyclerView;
    ChatAdapter chatAdapter;
    ArrayList<Chats> list;
    //start
    private Button btnSelect, btnUpload;

    // view for image view


    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        imageView=findViewById(R.id.imageViewuser);
        imageupload=findViewById(R.id.imageup);
        username=findViewById(R.id.usernamey);
        sendbtn=findViewById(R.id.btn_send);
        text_send=findViewById(R.id.text_send);
        recyclerView=findViewById(R.id.chatrecycler);

        //  Toolbar toolbar=findViewById(R.id.toolbarmsg);
        //    setSupportActionBar(toolbar);
        //   getSupportActionBar().setTitle("");
        //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      /*  toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



        intent=getIntent();
        String userid=intent.getStringExtra("userid");
        String msgid=userid;
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("MyUsers");
        referencech= FirebaseDatabase.getInstance().getReference("chat").child(fuser.getUid()).child(userid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        chatAdapter = new ChatAdapter(MessageActivity.this,list);
        recyclerView.setAdapter(chatAdapter);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        referencech.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Chats chats = dataSnapshot.getValue(Chats.class);
                    String a="Umo";
                    //   if((chats.getSender()==fuser.getUid().toString() && chats.getReceiver()==userid.toString())) {

                    list.add(chats);
                    // String a = fuser.toString();


                    //      }

                }
                chatAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Users user = dataSnapshot.getValue(Users.class);
                    String a=dataSnapshot.toString();
                    username.setText(user.getUsernamedemo());
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        //floating activity
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                DatabaseReference fbd=FirebaseDatabase.getInstance().getReference();
                fbd.child("EmailQueue").child(uid).removeValue();
                Toast.makeText(MessageActivity.this, "Chat Ended", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MessageActivity.this,Login.class);
                startActivity(intent);

            }
        });
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg=text_send.getText().toString();
                if(!msg.equals(""))
                {
                    // sendmessage(fuser.getUid(),userid,msg);
                    //Edit one 28/01

                    Date date = new Date();
                    DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
                    String localizedDate = df.format(date);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                    String currentDateandTime = sdf.format(new Date());


                    String user="User";
                    DatabaseReference dfr=FirebaseDatabase.getInstance().getReference();
                    HashMap<String,Object>hashMap=new HashMap<>();
                    hashMap.put("sender",fuser.getUid());
                    hashMap.put("receiver",userid);
                    hashMap.put("msg",msg);
                    hashMap.put("user",user);
                    hashMap.put("time",currentDateandTime);
                    dfr.child("chat").child(fuser.getUid()).child(userid).push().setValue(hashMap);

                }
                text_send.setText("");
            }
        });
        imageupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MessageActivity.this,ImagetestingActivity.class);
                intent.putExtra("userid",msgid);
                startActivity(intent);
            }
        });
    }


    private void sendmessage(String sender, String receiver, String msg) {

        DatabaseReference dfr=FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object>hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("msg",msg);
        dfr.child("chat").push().setValue(hashMap);

    }

    //  private void setSupportActionBar(Toolbar toolbar) {
    // }

}