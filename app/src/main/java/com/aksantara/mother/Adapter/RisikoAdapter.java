package com.aksantara.mother.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aksantara.mother.Model.PemeriksaanModel;
import com.aksantara.mother.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.text.DateFormat;

public class RisikoAdapter extends FirestoreAdapter<RisikoAdapter.ViewHolder> {

    private OnProductSelectedListener mListener;

    public RisikoAdapter(Query query, RisikoAdapter.OnProductSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public RisikoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new RisikoAdapter.ViewHolder(inflater.inflate(R.layout.item_rv_riwayat_risiko, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    public interface OnProductSelectedListener {

        void onProductSelected(DocumentSnapshot pemeriksaanModel);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNama;
        private TextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvDate = itemView.findViewById(R.id.tvDate);
        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnProductSelectedListener listener) {

            final PemeriksaanModel pemeriksaanModel = snapshot.toObject(PemeriksaanModel.class);

            tvNama.setText(pemeriksaanModel.getPemeriksaanId());

            DateFormat dateFormat = DateFormat.getDateTimeInstance();
            String date = dateFormat.format(pemeriksaanModel.getDate());

            tvDate.setText(date);

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
