package com.example.aspk1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder>{
    List<String> itemname;
    List<String> itemcategory;
    List<String> itemquantity;
    List<String> itemcode;
    List<String> itemexpdate;


    public  adapter(List<String> itemname, List<String> itemcategory, List<String> itemquantity, List<String> itemcode, List<String> itemexpdate){
        this.itemname =itemname;
        this.itemcategory = itemcategory;
        this.itemquantity = itemquantity;
        this.itemcode = itemcode;
        this.itemexpdate = itemexpdate;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemName.setText(itemname.get(position));
        holder.itemCategory.setText(itemcategory.get(position));
        holder.itemQuantity.setText(itemquantity.get(position));
        holder.itemCode.setText(itemcode.get(position));
        holder.itemExpdate.setText(itemexpdate.get(position));

    }

    @Override
    public int getItemCount() {
        return itemname.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemCategory, itemQuantity, itemCode, itemExpdate;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.viewitemname);
            itemCategory = itemView.findViewById(R.id.viewitemcategory);
            itemQuantity = itemView.findViewById(R.id.viewitemqt);
            itemCode = itemView.findViewById(R.id.viewitembarcode);
            itemExpdate = itemView.findViewById(R.id.viewitemexpdate);
            view = itemView;


        }
    }
}
