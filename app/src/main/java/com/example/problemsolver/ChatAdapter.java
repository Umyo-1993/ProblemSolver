package com.example.problemsolver;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder1> {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

    Context context;
    ArrayList<Chats> list;

    public ChatAdapter(Context context, ArrayList<Chats> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.chat_item,parent,false);
        return  new ChatAdapter.MyViewHolder1(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, int position) {
        Chats chats = list.get(position);

        holder.chatmsg.setText(chats.getMsg());

        Glide.with(context)
                .load(chats.getImageUrl())
                .into(holder.imgmsg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder1 extends RecyclerView.ViewHolder{

        TextView chatmsg;
        ImageView imgmsg;

        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);


            chatmsg=itemView.findViewById(R.id.chat_view);
            imgmsg=itemView.findViewById(R.id.imgViewmsg);

        }
    }
}