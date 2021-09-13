package com.aksantara.mother.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aksantara.mother.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PemeriksaanRisikoActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private ImageView ic_back;

    private RadioGroup rg_1, rg_2, rg_3, rg_4, rg_5, rg_6, rg_7, rg_8, rg_9, rg_10, rg_11, rg_12, rg_13, rg_14, rg_15, rg_16, rg_17, rg_18, rg_19, rg_20, rg_21, rg_22, rg_23, rg_24, rg_25;
    private String mudaHamil, lambatHamil, tuaHamil, cepatHamil, lamaHamil, banyakAnak, terlaluPendek, melahirkanDengan, operasiSesar, bengkakMuka, kembar2, kembarAir
            ,bayiMati, kehamilanLebih, letakSungsang, letakLintang, pendarahan, kejang, kurangDarah, menularSexual, tbcParu, payahJantung, kencingManis, malaria;

    private TextView tvTotalSkor;
    private TextView tvKelompokRisiko, tvPerawat, tvRujukan, tvTempat, tvPenolong;

    private String kelompokRisiko, perawatan, rujukan, tempat, penolong;


    private FirebaseUser user;
    private FirebaseFirestore mFirestore;
    private DocumentReference mUserRef;
    private FirebaseAuth mAuth;
    private String userId;

    private Button btnSelesai;

    private Double userTotalPemeriksaan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemeriksaan_risiko);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        user = mAuth.getCurrentUser();
        mUserRef = mFirestore.collection("users").document(userId);

        tvTotalSkor = findViewById(R.id.tvTotalSkor);
        tvKelompokRisiko = findViewById(R.id.tvKelompokRisiko);
        tvPerawat = findViewById(R.id.tvPerawatan);
        tvRujukan = findViewById(R.id.tvRujukan);
        tvTempat = findViewById(R.id.tvTempat);
        tvPenolong = findViewById(R.id.tvPenolong);
        btnSelesai = findViewById(R.id.btnSelesai);
        ic_back = findViewById(R.id.icBack);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        initData();

        initView();
    }


    private void initData() {

        mUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");

                        userTotalPemeriksaan = document.getDouble("totalPemeriksaanRisiko");
                        Log.d(TAG, String.valueOf(userTotalPemeriksaan));

                    } else {
                        Log.d(TAG, "Document does not exist!");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });

    }

    private void initView() {
        //muda
        rg_1 = (RadioGroup) findViewById(R.id.rg_terlalumuudahamil);
        rg_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId) {
                    case R.id.rb_ya_hamil:
                        mudaHamil = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_hamil:
                        mudaHamil = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //terlambat
        rg_2 = (RadioGroup) findViewById(R.id.rg_terlambathamil);
        rg_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_terlambat:
                        lambatHamil = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_terlambat:
                        lambatHamil = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //tua
        rg_3 = (RadioGroup) findViewById(R.id.rg_terlalutua);
        rg_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_tua:
                        tuaHamil = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_tua:
                        tuaHamil = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //cepat
        rg_4 = (RadioGroup) findViewById(R.id.rg_terlalucepat);
        rg_4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_cepat:
                        cepatHamil = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_cepat:
                        cepatHamil = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //lama
        rg_5 = (RadioGroup) findViewById(R.id.rg_terlalulama);
        rg_5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_lama:
                        lamaHamil = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_lama:
                        lamaHamil = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //banyak
        rg_6 = (RadioGroup) findViewById(R.id.rg_terlalubanyak);
        rg_6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_banyak:
                        banyakAnak = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_banyak:
                        banyakAnak = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //pendek
        rg_7 = (RadioGroup) findViewById(R.id.rg_terlalupendek);
        rg_7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_pendek:
                        terlaluPendek = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_pendek:
                        terlaluPendek = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //melahirkan dengan
        rg_8 = (RadioGroup) findViewById(R.id.rg_pernahmelahirkan);
        rg_8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_vacum:
                        melahirkanDengan = String.valueOf(4);
                        break;
                    case R.id.rb_uri_dirogoh:
                        melahirkanDengan = String.valueOf(4);
                        break;
                    case R.id.rb_diberi_infus:
                        melahirkanDengan = String.valueOf(4);
                        break;
                    case R.id.rb_belum_pernah:
                        melahirkanDengan = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //operasiSesar
        rg_9 = (RadioGroup) findViewById(R.id.rg_operasisesar);
        rg_9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_sesar:
                        operasiSesar = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_sesar:
                        operasiSesar = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //kurangDarah
        rg_20 = (RadioGroup) findViewById(R.id.rg_kurang_darah);
        rg_20.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_kurangDarah:
                        kurangDarah = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_kurangDarah:
                        kurangDarah = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //menularSexual
        rg_21 = (RadioGroup) findViewById(R.id.rg_penyakit_menular);
        rg_21.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_menular:
                        menularSexual = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_menular:
                        menularSexual = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //tbcParu
        rg_22 = (RadioGroup) findViewById(R.id.rg_tbc_paru);
        rg_22.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_tbc:
                        tbcParu = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_tbc:
                        tbcParu = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //payahJantung
        rg_23 = (RadioGroup) findViewById(R.id.rg_payah_jantung);
        rg_23.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_payah:
                        payahJantung = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_payah:
                        payahJantung = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //kencingManis
        rg_24 = (RadioGroup) findViewById(R.id.rg_kencing_manis);
        rg_24.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_kencing:
                        kencingManis = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_kencing:
                        kencingManis = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //malaria
        rg_25 = (RadioGroup) findViewById(R.id.rg_malaria);
        rg_25.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_malaria:
                        malaria = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_malaria:
                        malaria = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //bengkakMuka
        rg_11 = (RadioGroup) findViewById(R.id.rg_bengkakmuka);
        rg_11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_bengkak:
                        bengkakMuka = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_bengkak:
                        bengkakMuka = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //hamilKembar2
        rg_12 = (RadioGroup) findViewById(R.id.rg_hamilkembar);
        rg_12.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_kembar2:
                        kembar2 = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_kembar2:
                        kembar2 = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //kembarAir
        rg_13 = (RadioGroup) findViewById(R.id.rg_hamilkembarair);
        rg_13.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_kembarair:
                        kembarAir = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_kembarair:
                        kembarAir = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //bayiMati
        rg_14 = (RadioGroup) findViewById(R.id.rg_bayimati);
        rg_14.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_bayimati:
                        bayiMati = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_bayimati:
                        bayiMati = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //kehamilanLebih
        rg_15 = (RadioGroup) findViewById(R.id.rg_kemahilanlebih);
        rg_15.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_kemahilanlebih:
                        kehamilanLebih = String.valueOf(4);
                        break;
                    case R.id.rb_tidak_kemahilanlebih:
                        kehamilanLebih = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //letakSungsang
        rg_16 = (RadioGroup) findViewById(R.id.rg_hamilsungsang);
        rg_16.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_hamilsungsang:
                        letakSungsang = String.valueOf(8);
                        break;
                    case R.id.rb_tidak_hamilsungsang:
                        letakSungsang = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //letakLintang
        rg_17 = (RadioGroup) findViewById(R.id.rg_letaklintang);
        rg_17.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_letaklintang:
                        letakLintang = String.valueOf(8);
                        break;
                    case R.id.rb_tidak_letaklintang:
                        letakLintang = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //pendarahan
        rg_18 = (RadioGroup) findViewById(R.id.rg_pendarahan);
        rg_18.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_pendarahan:
                        pendarahan = String.valueOf(8);
                        break;
                    case R.id.rb_tidak_pendarahan:
                        pendarahan = String.valueOf(0);
                        break;
                    default:
                }
            }
        });
        //kejang
        rg_19 = (RadioGroup) findViewById(R.id.rg_kejang);
        rg_19.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_ya_kejang:
                        kejang = String.valueOf(8);
                        break;
                    case R.id.rb_tidak_kejang:
                        kejang = String.valueOf(0);
                        break;
                    default:
                }
            }
        });

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lihatSkor();
            }
        });
    }

    private void lihatSkor() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        if (TextUtils.isEmpty(mudaHamil)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan terlalu muda hamil boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(lambatHamil)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan terlalu lambat hamil tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(tuaHamil)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan terlalu tua hamil tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(cepatHamil)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan terlalu cepat tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(lamaHamil)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan terlalu lama tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(banyakAnak)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan terlalu banyak anak tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(terlaluPendek)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan terlalu pendek tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(melahirkanDengan)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan pernah melahirkan dengan tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(operasiSesar)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan operasi sesar tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(kurangDarah)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan penyakit kurang darah tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(menularSexual)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan penyakit menular sexual tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(tbcParu)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan penyakit tbc paru tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(payahJantung)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan penyakit payah jantung tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(kencingManis)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan penyakit kencing manis tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(malaria)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan penyakit malaria tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(bengkakMuka)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan bengkak muka tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(kembar2)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan hamil kembar 2 tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(kembarAir)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan hamil kembar air tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(bayiMati)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan bayi mati tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(kehamilanLebih)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan kehamilan lebih bulan tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(letakSungsang)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan letak sungsang tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(letakLintang)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan letak lintang tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(pendarahan)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan mengalami pendarahan tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(kejang)) {
            Toast.makeText(getApplicationContext(), "Pertanyaan mengalami kejang tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        int scor1 = Integer.parseInt(mudaHamil);
        int scor2 = Integer.parseInt(lambatHamil);
        int scor3 = Integer.parseInt(tuaHamil);
        int scor4 = Integer.parseInt(cepatHamil);
        int scor5 = Integer.parseInt(lamaHamil);
        int scor6 = Integer.parseInt(banyakAnak);
        int scor7 = Integer.parseInt(terlaluPendek);
        int scor8 = Integer.parseInt(melahirkanDengan);
        int scor9 = Integer.parseInt(operasiSesar);
//        int scor10 = Integer.parseInt(memilikiPenyakit);
        int scor11 = Integer.parseInt(bengkakMuka);
        int scor12 = Integer.parseInt(kembar2);
        int scor13 = Integer.parseInt(kembarAir);
        int scor14 = Integer.parseInt(bayiMati);
        int scor15 = Integer.parseInt(kehamilanLebih);
        int scor16 = Integer.parseInt(letakSungsang);
        int scor17 = Integer.parseInt(letakLintang);
        int scor18 = Integer.parseInt(pendarahan);
        int scor19 = Integer.parseInt(kejang);
        int scor20 = Integer.parseInt(kurangDarah);
        int scor21 = Integer.parseInt(menularSexual);
        int scor22 = Integer.parseInt(tbcParu);
        int scor23 = Integer.parseInt(payahJantung);
        int scor24 = Integer.parseInt(kencingManis);
        int scor25 = Integer.parseInt(malaria);

        int total = (2 + scor1 + scor2 + scor3 + scor4 + scor5 + scor6 + scor7 + scor8 + scor9 + scor11 + scor12 + scor13 + scor14 + scor15 + scor16 + scor17 +scor18 + scor19 + scor20
                + scor21 + scor22 + scor23 + scor24 + scor25);
        final String totalSkor = String.valueOf(total);

        if (total == 2 || total == 6) {
            tvKelompokRisiko.setText("KRR (KEHAMILAN RISIKO RENDAH)");
            tvPerawat.setText("BIDAN");
            tvRujukan.setText("TIDAK DIRUJUK");
            tvTempat.setText("RUMAH POLINDES");
            tvPenolong.setText("BIDAN");
        }
        else if (total >= 12) {
            tvKelompokRisiko.setText("KRST (KEHAMILAN RISIKO SANGAT TINGGI)");
            tvPerawat.setText("DOKTER");
            tvRujukan.setText("RUMAH SAKIT");
            tvTempat.setText("RUMAH SAKIT");
            tvPenolong.setText("DOKTER");
        }
        else {
            tvKelompokRisiko.setText("KRT (KEHAMILAN RISIKO TINGGI)");
            tvPerawat.setText("BIDAN / DOKTER");
            tvRujukan.setText("BIDAN / PKM");
            tvTempat.setText("POLINDES / PKM / RUMAH SAKIT");
            tvPenolong.setText("BIDAN / DOKTER");
        }

        kelompokRisiko = tvKelompokRisiko.getText().toString();
        perawatan = tvPerawat.getText().toString();
        rujukan = tvRujukan.getText().toString();
        tempat = tvTempat.getText().toString();
        penolong = tvPenolong.getText().toString();

        userTotalPemeriksaan += 1;
        final String pemeriksaanId = String.format("%02d", Math.round(userTotalPemeriksaan));
        final DocumentReference documentReference = mFirestore.collection("users").document(userId).collection("PemeriksaanRisiko").document(userId + pemeriksaanId);
        Map<String, Object> userPemeriksaan = new HashMap<>();
        userPemeriksaan.put("totalSkor", totalSkor);
        userPemeriksaan.put("kelompokRisiko", kelompokRisiko);
        userPemeriksaan.put("perawatan", perawatan);
        userPemeriksaan.put("rujukan", rujukan);
        userPemeriksaan.put("tempat", tempat);
        userPemeriksaan.put("penolong", penolong);
        userPemeriksaan.put("userId", userId);
        userPemeriksaan.put("pemeriksaanId", pemeriksaanId);
        userPemeriksaan.put("date", new Timestamp(new Date()));
        documentReference.set(userPemeriksaan).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent next = new Intent(PemeriksaanRisikoActivity.this, HasilPemeriksaanRisikoActivity.class);
                startActivity(next);
                next.putExtra(HasilPemeriksaanRisikoActivity.KEY_PEMERIKSAAN_ID, pemeriksaanId);
                next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
                finish();
                mUserRef.update("totalPemeriksaanRisiko", userTotalPemeriksaan);
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(PemeriksaanRisikoActivity.this, "Pemeriksaan gagal. Silahkan coba lagi dan isikan form dengan benar.",
                        Toast.LENGTH_LONG).show();

            }
        });

    }


}