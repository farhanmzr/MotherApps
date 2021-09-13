package com.aksantara.mother.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.aksantara.mother.Model.InformasiModel;
import com.aksantara.mother.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public class InformasiAdapter extends FirestoreAdapter<InformasiAdapter.ViewHolder> {

    private OnProductSelectedListener mListener;

    public InformasiAdapter(Query query, OnProductSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_rv_informasi, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    public interface OnProductSelectedListener {

        void onProductSelected(DocumentSnapshot productModel);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_informasi;

        public ViewHolder(View itemView) {
            super(itemView);
            img_informasi = itemView.findViewById(R.id.img_informasi);
        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnProductSelectedListener listener) {

            InformasiModel informasiModel = snapshot.toObject(InformasiModel.class);


            // Load image
            Glide.with(img_informasi.getContext())
                    .load(informasiModel.getImage())
                    .into(img_informasi);

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

