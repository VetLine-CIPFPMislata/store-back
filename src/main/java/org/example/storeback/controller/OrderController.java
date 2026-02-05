package org.example.storeback.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.storeback.controller.webmodel.request.CheckoutRequest;
import org.example.storeback.domain.models.Role;
import org.example.storeback.domain.service.OrderService;
import org.example.storeback.domain.service.dto.ClientDto;
import org.example.storeback.domain.service.dto.OrderDto;
import org.example.storeback.domain.validation.RequiresRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @RequiresRole(Role.USER)
    public ResponseEntity<List<OrderDto>> findAll(HttpServletRequest request) {
        ClientDto user = (ClientDto) request.getAttribute("authenticatedUser");

        if (user.role() == Role.USER) {
            List<OrderDto> orders = orderService.findByUserId(user.id());
            return ResponseEntity.ok(orders);
        }

        List<OrderDto> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    @RequiresRole(Role.USER)
    public ResponseEntity<OrderDto> findById(@PathVariable Long id, HttpServletRequest request) {
        ClientDto user = (ClientDto) request.getAttribute("authenticatedUser");
        OrderDto order = orderService.findById(id);

        if (order.user() != null && !order.user().id().equals(user.id())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "No tienes permiso para acceder a esta orden");
        }

        return ResponseEntity.ok(order);
    }

    @GetMapping("/user/{userId}")
    @RequiresRole(Role.USER)
    public ResponseEntity<List<OrderDto>> findByUserId(@PathVariable Long userId, HttpServletRequest request) {
        ClientDto user = (ClientDto) request.getAttribute("authenticatedUser");

        if (!userId.equals(user.id())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "No tienes permiso para acceder a órdenes de otro usuario");
        }

        List<OrderDto> orders = orderService.findByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    @RequiresRole(Role.USER)
    public ResponseEntity<OrderDto> create(@RequestBody OrderDto orderDto, HttpServletRequest request) {
        ClientDto user = (ClientDto) request.getAttribute("authenticatedUser");

        if (orderDto.user() != null && !orderDto.user().id().equals(user.id())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "No puedes crear órdenes para otro usuario");
        }

        OrderDto created = orderService.create(orderDto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    @RequiresRole(Role.USER)
    public ResponseEntity<OrderDto> update(@PathVariable Long id, @RequestBody OrderDto orderDto, HttpServletRequest request) {
        ClientDto user = (ClientDto) request.getAttribute("authenticatedUser");
        OrderDto existingOrder = orderService.findById(id);

        if (existingOrder.user() != null && !existingOrder.user().id().equals(user.id())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "No tienes permiso para actualizar esta orden");
        }

        OrderDto updated = orderService.update(id, orderDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @RequiresRole(Role.USER)
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        ClientDto user = (ClientDto) request.getAttribute("authenticatedUser");
        OrderDto order = orderService.findById(id);

        if (order.user() != null && !order.user().id().equals(user.id())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "No tienes permiso para eliminar esta orden");
        }

        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/checkout")
    @RequiresRole(Role.USER)
    public ResponseEntity<OrderDto> checkout(
            @PathVariable Long userId,
            @RequestBody CheckoutRequest checkoutRequest,
            HttpServletRequest httpRequest
    ) {
        ClientDto user = (ClientDto) httpRequest.getAttribute("authenticatedUser");

        if (!userId.equals(user.id())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "No puedes hacer checkout del carrito de otro usuario");
        }

        OrderDto order = orderService.checkout(userId, checkoutRequest);
        return ResponseEntity.status(201).body(order);
    }
}
