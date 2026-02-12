package org.example.storeback.Spring;

import org.example.storeback.domain.repository.*;
import org.example.storeback.domain.service.*;
import org.example.storeback.domain.service.impl.*;
import org.example.storeback.microservice.BankPaymentService;
import org.example.storeback.microservice.impl.BankPaymentServiceImpl;
import org.example.storeback.persistence.dao.*;
import org.example.storeback.persistence.dao.jpa.impl.*;
import org.example.storeback.persistence.repository.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringConfig {

    @Bean
    public PasswordEncryptionService passwordEncryptionService() {
        return new BCryptPasswordEncryptionService();
    }

    @Bean
    public OrderJpaDao orderJpaDao() {
        return new OrderJpaDaoImpl();
    }

    @Bean
    public OrderItemJpaDao orderItemJpaDao() {
        return new OrderItemJpaDaoImpl();
    }

    @Bean
    public CartJpaDao cartJpaDao() {
        return new CartJpaDaoImpl();
    }

    @Bean
    public CartItemJpaDao cartItemJpaDao() {
        return new CartItemJpaDaoImpl();
    }

    @Bean
    public ProductJpaDao productJpaDao() {
        return new ProductJpaDaoImpl();
    }

    @Bean
    public CategoryJpaDao categoryJpaDao() {
        return new CategoryJpaDaoImpl();
    }

    @Bean
    public ClientJpaDao clientJpaDao() {
        return new ClientJpaDaoImpl();
    }

    @Bean
    public SessionJpaDao sessionJpaDao() {
        return new SessionJpaDaoImpl();
    }

    @Bean
    public OrderRepository orderRepository(OrderJpaDao orderJpaDao) {
        return new OrderRepositoryImpl(orderJpaDao);
    }

    @Bean
    public OrderItemRepository orderItemRepository(OrderItemJpaDao orderItemJpaDao) {
        return new OrderItemRepositoryImpl(orderItemJpaDao);
    }

    @Bean
    public CartRepository cartRepository(CartJpaDao cartJpaDao) {
        return new CartRepositoryImpl(cartJpaDao);
    }

    @Bean
    public CartItemRepository cartItemRepository(CartItemJpaDao cartItemJpaDao) {
        return new CartItemRepositoryImpl(cartItemJpaDao);
    }

    @Bean
    public ProductRepository productRepository(ProductJpaDao productJpaDao) {
        return new ProductRepositoryImpl(productJpaDao);
    }

    @Bean
    public CategoryRepository categoryRepository(CategoryJpaDao categoryJpaDao) {
        return new CategoryRepositoryImpl(categoryJpaDao);
    }

    @Bean
    public ClientRepository clientRepository(ClientJpaDao clientJpaDao) {
        return new ClientRepositoryImpl(clientJpaDao);
    }

    @Bean
    public SessionRepository sessionRepository(SessionJpaDao sessionJpaDao) {
        return new SessionRepositoryImpl(sessionJpaDao);
    }

    @Bean
    public OrderService orderService(OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ClientRepository clientRepository) {
        return new OrderServiceImpl(orderRepository, orderItemRepository, cartRepository,
                cartItemRepository, clientRepository);
    }

    @Bean
    public CartService cartService(CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ClientRepository clientRepository,
            ProductRepository productRepository) {
        return new CartServiceImpl(cartRepository, cartItemRepository,
                clientRepository, productRepository);
    }

    @Bean
    public ProductService productService(ProductRepository productRepository) {
        return new ProductServiceImpl(productRepository);
    }

    @Bean
    public CategoryService categoryService(CategoryRepository categoryRepository,
            ProductRepository productRepository) {
        return new CategoryServiceImpl(categoryRepository, productRepository);
    }

    @Bean
    public ClientService clientService(ClientRepository clientRepository,
            PasswordEncryptionService passwordEncryptionService) {
        return new ClientServiceImpl(clientRepository, passwordEncryptionService);
    }

    @Bean
    public AuthService authService(SessionRepository sessionRepository,
            ClientRepository clientRepository) {
        return new AuthServiceImpl(sessionRepository, clientRepository);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public BankPaymentService bankPaymentService(
            RestTemplate restTemplate) {
        return new BankPaymentServiceImpl(restTemplate);
    }

    @Bean
    public PaymentService paymentService(
            BankPaymentService bankPaymentService) {
        return new PaymentServiceImpl(bankPaymentService);
    }
}
