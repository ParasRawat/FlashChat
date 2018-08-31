package com.paras_rawat.flashchatnewfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    private FirebaseAuth firebaseAuth;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);
        firebaseAuth=FirebaseAuth.getInstance();

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        // TODO: Grab an instance of FirebaseAuth

    }

    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {
        attemptLogin();
        // TODO: Call attemptLogin() here

    }

    // Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(this, com.paras_rawat.flashchatnewfirebase.RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {

        String email=mEmailView.getText().toString();
        String pass=mPasswordView.getText().toString();

        if(email.equals("") || pass.equals("") ){

            Snackbar.make(mEmailView,"Sorry, blank fields not allowed",Snackbar.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful()){
                    Snackbar.make(mEmailView,"Invalid Credentials with exception" + task.getException().toString(),Snackbar.LENGTH_SHORT).show();


                }
                else {
//                    Snackbar.make(mEmailView,"Login Success \n Welcome to the chatroom ",Snackbar.LENGTH_LONG).show();
                    alert();

                    startActivity(new Intent(LoginActivity.this,MainChatActivity.class));

                }

            }
        });



        // TODO: Use FirebaseAuth to sign in with email & password



    }

    // TODO: Show error on screen with an alert dialog
public void alert(){
    new AlertDialog.Builder(LoginActivity.this)
            .setTitle("Welcome")
            .setMessage("Welcome to the chat room")
            .show();
}


}