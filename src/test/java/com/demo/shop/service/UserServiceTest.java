package com.demo.shop.service;

import com.demo.shop.model.User;
import com.demo.shop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    void findInactiveReturnsUsersWithoutRecentLoginOrNeverLoggedIn() {
        List<User> inactive = service.findInactive(30);
        assertEquals(4, inactive.size());
        assertEquals(3, inactive.get(0).getId());
        assertEquals(4, inactive.get(1).getId());
        assertEquals(5, inactive.get(2).getId());
        assertEquals(6, inactive.get(3).getId());
    }

    // ▶ COPILOT : TEST ajouté.
    @Test
    void deactivateInactiveOnlyDeactivatesActiveUsers() {
        int count = service.deactivateInactive(30);
        assertEquals(2, count);
        assertFalse(repository.findById(3).isActive());
        assertFalse(repository.findById(5).isActive());
        assertTrue(repository.findById(2).isActive());
        assertEquals(2, service.countActive());
    }
}
