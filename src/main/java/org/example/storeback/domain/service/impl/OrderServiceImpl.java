package org.example.storeback.domain.service.impl;

import org.example.storeback.controller.webmodel.request.CheckoutRequest;
import org.example.storeback.domain.exception.BusinessException;
import org.example.storeback.domain.mappers.OrderMapper;
import org.example.storeback.domain.models.OrderState;
import org.example.storeback.domain.repository.CartItemRepository;
import org.example.storeback.domain.repository.CartRepository;
import org.example.storeback.domain.repository.ClientRepository;
import org.example.storeback.domain.repository.OrderItemRepository;
import org.example.storeback.domain.repository.OrderRepository;
import org.example.storeback.domain.repository.entity.CartEntity;
import org.example.storeback.domain.repository.entity.CartItemEntity;
import org.example.storeback.domain.repository.entity.ClientEntity;
import org.example.storeback.domain.repository.entity.OrderEntity;
import org.example.storeback.domain.repository.entity.OrderItemEntity;
import org.example.storeback.domain.service.OrderService;
import org.example.storeback.domain.service.dto.OrderDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ClientRepository clientRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
            CartRepository cartRepository, CartItemRepository cartItemRepository,
            ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<OrderDto> findAll() {
        return orderRepository.findAll().stream()
                .map(OrderMapper.getInstance()::fromOrderEntityToOrder)
                .map(OrderMapper.getInstance()::fromOrderToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto findById(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Order not found with id: " + id));
        return OrderMapper.getInstance().fromOrderToOrderDto(
                OrderMapper.getInstance().fromOrderEntityToOrder(order));
    }

    @Override
    public List<OrderDto> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderMapper.getInstance()::fromOrderEntityToOrder)
                .map(OrderMapper.getInstance()::fromOrderToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto create(OrderDto orderDto) {
        OrderEntity order = new OrderEntity(
                null,
                orderDto.totalProducts(),
                orderDto.totalPrice(),
                orderDto.state() != null ? orderDto.state() : OrderState.CART,
                null,
                LocalDateTime.now(),
                orderDto.orderAt(),
                orderDto.address(),
                List.of()
        );

        OrderEntity saved = orderRepository.save(order);
        return OrderMapper.getInstance().fromOrderToOrderDto(
                OrderMapper.getInstance().fromOrderEntityToOrder(saved));
    }

    @Override
    public OrderDto update(Long id, OrderDto orderDto) {
        OrderEntity existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Order not found with id: " + id));

        OrderEntity updatedOrder = new OrderEntity(
                existingOrder.id(),
                orderDto.totalProducts(),
                orderDto.totalPrice(),
                orderDto.state(),
                existingOrder.user(),
                existingOrder.createdAt(),
                orderDto.orderAt(),
                orderDto.address(),
                existingOrder.items()
        );

        OrderEntity saved = orderRepository.save(updatedOrder);
        return OrderMapper.getInstance().fromOrderToOrderDto(
                OrderMapper.getInstance().fromOrderEntityToOrder(saved));
    }

    @Override
    public void delete(Long id) {
        if (orderRepository.findById(id).isEmpty()) {
            throw new BusinessException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    @Override
    public OrderDto checkout(Long userId, CheckoutRequest request) {
        if (request.address() == null || request.address().trim().isEmpty()) {
            throw new BusinessException("Address is required for checkout");
        }

        ClientEntity user = clientRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found with id: " + userId));

        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException("Cart not found for user: " + userId));

        List<CartItemEntity> cartItems = cartItemRepository.findByCartId(cart.id());

        if (cartItems.isEmpty()) {
            throw new BusinessException("Cart is empty, cannot create order");
        }

        OrderEntity order = new OrderEntity(
                null,
                cart.totalProducts(),
                cart.totalPrice(),
                OrderState.ORDER,
                user,
                LocalDateTime.now(),
                LocalDateTime.now(),
                request.address(),
                List.of()
        );

        OrderEntity savedOrder = orderRepository.save(order);

        for (CartItemEntity cartItem : cartItems) {
            OrderItemEntity orderItem = new OrderItemEntity(
                    null,
                    cartItem.quantity(),
                    savedOrder.id(),
                    cartItem.product()
            );
            orderItemRepository.save(orderItem);
        }


        cartRepository.clearAllItems(cart.id());

        CartEntity updatedCart = new CartEntity(
                cart.id(),
                0,
                BigDecimal.ZERO,
                cart.user(),
                List.of()
        );
        cartRepository.save(updatedCart);

        OrderEntity finalOrder = orderRepository.findById(savedOrder.id())
                .orElseThrow(() -> new BusinessException("Error retrieving created order"));

        return OrderMapper.getInstance().fromOrderToOrderDto(
                OrderMapper.getInstance().fromOrderEntityToOrder(finalOrder));
    }
}
