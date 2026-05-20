package com.example.ecommerce_api.service.inf;

import com.example.ecommerce_api.model.dto.request.WishlistItemRequest;
import com.example.ecommerce_api.model.dto.response.WishlistResponse;

public interface IWishlistService {

    String addToWishlist(WishlistItemRequest request);
    WishlistResponse removeFromWishlist(Long id);
    WishlistResponse getWishlist();

}
