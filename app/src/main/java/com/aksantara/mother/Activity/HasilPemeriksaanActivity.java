package com.aksantara.mother.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aksantara.mother.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HasilPemeriksaanActivity extends AppCompatActivity {

    public static final String KEY_PEMERIKSAAN_ID = "key_pemeriksaan_id";

    private Button btnSelesai;
    //Pemeriksaan Identitas
    private TextView tvNama, tvTtl, tvUmur, tvKehamilanKe, tvUsiaAnakTerakhkir, tvGolDarah;
    //Pemeriksaan Mandiri
    private TextView tvHPHT, tvUsiaKehamilan, tvTaksiranPersalinan, tvTinggiBadan, tvBB_sebelum, tvBB_sesudah, tvTekananDarah, tvLingkarlila, tvTinggiRahim, tvGerakJanin, tvIntensitasKontraksi;
    //Tambahan
    private TextView tvHbDarah, tvTetanus1, tvTetanus2, tvUrine, tvFeses, tvSwabVagina;
    //Kesimpulan
    private TextView tvKriteriaResikoKehamilan, tvKriteriaResikoPanggul, tvKriteriaBeratBadan, tvKriteriaTekananDarah, tvKriteriaLingkarlila, tvKriteriaTinggiRahim, tvKriteriaGerakJanin, tvKriteriaIntensitasKontraksi,
            tvKriteriaHbDarah;

    private static final String TAG = "ShippingAddressActivity";
    private FirebaseFirestore mFirestore;
    private DocumentReference mPemeriksaanRef, mUserRef;
    private FirebaseAuth mAuth;
    private Query mQuery;
    private String userId, pemeriksaanId;

    String ttl, umur;

    private Integer defaultTinggi = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_pemeriksaan);

        pemeriksaanId = getIntent().getExtras().getString(KEY_PEMERIKSAAN_ID);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        mPemeriksaanRef = mFirestore.collection("users").document(userId).collection("PemeriksaanMandiri").document(userId + pemeriksaanId);
        mUserRef = mFirestore.collection("users").document(userId);

        initView();
        initData();

    }

    private void initView() {

        //Pemeriksaan Identitas
        tvNama = findViewById(R.id.tvNama);
        tvTtl = findViewById(R.id.tvTtl);
        tvUmur = findViewById(R.id.tvUmur);
        tvKehamilanKe = findViewById(R.id.tvKehamilanKe);
        tvUsiaAnakTerakhkir = findViewById(R.id.tvUsiaAnakTerakhkir);
        tvGolDarah = findViewById(R.id.tvGolDarah);

        //Pemeriksaan Mandiri
        tvHPHT = findViewById(R.id.tvHPHT);
        tvUsiaKehamilan = findViewById(R.id.tvUsiaKehamilan);
        tvTaksiranPersalinan = findViewById(R.id.tvTaksiranPersalinan);
        tvTinggiBadan = findViewById(R.id.tvTinggiBadan);
        tvBB_sebelum = findViewById(R.id.tvBB_sebelum);
        tvBB_sesudah = findViewById(R.id.tvBB_sesudah);
        tvTekananDarah = findViewById(R.id.tvTekananDarah);
        tvLingkarlila = findViewById(R.id.tvLingkarlila);
        tvTinggiRahim = findViewById(R.id.tvTinggiRahim);
        tvGerakJanin = findViewById(R.id.tvGerakJanin);
        tvIntensitasKontraksi = findViewById(R.id.tvIntensitasKontraksi);

        //Pemeriksaan Tambahan
        tvHbDarah = findViewById(R.id.tvHbDarah);
        tvTetanus1 = findViewById(R.id.tvTetanus1);
        tvTetanus2 = findViewById(R.id.tvTetanus2);
        tvUrine = findViewById(R.id.tvUrine);
        tvFeses = findViewById(R.id.tvFeses);
        tvSwabVagina = findViewById(R.id.tvSwabVagina);

        //Kesimpulan
        tvKriteriaResikoKehamilan = findViewById(R.id.tvKriteriaResikoKehamilan);
        tvKriteriaResikoPanggul = findViewById(R.id.tvKriteriaResikoPanggul);
        tvKriteriaBeratBadan = findViewById(R.id.tvKriteriaBeratBadan);
        tvKriteriaTekananDarah = findViewById(R.id.tvKriteriaTekananDarah);
        tvKriteriaLingkarlila = findViewById(R.id.tvKriteriaLingkarlila);
        tvKriteriaTinggiRahim = findViewById(R.id.tvKriteriaTinggiRahim);
        tvKriteriaGerakJanin = findViewById(R.id.tvKriteriaGerakJanin);
        tvKriteriaIntensitasKontraksi = findViewById(R.id.tvKriteriaIntensitasKontraksi);
        tvKriteriaHbDarah = findViewById(R.id.tvKriteriaHbDarah);


        btnSelesai = findViewById(R.id.btnSelesai);
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HasilPemeriksaanActivity.this, RiwayatPemeriksaanActivity.class);
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
                        tvNama.setText(document.getString("nama"));

                        mPemeriksaanRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Log.d(TAG, "Document exists!");

                                        String ttl = document.getString("ttl");
                                        String umur = document.getString("umur");
                                        String kehamilanke = document.getString("kehamilanKe");
                                        String usiaAnakTerakhir = document.getString("usiaAnakTerakhir");
                                        String golDarah = document.getString("golonganDarah");

                                        String hpht = document.getString("tglHPHT");
                                        String usiaKehamilan = document.getString("usiaKehamilan");
                                        String taksiranPersalinan = document.getString("taksiranPersalinan");
                                        String tinggiBadan = document.getString("tinggiBadan");
                                        String bbSebelumHamil = document.getString("bbSebelumHamil");
                                        String bbSesudahHamil = document.getString("bbSesudahHamil");
                                        String tekananDarah1 = document.getString("tekananDarah1");
                                        String tekananDarah2 = document.getString("tekananDarah2");
                                        String ukuranLILA = document.getString("ukuranLILA");
                                        String tinggiRahim = document.getString("tinggiRahim");
                                        String gerakJanin = document.getString("gerakJaninSehari");
                                        String intensitasKontraksi = document.getString("intensitasKontraksi");

                                        String hemogoblin = document.getString("hemogoblin");
                                        String tetanus1 = document.getString("tetanus1");
                                        String tetanus2 = document.getString("tetanus2");
                                        String urine = document.getString("urine");
                                        String feses = document.getString("feses");
                                        String swabVagina = document.getString("swabVagina");

                                        tvTtl.setText(ttl);
                                        tvUmur.setText("(" + umur + " tahun" + ")");
                                        tvKehamilanKe.setText(kehamilanke);
                                        tvUsiaAnakTerakhkir.setText(usiaAnakTerakhir);
                                        tvGolDarah.setText(golDarah);

                                        tvHPHT.setText(hpht);
                                        tvUsiaKehamilan.setText(usiaKehamilan);
                                        tvTaksiranPersalinan.setText(taksiranPersalinan);
                                        tvTinggiBadan.setText(tinggiBadan + " cm");
                                        tvBB_sebelum.setText(bbSebelumHamil + " kg");
                                        tvBB_sesudah.setText(bbSesudahHamil + " kg");
                                        tvTekananDarah.setText(tekananDarah1 + " / " + tekananDarah2 + " mmHg");
                                        tvLingkarlila.setText(ukuranLILA + " cm");
                                        tvTinggiRahim.setText(tinggiRahim + " cm");
                                        tvGerakJanin.setText(gerakJanin + " kali sehari");
                                        tvIntensitasKontraksi.setText(intensitasKontraksi);

                                        tvHbDarah.setText(hemogoblin + " gr%");
                                        tvTetanus1.setText(tetanus1);
                                        tvTetanus2.setText(tetanus2);
                                        tvUrine.setText(urine);
                                        tvFeses.setText(feses);
                                        tvSwabVagina.setText(swabVagina);

                                        //kesimpulan1
                                        int age = Integer.parseInt(umur);
                                        int kehamilanKe = Integer.parseInt(kehamilanke);
                                        int anakTerakhir = Integer.parseInt(usiaAnakTerakhir);
                                        if (age < 20 || age > 30 ) {
                                            tvKriteriaResikoKehamilan.setText("Kehamilan anda berisiko");
                                        }
                                        else if (kehamilanKe > 2) {
                                            tvKriteriaResikoKehamilan.setText("Kehamilan anda berisiko");
                                        }
                                        else if (anakTerakhir < 2) {
                                            tvKriteriaResikoKehamilan.setText("Kehamilan anda berisiko");
                                        }
                                        else {
                                            tvKriteriaResikoKehamilan.setText("Kehamilan anda tidak berisiko");
                                        }

                                        //kesimpulan2
                                        int heightBody = Integer.parseInt(tinggiBadan);
                                        if (heightBody < 150) {
                                            tvKriteriaResikoPanggul.setText("Anda berisko panggul sempit");
                                        }
                                        else {
                                            tvKriteriaResikoPanggul.setText("Anda tidak berisiko panggul sempit");
                                        }

                                        //kesimpulan3 beratbadan
                                        int bbSebelum = Integer.parseInt(bbSebelumHamil);
                                        int bbSesudah = Integer.parseInt(bbSesudahHamil);
                                        if (bbSesudah == bbSebelum+8 || bbSesudah == bbSebelum+9 || bbSesudah == bbSebelum+10 || bbSesudah == bbSebelum+11 || bbSesudah == bbSebelum+12) {
                                                tvKriteriaBeratBadan.setText("Gizi anda cukup");
                                        }
                                        else if (bbSesudah < bbSebelum) {
                                            tvKriteriaBeratBadan.setText("Gizi anda kurang");
                                        }
                                        else if (bbSesudah == bbSebelum || bbSesudah == bbSebelum+1 || bbSesudah == bbSebelum+2 || bbSesudah == bbSebelum+3 || bbSesudah == bbSebelum+4 || bbSesudah == bbSebelum+5 || bbSesudah == bbSebelum+6 || bbSesudah == bbSebelum+7) {
                                            tvKriteriaBeratBadan.setText("Gizi anda tidak cukup");
                                        }
                                        else {
                                            tvKriteriaBeratBadan.setText("Gizi anda berlebih");
                                        }

                                        //kesimpulan4 tekanandarah
                                        int tekanandarah1 = Integer.parseInt(tekananDarah1);
                                        int tekanandarah2 = Integer.parseInt(tekananDarah2);
                                        if (tekanandarah1 >= 90 == tekanandarah2 >=60 == tekanandarah1 <=120 == tekanandarah2 <=80) {
                                            tvKriteriaTekananDarah.setText("Tekanan darah anda normal");
                                        }
                                        else if (tekanandarah1 < 90 || tekanandarah2 < 60) {
                                            tvKriteriaTekananDarah.setText("Tekanan darah anda rendah");
                                        }
                                        else {
                                            tvKriteriaTekananDarah.setText("Tekanan darah anda tinggi");
                                        }

                                        //kesimpulan5 lila
                                        Double lila = Double.valueOf(ukuranLILA);
                                        if (lila < 23.5){
                                            tvKriteriaLingkarlila.setText("Gizi anda kurang");
                                        }
                                        else {
                                            tvKriteriaLingkarlila.setText("Gizi anda cukup");
                                        }

                                        //kesimpulan6 tinggirahim
                                        int heightRahim = Integer.parseInt(tinggiRahim);
                                        int TaksiranBerat = (heightRahim - 12) * 155;
                                        String taksiran = String.valueOf(TaksiranBerat);
                                        tvKriteriaTinggiRahim.setText("Taksiran berat janin anda " + taksiran + " gram" );

                                        //kesimpulan7 gerak janin
                                        int gerak = Integer.parseInt(gerakJanin);
                                        if (gerak < 8 || gerak > 33) {
                                            tvKriteriaGerakJanin.setText("Gawat Janin");
                                        }
                                        else {
                                            tvKriteriaGerakJanin.setText("Janin Normal");
                                        }
                                        //kesimpulan8 kontraksi
                                        if (intensitasKontraksi.equals("Sering")) {
                                            tvKriteriaIntensitasKontraksi.setText("Kontraksi anda tidak normal");
                                        }
                                        else {
                                            tvKriteriaIntensitasKontraksi.setText("Kontraksi anda normal");
                                        }
                                        //kesimpulan9 hbDarah
                                        int hb = Integer.parseInt(hemogoblin);
                                        if (hb < 12) {
                                            tvKriteriaHbDarah.setText("Tidak Normal");
                                        }
                                        else if (hb == 10 && hb == 11) {
                                            tvKriteriaHbDarah.setText("Anemi Kehamilan");
                                        }
                                        else {
                                            tvKriteriaHbDarah.setText("Normal");
                                        }


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