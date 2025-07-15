package com.wave.Mirissa.controllers;

import com.wave.Mirissa.dtos.CartItemRequest;
import com.wave.Mirissa.dtos.CartItemResponse;
import com.wave.Mirissa.dtos.CartResponse;
import com.wave.Mirissa.models.Cart;
import com.wave.Mirissa.models.CartItem;
import com.wave.Mirissa.services.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public CartResponse addToCart(@RequestBody CartItemRequest request) {
        Cart cart = cartService.addToCart(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity(),
                request.getSize(),
                request.getCustomMaterial(),
                request.getPrice()  // ✅ pass price from frontend
        );
        return convertToDto(cart);
    }

    @GetMapping("/{userId}")
    public CartResponse getCart(@PathVariable Long userId) {
        Cart cart = cartService.getCart(userId);
        return convertToDto(cart);
    }

    @DeleteMapping("/clear/{userId}")
    public void clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
    }

    @DeleteMapping("/item/{itemId}")
    public CartResponse deleteCartItem(@PathVariable Long itemId) {
        Cart updatedCart = cartService.removeItemFromCart(itemId);
        return convertToDto(updatedCart);
    }


    private CartResponse convertToDto(Cart cart) {
        List<CartItemResponse> items = cart.getItems().stream().map(item ->
                new CartItemResponse(
                        item.getId(),
                        item.getProduct().getName(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getProduct().getImageUrl1()
                )
        ).collect(Collectors.toList());

        return new CartResponse(cart.getId(), cart.getTotal(), items);
    }
}
