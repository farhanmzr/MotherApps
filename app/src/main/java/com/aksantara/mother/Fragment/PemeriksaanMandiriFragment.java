package com.aksantara.mother.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aksantara.mother.Activity.HasilPemeriksaanActivity;
import com.aksantara.mother.Adapter.PemeriksaanAdapter;
import com.aksantara.mother.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class PemeriksaanMandiriFragment extends Fragment implements PemeriksaanAdapter.OnProductSelectedListener {

    private static final String TAG = "Not Confirm Fragment";

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private Query mQuery;
    private PemeriksaanAdapter mAdapter;

    private RecyclerView rvRiwayat;
    private String userId, pemeriksaanId;

    private ImageView img_empty_mandiri;
    private TextView tv_empty_mandiri;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        mQuery = mFirestore.collection("users").document(userId).collection("PemeriksaanMandiri");
        mQuery = mQuery.whereEqualTo("userId", userId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pemeriksaan_mandiri, container, false);

        rvRiwayat = v.findViewById(R.id.rvRiwayat);
        img_empty_mandiri = v.findViewById(R.id.img_empty_mandiri);
        tv_empty_mandiri = v.findViewById(R.id.tv_empty_mandiri);
        initRv();

        return v;
    }


    private void initRv() {

        if (mQuery == null) {
            Log.w(TAG, "No query, not initializing RecyclerView");
        }

        mAdapter = new PemeriksaanAdapter(mQuery, this) {

            @Override
            protected void onDataChanged() {
                // Show/hide content if the query returns empty.
                if (getItemCount() == 0) {
                    rvRiwayat.setVisibility(View.GONE);
                    img_empty_mandiri.setVisibility(View.VISIBLE);
                    tv_empty_mandiri.setVisibility(View.VISIBLE);
                    Log.w(TAG, "ItemCount = 0");
                } else {
                    rvRiwayat.setVisibility(View.VISIBLE);
                    img_empty_mandiri.setVisibility(View.GONE);
                    tv_empty_mandiri.setVisibility(View.GONE);
                    Log.w(TAG, "Show Produk");
                }
            }

            @Override
            protected void onError(FirebaseFirestoreException e) {
                // Show a snackbar on errors
                Log.w(TAG, "Error" + e);
            }
        };

        rvRiwayat.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRiwayat.setAdapter(mAdapter);
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
    public void onProductSelected(DocumentSnapshot pemeriksaanModel) {

        final DocumentReference orderIdRef = pemeriksaanModel.getReference();
        orderIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Intent intent = new Intent(getActivity(), HasilPemeriksaanActivity.class);
                    intent.putExtra(HasilPemeriksaanActivity.KEY_PEMERIKSAAN_ID, document.getString("pemeriksaanId"));
                    intent.putExtra("no-Button", true);
                    startActivity(intent);
                }
            }
        });
    }


}