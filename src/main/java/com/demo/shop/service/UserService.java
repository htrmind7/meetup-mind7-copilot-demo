package com.demo.shop.service;

import com.demo.shop.model.User;
import com.demo.shop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final long DAY_MS = 24L * 60 * 60 * 1000;

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

    /**
     * Retourne les utilisateurs sans connexion depuis plus de {@code days} jours.
     * Un utilisateur qui ne s'est jamais connecté (lastLogin null) est considéré comme inactif.
     */
    public List<User> findInactive(int days) {
        long threshold = System.currentTimeMillis() - days * DAY_MS;
        List<User> result = new ArrayList<User>();
        List<User> all = userRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
            User u = all.get(i);
            if (u.getLastLogin() == null || u.getLastLogin().getTime() < threshold) {
                result.add(u);
            }
        }
        return result;
    }

    /**
     * Désactive les utilisateurs actifs inactifs depuis plus de {@code days} jours.
     * @return le nombre d'utilisateurs désactivés
     */
    public int deactivateInactive(int days) {
        int count = 0;
        List<User> inactive = findInactive(days);
        for (int i = 0; i < inactive.size(); i++) {
            User u = inactive.get(i);
            if (u.isActive()) {
                u.setActive(false);
                userRepository.save(u);
                count++;
            }
        }
        return count;
    }
}
