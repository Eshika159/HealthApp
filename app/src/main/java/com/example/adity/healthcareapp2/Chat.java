package com.example.adity.healthcareapp2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;

public class Chat extends AppCompatActivity {
    private  static int SIGN_IN_REQUESTCODE=1;
    private FirebaseListAdapter<ChatMessage> adapter;
    RelativeLayout activity;
FloatingActionButton fab;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_signout){
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(activity,"you have been signed out",Snackbar.LENGTH_SHORT).show();
                    finish();
                }
            });

        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        activity =(RelativeLayout)findViewById(R.id.activity_chat);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input=(EditText)findViewById(R.id.input);
                FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getEmail()));

            }
        });

        Snackbar.make(activity,"Welcome"+ FirebaseAuth.getInstance().getCurrentUser().getEmail(),Snackbar.LENGTH_SHORT).show();


        //

        displayChatMessage();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if(requestCode==SIGN_IN_REQUESTCODE){

        if(resultCode==RESULT_OK){
            Snackbar.make(activity,"Successfully signed in",Snackbar.LENGTH_SHORT).show();
            displayChatMessage();

        }
        else {
            Snackbar.make(activity,"Not successfully completed",Snackbar.LENGTH_SHORT).show();
            finish();
        }
    }

    }

    private void displayChatMessage() {
        ListView listofMessage=(ListView)findViewById(R.id.list_messsage);
        adapter=new FirebaseListAdapter<ChatMessage>(this,ChatMessage.class,R.layout.list_item,FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {

                TextView messageText,messageUser,messageTime;
                messageText=(TextView) v.findViewById(R.id.message_text);
                messageUser=(TextView) v.findViewById(R.id.message_user);
                messageTime=(TextView) v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUSer());
                messageTime.setText(android.text.format.DateFormat.format("dd-mm-yy (hh:mm:ss)",model.getMessageTime()));

            }
        };
        listofMessage.setAdapter(adapter);

    }
}
