package com.aksantara.mother.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aksantara.mother.Adapter.HomeAdapter;
import com.aksantara.mother.CirclePagerIndicatorDecoration;
import com.aksantara.mother.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity implements HomeAdapter.OnProductSelectedListener {

    private static final String TAG = "MainActivity";

    private FirebaseUser user;
    private FirebaseFirestore mFirestore;
    private DocumentReference mUserRef, mLinkRef;
    private FirebaseAuth mAuth;
    private LinearLayout btn_pemeriksaan_mandiri, btn_pemeriksaan_risiko, btnRs, btnPuskesmas;

    private TextView tvLihatSemua, tvNama, tvTitle;
    private ImageView imgStory;
    private String userId, image, url, title;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        user = mAuth.getCurrentUser();
        mUserRef = mFirestore.collection("users").document(userId);
        mLinkRef = mFirestore.collection("Iklan").document("Iklan-1");


        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        bottomNav();
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            initView();
            initDataProfile();
        } else {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
    }

    private void initView() {

        tvNama = findViewById(R.id.tvNama);
        btnRs = findViewById(R.id.btnRs);
        btnPuskesmas = findViewById(R.id.btnPuskesmas);
        imgStory = findViewById(R.id.imgStory);
        tvTitle = findViewById(R.id.tvTitle);

        btnRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://pelayananpublik.id/2019/08/01/daftar-rumah-sakit-tipe-a-b-c-dan-d-di-kota-semarang/";
                Uri uriUrl = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

        btnPuskesmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://dinkes.semarangkota.go.id/content/menu/11";
                Uri uriUrl = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

        tvLihatSemua = findViewById(R.id.tvLihatSemua);
        tvLihatSemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent story = new Intent(getApplicationContext(), StoryActivity.class);
                startActivity(story);
            }
        });

        btn_pemeriksaan_mandiri = findViewById(R.id.btn_pemeriksaan_mandiri);
        btn_pemeriksaan_mandiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent periksa = new Intent(getApplicationContext(), PemeriksaanIdentitasActivity.class);
                startActivity(periksa);
            }
        });

        btn_pemeriksaan_risiko = findViewById(R.id.btn_pemeriksaan_risiko);
        btn_pemeriksaan_risiko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent konsultasi = new Intent(getApplicationContext(), PemeriksaanRisikoActivity.class);
                startActivity(konsultasi);
            }
        });




    }

    private void initDataProfile() {

        mUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        tvNama.setText("Hai, " + document.getString("nama") + "!");

                    } else {
                        Log.d(TAG, "Document does not exist!");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
        mLinkRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        Glide.with(imgStory.getContext())
                                .load(document.getString("image"))
                                .into(imgStory);
                        tvTitle.setText(document.getString("title"));
                        url = document.getString("url");

                    } else {
                        Log.d(TAG, "Document does not exist!");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
        imgStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan sekali lagi, untuk keluar!", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void bottomNav() {
        //inisialisasi
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // set selected home
        bottomNavigationView.setSelectedItemId(R.id.beranda);
        // item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.beranda:
                        return true;
                    //change infoemasi
                    case R.id.informasi:
                        startActivity(new Intent(getApplicationContext(), InformasiActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                    //change riwayat
                    case R.id.riwayat:
                        startActivity(new Intent(getApplicationContext(), RiwayatPemeriksaanActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                        //change akun
                    case R.id.akun:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onProductSelected(DocumentSnapshot productModel) {

    }

}