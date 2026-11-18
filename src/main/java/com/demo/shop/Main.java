package com.demo.shop;

import com.demo.shop.model.Order;
import com.demo.shop.model.OrderItem;
import com.demo.shop.repository.OrderRepository;
import com.demo.shop.repository.UserRepository;
import com.demo.shop.service.OrderService;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        System.out.println("Users: " + userRepository.findAll().size());

        List<OrderItem> items = new ArrayList<OrderItem>();
        items.add(new OrderItem("Keyboard", 80.0, 1));
        items.add(new OrderItem("Mouse", 60.0, 2));
        Order order = new Order(1, 1, items, 10.0);
        order.setPromoCode("PROMO10");

        OrderService orderService = new OrderService(new OrderRepository());
        System.out.println("Order total: " + orderService.computeTotal(order));
    }
}
