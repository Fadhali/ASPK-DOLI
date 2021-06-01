package com.example.aspk1;

public class Orders {
    private String ordername;
    private String orderquantity;
    private String ordercode;

    public Orders(){}

    public Orders(String ordername, String orderquantity, String ordercode) {
        this.ordername = ordername;
        this.orderquantity = orderquantity;
        this.ordercode = ordercode;

    }

    public String getOrdername() {
        return ordername;
    }

    public String getOrderquantity() {
        return orderquantity;
    }

    public String getOrdercode() {
        return ordercode;
    }
}
