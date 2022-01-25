package com.example.problemsolver;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class personAdapter2 extends RecyclerView.Adapter<personAdapter2.MyViewHolder2>  {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

    Context context;

    ArrayList<Users2> list;


    public personAdapter2(Context context, ArrayList<Users2> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public personAdapter2.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_item2,parent,false);
        return  new personAdapter2.MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull personAdapter2.MyViewHolder2 holder, int position) {
        Users2 user = list.get(position);
        holder.emailqueues.setText(user.getEmailqueue());

        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user1.getUid();
        DatabaseReference drv= FirebaseDatabase.getInstance().getReference().child("EmailQueue").child(uid).child("emailqueue");


        //holder should be done...
        drv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value =snapshot.getValue(String.class);
                String emailverify=holder.emailqueues.getText().toString();
                if(holder.getAdapterPosition()==0) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, UsersActivity.class);
                            //  intent.putExtra("userid", user.getEmailqueue());
                            context.startActivity(intent);

                        }
                    });
                }
                else{
                    Toast.makeText(context, "Please wait for  some moment...One of our Engineers will meet you..", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


     /*   if(position==0 && emailverify==holder.emailqueues.getText()) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UsersActivity.class);
                    //  intent.putExtra("userid", user.getEmailqueue());
                    context.startActivity(intent);
                }
            });
        }
        else{
            Toast.makeText(context, "Please wait for  some moment...One of our Engineers will meet you..", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView emailqueues;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);


            emailqueues=itemView.findViewById(R.id.emailqueues);

        }
    }

}