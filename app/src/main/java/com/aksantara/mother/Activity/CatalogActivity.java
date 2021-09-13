package com.aksantara.mother.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aksantara.mother.Adapter.InformasiAdapter;
import com.aksantara.mother.CirclePagerIndicatorDecoration;
import com.aksantara.mother.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class CatalogActivity extends AppCompatActivity implements InformasiAdapter.OnProductSelectedListener {

    public static final String KEY_PRODUCT_CATEGORY = "key_product_category";

    private static final String TAG = "CatalogActivity";

    private RecyclerView rvInformasi;

    private FirebaseFirestore mFirestore;
    private Query mQuery;

    private InformasiAdapter mAdapter;
    private String productCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);


        productCategory = getIntent().getExtras().getString(KEY_PRODUCT_CATEGORY);
        rvInformasi = findViewById(R.id.rvInformasi);

        initFirestore();
        initRv();
    }

    private void initFirestore() {
        mFirestore = FirebaseFirestore.getInstance();

        if (productCategory.equals("All")) {
            mQuery = mFirestore.collection("Informasi");
        }
        else {
            mQuery = mFirestore.collection("Informasi");
            mQuery = mQuery.whereEqualTo("category", productCategory);
        }
    }

    private void initRv() {
        if (mQuery == null) {
            Log.w(TAG, "No query, not initializing RecyclerView");
        }

        mAdapter = new InformasiAdapter(mQuery, this) {

            @Override
            protected void onDataChanged() {
                // Show/hide content if the query returns empty.
                if (getItemCount() == 0) {

                    Log.w(TAG, "ItemCount = 0");

                } else {
                    rvInformasi.setVisibility(View.VISIBLE);

                    Log.w(TAG, "Show Informasi");
                }
            }

            @Override
            protected void onError(FirebaseFirestoreException e) {
                // Show a snackbar on errors
                Snackbar.make(findViewById(android.R.id.content),
                        "Error: check logs for info.", Snackbar.LENGTH_LONG).show();
            }
        };

        rvInformasi.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvInformasi.setAdapter(mAdapter);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvInformasi);
//        rvInformasi.addItemDecoration(new CirclePagerIndicatorDecoration());


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

    @Override
    public void onProductSelected(DocumentSnapshot productModel) {

    }
}
