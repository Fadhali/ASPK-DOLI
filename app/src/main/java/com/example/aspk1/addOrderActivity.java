package com.example.aspk1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addOrderActivity extends AppCompatActivity {
    public static EditText ordercode;
    EditText ordername,orderquantity;
    TextView scanbarcode;
    Button addorderbtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        ordername = findViewById(R.id.pOrdername);
        orderquantity = findViewById(R.id.pOrderqt);
        ordercode = findViewById(R.id.pOrderbarcode);
        scanbarcode = findViewById(R.id.scanAddorderBtn);
        addorderbtn = findViewById(R.id.makeOrderBtn);
        fAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();

        scanbarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanAddorderActivity.class));
            }
        });

        addorderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ordernameValue = ordername.getText().toString().trim();
                String orderquantityValue = orderquantity.getText().toString().trim();
                String ordercodeValue = ordercode.getText().toString().trim();

                if (ordernameValue.isEmpty()) {
                    ordername.setError("It's Empty");
                    return;
                }
                if (ordercodeValue.isEmpty()) {
                    ordercode.setError("It's Empty");
                    return;
                }
                if (orderquantityValue.isEmpty()) {
                    orderquantity.setError("It's Empty");
                    return;
                }

                DocumentReference docref = fStore.collection("order").document(ordercodeValue);
                Map<String, Object> note = new HashMap<>();
                note.put("ordername", ordernameValue);
                note.put("orderquantity", orderquantityValue);
                note.put("ordercode", ordercodeValue);

                docref.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(addOrderActivity.this, "Order made", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(addOrderActivity.this, viewOrderActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addOrderActivity.this, "try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}