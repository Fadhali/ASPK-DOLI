package com.example.aspk1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class viewInventoryActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirestoreRecyclerAdapter<Items,ItemsViewHolder> itemAdapter;
    RecyclerView mrecyclerview;
    //TextView totalnoofitem,totalnoofsum;
    //int counttotalnoofitem =0;
    String userId;
    //adapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inventory);
        //totalnoofitem =findViewById(R.id.totalnoitem);
       // totalnoofsum = findViewById(R.id.totalsum);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();


        //List<String> itemname = new ArrayList<>();
        //List<String> itemcategory = new ArrayList<>();
        //List<String> itemquantity = new ArrayList<>();
        //List<String> itemcode = new ArrayList<>();
        //List<String> itemexpdate = new ArrayList<>();

        //itemname.add("testitemnamme");
        //itemcategory.add("test");
        //itemquantity.add("5");
        //itemcode.add("Test");
        //itemexpdate.add("test");




        Query query = fStore.collection("inventory").document(userId).collection("myItems").orderBy("itemname", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Items> allItems = new FirestoreRecyclerOptions.Builder<Items>()
                .setQuery(query,Items.class)
                .build();

        itemAdapter = new FirestoreRecyclerAdapter<Items, ItemsViewHolder>(allItems) {
            @Override
            protected void onBindViewHolder(@NonNull ItemsViewHolder itemsViewHolder, int i, @NonNull Items items) {
                itemsViewHolder.itemName.setText(items.getItemname());
                itemsViewHolder.itemCategory.setText(items.getItemcategory());
                itemsViewHolder.itemQuantity.setText(items.getItemquantity());
                itemsViewHolder.itemCode.setText(items.getItemcode());
                itemsViewHolder.itemExpdate.setText(items.getItemexpdate());
                //itemsViewHolder.view.setOnClickListener(v -> {
                //});

            }

            @NonNull
            @Override
            public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);
                return new ItemsViewHolder(view);
            }
        };



        //Adapter = new adapter(itemname,itemcategory,itemquantity,itemcode,itemexpdate);
        mrecyclerview = findViewById(R.id.recyclerViews);
        //LinearLayoutManager manager = new LinearLayoutManager(this);
        //mrecyclerview.setLayoutManager(manager);
        mrecyclerview.setHasFixedSize(true);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        //mrecyclerview.setAdapter(Adapter);
        mrecyclerview.setAdapter(itemAdapter);
    }


    public class ItemsViewHolder extends RecyclerView.ViewHolder{
        TextView itemName, itemCategory, itemQuantity, itemCode, itemExpdate;
        View view;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.viewitemname);
            itemCategory = itemView.findViewById(R.id.viewitemcategory);
            itemQuantity = itemView.findViewById(R.id.viewitemqt);
            itemCode = itemView.findViewById(R.id.viewitembarcode);
            itemExpdate = itemView.findViewById(R.id.viewitemexpdate);
            view = itemView;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        itemAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (itemAdapter != null){
            itemAdapter.stopListening();
        }
    }
}