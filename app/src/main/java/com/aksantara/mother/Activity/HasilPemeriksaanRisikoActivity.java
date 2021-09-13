package com.aksantara.mother.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aksantara.mother.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HasilPemeriksaanRisikoActivity extends AppCompatActivity {


    public static final String KEY_PEMERIKSAAN_ID = "key_pemeriksaan_id";

    private static final String TAG = "ShippingAddressActivity";
    private FirebaseFirestore mFirestore;
    private DocumentReference mPemeriksaanRef, mUserRef;
    private FirebaseAuth mAuth;
    private Query mQuery;
    private String userId, pemeriksaanId;

    private Button btnSelesai;
    private TextView tvNama, tvTotalSkor, tvKategoriRisiko, tvPerawat, tvRujukan, tvTempat, tvPenolong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_pemeriksaan_risiko);


        pemeriksaanId = getIntent().getExtras().getString(KEY_PEMERIKSAAN_ID);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        mPemeriksaanRef = mFirestore.collection("users").document(userId).collection("PemeriksaanRisiko").document(userId + pemeriksaanId);
        mUserRef = mFirestore.collection("users").document(userId);

        initView();
        initData();

    }

    private void initView() {

        tvNama = findViewById(R.id.tvNama);
        tvTotalSkor = findViewById(R.id.tvTotalSkor);
        tvKategoriRisiko = findViewById(R.id.tvKategoriRisiko);
        tvPerawat = findViewById(R.id.tvPerawat);
        tvRujukan = findViewById(R.id.tvRujukan);
        tvTempat = findViewById(R.id.tvTempat);
        tvPenolong = findViewById(R.id.tvPenolong);

        btnSelesai = findViewById(R.id.btnSelesai);
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HasilPemeriksaanRisikoActivity.this, RiwayatPemeriksaanActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });

    }

    private void initData() {


        if (getIntent().hasExtra("no-Button")) {
            btnSelesai.setVisibility(View.GONE);
        }
        mUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");

                        tvNama.setText("Halo, " + document.getString("nama"));

                        mPemeriksaanRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Log.d(TAG, "Document exists!");

                                        String totalSkor = document.getString("totalSkor");
                                        String kelompokRisiko = document.getString("kelompokRisiko");
                                        String perawat = document.getString("perawatan");
                                        String rujukan = document.getString("rujukan");
                                        String tempat = document.getString("tempat");
                                        String penolong = document.getString("penolong");

                                        Log.e(perawat, "perawat");
                                        Log.e(rujukan, "rujukan");
                                        Log.e(tempat, "tempat");

                                        tvTotalSkor.setText(totalSkor);
                                        tvKategoriRisiko.setText(kelompokRisiko);
                                        tvPerawat.setText(perawat);
                                        tvRujukan.setText(rujukan);
                                        tvTempat.setText(tempat);
                                        tvPenolong.setText(penolong);


                                    } else {
                                        Log.d(TAG, "Document does not exist!");

                                    }
                                } else {
                                    Log.d(TAG, "Failed with: ", task.getException());
                                }
                            }
                        });

                    } else {
                        Log.d(TAG, "Document does not exist!");

                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent home = new Intent(getApplicationContext(), RiwayatPemeriksaanActivity.class);
                startActivity(home);
                finish();
            }
        }, 0);
    }

}