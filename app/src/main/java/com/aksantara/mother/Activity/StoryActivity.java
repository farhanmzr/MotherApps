package com.aksantara.mother.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.aksantara.mother.Adapter.HomeAdapter;
import com.aksantara.mother.Adapter.StoryAdapter;
import com.aksantara.mother.CirclePagerIndicatorDecoration;
import com.aksantara.mother.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class StoryActivity extends AppCompatActivity implements StoryAdapter.OnProductSelectedListener {

    private static final String TAG = "StoryActivity";

    private ImageView icBack;
    private RecyclerView rvStory;

    private FirebaseFirestore mFirestore;
    private Query mQuery;
    private StoryAdapter mAdapter;

    private ShimmerFrameLayout mShimmerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        mFirestore = FirebaseFirestore.getInstance();
        mQuery = mFirestore.collection("Story");

        icBack = findViewById(R.id.icBack);
        rvStory = findViewById(R.id.rvStory);
        mShimmerLayout = findViewById(R.id.shimmer_view);

        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        showShimmer();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideShimmer();
            }
        }, 2000);
        initRecyclerView();

    }
    private void hideShimmer() {
        mShimmerLayout.setVisibility(View.GONE);
        mShimmerLayout.stopShimmerAnimation();
    }

    private void showShimmer() {
        mShimmerLayout.setVisibility(View.VISIBLE);
        mShimmerLayout.startShimmerAnimation();
    }

    private void initRecyclerView() {

        if (mQuery == null) {
            Log.w(TAG, "No query, not initializing RecyclerView");
        }

        mAdapter = new StoryAdapter(mQuery, this) {

            @Override
            protected void onDataChanged() {
                // Show/hide content if the query returns empty.
                if (getItemCount() == 0) {
                    rvStory.setVisibility(View.GONE);

                    Log.w(TAG, "ItemCount = 0");
                } else {
                    rvStory.setVisibility(View.VISIBLE);
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

        rvStory.setLayoutManager(new LinearLayoutManager(this));
        rvStory.setAdapter(mAdapter);

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
    public void onProductSelected(DocumentSnapshot storyModel) {
        String url = storyModel.getString("url");
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
        }
    }
