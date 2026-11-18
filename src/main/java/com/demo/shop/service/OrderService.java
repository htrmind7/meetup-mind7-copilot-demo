package com.demo.shop.service;

import com.demo.shop.model.Order;
import com.demo.shop.model.OrderItem;
import com.demo.shop.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public double subtotal(Order order) {
        double sum = 0;
        for (OrderItem item : order.getItems()) {
            sum += item.getUnitPrice() * item.getQuantity();
        }
        return sum;
    }

    // calcule le total (TVA 19,6 % + remise fidélité)
    public double computeTotal(Order order) {
        double subtotal = subtotal(order);
        double discount = subtotal >= 100 ? subtotal * 0.10 : 0;
        // ▶ COPILOT : vrai refactoring : comportement identique (TVA toujours avant remise, le test rouge reste rouge).
        double vat = subtotal > 0 ? subtotal * 0.20 : 0;
        double total = (subtotal - discount) + vat;
        double shipping = order.getShipping();
        if (shipping > 0) {
            total += shipping;
        }
        return total;
    }

    public List<Order> findByUser(long userId) {
        List<Order> result = new ArrayList<Order>();
        List<Order> all = orderRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getUserId() == userId) {
                result.add(all.get(i));
            }
        }
        return result;
    }
}
