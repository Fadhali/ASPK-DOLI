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

public class addShippingActivity extends AppCompatActivity {
    TextView shipCode, shipName, shipQuantity;
    EditText shipPrice;
    Button shipBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shipping);

        shipName = findViewById(R.id.oConfirname);
        shipCode = findViewById(R.id.oConfircode);
        shipQuantity = findViewById(R.id.oConfirquantity);
        shipPrice = findViewById(R.id.oShipPrice);
        shipBtn = findViewById(R.id.oConfirorderBtn);

        Intent data = getIntent();

        shipName.setText(data.getStringExtra("orderName"));
        shipCode.setText(data.getStringExtra("orderCode"));
        shipQuantity.setText(data.getStringExtra("orderQuantity"));

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        shipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shipnameVal = shipName.getText().toString().trim();
                String shipcodeVal = shipCode.getText().toString().trim();
                String shipquantityVal = shipQuantity.getText().toString().trim();
                String shippriceVal = shipPrice.getText().toString().trim();

                if (shippriceVal.isEmpty()) {
                    shipPrice.setError("It's Empty");
                    return;
                }
                DocumentReference docref = fStore.collection("shipping").document(shipcodeVal);
                Map<String, Object> note = new HashMap<>();
                note.put("shippingname", shipnameVal);
                note.put("shippingquantity", shipquantityVal);
                note.put("shippingcode", shipcodeVal);
                note.put("shippingprice", shippriceVal);

                docref.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(addShippingActivity.this, "Shipping Confirmed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(addShippingActivity.this, warehouseDashboardActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addShippingActivity.this, "Try again", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }
}