package com.demo.shop.service;

import com.demo.shop.model.User;
import com.demo.shop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    void deactivate() {
        service.deactivate(1);
        assertFalse(repository.findById(1).isActive());
    }
}
