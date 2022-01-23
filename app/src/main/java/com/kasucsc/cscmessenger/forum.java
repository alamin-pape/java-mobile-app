package com.kasucsc.cscmessenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class forum extends AppCompatActivity {

    private static final int SIGN_IN_REQUEST_CODE = 102;
    private FirebaseListAdapter<ChatMessage> adapter;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    private FloatingActionButton fab;
    private String key;
    private EditText msg;
    private String roomName;
    private TextView text;
    private String username;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        this.auth = FirebaseAuth.getInstance();
        this.auth.getCurrentUser();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(AuthUI.getInstance().
                    createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
        } else {
            Toast.makeText(this, "Welcome " +
                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                    Toast.LENGTH_LONG).show();
            displayChatMessages();
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    EditText input = forum.this.findViewById(R.id.input);
                    FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
                    input.setText("");
                }
            });
        }
    }

    /* Access modifiers changed, original: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this,
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG)
                        .show();
                displayChatMessages();
        }else{
                Toast.makeText(this, "We couldn't sign you in. Please try again later.", Toast.LENGTH_SHORT).show();
                finish();
            }

        }

    }

    private void displayChatMessages() {
        ListView listOfMessages = findViewById(R.id.list_of_messages);
        this.adapter = new FirebaseListAdapter<ChatMessage>(this,
                ChatMessage.class, R.layout.message, FirebaseDatabase.getInstance().getReference()) {
            /* Access modifiers changed, original: protected */
            public void populateView(View v, ChatMessage model, int position) {
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);
                TextView mgtext = v.findViewById(R.id.message_text);

                //((TextView) .setText(model.getMessageText());
                ArrayList<String> censoredwords = new ArrayList<>(Arrays.asList(" wawa", "stupid", "idiot"));
                if (censoredwords.contains(mgtext)) {
                    mgtext.setText(" ");
                    messageUser.setText(" ");
                } else {

                    //

                    mgtext.setText(model.getMessageText());
                    messageUser.setText(model.getMessageUser());
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
                }
            }
        };
        listOfMessages.setAdapter(this.adapter);
    }

    /*public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(forum.this.getApplicationContext(), "You have been signed out.", Toast.LENGTH_LONG).show();
                    forum.this.finish();
                    forum.this.startActivity(new Intent(forum.this, MainActivity.class));
                }
            });
        }
        return true;
    }*/
}