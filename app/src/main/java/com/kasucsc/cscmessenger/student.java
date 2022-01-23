package com.kasucsc.cscmessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//student page
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class student extends AppCompatActivity implements OnClickListener{

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Button buttonlogout = findViewById(R.id.btlogout);
        Button buttonforum = findViewById(R.id.btforum);
        Button buttonportal = findViewById(R.id.btportal);
        Button buttonfile = findViewById(R.id.btvfiles);
        try {
            findViewById(R.id.btabout).setOnClickListener(this);
            buttonportal.setOnClickListener(this);
            buttonfile.setOnClickListener(this);
            buttonforum.setOnClickListener(this);
            buttonlogout.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btvfiles /*2131558577*/:
                startActivity(new Intent(getApplicationContext(), viewuploads.class));
                return;
            case R.id.btforum /*2131558578*/:
                startActivity(new Intent(this, forum.class));
                return;
            case R.id.btportal /*2131558579*/:
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.kasu.edu.ng")));
                return;
            case R.id.btlogout /*2131558587*/:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                return;
            case R.id.btabout /*2131558588*/:
                startActivity(new Intent(this, about.class));
                return;
            default:
                return;
        }
    }
}