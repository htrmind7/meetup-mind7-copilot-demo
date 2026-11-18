package com.demo.shop.service;

import com.demo.shop.model.User;
import com.demo.shop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void deactivate() {
        service.deactivate(1);
        assertFalse(repository.findById(1).isActive());
    }

    // ▶ COPILOT : TEST ajouté.
    @Test
    void findInactiveUsers() {
        assertEquals(2, service.findInactiveUsers().size());
        assertTrue(service.findInactiveUsers().stream().allMatch(user -> !user.isActive()));
    }

    // ▶ COPILOT : TEST ajouté.
    @Test
    void activate() {
        service.activate(4);
        assertTrue(repository.findById(4).isActive());
        assertEquals(1, service.findInactiveUsers().size());
    }
}
