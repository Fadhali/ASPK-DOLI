package com.example.aspk1;

public class Items {
    private String itemname;
    private String itemcategory;
    private String itemquantity;
    private String itemcode;
    private String itemexpdate;

    public Items(){}

    public Items(String itemname, String itemcategory, String itemquantity, String itemcode, String itemexpdate) {

    this.itemname = itemname;
    this.itemcategory = itemcategory;
    this.itemquantity = itemquantity;
    this.itemcode = itemcode;
    this.itemexpdate = itemexpdate;
    }

    public String getItemname() {
        return itemname;
    }

    public String getItemcategory() {
        return itemcategory;
    }

    public String getItemquantity() {
        return itemquantity;
    }

    public String getItemcode() {
        return itemcode;
    }

    public String getItemexpdate() {
        return itemexpdate;
    }
}
