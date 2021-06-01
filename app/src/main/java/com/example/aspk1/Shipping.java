package com.example.aspk1;

public class Shipping {
    private String shippingname;
    private String shippingquantity;
    private String shippingcode;
    private String shippingprice;

    public Shipping(){}

    public Shipping(String shippingname, String shippingquantity, String shippingcode, String shippingprice){
        this.shippingcode = shippingcode;
        this.shippingname = shippingname;
        this.shippingquantity = shippingquantity;
        this.shippingprice = shippingprice;
    }

    public String getShippingname() {
        return shippingname;
    }

    public String getShippingquantity() {
        return shippingquantity;
    }

    public String getShippingcode() {
        return shippingcode;
    }

    public String getShippingprice() {
        return shippingprice;
    }
}
