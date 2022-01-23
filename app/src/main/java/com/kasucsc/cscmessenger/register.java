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
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity implements OnClickListener{

    private static final String TAG = "Register";
    private Button buttonreg;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    AuthStateListener listener;
    private TextView loginlink;
    ProgressDialog pd;
    private EditText rcpassword;
    private EditText remail;
    private EditText rname;
    private EditText rpassword;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.pd = new ProgressDialog(this);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.rname = findViewById(R.id.input_regfullname);
        this.remail = findViewById(R.id.input_regusername);
        this.rpassword = findViewById(R.id.input_regpassword);
        this.rcpassword = findViewById(R.id.input_cpass);
        this.buttonreg = findViewById(R.id.btnregister);
        this.loginlink = findViewById(R.id.regloginlink);
        this.buttonreg.setOnClickListener(this);
        this.loginlink.setOnClickListener(this);
    }

    private void registerUser() {
        final String fname = this.rname.getText().toString();
        final String email = this.remail.getText().toString();
        final String password = this.rpassword.getText().toString();
        String confirmPassword = this.rcpassword.getText().toString();
        if (TextUtils.isEmpty(fname)) {
            this.rname.setError("Field cannot be empty");
            return;
        }
        this.rname.setError(null);
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.remail.setError("invalid email address");
            return;
        }
        this.remail.setError(null);
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            this.rpassword.setError("password must be atleast 6 characters");
            return;
        }
        this.rpassword.setError(null);
        if (confirmPassword.compareTo(password) != 0) {
            this.rcpassword.setError("passwords do not match");
            return;
        }
        this.rcpassword.setError(null);
        this.pd.setMessage("Creating a new user...");
        this.pd.show();
        this.firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    register.this.databaseReference = FirebaseDatabase.getInstance().getReference().child(Event.LOGIN);
                    Map<String, Object> map = new HashMap();
                    map.put("username", email);
                    map.put("FullName", fname);
                    map.put(EmailAuthProvider.PROVIDER_ID, password);
                    register.this.databaseReference.updateChildren(map);
                    register.this.pd.dismiss();
                    Toast.makeText(register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    register.this.finish();
                    Toast.makeText(register.this.getApplicationContext(), "login to your account" +
                            register.this.firebaseAuth.getCurrentUser(), Toast.LENGTH_SHORT).show();
                    register.this.startActivity(new Intent(register.this.getApplicationContext(), MainActivity.class));
                    return;
                }
                Toast.makeText(register.this, "Failed to create account", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnregister /*2131558566*/:
                registerUser();
                return;
            case R.id.regloginlink /*2131558567*/:
                loginUser();
                return;
            default:
                return;
        }
    }
}