package com.aksantara.mother.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aksantara.mother.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.media.CamcorderProfile.get;

public class PemeriksaanMandiriActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private FirebaseUser user;
    private FirebaseFirestore mFirestore;
    private DocumentReference mUserRef;
    private FirebaseAuth mAuth;
    private String userId;

    private TextInputEditText etHTHP, etTinggiBadan, etBB_sebelum_hamil, etBB_setelah_hamil, etTekananDarah_1, etTekananDarah_2, etUkuranLILA , etTinggiRahim , etGerakJaninSehari, etIntensitasKontraksi;
    String[] optionKontraksi = {"Jarang","Tidak Sering", "Sering"};
    String hthp, usiaKehamilan, taksiranPersalinan, tinggibadan, bb_sebelum, bb_sesudah, tekanandarah_1, tekanandarah_2, ukuranlila, tinggirahim, gerakjanin, intensitaskontraksi;
    private LinearLayout linearUsiaKehamilan;
    private TextView tvUsiaKehamilan, tvTaksiranPersalinan;
    private TextView tvUsername, tvUmur, tvTtl, tvKehamilanKe, tvUsiaAnakTerakhkir, tvGolDarah;

    //pemeriksaan tambahan
    private TextInputEditText etHBDarah, etUrine, etFeses, etSwabVagina;
    private RadioGroup rg_tetanus_1, rg_tetanus_2;
    private RadioButton mTetanus_1, mTetanus_2;
    String hbDarah, tetanus1, tetanus2, urine, feses, swabVagina ;
    String tekananDarah;

    String nama,ttl,umur,kehamilan_ke,usia_anak_terakhir,gol_darah;

    private TextView tvDialogTinggiRahim;
    private ImageView icBack;
    private Spinner spinner_kontraksi;
    private Button btnNext;

    private Double userTotalPemeriksaan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemeriksaan_mandiri);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        user = mAuth.getCurrentUser();
        mUserRef = mFirestore.collection("users").document(userId);


        initData();
        initView();
    }
    private void initView() {

        //maret-desember
        //day+7, month-3, year+1
        //january-maret
        //day+7, month-1, year+0

        tvTaksiranPersalinan = findViewById(R.id.tvTaksiranPersalinan);
        tvGolDarah = findViewById(R.id.tvGolDarah);
        tvUsiaAnakTerakhkir = findViewById(R.id.tvUsiaAnakTerakhkir);
        tvKehamilanKe = findViewById(R.id.tvKehamilanKe);
        tvTtl = findViewById(R.id.tvTtl);
        tvUmur = findViewById(R.id.tvUmur);
        tvUsername = findViewById(R.id.tvUsername);
        icBack = findViewById(R.id.icBack);
        tvDialogTinggiRahim = findViewById(R.id.tvDialogTinggiRahim);
        btnNext = findViewById(R.id.btnNext);
        //inputan
        etHTHP = findViewById(R.id.etHTHP);
        linearUsiaKehamilan = findViewById(R.id.linearUsiaKehamilan);
        tvUsiaKehamilan = findViewById(R.id.tvUsiaKehamilan);
        etTinggiBadan = findViewById(R.id.etTinggiBadan);
        etBB_sebelum_hamil = findViewById(R.id.etBB_sebelum_hamil);
        etBB_setelah_hamil = findViewById(R.id.etBB_setelah_hamil);
        etTekananDarah_1 = findViewById(R.id.etTekananDarah_1);
        etTekananDarah_2 = findViewById(R.id.etTekananDarah_2);
        etUkuranLILA = findViewById(R.id.etUkuranLILA);
        etTinggiRahim = findViewById(R.id.etTinggiRahim);
        etGerakJaninSehari = findViewById(R.id.etGerakJaninSehari);
        etIntensitasKontraksi = findViewById(R.id.etIntensitasKontraksi);
        spinner_kontraksi = findViewById(R.id.spinner_kontraksi);
        //inputan tambahan
        etHBDarah = findViewById(R.id.etHBDarah);
        etUrine = findViewById(R.id.etUrine);
        etFeses = findViewById(R.id.etFeses);
        etSwabVagina = findViewById(R.id.etSwabVagina);

        Intent intent = getIntent();
        String name = intent.getStringExtra("nama");
        String lahir = intent.getStringExtra("ttl");
        String age = intent.getStringExtra("umur");
        String kehamilan = intent.getStringExtra("kehamilanke");
        String usia = intent.getStringExtra("anakUsiaTerakhir");
        String goldarah = intent.getStringExtra("golDarah");
        tvUsername.setText(name);
        tvTtl.setText(lahir);
        tvUmur.setText(age);
        tvKehamilanKe.setText(kehamilan);
        tvUsiaAnakTerakhkir.setText(usia);
        tvGolDarah.setText(goldarah);


        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //maret-desember
        //day+7, month-3, year+1
        //january-maret
        //day+7, month+9, year+0

        etHTHP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(v.getContext(), datePickerListener, mYear, mMonth, mDay);
                dateDialog.getDatePicker().setMaxDate(new Date().getTime());
                dateDialog.show();
            }
        });



        ArrayAdapter jk = new ArrayAdapter(this, android.R.layout.simple_spinner_item, optionKontraksi);
        jk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_kontraksi.setAdapter(jk);

        spinner_kontraksi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                etIntensitasKontraksi.setText(parent.getItemAtPosition(position).toString());
                view.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvDialogTinggiRahim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(PemeriksaanMandiriActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_tinggi_rahim, null);

                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //tambahan radiobutton
        rg_tetanus_1 = (RadioGroup) findViewById(R.id.rg_tetanus_1);
        rg_tetanus_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mTetanus_1 = rg_tetanus_1.findViewById(checkedId);

                switch (checkedId) {
                    case R.id.rb_belum_pernah:
                        tetanus1 = mTetanus_1.getText().toString();
                        break;
                    case R.id.rb_sudah_pernah:
                        tetanus1 = mTetanus_1.getText().toString();
                        break;
                    default:
                }
            }
        });
        rg_tetanus_2 = (RadioGroup) findViewById(R.id.rg_tetanus_2);
        rg_tetanus_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mTetanus_2 = rg_tetanus_2.findViewById(checkedId);

                switch (checkedId) {
                    case R.id.rb_belum_pernah_2:
                        tetanus2 = mTetanus_2.getText().toString();
                        break;
                    case R.id.rb_sudah_pernah_2:
                        tetanus2 = mTetanus_2.getText().toString();
                        break;
                    default:
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pemeriksaanMandiri();
            }
        });

    }


    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);

            String format = new SimpleDateFormat("dd MMM YYYY").format(c.getTime());
            etHTHP.setText(format);
            linearUsiaKehamilan.setVisibility(View.VISIBLE);
            tvUsiaKehamilan.setText(Integer.toString(calculateAge(c.getTimeInMillis())) + " minggu");


            String date = new SimpleDateFormat("MMM").format(c.getTime());

            if (date.equals("Jan")) {
                day = day+7;
                month = month+10;
                year = year+0;
                String taksiran = day+"/"+month+"/"+year;
                tvTaksiranPersalinan.setText(taksiran);
            }
            else if (date.equals("Feb")) {
                day = day+7;
                month = month+10;
                year = year+0;
                String taksiran = day+"/"+month+"/"+year;
                tvTaksiranPersalinan.setText(taksiran);
            }
            else if (date.equals("Mar")) {
                day = day+7;
                month = month+10;
                year = year+0;
                String taksiran = day+"/"+month+"/"+year;
                tvTaksiranPersalinan.setText(taksiran);
            }
            else if (date.equals("Apr")) {
                day = day+7;
                month = month-2;
                year = year+1;
                String taksiran = day+"/"+month+"/"+year;
                tvTaksiranPersalinan.setText(taksiran);
            }
            else if (date.equals("Mei")) {
                day = day+7;
                month = month-2;
                year = year+1;
                String taksiran = day+"/"+month+"/"+year;
                tvTaksiranPersalinan.setText(taksiran);
            }
            else if (date.equals("Jun")) {
                day = day+7;
                month = month-2;
                year = year+1;
                String taksiran = day+"/"+month+"/"+year;
                tvTaksiranPersalinan.setText(taksiran);
            }
            else if (date.equals("Jul")) {
                day = day+7;
                month = month-2;
                year = year+1;
                String taksiran = day+"/"+month+"/"+year;
                tvTaksiranPersalinan.setText(taksiran);
            }
            else if (date.equals("Agu")) {
                day = day+7;
                month = month-2;
                year = year+1;
                String taksiran = day+"/"+month+"/"+year;
                tvTaksiranPersalinan.setText(taksiran);
            }
            else if (date.equals("Sep")) {
                day = day+7;
                month = month-2;
                year = year+1;
                String taksiran = day+"/"+month+"/"+year;
                tvTaksiranPersalinan.setText(taksiran);
            }
            else if (date.equals("Okt")) {
                day = day+7;
                month = month-2;
                year = year+1;
                String taksiran = day+"/"+month+"/"+year;
                tvTaksiranPersalinan.setText(taksiran);
            }
            else if (date.equals("Nov")) {
                day = day+7;
                month = month-2;
                year = year+1;
                String taksiran = day+"/"+month+"/"+year;
                tvTaksiranPersalinan.setText(taksiran);
            }
            else {
                if (date.equals("Des")) {
                    day = day + 7;
                    month = month-2;
                    year = year + 1;
                    String taksiran = day + "/" + month + "/" + year;
                    tvTaksiranPersalinan.setText(taksiran);
                }
            }

        }
    };

    private int calculateAge(long date) {

        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);
        Calendar today = Calendar.getInstance();

        int age;

        if ((today.get(Calendar.YEAR) != dob.get(Calendar.YEAR))) {
            Toast.makeText(getApplicationContext(), "tahun tidak sama", Toast.LENGTH_SHORT).show();
             age = today.get(Calendar.WEEK_OF_YEAR) - dob.get(Calendar.WEEK_OF_YEAR);
        } else {
            age = today.get(Calendar.WEEK_OF_YEAR) - dob.get(Calendar.WEEK_OF_YEAR);
        }

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }
        return age;

    }


    private void initData() {

        mUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");

                        userTotalPemeriksaan = document.getDouble("totalPemeriksaanMandiri");
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


    private void pemeriksaanMandiri() {

        hthp = Objects.requireNonNull(etHTHP.getText()).toString();
        usiaKehamilan = Objects.requireNonNull(tvUsiaKehamilan.getText()).toString();
        tinggibadan = Objects.requireNonNull(etTinggiBadan.getText()).toString();
        bb_sebelum = Objects.requireNonNull(etBB_sebelum_hamil.getText()).toString();
        bb_sesudah = Objects.requireNonNull(etBB_setelah_hamil.getText()).toString();
        tekanandarah_1 = Objects.requireNonNull(etTekananDarah_1.getText()).toString();
        tekanandarah_2 = Objects.requireNonNull(etTekananDarah_2.getText()).toString();
        ukuranlila = Objects.requireNonNull(etUkuranLILA.getText()).toString();
        tinggirahim = Objects.requireNonNull(etTinggiRahim.getText()).toString();
        gerakjanin = Objects.requireNonNull(etGerakJaninSehari.getText()).toString();
        intensitaskontraksi = Objects.requireNonNull(etIntensitasKontraksi.getText()).toString();
        hbDarah = Objects.requireNonNull(etHBDarah.getText()).toString();
        urine = Objects.requireNonNull(etUrine.getText()).toString();
        feses = Objects.requireNonNull(etFeses.getText()).toString();
        swabVagina = Objects.requireNonNull(etSwabVagina.getText()).toString();
        taksiranPersalinan = tvTaksiranPersalinan.getText().toString();
        //
        nama = tvUsername.getText().toString();
        ttl = tvTtl.getText().toString();
        umur = tvUmur.getText().toString();
        kehamilan_ke = tvKehamilanKe.getText().toString();
        usia_anak_terakhir = tvUsiaAnakTerakhkir.getText().toString();
        gol_darah = tvGolDarah.getText().toString();

        if (hthp.equals("HH-BB-TTTT")) {
            Toast.makeText(getApplicationContext(), "Tanggal HTHP tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tinggibadan)) {
            Toast.makeText(getApplicationContext(), "Tinggi Badan tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(bb_sebelum)) {
            Toast.makeText(getApplicationContext(), "Berat Badan Sebelum Hamil tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(bb_sesudah)) {
            Toast.makeText(getApplicationContext(), "Berat Badan Sesudah Hamil tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tekanandarah_1)) {
            Toast.makeText(getApplicationContext(), "Tekanan Darah tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tekanandarah_2)) {
            Toast.makeText(getApplicationContext(), "Tekanan Darah tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ukuranlila)) {
            Toast.makeText(getApplicationContext(), "Ukuran LILA tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tinggirahim)) {
            Toast.makeText(getApplicationContext(), "Tinggi Rahim tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(gerakjanin)) {
            Toast.makeText(getApplicationContext(), "Gerak Janin tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        //tambahan
        if (TextUtils.isEmpty(hbDarah)) {
            Toast.makeText(getApplicationContext(), "HB Darah tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tetanus1)) {
            Toast.makeText(getApplicationContext(), "Tetanus 1 tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tetanus2)) {
            Toast.makeText(getApplicationContext(), "Tetanus 2 tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(urine)) {
            Toast.makeText(getApplicationContext(), "Urine tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(feses)) {
            Toast.makeText(getApplicationContext(), "Feses tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(swabVagina)) {
            Toast.makeText(getApplicationContext(), "Swab Vagina tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog pd = new ProgressDialog(PemeriksaanMandiriActivity.this);
        pd.setMessage("Mohon tunggu..");
        pd.show();
        pd.setCanceledOnTouchOutside(false);

        userTotalPemeriksaan += 1;
        final String pemeriksaanId = String.format("%04d", Math.round(userTotalPemeriksaan));
        final DocumentReference documentReference = mFirestore.collection("users").document(userId).collection("PemeriksaanMandiri").document(userId + pemeriksaanId);
        Map<String, Object> userPemeriksaan = new HashMap<>();
        userPemeriksaan.put("nama", nama);
        userPemeriksaan.put("ttl", ttl);
        userPemeriksaan.put("umur", umur);
        userPemeriksaan.put("kehamilanKe", kehamilan_ke);
        userPemeriksaan.put("usiaAnakTerakhir", usia_anak_terakhir);
        userPemeriksaan.put("golonganDarah", gol_darah);
        userPemeriksaan.put("tglHPHT", hthp);
        userPemeriksaan.put("usiaKehamilan", usiaKehamilan);
        userPemeriksaan.put("taksiranPersalinan", taksiranPersalinan);
        userPemeriksaan.put("tinggiBadan", tinggibadan);
        userPemeriksaan.put("bbSebelumHamil",bb_sebelum);
        userPemeriksaan.put("bbSesudahHamil", bb_sesudah);
        userPemeriksaan.put("tekananDarah1", tekanandarah_1);
        userPemeriksaan.put("tekananDarah2", tekanandarah_2);
        userPemeriksaan.put("ukuranLILA", ukuranlila);
        userPemeriksaan.put("tinggiRahim", tinggirahim);
        userPemeriksaan.put("gerakJaninSehari", gerakjanin);
        userPemeriksaan.put("intensitasKontraksi", intensitaskontraksi);
        userPemeriksaan.put("hemogoblin", hbDarah);
        userPemeriksaan.put("tetanus1", tetanus1);
        userPemeriksaan.put("tetanus2", tetanus2);
        userPemeriksaan.put("urine", urine);
        userPemeriksaan.put("feses", feses);
        userPemeriksaan.put("swabVagina", swabVagina);
        userPemeriksaan.put("userId", userId);
        userPemeriksaan.put("pemeriksaanId", pemeriksaanId);
        userPemeriksaan.put("date", new Timestamp(new Date()));
        documentReference.set(userPemeriksaan).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent next = new Intent(PemeriksaanMandiriActivity.this, HasilPemeriksaanActivity.class);
                startActivity(next);
                next.putExtra(HasilPemeriksaanActivity.KEY_PEMERIKSAAN_ID, pemeriksaanId);
                next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(next);
                finish();
                mUserRef.update("totalPemeriksaanMandiri", userTotalPemeriksaan);
                pd.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(PemeriksaanMandiriActivity.this, "Pemeriksaan gagal. Silahkan coba lagi dan isikan form dengan benar.",
                        Toast.LENGTH_LONG).show();

            }
        });
    }



}