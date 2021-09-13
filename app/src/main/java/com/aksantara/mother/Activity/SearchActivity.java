package com.aksantara.mother.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.aksantara.mother.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        CollectionReference productsRef = mFirestore.collection("products");

//        Map<String, Object> mapOne = new HashMap<>();
//        mapOne.put("productName", "Milk");
//        Map<String, Object> mapTwo = new HashMap<>();
//        mapTwo.put("productName", "Soy Milk");
//        Map<String, Object> mapThree = new HashMap<>();
//        mapThree.put("productName", "Bacon");
//
//        WriteBatch writeBatch = mFirestore.batch();
//        writeBatch.set(productsRef.document(), mapOne);
//        writeBatch.set(productsRef.document(), mapTwo);
//        writeBatch.set(productsRef.document(), mapThree);
//        writeBatch.commit();
//
        Client client = new Client("VLYAGW062Q", "f15aef538e0279ff142e649bab685c8a");
        final Index index = client.getIndex("products");
//
//        List<JSONObject> productList = new ArrayList<>();
//        productList.add(new JSONObject(mapOne));
//        productList.add(new JSONObject(mapTwo));
//        productList.add(new JSONObject(mapThree));
//        index.addObjectsAsync(new JSONArray(productList), null);

        final EditText editText = findViewById(R.id.et_search);
        final ListView listView = findViewById(R.id.list_view);
        productsRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> list = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                list.add(document.getString("productName"));
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                            listView.setAdapter(arrayAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                com.algolia.search.saas.Query query = new com.algolia.search.saas.Query(editable.toString())
                        .setAttributesToRetrieve("productName")
                        .setHitsPerPage(50);
                index.searchAsync(query, new CompletionHandler() {
                    @Override
                    public void requestCompleted(JSONObject content, AlgoliaException error) {
                        try {
                            JSONArray hits = content.getJSONArray("hits");
                            List<String> list = new ArrayList<>();
                            for (int i = 0; i < hits.length(); i++) {
                                JSONObject jsonObject = hits.getJSONObject(i);
                                String productName = jsonObject.getString("productName");
                                list.add(productName);
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                            listView.setAdapter(arrayAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


    }
}