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


public class staffprofile extends AppCompatActivity implements OnClickListener{

    private EditText cous;
    private DatabaseReference databaseReference;
    private EditText dept;
    private EditText fullname;
    private String key;
    private ProgressDialog pd;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staffprofile);
        this.pd = new ProgressDialog(this);
        this.fullname = findViewById(R.id.fullname);
        this.cous = findViewById(R.id.course);
        this.dept = findViewById(R.id.dept);
        Button updatestaffprof = findViewById(R.id.btnupdatestaffprofile);
        if (updatestaffprof != null) {
            updatestaffprof.setOnClickListener(this);
        }
    }

    private void updateProfile() {
        String fullName = this.fullname.getText().toString();
        String course = this.cous.getText().toString();
        String depart = this.dept.getText().toString();
        if (TextUtils.isEmpty(fullName) || fullName.isEmpty()) {
            this.fullname.setError("Field cannot be empty");
            return;
        }
        this.fullname.setError(null);
        if (TextUtils.isEmpty(course) || course.isEmpty()) {
            this.cous.setError("Field cannot be empty");
            return;
        }
        this.cous.setError(null);
        if (TextUtils.isEmpty(depart) || depart.isEmpty()) {
            this.dept.setError("Field cannot be empty");
            return;
        }
        this.dept.setError(null);
        this.databaseReference = FirebaseDatabase.getInstance().getReference().child("staff");
        Map<String, Object> map = new HashMap();
        this.key = this.databaseReference.push().getKey();
        this.databaseReference.updateChildren(map);
        DatabaseReference dr = this.databaseReference.child(this.key);
        Map<String, Object> map2 = new HashMap();
        map2.put("FullName", fullName);
        map2.put("course", course);
        map2.put("department", depart);
        dr.updateChildren(map2);
        this.pd.setMessage("Updating information...");
        this.pd.show();
        Toast.makeText(getApplicationContext(),
                "information saved", Toast.LENGTH_SHORT).show();
        finish();
        Intent intent = new Intent(getApplicationContext(), staffpage.class);
        intent.putExtra("department", depart);
        intent.putExtra("staff name", fullName);
        startActivity(intent);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnupdatestaffprofile /*2131558586*/:
                updateProfile();
                return;
            default:
                return;
        }
    }
}