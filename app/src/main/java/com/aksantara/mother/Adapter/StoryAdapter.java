package com.aksantara.mother.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aksantara.mother.Model.InformasiModel;
import com.aksantara.mother.Model.StoryModel;
import com.aksantara.mother.R;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public class StoryAdapter extends FirestoreAdapter<StoryAdapter.ViewHolder> {

    private OnProductSelectedListener mListener;

    public StoryAdapter(Query query, OnProductSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_rv_story, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    public interface OnProductSelectedListener {

        void onProductSelected(DocumentSnapshot storyModel);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgStory;
        private TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            imgStory = itemView.findViewById(R.id.imgStory);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnProductSelectedListener listener) {

            StoryModel storyModel = snapshot.toObject(StoryModel.class);

            // Load image
            Glide.with(imgStory.getContext())
                    .load(storyModel.getImage())
                    .into(imgStory);
            tvTitle.setText(storyModel.getTitle());

            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onProductSelected(snapshot);
                    }
                }
            });
        }

    }
}
