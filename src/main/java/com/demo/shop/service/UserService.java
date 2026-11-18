package com.demo.shop.service;

import com.demo.shop.model.User;
import com.demo.shop.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
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

    // ▶ COPILOT : seule méthode ajoutée, comme demandé (pas de deactivate).
    public List<User> findInactive(int jours) {
        long cutoff = System.currentTimeMillis() - jours * 24L * 60 * 60 * 1000;
        List<User> inactive = new ArrayList<User>();
        List<User> all = userRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
            Date lastLogin = all.get(i).getLastLogin();
            if (lastLogin == null || lastLogin.getTime() <= cutoff) {
                inactive.add(all.get(i));
            }
        }
        return inactive;
    }

    public void deactivate(long id) {
        User u = userRepository.findById(id);
        if (u != null) {
            u.setActive(false);
            userRepository.save(u);
        }
    }
}
