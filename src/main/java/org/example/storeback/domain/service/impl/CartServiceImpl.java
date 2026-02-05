package org.example.storeback.domain.service.impl;

import org.example.storeback.controller.webmodel.request.AddProductToCartRequest;
import org.example.storeback.domain.exception.BusinessException;
import org.example.storeback.domain.mappers.CartMapper;
import org.example.storeback.domain.repository.CartItemRepository;
import org.example.storeback.domain.repository.CartRepository;
import org.example.storeback.domain.repository.ClientRepository;
import org.example.storeback.domain.repository.ProductRepository;
import org.example.storeback.domain.repository.entity.CartEntity;
import org.example.storeback.domain.repository.entity.CartItemEntity;
import org.example.storeback.domain.repository.entity.ClientEntity;
import org.example.storeback.domain.repository.entity.ProductEntity;
import org.example.storeback.domain.service.CartService;
import org.example.storeback.domain.service.dto.CartDto;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository,
                          ClientRepository clientRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<CartDto> findAll() {
        return cartRepository.findAll().stream()
                .map(CartMapper.getInstance()::fromCartEntityToCart)
                .map(CartMapper.getInstance()::fromCartToCartDto)
                .collect(Collectors.toList());
    }

    @Override
    public CartDto findById(Long id) {
        CartEntity cart = cartRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Cart not found with id: " + id));
        return CartMapper.getInstance().fromCartToCartDto(
                CartMapper.getInstance().fromCartEntityToCart(cart));
    }

    @Override
    public CartDto findByUserId(Long userId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException("Cart not found for user: " + userId));
        return CartMapper.getInstance().fromCartToCartDto(
                CartMapper.getInstance().fromCartEntityToCart(cart));
    }

    @Override
    public CartDto create(CartDto cartDto) {
        CartEntity cart = new CartEntity(null, 0, BigDecimal.ZERO, null, List.of());
        CartEntity saved = cartRepository.save(cart);
        return CartMapper.getInstance().fromCartToCartDto(
                CartMapper.getInstance().fromCartEntityToCart(saved));
    }

    @Override
    public CartDto update(Long id, CartDto cartDto) {
        CartEntity existingCart = cartRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Cart not found with id: " + id));

        CartEntity updatedCart = new CartEntity(
                existingCart.id(),
                cartDto.totalProducts(),
                cartDto.totalPrice(),
                existingCart.user(),
                existingCart.items()
        );

        CartEntity saved = cartRepository.save(updatedCart);
        return CartMapper.getInstance().fromCartToCartDto(
                CartMapper.getInstance().fromCartEntityToCart(saved));
    }

    @Override
    public void delete(Long id) {
        if (cartRepository.findById(id).isEmpty()) {
            throw new BusinessException("Cart not found with id: " + id);
        }
        cartRepository.deleteById(id);
    }

    @Override
    public CartDto addProductToCart(Long userId, AddProductToCartRequest request) {
        ClientEntity user = clientRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found with id: " + userId));

        ProductEntity product = productRepository.findById(request.productId())
                .orElseThrow(() -> new BusinessException("Product not found with id: " + request.productId()));

        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity(null, 0, BigDecimal.ZERO, user, List.of());
                    return cartRepository.save(newCart);
                });

        var existingItem = cartItemRepository.findByCartIdAndProductId(cart.id(), product.id());

        CartItemEntity cartItem;
        if (existingItem.isPresent()) {
            CartItemEntity existing = existingItem.get();
            cartItem = new CartItemEntity(
                    existing.id(),
                    existing.quantity() + request.quantity(),
                    existing.cartId(),
                    existing.product(),
                    existing.unitPrice()
            );
        } else {
            BigDecimal unitPrice = product.discountPercentage() != null &&
                                  product.discountPercentage().compareTo(BigDecimal.ZERO) > 0
                    ? product.basePrice().subtract(
                        product.basePrice().multiply(product.discountPercentage())
                                .divide(BigDecimal.valueOf(100), 2, java.math.RoundingMode.HALF_UP))
                    : product.basePrice();

            cartItem = new CartItemEntity(
                    null,
                    request.quantity(),
                    cart.id(),
                    product,
                    unitPrice
            );
        }

        cartItemRepository.save(cartItem);

        recalculateAndUpdateCart(cart.id());

        CartEntity updatedCart = cartRepository.findById(cart.id())
                .orElseThrow(() -> new BusinessException("Error retrieving updated cart"));

        return CartMapper.getInstance().fromCartToCartDto(
                CartMapper.getInstance().fromCartEntityToCart(updatedCart));
    }

    private void recalculateAndUpdateCart(Long cartId) {
        List<CartItemEntity> items = cartItemRepository.findByCartId(cartId);

        int totalProducts = items.stream()
                .mapToInt(CartItemEntity::quantity)
                .sum();

        BigDecimal totalPrice = items.stream()
                .map(item -> item.unitPrice().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CartEntity existingCart = cartRepository.findById(cartId)
                .orElseThrow(() -> new BusinessException("Cart not found"));

        CartEntity updatedCart = new CartEntity(
                existingCart.id(),
                totalProducts,
                totalPrice,
                existingCart.user(),
                existingCart.items()
        );

        cartRepository.save(updatedCart);
    }

    @Override
    public CartDto updateCartItemQuantity(Long userId, Long cartItemId, Integer quantity) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException("Cart not found for user: " + userId));

        CartItemEntity cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new BusinessException("Cart item not found with id: " + cartItemId));

        if (!cartItem.cartId().equals(cart.id())) {
            throw new BusinessException("Cart item does not belong to user's cart");
        }

        CartItemEntity updatedItem = new CartItemEntity(
                cartItem.id(),
                quantity,
                cartItem.cartId(),
                cartItem.product(),
                cartItem.unitPrice()
        );

        cartItemRepository.save(updatedItem);

        recalculateAndUpdateCart(cart.id());

        CartEntity updatedCart = cartRepository.findById(cart.id())
                .orElseThrow(() -> new BusinessException("Error retrieving updated cart"));

        return CartMapper.getInstance().fromCartToCartDto(
                CartMapper.getInstance().fromCartEntityToCart(updatedCart));
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException("Cart not found for user: " + userId));

        CartItemEntity cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new BusinessException("Cart item not found with id: " + cartItemId));

        if (!cartItem.cartId().equals(cart.id())) {
            throw new BusinessException("Cart item does not belong to user's cart");
        }

        cartRepository.removeItemFromCart(cart.id(), cartItemId);

        recalculateAndUpdateCart(cart.id());
    }
}
