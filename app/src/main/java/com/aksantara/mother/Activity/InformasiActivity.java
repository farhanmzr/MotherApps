package com.aksantara.mother.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aksantara.mother.Adapter.HomeAdapter;
import com.aksantara.mother.CirclePagerIndicatorDecoration;
import com.aksantara.mother.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class InformasiActivity extends AppCompatActivity implements HomeAdapter.OnProductSelectedListener {

    private static final String TAG = "InformasiActivity";

    private LinearLayout btn_kelola_kehamilan, btn_masalah_kehamilan, btn_larangan_hamil, btn_tanda_bahaya_kehamilan;
    private ImageView img_all;

    //Image
    private FirebaseFirestore mFirestore;
    private Query mQuery;
    private RecyclerView rvHome;
    private HomeAdapter mAdapter;



    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi);


        mFirestore = FirebaseFirestore.getInstance();
        mQuery = mFirestore.collection("Beranda");

        rvHome = findViewById(R.id.rvHome);

        initRecyclerView();
        categoryAll();
        categoryPengelolaan();
        categoryMasalah();
        categoryBahaya();
        categoryLarangan();
        bottomNav();
    }

    private void initRecyclerView() {

        if (mQuery == null) {
            Log.w(TAG, "No query, not initializing RecyclerView");
        }

        mAdapter = new HomeAdapter(mQuery, this) {

            @Override
            protected void onDataChanged() {
                // Show/hide content if the query returns empty.
                if (getItemCount() == 0) {
                    rvHome.setVisibility(View.GONE);

                    Log.w(TAG, "ItemCount = 0");
                } else {
                    rvHome.setVisibility(View.VISIBLE);
                    Log.w(TAG, "Show Produk");
                }
            }

            @Override
            protected void onError(FirebaseFirestoreException e) {
                // Show a snackbar on errors
                Snackbar.make(findViewById(android.R.id.content),
                        "Error: check logs for info.", Snackbar.LENGTH_LONG).show();
            }
        };

        rvHome.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvHome.setAdapter(mAdapter);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvHome);

        rvHome.addItemDecoration(new CirclePagerIndicatorDecoration());
    }

    @Override
    public void onStart() {
        super.onStart();
        // Start listening for Firestore updates
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    private void categoryMasalah() {

        btn_masalah_kehamilan = findViewById(R.id.btn_masalah_kehamilan);
        btn_masalah_kehamilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent info = new Intent(InformasiActivity.this, CatalogActivity.class);
                info.putExtra(CatalogActivity.KEY_PRODUCT_CATEGORY, "Masalah");
                startActivity(info);

            }
        });

    }

    private void categoryPengelolaan() {

        btn_kelola_kehamilan = findViewById(R.id.btn_kelola_kehamilan);
        btn_kelola_kehamilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent info = new Intent(InformasiActivity.this, CatalogActivity.class);
                info.putExtra(CatalogActivity.KEY_PRODUCT_CATEGORY, "Pengelolaan");
                startActivity(info);

            }
        });

    }

    private void categoryAll() {
        img_all = findViewById(R.id.img_all);
        img_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent catalog = new Intent(InformasiActivity.this, CatalogActivity.class);
                catalog.putExtra(CatalogActivity.KEY_PRODUCT_CATEGORY, "All");
                startActivity(catalog);

            }
        });
    }

    private void categoryLarangan() {

        btn_larangan_hamil = findViewById(R.id.btn_larangan_hamil);
        btn_larangan_hamil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent info = new Intent(InformasiActivity.this, CatalogActivity.class);
                info.putExtra(CatalogActivity.KEY_PRODUCT_CATEGORY, "Larangan");
                startActivity(info);

            }
        });
    }

    private void categoryBahaya() {
        btn_tanda_bahaya_kehamilan = findViewById(R.id.btn_tanda_bahaya_kehamilan);
        btn_tanda_bahaya_kehamilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent info = new Intent(InformasiActivity.this, CatalogActivity.class);
                info.putExtra(CatalogActivity.KEY_PRODUCT_CATEGORY, "Bahaya");
                startActivity(info);

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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // set selected home
        bottomNavigationView.setSelectedItemId(R.id.informasi);
        // item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //change beranda
                    case R.id.beranda:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.informasi:
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