package org.example.storeback.domain.service;

import org.example.storeback.domain.service.dto.OrderItemDto;

import java.util.List;

public interface OrderItemService {
    List<OrderItemDto> findAll();
    OrderItemDto findById(Long id);
    List<OrderItemDto> findByOrderId(Long orderId);
    OrderItemDto create(OrderItemDto orderItemDto);
    OrderItemDto update(Long id, OrderItemDto orderItemDto);
    void delete(Long id);
}
