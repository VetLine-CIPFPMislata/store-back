package org.example.storeback.domain.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void testClientCreation() {
        Long id = 1L;
        String name = "Juan Pérez";
        String email = "juan@example.com";
        String password = "password123";
        String phone = "+34600111222";
        Long cartId = 10L;
        Role role = Role.USER;

        Client client = new Client(id, name, email, password, phone, cartId, role);

        assertNotNull(client);
        assertEquals(id, client.getId());
        assertEquals(name, client.getName());
        assertEquals(email, client.getEmail());
        assertEquals(password, client.getPassword());
        assertEquals(phone, client.getPhone());
        assertEquals(cartId, client.getCartId());
        assertEquals(role, client.getRole());
    }

    @Test
    void testClientWithAdminRole() {
        Client client = new Client(1L, "Admin", "admin@store.com", "admin123", "+34600222333", 1L, Role.ADMIN);

        assertEquals(Role.ADMIN, client.getRole());
    }

    @Test
    void testClientGetters() {
        Client client = new Client(5L, "Test User", "test@example.com", "testpass", "+34600666777", 15L, Role.USER);


        assertEquals(5L, client.getId());
        assertEquals("Test User", client.getName());
        assertEquals("test@example.com", client.getEmail());
        assertEquals("testpass", client.getPassword());
        assertEquals("+34600666777", client.getPhone());
        assertEquals(15L, client.getCartId());
        assertEquals(Role.USER, client.getRole());
    }

}

