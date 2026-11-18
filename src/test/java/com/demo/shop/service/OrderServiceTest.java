package com.demo.shop.service;

import com.demo.shop.model.Order;
import com.demo.shop.model.OrderItem;
import com.demo.shop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderServiceTest {

    private OrderService service;

    @BeforeEach
    void setUp() {
        service = new OrderService(new OrderRepository());
    }

    private Order order(double shipping, OrderItem... items) {
        List<OrderItem> list = new ArrayList<OrderItem>();
        for (OrderItem item : items) {
            list.add(item);
        }
        return new Order(1, 1, list, shipping);
    }

    @Test
    void subtotalSumsItems() {
        Order o = order(0, new OrderItem("A", 10.0, 2), new OrderItem("B", 5.0, 3));
        assertEquals(35.0, service.subtotal(o), 0.001);
    }

    @Test
    void noDiscountBelow100() {
        Order o = order(0, new OrderItem("A", 25.0, 2));
        // 50 + TVA 10 = 60
        assertEquals(60.0, service.computeTotal(o), 0.001);
    }

    @Test
    void shippingIsNotTaxed() {
        Order o = order(10.0, new OrderItem("A", 25.0, 2));
        // 50 + TVA 10 + port 10 = 70
        assertEquals(70.0, service.computeTotal(o), 0.001);
    }

    @Test
    void shippingIsFreeAtThreshold() {
        Order paid = order(10.0, new OrderItem("A", 100.0, 1));
        Order free = order(0.0, new OrderItem("A", 100.0, 1));
        assertEquals(service.computeTotal(free), service.computeTotal(paid), 0.001);
    }

    @Test
    void shippingIsChargedJustBelowThreshold() {
        Order paid = order(10.0, new OrderItem("A", 99.99, 1));
        Order free = order(0.0, new OrderItem("A", 99.99, 1));
        assertEquals(10.0, service.computeTotal(paid) - service.computeTotal(free), 0.001);
    }

    @Test
    void totalWithDiscountAndVat() {
        Order o = order(10.0, new OrderItem("A", 100.0, 1), new OrderItem("B", 50.0, 2));
        // 200 - 10 % = 180, TVA 20 % = 36, soit 216 ; livraison offerte (>= 100)
        assertEquals(216.0, service.computeTotal(o), 0.001);
    }
}
