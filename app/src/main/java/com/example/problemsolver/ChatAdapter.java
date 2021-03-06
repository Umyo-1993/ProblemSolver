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
import com.zolad.zoominimageview.ZoomInImageView;

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
        holder.user.setText(chats.getUser());
        holder.chattime.setText(chats.getTime());

        Glide.with(context)
                .load(chats.getImageUrl())
                .into(holder.zommim);
        //Edit image1
      
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder1 extends RecyclerView.ViewHolder{
        TextView user;
        TextView chatmsg;
        TextView chattime;
        ImageView imgmsg;
        ZoomInImageView zommim;

        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);


            chatmsg=itemView.findViewById(R.id.chat_view);
            zommim=itemView.findViewById(R.id.imgViewmsg);
            user=itemView.findViewById(R.id.chat_view_name);
            chattime=itemView.findViewById(R.id.chat_time);

        }
    }
}