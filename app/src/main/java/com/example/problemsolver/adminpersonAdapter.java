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
import java.util.ArrayList;

public class adminpersonAdapter extends RecyclerView.Adapter<personAdapter2.MyViewHolder2>  {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

    Context context;

    ArrayList<Users2> list;


    public adminpersonAdapter(Context context, ArrayList<Users2> list) {
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Admin_premsg_Activity.class);
                intent.putExtra("adminuserid", user.getUid());
                context.startActivity(intent);
            }
        });

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
