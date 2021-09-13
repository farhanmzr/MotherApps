package com.aksantara.mother.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aksantara.mother.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PemeriksaanIdentitasActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private FirebaseUser user;
    private FirebaseFirestore mFirestore;
    private DocumentReference mUserRef;
    private FirebaseAuth mAuth;
    private String userId;

    private TextInputEditText etNama, etTtl, etKelahiranKe, etUsiaAnakTerakhir, etGolDarah;
    private ImageView icBack;
    private Spinner spinner_gol_darah;
    private Button btnNext;

    TextView tvAge;

    String[] mOption = {"-","A", "B", "AB", "O"};
    String nama, ttl, umur, kehamilan_ke, usia_anak_terakhir, gol_darah;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemeriksaan_identitas);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        user = mAuth.getCurrentUser();
        mUserRef = mFirestore.collection("users").document(userId);

        initData();
        initView();
    }

    private void initView() {

        icBack = findViewById(R.id.icBack);
        etNama = findViewById(R.id.etNama);
        etNama.setEnabled(false);
        etTtl = findViewById(R.id.etTtl);
        etKelahiranKe = findViewById(R.id.etKelahiranKe);
        etUsiaAnakTerakhir = findViewById(R.id.etUsiaAnakTerakhir);
        etGolDarah = findViewById(R.id.etGolDarah);
        btnNext = findViewById(R.id.btnNext);
        tvAge = findViewById(R.id.tvAge);
        tvAge.setVisibility(View.INVISIBLE);

        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        spinner_gol_darah = findViewById(R.id.spinner_gol_darah);
        ArrayAdapter jk = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mOption);
        jk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gol_darah.setAdapter(jk);

        spinner_gol_darah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                etGolDarah.setText(parent.getItemAtPosition(position).toString());
                view.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etTtl.setOnClickListener(new View.OnClickListener() {
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

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pemeriksaanIdentitas();
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
            etTtl.setText(format);
            tvAge.setText(Integer.toString(calculateAge(c.getTimeInMillis())));
        }
    };

    private int calculateAge(long date) {

        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }
        return age;

    }

    private void pemeriksaanIdentitas() {

        nama = etNama.getText().toString();
        ttl = etTtl.getText().toString();
        umur = tvAge.getText().toString();
        kehamilan_ke = etKelahiranKe.getText().toString();
        usia_anak_terakhir = etUsiaAnakTerakhir.getText().toString();
        gol_darah = etGolDarah.getText().toString();

        if (TextUtils.isEmpty(nama)) {
            Toast.makeText(getApplicationContext(), "Nama tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ttl.equals("HH-BB-TTTT")) {
            Toast.makeText(getApplicationContext(), "Tanggal Lahir tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(kehamilan_ke)) {
            Toast.makeText(getApplicationContext(), "Kelahiran Ke- tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(usia_anak_terakhir)) {
            Toast.makeText(getApplicationContext(), "Usia Anak Terakhir tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(gol_darah)) {
            Toast.makeText(getApplicationContext(), "Golongan Darah tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent next = new Intent(PemeriksaanIdentitasActivity.this, PemeriksaanMandiriActivity.class);
        next.putExtra("nama", nama);
        next.putExtra("ttl", ttl);
        next.putExtra("umur", umur);
        next.putExtra("kehamilanke", kehamilan_ke);
        next.putExtra("anakUsiaTerakhir", usia_anak_terakhir);
        next.putExtra("golDarah", gol_darah);
        startActivity(next);
        startActivity(next);
        Toast.makeText(PemeriksaanIdentitasActivity.this, "Silahkan isi form pada tahap 2.",
                Toast.LENGTH_LONG).show();

    }


    private void initData() {

        mUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        etNama.setText(document.getString("nama"));

                    } else {
                        Log.d(TAG, "Document does not exist!");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });

    }





}