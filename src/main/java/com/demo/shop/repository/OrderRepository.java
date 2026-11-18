package com.demo.shop.repository;

import com.demo.shop.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private final List<Order> orders = new ArrayList<Order>();

    public List<Order> findAll() {
        return orders;
    }

    public Order findById(long id) {
        for (Order o : orders) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }

    public void save(Order order) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == order.getId()) {
                orders.set(i, order);
                return;
            }
        }
        orders.add(order);
    }
}
