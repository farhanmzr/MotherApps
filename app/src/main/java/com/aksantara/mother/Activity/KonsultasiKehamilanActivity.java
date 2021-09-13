package com.aksantara.mother.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aksantara.mother.R;
import com.google.android.material.textfield.TextInputEditText;

import java.net.URLEncoder;

public class KonsultasiKehamilanActivity extends AppCompatActivity {

    private TextInputEditText etNama, etAlamat, etUmur;
    private LinearLayout btnKonsultasi;
    private ImageView icBack;

    String nama, alamat, umur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsultasi_kehamilan);

        initView();
        startKonsultasi();
    }

    private void initView() {

        etNama = findViewById(R.id.etNama);
        etAlamat = findViewById(R.id.etAlamat);
        etUmur = findViewById(R.id.etUmur);
        btnKonsultasi = findViewById(R.id.btnKonsultasi);
        icBack = findViewById(R.id.icBack);
        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void startKonsultasi() {

        btnKonsultasi.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        PackageManager packageManager = KonsultasiKehamilanActivity.this.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);

        try {
            nama = etNama.getText().toString().trim();
            alamat = etAlamat.getText().toString().trim();
            umur = etUmur.getText().toString().trim();

            if (TextUtils.isEmpty(nama)) {
                Toast.makeText(getApplicationContext(), "Nama tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(alamat)) {
                Toast.makeText(getApplicationContext(), "Alamat tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(umur)) {
                Toast.makeText(getApplicationContext(), "Umur tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                return;
            }

            String phone = "+6281326308666";
            String message = "Permisi, perkenalkan saya " + nama + ", umur " + umur +  " tahun" + " dan berdomisili di " +  alamat + " pengguna aplikasi MOTHER ingin berkonsultasi mengenai..";

            String url = "https://api.whatsapp.com/send?phone="+ phone +"&text=" + URLEncoder.encode(message, "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                KonsultasiKehamilanActivity.this.startActivity(i);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

}
        });

    }
}