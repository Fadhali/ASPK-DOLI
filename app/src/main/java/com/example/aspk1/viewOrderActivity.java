package com.example.aspk1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

public class viewOrderActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirestoreRecyclerAdapter<Orders, viewOrderActivity.OrdersViewHolder> orderAdapter;
    RecyclerView orecyclerview;
    TextView countorder;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        countorder = findViewById(R.id.totalnoorder);

        fStore.collection("order")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            count = task.getResult().size();
                            countorder.setText(Integer.toString(count));
                        }else{
                            countorder.setText("0");
                        }
                    }
                });


        Query query = fStore.collection("order").orderBy("ordername", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Orders> allOrders = new FirestoreRecyclerOptions.Builder<Orders>()
                .setQuery(query,Orders.class)
                .build();

        orderAdapter = new FirestoreRecyclerAdapter<Orders, OrdersViewHolder>(allOrders) {
            @Override
            protected void onBindViewHolder(@NonNull OrdersViewHolder ordersViewHolder, int i, @NonNull Orders orders) {
                ordersViewHolder.orderName.setText(orders.getOrdername());
                ordersViewHolder.orderQuantity.setText(orders.getOrderquantity());
                ordersViewHolder.orderCode.setText(orders.getOrdercode());
                ordersViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), confirmOrderActivity.class);
                        i.putExtra("orderName",orders.getOrdername());
                        i.putExtra("orderQuantity",orders.getOrderquantity());
                        i.putExtra("orderCode",orders.getOrdercode());

                        v.getContext().startActivity(i);
                        finish();
                    }
                });
            }

            @NonNull
            @Override
            public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlist_layout,parent,false);
                return new OrdersViewHolder(view);
            }
        };

        orecyclerview = findViewById(R.id.recyclerViewsOrders);
        orecyclerview.setHasFixedSize(true);
        orecyclerview.setLayoutManager(new LinearLayoutManager(this));
        orecyclerview.setAdapter(orderAdapter);

    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder{
        TextView orderName,orderQuantity,orderCode;
        View view;
        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            orderName = itemView.findViewById(R.id.viewordername);
            orderQuantity = itemView.findViewById(R.id.vieworderqt);
            orderCode = itemView.findViewById(R.id.vieworderbarcode);
            view = itemView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.newOrderBtn:{
                startActivity(new Intent(viewOrderActivity.this,addOrderActivity.class));
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        orderAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (orderAdapter != null) {
            orderAdapter.stopListening();
        }
    }
}