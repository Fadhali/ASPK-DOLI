package com.example.aspk1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class confirmOrderActivity extends AppCompatActivity {
    TextView confirOcode, confirOname, confirOquantity;
    Button confirObtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        confirOname = findViewById(R.id.oConfirname);
        confirOcode = findViewById(R.id.oConfircode);
        confirOquantity = findViewById(R.id.oConfirquantity);
        confirObtn = findViewById(R.id.oConfirorderBtn);

        Intent data = getIntent();

        confirOname.setText(data.getStringExtra("orderName"));
        confirOcode.setText(data.getStringExtra("orderCode"));
        confirOquantity.setText(data.getStringExtra("orderQuantity"));

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        confirObtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirOrdernameVal= confirOname.getText().toString().trim();
                String confirOrdercodeVal= confirOcode.getText().toString().trim();
                String confirOrderqtVal= confirOquantity.getText().toString().trim();

                DocumentReference docref = fStore.collection("confirmedOrder").document(confirOrdercodeVal);
                Map<String, Object> note = new HashMap<>();
                note.put("ordername", confirOrdernameVal);
                note.put("orderquantity", confirOrderqtVal);
                note.put("ordercode", confirOrdercodeVal);

                docref.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(confirmOrderActivity.this, "Order Confirmed", Toast.LENGTH_SHORT).show();
                        DocumentReference docref = fStore.collection("order").document(confirOrdercodeVal);
                        docref.delete();
                        startActivity(new Intent(confirmOrderActivity.this, viewOrderActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(confirmOrderActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}