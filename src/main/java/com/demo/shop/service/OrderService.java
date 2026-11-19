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

    // comme computeTotal(Order), avec en plus une remise de 10 % sur le sous-total si promoCode vaut "PROMO10"
    // ▶ COPILOT : MOINS BIEN : recopie tout le corps de computeTotal(order) au lieu de l'appeler (duplication). Le prompt précis a supprimé la Map, la normalisation et les 4 tests, mais pas la copie.
    public double computeTotal(Order order, String promoCode) {
        double s = subtotal(order);
        double d = 0;
        if (s >= 100) {
            d = s * 0.10;
        }
        double promo = 0;
        if ("PROMO10".equals(promoCode)) {
            promo = s * 0.10;
        }
        double r = s - d - promo;
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
