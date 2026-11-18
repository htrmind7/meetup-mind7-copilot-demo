package com.demo.shop.repository;

import com.demo.shop.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserRepository {

    private static final long DAY_MS = 24L * 60 * 60 * 1000;

    private final List<User> users = new ArrayList<User>();

    public UserRepository() {
        long now = System.currentTimeMillis();
        users.add(new User(1, "Alice Martin", "alice@demo.com", new Date(now - 1 * 60 * 60 * 1000L), true));
        users.add(new User(2, "Bob Durand", "bob@demo.com", new Date(now - 10 * DAY_MS), true));
        users.add(new User(3, "Claire Petit", "claire@demo.com", new Date(now - 45 * DAY_MS), true));
        users.add(new User(4, "David Leroy", "david@demo.com", new Date(now - 120 * DAY_MS), false));
        users.add(new User(5, "Emma Roux", "emma@demo.com", null, true));
        users.add(new User(6, "Farid Benali", "farid@demo.com", null, false));
    }

    public List<User> findAll() {
        return users;
    }

    public User findById(long id) {
        for (User u : users) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public void save(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                return;
            }
        }
        users.add(user);
    }
}
