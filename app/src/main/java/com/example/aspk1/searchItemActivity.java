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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class searchItemActivity extends AppCompatActivity {
    public static EditText resultsearcheview;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirestoreRecyclerAdapter<Items, searchItemActivity.ItemsViewHolder> itemAdapter;
    ImageButton scantosearch;
    Button searchbtn;
    //Adapter adapter;
    RecyclerView mrecyclerview;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        scantosearch = findViewById(R.id.imageButtonsearch);
        searchbtn = findViewById(R.id.searchbtnn);
        resultsearcheview = findViewById(R.id.searchfield);

        mrecyclerview = findViewById(R.id.recyclerViews);
        mrecyclerview.setHasFixedSize(true);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));



        scantosearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanSearchitemActivity.class));
            }
        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchtext = resultsearcheview.getText().toString();
                firebasesearch(searchtext);
                itemAdapter.startListening();
            }
        });
    }

    public void firebasesearch(String searchtext){
        Query query = fStore.collection("inventory").document(userId).collection("myItems").orderBy("itemcode").startAt(searchtext).endAt(searchtext+"\uf8ff");;
        FirestoreRecyclerOptions<Items> allItems = new FirestoreRecyclerOptions.Builder<Items>()
                .setQuery(query,Items.class)
                .build();
        itemAdapter=new FirestoreRecyclerAdapter<Items, ItemsViewHolder>(allItems) {
            @Override
            protected void onBindViewHolder(@NonNull ItemsViewHolder itemsViewHolder, int i, @NonNull Items items) {
                itemsViewHolder.itemName.setText(items.getItemname());
                itemsViewHolder.itemCategory.setText(items.getItemcategory());
                itemsViewHolder.itemQuantity.setText(items.getItemquantity());
                itemsViewHolder.itemCode.setText(items.getItemcode());
                itemsViewHolder.itemExpdate.setText(items.getItemexpdate());

            }

            @NonNull
            @Override
            public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);
                return new ItemsViewHolder(view);
            }
        };
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
    protected void onStop() {
        super.onStop();
        if (itemAdapter != null){
            itemAdapter.stopListening();
        }
    }
}