package com.kasucsc.cscmessenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private Button stafflog;
    private String status;
    private Button studentlog;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.stafflog = findViewById(R.id.stafflogin);
        this.studentlog = findViewById(R.id.studentlogin);
        this.stafflog.setOnClickListener(this);
        this.studentlog.setOnClickListener(this);
    }

    /*public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "You have been signed out.", Toast.LENGTH_LONG).show();
                    MainActivity.this.finish();
                    MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), MainActivity.class));
                }
            });
        }
        return true;
    }*/

    public void onClick(View v) {
        if (v.getId() == R.id.stafflogin) {
            loginAsStaff();
        }
        if (v.getId() == R.id.studentlogin) {
            loginAsStudent();
        }
    }

    private void loginAsStaff() {
        finish();
        startActivity(new Intent(this, stafflogin.class));
    }

    private void loginAsStudent() {
        startActivity(new Intent(this, login.class));
    }
}