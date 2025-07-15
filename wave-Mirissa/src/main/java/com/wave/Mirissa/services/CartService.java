package com.wave.Mirissa.services;
import com.wave.Mirissa.models.*;
import com.wave.Mirissa.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartService {

    @Autowired private CartRepository cartRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ProductRepository productsRepository;

    public Cart addToCart(Long userId, Long productId, int quantity, String size, String customMaterial, BigDecimal customPrice) {
        User user = userRepository.findById(userId).orElseThrow();
        Products product = productsRepository.findById(productId).orElseThrow();

        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setTotal(BigDecimal.ZERO);
            return cartRepository.save(newCart);
        });

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setSize(size);
        item.setCustomMaterial(customMaterial);


        BigDecimal totalItemPrice = customPrice.multiply(BigDecimal.valueOf(quantity));
        item.setPrice(totalItemPrice);

        cart.getItems().add(item);
        cart.setTotal(cart.getTotal().add(totalItemPrice));

        cartItemRepository.save(item);
        return cartRepository.save(cart);
    }

    public Cart getCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return cartRepository.findByUser(user).orElseThrow();
    }

    public void clearCart(Long userId) {
        Cart cart = getCart(userId);
        cart.getItems().clear();
        cart.setTotal(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    public Cart removeItemFromCart(Long itemId) {
        CartItem item = cartItemRepository.findById(itemId).orElseThrow();
        Cart cart = item.getCart();

        cart.setTotal(cart.getTotal().subtract(item.getPrice()));
        cart.getItems().remove(item);
        cartItemRepository.delete(item);

        return cartRepository.save(cart);
    }
}
