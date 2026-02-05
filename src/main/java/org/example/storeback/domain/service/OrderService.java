package org.example.storeback.domain.service;

import org.example.storeback.controller.webmodel.request.CheckoutRequest;
import org.example.storeback.domain.service.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> findAll();
    OrderDto findById(Long id);
    List<OrderDto> findByUserId(Long userId);
    OrderDto create(OrderDto orderDto);
    OrderDto update(Long id, OrderDto orderDto);
    void delete(Long id);
    OrderDto checkout(Long userId, CheckoutRequest request);
}
