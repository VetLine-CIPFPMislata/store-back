package org.example.storeback.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.storeback.controller.webmodel.request.AddProductToCartRequest;
import org.example.storeback.controller.webmodel.request.UpdateCartItemRequest;
import org.example.storeback.domain.models.Role;
import org.example.storeback.domain.service.CartService;
import org.example.storeback.domain.service.dto.CartDto;
import org.example.storeback.domain.service.dto.ClientDto;
import org.example.storeback.domain.validation.RequiresRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @RequiresRole(Role.USER)
    public ResponseEntity<List<CartDto>> findAll(HttpServletRequest request) {
        ClientDto user = (ClientDto) request.getAttribute("authenticatedUser");

        if (user.role() == Role.USER) {
            CartDto cart = cartService.findByUserId(user.id());
            return ResponseEntity.ok(List.of(cart));
        }

        List<CartDto> carts = cartService.findAll();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/user/{userId}")
    @RequiresRole(Role.USER)
    public ResponseEntity<CartDto> findByUserId(@PathVariable Long userId, HttpServletRequest request) {
        ClientDto user = (ClientDto) request.getAttribute("authenticatedUser");

        if (!userId.equals(user.id())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "No tienes permiso para acceder al carrito de otro usuario");
        }

        CartDto cart = cartService.findByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping
    @RequiresRole(Role.USER)
    public ResponseEntity<CartDto> create(@RequestBody CartDto cartDto, HttpServletRequest request) {
        ClientDto user = (ClientDto) request.getAttribute("authenticatedUser");

        if (cartDto.user() != null && !cartDto.user().id().equals(user.id())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "No tienes permiso para crear un carrito para otro usuario");
        }

        CartDto created = cartService.create(cartDto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    @RequiresRole(Role.USER)
    public ResponseEntity<CartDto> update(@PathVariable Long id, @RequestBody CartDto cartDto, HttpServletRequest request) {
        ClientDto user = (ClientDto) request.getAttribute("authenticatedUser");
        CartDto existingCart = cartService.findById(id);

        if (existingCart.user() != null && !existingCart.user().id().equals(user.id())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "No tienes permiso para actualizar este carrito");
        }

        CartDto updated = cartService.update(id, cartDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @RequiresRole(Role.USER)
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        ClientDto user = (ClientDto) request.getAttribute("authenticatedUser");
        CartDto cart = cartService.findById(id);

        if (cart.user() != null && !cart.user().id().equals(user.id())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "No tienes permiso para eliminar este carrito");
        }

        cartService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/items")
    @RequiresRole(Role.USER)
    public ResponseEntity<CartDto> addProductToCart(
            @PathVariable Long userId,
            @RequestBody AddProductToCartRequest request,
            HttpServletRequest httpRequest
    ) {
        ClientDto user = (ClientDto) httpRequest.getAttribute("authenticatedUser");

        if (!userId.equals(user.id())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "No puedes agregar productos al carrito de otro usuario");
        }

        CartDto updatedCart = cartService.addProductToCart(userId, request);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/{userId}/items/{cartItemId}")
    @RequiresRole(Role.USER)
    public ResponseEntity<CartDto> updateCartItemQuantity(
            @PathVariable Long userId,
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequest request,
            HttpServletRequest httpRequest
    ) {
        ClientDto user = (ClientDto) httpRequest.getAttribute("authenticatedUser");

        if (!userId.equals(user.id())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "No puedes actualizar items del carrito de otro usuario");
        }

        CartDto updatedCart = cartService.updateCartItemQuantity(userId, cartItemId, request.quantity());
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{userId}/items/{cartItemId}")
    @RequiresRole(Role.USER)
    public ResponseEntity<Void> removeCartItem(
            @PathVariable Long userId,
            @PathVariable Long cartItemId,
            HttpServletRequest httpRequest
    ) {
        ClientDto user = (ClientDto) httpRequest.getAttribute("authenticatedUser");

        if (!userId.equals(user.id())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "No puedes eliminar items del carrito de otro usuario");
        }

        cartService.removeCartItem(userId, cartItemId);
        return ResponseEntity.noContent().build();
    }
}
