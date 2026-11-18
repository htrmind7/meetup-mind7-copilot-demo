package com.demo.shop.service;

import com.demo.shop.model.Order;
import com.demo.shop.model.OrderItem;
import com.demo.shop.repository.OrderRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    // code promo -> remise en pourcentage sur le sous-total
    private static final Map<String, Double> PROMO_CODES;

    static {
        Map<String, Double> codes = new HashMap<String, Double>();
        codes.put("BIENVENUE10", 0.10);
        PROMO_CODES = Collections.unmodifiableMap(codes);
    }

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
        return computeTotal(order, null);
    }

    // calcule le total avec un code promo optionnel (ignoré s'il est inconnu)
    public double computeTotal(Order order, String promoCode) {
        double s = 0;
        List<OrderItem> items = order.getItems();
        for (int i = 0; i < items.size(); i++) {
          OrderItem it = items.get(i);
          s = s + it.getUnitPrice() * it.getQuantity();
        }
        double d = 0;
        if (s >= 100) {
            d = s * 0.10;
        }
        Double promo = promoCode != null ? PROMO_CODES.get(promoCode.trim().toUpperCase()) : null;
        if (promo != null) {
            d = d + s * promo;
        }
        double r = s - d;
        double t = 0;
        if (s > 0) {
          t = s * 0.20;
        }
        double total = r + t;
        double p = order.getShipping();
        if (p > 0) {
            total = total + p;
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
