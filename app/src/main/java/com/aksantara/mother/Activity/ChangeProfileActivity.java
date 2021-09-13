package com.aksantara.mother.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.aksantara.mother.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ChangeProfileActivity extends AppCompatActivity {

    private static final String TAG = "ChangeProfileActivity";
    private final int PICK_IMAGE_REQUEST = 22;

    FirebaseStorage storage;
    StorageReference storageReference;

    private FirebaseUser user;
    private FirebaseFirestore mFirestore;
    private DocumentReference mUserRef;
    private FirebaseAuth mAuth;
    private Uri filePath;

    private boolean isImgChange;

    private LinearLayout btnSimpan;
    private TextInputEditText etEmail, etUsername, etNomorPonsel, etAlamat;
    private ImageView img_profile, change_img_user, icBack;
    private String userId, nama, email, phone, alamat, avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);



        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        user = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mUserRef = mFirestore.collection("users").document(userId);

        initView();

    }

    private void initView() {
        img_profile = findViewById(R.id.img_profile);
        change_img_user = findViewById(R.id.change_img_user);
        btnSimpan = findViewById(R.id.btnSimpan);
        etUsername = findViewById(R.id.etUsername);
        etNomorPonsel = findViewById(R.id.etNomorPonsel);
        etAlamat = findViewById(R.id.etAlamat);
        icBack = findViewById(R.id.icBack);
        etEmail = findViewById(R.id.etEmail);
        etEmail.setAlpha((float) 0.5);

        Glide.with(img_profile.getContext())
                .load(user.getPhotoUrl())
                .into(img_profile);

        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initDataProfile();

        change_img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAkun();
            }
        });

    }

    private void editAkun() {

        nama = etUsername.getText().toString();
        phone = etNomorPonsel.getText().toString();
        alamat = etAlamat.getText().toString();

        if (TextUtils.isEmpty(nama)) {
            Toast.makeText(getApplicationContext(), "Nama Tidak Boleh Kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getApplicationContext(), "Nomor Ponsel Tidak Boleh Kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(alamat)) {
            Toast.makeText(getApplicationContext(), "Alamat Tidak Boleh Kosong.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isImgChange && filePath != null) {
            UploadImg();
        } else {
            uploadDataProfile();
        }
    }

    private void initDataProfile() {

        mUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        etUsername.setText(document.getString("nama"));
                        etEmail.setText(document.getString("email"));
                        etNomorPonsel.setText(document.getString("phone"));
                        etAlamat.setText(document.getString("alamat"));

                    } else {
                        Log.d(TAG, "Document does not exist!");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });


    }

    private void selectImage() {

// Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                img_profile.setImageBitmap(bitmap);
                Log.e(String.valueOf(filePath), "uri");
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void UploadImg() {

        if (filePath != null) {

            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Upload Foto");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);

            // Defining the child of storageReference
            final StorageReference ref
                    = storageReference
                    .child(
                            "imagesUser/"
                                    + userId + "." + GetFileExtension(filePath));

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    // Image uploaded successfully
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Log.d(TAG, "User Profile" + uri);
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            avatar = String.valueOf(uri);
                                            // Dismiss dialog
                                            progressDialog.dismiss();
                                            uploadDataProfile();
                                            Log.e(avatar, "userPicture");
                                        }
                                    });
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(ChangeProfileActivity.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });
        }
    }

    private void uploadDataProfile() {

        final ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setMessage("Memperbarui Profile...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Map<String, Object> updateUser = new HashMap<>();
        updateUser.put("avatar", avatar);
        updateUser.put("nama", nama);
        updateUser.put("phone", phone);
        updateUser.put("alamat", alamat);

        mUserRef.set(updateUser, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.w(TAG, "Successfully");

                progressDialog.dismiss();
                Intent intent = new Intent(ChangeProfileActivity.this, ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                Toast.makeText(ChangeProfileActivity.this, "Profile berhasil diperbarui",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Log.w(TAG, "Error writing document", e);
            }
        });
    }
}