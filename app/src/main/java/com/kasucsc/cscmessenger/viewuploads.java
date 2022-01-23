package com.kasucsc.cscmessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
//
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class viewuploads extends AppCompatActivity {
    ListView listView;
    DatabaseReference mDatabaseReference;
    List<Upload> uploadList;

    /* Access modifiers changed, original: protected */
    @SuppressLint("WrongViewCast")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewuploads);
        this.uploadList = new ArrayList();
        this.listView = findViewById(R.id.listview);
        this.listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Upload upload = viewuploads.this.uploadList.get(i);
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(upload.getUrl()));
                viewuploads.this.startActivity(intent);
            }
        });
        this.mDatabaseReference = FirebaseDatabase.getInstance().
                getReference(constants.DATABASE_PATH_UPLOADS);
        this.mDatabaseReference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    viewuploads.this.uploadList.add(postSnapshot.getValue(Upload.class));
                }
                String[] uploads = new String[viewuploads.this.uploadList.size()];
                for (int i = 0; i < uploads.length; i++) {
                    uploads[i] = viewuploads.this.uploadList.get(i).getName();
                }
                viewuploads.this.listView.setAdapter(new ArrayAdapter(viewuploads.this.
                        getApplicationContext(), android.R.layout.simple_list_item_1, uploads));
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}