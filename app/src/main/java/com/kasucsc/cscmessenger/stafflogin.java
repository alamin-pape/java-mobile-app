package com.kasucsc.cscmessenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;


public class stafflogin extends AppCompatActivity {

    private static final String TAG = "login";
    private Button buttonlogin;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private AuthStateListener listener;
    private ProgressDialog pd;
    private TextView textViewregister;
    private EditText txtpassword;
    private EditText txtusername;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stafflogin);
        this.pd = new ProgressDialog(this);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.listener = new AuthStateListener() {
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    stafflogin.this.finish();
                    stafflogin.this.startActivity(new Intent(stafflogin.this.getApplicationContext(),
                            MainActivity.class));
                    return;
                }
                Log.d("login", "singned out successfully");
                Toast.makeText(stafflogin.this.getApplicationContext(), "logout "
                        + user.getEmail(), Toast.LENGTH_SHORT).show();
            }
        };
        this.txtusername = findViewById(R.id.staffusername);
        this.txtpassword = findViewById(R.id.staffpassword);
        this.buttonlogin = findViewById(R.id.stafflogin);
        this.textViewregister = findViewById(R.id.staffregisterlink);
        this.buttonlogin.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                stafflogin.this.userLogin();
            }
        });
        this.textViewregister.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                stafflogin.this.registerUser();
            }
        });
    }


    private void userLogin() {
        final String username = this.txtusername.getText().toString();
        final String password = this.txtpassword.getText().toString();
        if (TextUtils.isEmpty(username) || !Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            this.pd.dismiss();
            this.txtusername.setError("invalid email address");
            this.txtusername.setText("");
            return;
        }
        this.txtusername.setError(null);
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            this.pd.dismiss();
            this.txtpassword.setError("password is not correct");
            this.txtpassword.setText("");
            return;
        }
        this.txtpassword.setError(null);
        this.pd.setMessage("logging please wait...");
        this.pd.show();
        this.firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> task) {
                stafflogin.this.pd.dismiss();
                stafflogin.this.databaseReference =
                        FirebaseDatabase.getInstance().getReference().child("login");
                Map<String, Object> map = new HashMap();
                map.put("username", username);
                map.put(EmailAuthProvider.PROVIDER_ID, password);
                stafflogin.this.databaseReference.updateChildren(map);
                if (task.isSuccessful()) {
                    Toast.makeText(stafflogin.this,
                            "Login Successfully", Toast.LENGTH_SHORT).show();
                    stafflogin.this.finish();
                    stafflogin.this.startActivity(new Intent(stafflogin.this.getApplicationContext(),
                            staffprofile.class));
                    return;
                }
                Toast.makeText(stafflogin.this, "email or password is wrong",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUser() {
        startActivity(new Intent(this, register.class));
    }
}