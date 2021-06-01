package com.example.aspk1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class viewShippingActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirestoreRecyclerAdapter<Shipping, viewShippingActivity.ShippingViewHolder> shippingAdapter;
    RecyclerView srecyclerview;
    TextView countshipping;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shipping);
        fAuth=FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        countshipping = findViewById(R.id.totalnoshipping);

        fStore.collection("shipping")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            count = task.getResult().size();
                            countshipping.setText(Integer.toString(count));
                        }else{
                            countshipping.setText("0");
                        }
                    }
                });

        Query query = fStore.collection("shipping").orderBy("shippingname", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Shipping> allShippings = new FirestoreRecyclerOptions.Builder<Shipping>()
                .setQuery(query,Shipping.class)
                .build();

        shippingAdapter = new FirestoreRecyclerAdapter<Shipping, ShippingViewHolder>(allShippings) {
            @Override
            protected void onBindViewHolder(@NonNull ShippingViewHolder shippingViewHolder, int i, @NonNull Shipping shipping) {
                shippingViewHolder.shippingName.setText(shipping.getShippingname());
                shippingViewHolder.shippingCode.setText(shipping.getShippingcode());
                shippingViewHolder.shippingQuantity.setText(shipping.getShippingquantity());
                shippingViewHolder.shippingPrice.setText(shipping.getShippingprice());

            }

            @NonNull
            @Override
            public ShippingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shippinglist_layout,parent,false);
                return new ShippingViewHolder(view);
            }
        };

        srecyclerview = findViewById(R.id.recyclerViewsShippings);
        srecyclerview.setHasFixedSize(true);
        srecyclerview.setLayoutManager(new LinearLayoutManager(this));
        srecyclerview.setAdapter(shippingAdapter);
    }

    public class ShippingViewHolder extends RecyclerView.ViewHolder{
        TextView shippingName,shippingQuantity,shippingCode,shippingPrice;
        View view;
        public ShippingViewHolder(@NonNull View itemView) {
            super(itemView);
            shippingName = itemView.findViewById(R.id.viewshippingname);
            shippingQuantity = itemView.findViewById(R.id.viewshippingqt);
            shippingCode = itemView.findViewById(R.id.viewshippingbarcode);
            shippingPrice = itemView.findViewById(R.id.viewshippingprice);
            view = itemView;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        shippingAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (shippingAdapter != null) {
            shippingAdapter.stopListening();
        }
    }
}