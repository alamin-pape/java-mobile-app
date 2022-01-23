package com.kasucsc.cscmessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class studentprofile extends AppCompatActivity {

    private DatabaseReference databaseReference;
    EditText department;
    EditText firstname;
    private String key;
    EditText lastname;
    EditText matricno;
    private ProgressDialog pd;
    private Button updateProfile;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentprofile);
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        this.pd = new ProgressDialog(this);
        this.firstname = findViewById(R.id.fname);
        this.lastname = findViewById(R.id.lname);
        this.matricno = findViewById(R.id.matric);
        this.updateProfile = findViewById(R.id.btnupdatestudentprofile);
        this.department = findViewById(R.id.department);
        this.updateProfile.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                studentprofile.this.updateProfile();
            }
        });
    }

    private void updateProfile() {
        String fName = this.firstname.getText().toString();
        String lname = this.lastname.getText().toString();
        String depart = this.department.getText().toString();
        String matricNo = this.matricno.getText().toString();
        if (TextUtils.isEmpty(fName) || fName.isEmpty()) {
            this.firstname.setError("Field cannot be empty");
            return;
        }
        this.firstname.setError(null);
        if (TextUtils.isEmpty(lname) || lname.isEmpty()) {
            this.lastname.setError("Field cannot be empty");
            return;
        }
        this.lastname.setError(null);
        if (TextUtils.isEmpty(depart) || depart.isEmpty()) {
            this.department.setError("Field cannot be empty");
            return;
        }
        this.department.setError(null);
        if (TextUtils.isEmpty(matricNo) || matricNo.isEmpty()
                || matricNo.indexOf("/") != 4) {
            this.matricno.setError("Field cannot be empty");
            return;
        }
        this.matricno.setError(null);
        this.databaseReference = FirebaseDatabase.getInstance().
                getReference().child("students");
        Map<String, Object> map = new HashMap();
        this.key = this.databaseReference.push().getKey();
        this.databaseReference.updateChildren(map);
        DatabaseReference dr = this.databaseReference.child(this.key);
        Map<String, Object> map2 = new HashMap();
        map2.put("FullName", fName);
        map2.put("course", lname);
        map2.put("department", depart);
        map2.put("matricno", matricNo);
        dr.updateChildren(map2);
        this.pd.setMessage("Updating information...");
        this.pd.show();
        Toast.makeText(getApplicationContext(), "information saved",
                Toast.LENGTH_SHORT).show();
        finish();
        Intent intent = new Intent(getApplicationContext(), student.class);
        intent.putExtra("department", depart);
        intent.putExtra("staffname", fName);
        startActivity(intent);
    }
}