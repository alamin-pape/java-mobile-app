package com.kasucsc.cscmessenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
//
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask.TaskSnapshot;

public class file_upload extends AppCompatActivity {
    static final int PICK_PDF_CODE = 2342;
    String Database_Path = constants.DATABASE_PATH_UPLOADS;
    String Storage_Path = constants.STORAGE_PATH_UPLOADS;
    DatabaseReference databaseReference;
    EditText editTextFilename;
    Button finish;
    DatabaseReference mDatabaseReference;
    StorageReference mStorageReference;
    Uri path;
    ProgressBar progressBar;
    StorageReference storageReference;
    TextView textViewStatus;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);
        this.storageReference = FirebaseStorage.getInstance().getReference();
        this.databaseReference = FirebaseDatabase.getInstance().getReference(this.Database_Path);
        this.mStorageReference = FirebaseStorage.getInstance().getReference();
        this.mDatabaseReference = FirebaseDatabase.getInstance().getReference(constants.DATABASE_PATH_UPLOADS);
        this.textViewStatus = findViewById(R.id.viewstatus);
        this.editTextFilename = findViewById(R.id.fileName);
        this.progressBar = findViewById(R.id.progressbar);
        findViewById(R.id.btnselect).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(file_upload.this.getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0) {
                    Intent intent = new Intent();
                    intent.setType("application/pdf");
                    intent.setAction("android.intent.action.GET_CONTENT");
                    file_upload.this.startActivityForResult(Intent.createChooser(intent, "Select a file "), file_upload.PICK_PDF_CODE);
                    return;
                }
                file_upload.this.startActivity(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + file_upload.this.getPackageName())));
            }
        });
        findViewById(R.id.btnupload).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                file_upload.this.uploadFile();
            }
        });
        findViewById(R.id.viewuploads).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               file_upload.this.startActivity(new Intent(file_upload.this.getApplicationContext(), viewuploads.class));
            }
        });
    }

    /* Access modifiers changed, original: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != PICK_PDF_CODE || resultCode != requestCode || data == null || data.getData() == null) {
            Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
        } else {
            this.path = data.getData();
        }
    }

    public String fileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));
    }

    private void uploadFile() {
        if (this.path != null) {
            this.progressBar.setVisibility(View.VISIBLE);
            this.storageReference.child(this.Storage_Path + System.currentTimeMillis() + "." + fileExtension(this.path)).putFile(this.path).addOnSuccessListener((OnSuccessListener) new OnSuccessListener<TaskSnapshot>() {
                public void onSuccess(TaskSnapshot taskSnapshot) {
                    file_upload.this.progressBar.setVisibility(View.GONE);
                    file_upload.this.textViewStatus.setText("File Uploaded Successfully");
                   // file_upload.this.databaseReference.child(file_upload.this.databaseReference.push().getKey()).setValue(new Upload(file_upload.this.editTextFilename.getText().toString(), taskSnapshot.getDownloadUrl().toString()));
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(file_upload.this.getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<TaskSnapshot>() {
                public void onProgress(TaskSnapshot taskSnapshot) {
                    file_upload.this.textViewStatus.setText(((int) ((100.0d * ((double) taskSnapshot.getBytesTransferred())) / ((double) taskSnapshot.getTotalByteCount()))) + "% Uploading...");
                }
            });
            return;
        }
        Toast.makeText(this, "Please select a file to upload", Toast.LENGTH_SHORT).show();
    }
}