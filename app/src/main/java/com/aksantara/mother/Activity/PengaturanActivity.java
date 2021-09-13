package com.aksantara.mother.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aksantara.mother.R;
import com.facebook.login.LoginManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class PengaturanActivity extends AppCompatActivity {

    private ImageView icBack;
    private CardView btnEditProfile, btnAbout, btnSaran;
    private LinearLayout btnLogout;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        mAuth = FirebaseAuth.getInstance();

        initView();
    }

    private void initView() {

        icBack = findViewById(R.id.icBack);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnAbout = findViewById(R.id.btnAbout);
        btnSaran = findViewById(R.id.btnSaran);
        btnLogout = findViewById(R.id.btnLogout);

        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PengaturanActivity.this, ChangeProfileActivity.class);
                startActivity(i);
            }
        });
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PengaturanActivity.this, TentangAplikasiActivity.class);
                startActivity(i);
            }
        });
        btnSaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + "aksantara.digital@gmail.com")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, "");
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MaterialAlertDialogBuilder(PengaturanActivity.this, R.style.Theme_MaterialComponents_Dialog)
                        .setTitle("SEHAT App Message")
                        .setMessage("Apakah anda ingin keluar dari aplikasi?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                LoginManager.getInstance().logOut();
                                mAuth.signOut();
                                Intent logout = new Intent(PengaturanActivity.this, LoginActivity.class);
                                logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(logout);
                                finish();
                                Toast.makeText(getApplicationContext(), "Anda telah keluar dari aplikasi. Silahkan masuk kembali", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();

            }
        });

    }
}