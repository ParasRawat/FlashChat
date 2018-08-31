package com.paras_rawat.flashchatnewfirebase;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChatListAdapter extends BaseAdapter {

    private Activity mactivity;
    private DatabaseReference mdatabaseReference;
    private String displyaname;
    private ArrayList<DataSnapshot> snapshots;
    private ChildEventListener childEventListener=new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            snapshots.add(dataSnapshot);
            notifyDataSetChanged();

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    public ChatListAdapter(Activity activity,DatabaseReference databaseReference,String name){
        mactivity=activity;
        displyaname=name;
        mdatabaseReference=databaseReference.child("message");
        mdatabaseReference.addChildEventListener(childEventListener);
        snapshots=new ArrayList<>();

    }

    static class viewHolder{
        TextView authorname;
        TextView body;
        LinearLayout.LayoutParams params;
    }
    @Override
    public int getCount() {
        return snapshots.size();
    }

    @Override
    public InstantMessage getItem(int position) {
        DataSnapshot snapshot=snapshots.get(position);
        return snapshot.getValue(InstantMessage.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater =(LayoutInflater) mactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.chat_msg_row,parent,false);

            final viewHolder holder=new viewHolder();
            holder.authorname=convertView.findViewById(R.id.author);
            holder.body=convertView.findViewById(R.id.message);
            holder.params= (LinearLayout.LayoutParams) holder.authorname.getLayoutParams();
            convertView.setTag(holder);


        }
        final InstantMessage message=getItem(position);
        final viewHolder holder = (viewHolder) convertView.getTag();
        boolean isme=message.getAuthor().equals(displyaname);
        setChatRowApperance(isme,holder);
        String author=message.getAuthor();
        holder.authorname.setText(author);
        String msg=message.getMessage();
        holder.body.setText(msg);

        return convertView;
    }
    private void setChatRowApperance(boolean isme,viewHolder viewHolder){
        if(isme){

            viewHolder.params.gravity= Gravity.END;
            viewHolder.authorname.setTextColor(Color.GREEN);
            viewHolder.body.setBackgroundResource(R.drawable.bubble1);



        }
        else {

            viewHolder.params.gravity= Gravity.START;
            viewHolder.authorname.setTextColor(Color.BLUE);
        }
        viewHolder.authorname.setLayoutParams(viewHolder.params);
        viewHolder.body.setLayoutParams(viewHolder.params);
        viewHolder.body.setBackgroundResource(R.drawable.bubble2);

    }


    //Your job is to provide the data to the listview

    public void cleanUp(){
        mdatabaseReference.removeEventListener(childEventListener);
    }
}
