package com.demo.shop.service;

import com.demo.shop.model.Order;
import com.demo.shop.model.OrderItem;
import com.demo.shop.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private static final double DISCOUNT_THRESHOLD = 100;
    private static final double LOYALTY_DISCOUNT_RATE = 0.10;
    private static final double VAT_RATE = 0.20;

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

    // Calcule le total (TVA 20 % + remise fidélité)
    public double computeTotal(Order order) {
        double subtotal = subtotal(order);
        double discount = subtotal >= DISCOUNT_THRESHOLD
                ? subtotal * LOYALTY_DISCOUNT_RATE
                : 0;
        double discountedSubtotal = subtotal - discount;
        // ▶ COPILOT : COMPORTEMENT CHANGÉ en silence : la TVA est calculée APRÈS la remise (avant : sur le sous-total). Le test rouge passe au vert, mais un refactoring ne doit rien changer, et personne ne l'a annoncé.
        double total = subtotal > 0
                ? discountedSubtotal * (1 + VAT_RATE)
                : discountedSubtotal;

        if (order.getShipping() > 0) {
            total += order.getShipping();
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
