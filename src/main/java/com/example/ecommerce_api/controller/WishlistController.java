package com.example.ecommerce_api.controller;

import com.example.ecommerce_api.model.dto.request.WishlistItemRequest;
import com.example.ecommerce_api.model.dto.response.WishlistResponse;
import com.example.ecommerce_api.service.inf.IWishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wishlist")
@Tag(name = "Wishlist", description = "APIs for managing user's wishlist")
public class WishlistController {

    private final IWishlistService wishlistService;

    @Operation(summary = "Add product to wishlist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product added to wishlist successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PostMapping("/item")
    public ResponseEntity<String> addToWishlist(
            @Valid @RequestBody WishlistItemRequest wishlistItemRequest) {
        return ResponseEntity.ok(wishlistService.addToWishlist(wishlistItemRequest));
    }

    @Operation(summary = "Remove product from wishlist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product removed from wishlist successfully"),
            @ApiResponse(responseCode = "404", description = "Wishlist item not found")
    })
    @DeleteMapping("/{itemId}")
    public ResponseEntity<WishlistResponse> removeFromWishlist(
            @Parameter(description = "Wishlist item ID") @PathVariable Long itemId) {
        return ResponseEntity.ok(wishlistService.removeFromWishlist(itemId));
    }

    @Operation(summary = "Get current user's wishlist")
    @ApiResponse(responseCode = "200", description = "Wishlist retrieved successfully")
    @GetMapping()
    public ResponseEntity<WishlistResponse> getWishlist() {
        return ResponseEntity.ok(wishlistService.getWishlist());
    }
}