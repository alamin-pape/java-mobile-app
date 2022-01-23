package com.kasucsc.cscmessenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
//
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;


public class staffpage extends AppCompatActivity implements OnClickListener{

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staffpage);
        Button buttonlogout = findViewById(R.id.buttonlogout);
        Button buttonforum = findViewById(R.id.btnforum);
        Button buttonportal = findViewById(R.id.btnportal);
        Button buttonfile = findViewById(R.id.btnfile);
        try {
            findViewById(R.id.buttonabout).setOnClickListener(this);
            buttonportal.setOnClickListener(this);
            buttonfile.setOnClickListener(this);
            buttonforum.setOnClickListener(this);
            buttonlogout.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(staffpage.this.getApplicationContext(),
                            "You have been signed out.", Toast.LENGTH_LONG).show();
                    staffpage.this.finish();
                    staffpage.this.startActivity(new Intent(staffpage.this.getApplicationContext(),
                            MainActivity.class));
                }
            });
        }
        return true;
    }*/

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonlogout /*2131558575*/:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                return;
            case R.id.btnfile /*2131558577*/:
                startActivity(new Intent(getApplicationContext(), file_upload.class));
                return;
            case R.id.btnforum /*2131558578*/:
                startActivity(new Intent(this, forum.class));
                return;
            case R.id.btnportal /*2131558579*/:
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.kasu.edu.ng")));
                return;
            case R.id.buttonabout /*2131558580*/:
                startActivity(new Intent(this, about.class));
                return;
            default:
                return;
        }
    }
}