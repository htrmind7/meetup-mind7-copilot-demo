package com.demo.shop.service;

import com.demo.shop.model.User;
import com.demo.shop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserServiceTest {

    private UserRepository repository;
    private UserService service;

    @BeforeEach
    void setUp() {
        repository = new UserRepository();
        service = new UserService(repository);
    }

    @Test
    void findByEmail() {
        User u = service.findByEmail("bob@demo.com");
        assertNotNull(u);
        assertEquals(2, u.getId());
    }

    @Test
    void countActive() {
        assertEquals(4, service.countActive());
    }

    // ▶ COPILOT : TEST ajouté.
    @Test
    void findInactiveReturnsUsersWithoutRecentLogin() {
        List<User> inactive = service.findInactive(30);
        List<Long> ids = new ArrayList<Long>();
        for (User u : inactive) {
            ids.add(u.getId());
        }
        assertEquals(Arrays.asList(3L, 4L, 5L, 6L), ids);
    }

    @Test
    void deactivate() {
        service.deactivate(1);
        assertFalse(repository.findById(1).isActive());
    }
}
