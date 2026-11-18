package com.demo.shop.service;

import com.demo.shop.model.Order;
import com.demo.shop.model.OrderItem;
import com.demo.shop.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private static final String PROMO_CODE = "PROMO10";
    private static final double PROMO_DISCOUNT = 0.10;

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

    // calcule le total (TVA 20 % + remise fidélité et code promo)
    public double computeTotal(Order order) {
        double discountedSubtotal = subtotal(order);
        if (discountedSubtotal >= 100) {
            discountedSubtotal *= 0.90;
        }

        String promoCode = order.getPromoCode();
        if (promoCode != null && !promoCode.trim().isEmpty()) {
            if (!PROMO_CODE.equalsIgnoreCase(promoCode.trim())) {
                throw new IllegalArgumentException("Code promo invalide : " + promoCode);
            }
            discountedSubtotal *= 1 - PROMO_DISCOUNT;
        }

        double total = discountedSubtotal > 0 ? discountedSubtotal * 1.20 : discountedSubtotal;
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
