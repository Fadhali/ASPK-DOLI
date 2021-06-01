package com.example.aspk1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class deleteItemActivity extends AppCompatActivity {
    public static TextView resultdeleteview;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button scantodelete, deletebtn;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);
        scantodelete = findViewById(R.id.buttonscandelete);
        deletebtn= findViewById(R.id.deleteItemToTheDatabasebtn);
        resultdeleteview = findViewById(R.id.barcodedelete);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        scantodelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanDeleteitemActivity.class));
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletefrmdatabase();
            }
        });

    }

    public void deletefrmdatabase(){
        String deletecodevalue= resultdeleteview.getText().toString();
        if (!TextUtils.isEmpty(deletecodevalue)){
            DocumentReference docref= fStore.collection("inventory").document(userId).collection("myItems").document(deletecodevalue);
            docref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(deleteItemActivity.this,"Item deleted",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(deleteItemActivity.this,"error!",Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(deleteItemActivity.this,"Please scan Barcode",Toast.LENGTH_SHORT).show();
        }
    }
}