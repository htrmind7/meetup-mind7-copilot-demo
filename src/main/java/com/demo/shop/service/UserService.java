package com.demo.shop.service;

import com.demo.shop.model.User;
import com.demo.shop.repository.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        List<User> all = userRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
            User u = all.get(i);
            if (u.getEmail() != null && u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    public int countActive() {
        int count = 0;
        List<User> all = userRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).isActive()) {
                count++;
            }
        }
        return count;
    }

    public void deactivate(long id) {
        User u = userRepository.findById(id);
        if (u != null) {
            u.setActive(false);
            userRepository.save(u);
        }
    }
}
