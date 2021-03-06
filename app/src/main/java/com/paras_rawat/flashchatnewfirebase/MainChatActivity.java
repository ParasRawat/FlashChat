package com.paras_rawat.flashchatnewfirebase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.print.PrinterId;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainChatActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    private String mDisplayName;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageButton mSendButton;
    private DatabaseReference databaseReference;
    private ChatListAdapter chatListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        // TODO: Set up the display name and
        // get the Firebase reference
        getDisplayName();
        databaseReference= FirebaseDatabase.getInstance().getReference();


        // Link the Views in the layout to the Java code
        mInputText = (EditText) findViewById(R.id.messageInput);
        mSendButton = (ImageButton) findViewById(R.id.sendButton);
        mChatListView = (ListView) findViewById(R.id.chat_list_view);

        // TODO: Send the message when the "enter" button is pressed
        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                sendMessage();
                return true;
                }
        });


        // TODO: Add an OnClickListener to the sendButton to send a message
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

    }

    // TODO: Retrieve the display name from the Shared Preferences
    private void getDisplayName(){
        SharedPreferences preferences=getSharedPreferences(RegisterActivity.CHAT_PREFS,MODE_PRIVATE);
        mDisplayName=preferences.getString(RegisterActivity.DISPLAY_NAME_KEY,null);
        if(mDisplayName==null){
            mDisplayName="Anonymous";
        }
    }


    private void sendMessage() {

//        Toast.makeText(this,"Message Has been entered",Toast.LENGTH_SHORT).show();

        // TODO: Grab the text the user typed in and push the message to Firebase

        String input= mInputText.getText().toString();
        if(!input.equals("")){
            Toast.makeText(this,"Entererd here",Toast.LENGTH_SHORT).show();
            InstantMessage instantMessage=new InstantMessage(input,mDisplayName);
            Toast.makeText(this,instantMessage.getAuthor(),Toast.LENGTH_LONG).show();
            databaseReference.child("message").push().setValue(instantMessage);
            mInputText.setText("");



        }
    }

    //Listview require a middle man, it do not directly talk to the data, it requires a so called adapter


    // TODO: Override the onStart() lifecycle method. Setup the adapter here.
    @Override
    public void onStart(){
        super.onStart();
        chatListAdapter=new ChatListAdapter(this,databaseReference,mDisplayName);
        mChatListView.setAdapter(chatListAdapter);
    }


    @Override
    public void onStop() {
        super.onStop();

        // TODO: Remove the Firebase event listener on the adapter.
        chatListAdapter.cleanUp();

    }

}
