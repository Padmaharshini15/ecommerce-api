package com.example.ecommerce_api.service.inf;

import com.example.ecommerce_api.model.dto.request.CartItemRequest;
import com.example.ecommerce_api.model.dto.response.CartResponse;

public interface ICartService {

    String addToCart(CartItemRequest request);
    CartResponse updateCart(CartItemRequest request);
    CartResponse removeFromCart(Long cartItemId);
    CartResponse getCart();
}
