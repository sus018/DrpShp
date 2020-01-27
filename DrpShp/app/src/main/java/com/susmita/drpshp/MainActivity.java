package com.susmita.drpshp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentCollections;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button save,fetch;
    String productId, customerId, brandCode, brandName, productCode, productDesc, mrp, expiry;

    private FirebaseFirestore firebaseFirestore;
    CollectionReference yourCollRef;

    private RecyclerView recyclerView;
    private List<Products> productsList;
    private ProductListAdapter productListAdapter;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        save = findViewById(R.id.save);
        fetch = findViewById(R.id.fetch);


        productsList = new ArrayList<>();
        productListAdapter = new ProductListAdapter(productsList);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productListAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        yourCollRef = firebaseFirestore.collection("all_products");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               yourCollRef.document("product_1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()){
                            Toast.makeText(MainActivity.this, "Database is already present", Toast.LENGTH_SHORT).show();
                        }else {
                            get_json();
                            Toast.makeText(MainActivity.this, "Data added to FireStore", Toast.LENGTH_SHORT).show();
                        }
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                      Log.d("FireStore Error :",e.toString());
                   }
               });
            }
        });
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productsList.clear();
                productListAdapter.notifyDataSetChanged();
                pullProductsFromDatabase();
            }
        });
    }

    public void get_json(){
        String json;
        try {
            InputStream inputStream = getAssets().open("test.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> keys = jsonObject.keys();
            while(keys.hasNext()){
                String key = keys.next();
                Log.d("Dinu", key);
                JSONObject inside = jsonObject.getJSONObject(key);
                productId = inside.getString("productId");
                customerId = inside.getString("customerId");
                brandCode = inside.getString("brandCode");
                brandName = inside.getString("brandName");
                productCode = inside.getString("productCode");
                productDesc = inside.getString("productDesc");
                mrp = inside.getString("mrp");
                expiry = inside.getString("expiry");

                Map<String, Object> map = new  HashMap<>();
                Log.d("Tanu","map created");
                map.put("productId",productId);
                map.put("customerId",customerId);
                map.put("brandCode",brandCode);
                map.put("brandName",brandName);
                map.put("productCode",productCode);
                map.put("productDesc",productDesc);
                map.put("mrp",mrp);
                map.put("expiry",expiry);

                yourCollRef.document(key).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TANU", "Data added to FireStore");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error"+" "+e, Toast.LENGTH_SHORT).show();
                    }
                });

                Log.d("TANU", productId);
                Toast.makeText(MainActivity.this, "Data added to FireStore", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void pullProductsFromDatabase() {

        firebaseFirestore.collection("all_products").orderBy("expiry").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null){
                    Log.d("FireStore : ", "Error ~~~~"+e.getMessage());
                }
                assert queryDocumentSnapshots != null;
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED || doc.getType() == DocumentChange.Type.REMOVED || doc.getType() == DocumentChange.Type.MODIFIED){
                        Products products = doc.getDocument().toObject(Products.class);
                        productsList.add(products);
                        productListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

}
