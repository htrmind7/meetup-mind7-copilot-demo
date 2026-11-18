package com.demo.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private long id;
    private long userId;
    private List<OrderItem> items;
    private double shipping;

    public Order(long id, long userId, List<OrderItem> items, double shipping) {
        this.id = id;
        this.userId = userId;
        this.items = items != null ? items : new ArrayList<OrderItem>();
        this.shipping = shipping;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }
}
