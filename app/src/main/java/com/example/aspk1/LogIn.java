package com.example.aspk1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogIn extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mSignUpBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mEmail      = findViewById(R.id.SemailAddress);
        mPassword   = findViewById(R.id.Spassword);
        mLoginBtn = findViewById(R.id.loginBtn);
        mSignUpBtn   = findViewById(R.id.goSignUp);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() ,SignUp.class));
                finish();
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required");
                    return;
                }

                //authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LogIn.this, "Logged in", Toast.LENGTH_SHORT).show();
                        checkIfWarehouse(authResult.getUser().getUid());
                        //startActivity(new Intent(getApplicationContext() ,MainActivity.class));
                        //finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LogIn.this, "Error!" , Toast.LENGTH_SHORT).show();

                    }
                });

                //fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    //@Override
                    //public void onComplete(@NonNull Task<AuthResult> task) {
                        //if(task.isSuccessful()){
                            //Toast.makeText(LogIn.this, "Logged in", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(getApplicationContext() ,MainActivity.class));

                        //}else{
                            //Toast.makeText(LogIn.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        //}
                    //}
                //});
            }
        });

    }

    private void checkIfWarehouse(String uid) {
        DocumentReference docref= fStore.collection("users").document(uid);
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d(TAG, "onSuccess: " + documentSnapshot.getData());

                if(documentSnapshot.getString("isWarehouse") !=null ){
                    startActivity(new Intent(getApplicationContext(),warehouseDashboardActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }

            }
        });
    }
}