package com.example.aspk1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class addItemActivity extends AppCompatActivity {
    String TAG = "TAG";
    Intent data;
    EditText itemname, itemcategory, itemquantity, itemexpdate;
    TextView scanbarcode;
    Button additembtn;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;
    public static EditText itemcode;
    private int mDate, mMonth, mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
// pop up calendar
        itemexpdate = findViewById(R.id.eExpiryDate);

        itemexpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar Cal = Calendar.getInstance();
                mDate = Cal.get(Calendar.DATE);
                mMonth = Cal.get(Calendar.MONTH);
                mYear = Cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(addItemActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        itemexpdate.setText(date+"/"+month+"/"+year);
                    }
                }, mYear, mMonth, mDate   );
                datePickerDialog.show();

            }
        });
 //
        itemname = findViewById(R.id.eItemName);
        itemcategory = findViewById(R.id.eItemCategory);
        itemquantity = findViewById(R.id.eItemQt);
        itemcode = findViewById(R.id.eItemCode);
        scanbarcode = findViewById(R.id.goScanItem);
        additembtn = findViewById(R.id.addItemBtn);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        data = getIntent();

        scanbarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanAdditemActivity.class));
            }
        });


        additembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemnameValue = itemname.getText().toString().trim();
                String itemcategoryValue = itemcategory.getText().toString().trim();
                String itemquantityValue = itemquantity.getText().toString().trim();
                String itemcodeValue = itemcode.getText().toString().trim();
                String itemexpValue = itemexpdate.getText().toString().trim();

                if (itemnameValue.isEmpty()) {
                    itemcode.setError("It's Empty");
                    return;
                }
                if (itemcategoryValue.isEmpty()) {
                    itemcode.setError("It's Empty");
                    return;
                }
                if (itemquantityValue.isEmpty()) {
                    itemcode.setError("It's Empty");
                    return;
                }
                if (itemcodeValue.isEmpty()) {
                    itemcode.setError("It's Empty");
                    return;
                }
                if (itemexpValue.isEmpty()) {
                    itemcode.setError("It's Empty");
                    return;
                }

                userID = fAuth.getCurrentUser().getUid();
                DocumentReference docref = fStore.collection("inventory").document(userID).collection("myItems").document(itemcodeValue);
                Map<String, Object> note = new HashMap<>();
                note.put("itemname", itemnameValue);
                note.put("itemcategory", itemcategoryValue);
                note.put("itemquantity", itemquantityValue);
                note.put("itemcode", itemcodeValue);
                note.put("itemexpdate", itemexpValue);

                docref.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(addItemActivity.this, "Item added", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addItemActivity.this, "try again", Toast.LENGTH_SHORT).show();
                    }
                });




            }

        });


}
}