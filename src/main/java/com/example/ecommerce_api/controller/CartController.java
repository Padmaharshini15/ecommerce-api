package com.example.ecommerce_api.controller;

import com.example.ecommerce_api.model.dto.request.CartItemRequest;
import com.example.ecommerce_api.model.dto.response.CartResponse;
import com.example.ecommerce_api.service.inf.ICartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
@Tag(name = "Cart", description = "APIs for managing user's shopping cart")
@SecurityRequirement(name = "bearerAuth")
public class CartController {

    private final ICartService cartService;


    @Operation(
            summary = "Add item to cart",
            description = "Adds a product to the authenticated user's cart."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Item added successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PostMapping("/items")
    public ResponseEntity<String> addToCart(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Cart item details",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CartItemRequest.class)
                    )
            )
            @Valid @RequestBody CartItemRequest request) {

        String response = cartService.addToCart(request);
        return ResponseEntity.ok(response);
    }



    @Operation(
            summary = "Update cart item",
            description = "Updates the quantity of an existing item in the cart."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cart updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CartResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @PutMapping("/items")
    public ResponseEntity<CartResponse> updateCart(
            @Valid @RequestBody CartItemRequest request) {

        CartResponse response = cartService.updateCart(request);
        return ResponseEntity.ok(response);
    }



    @Operation(
            summary = "Remove item from cart",
            description = "Removes an item from the cart using cart item ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Item removed successfully",
                    content = @Content(
                            schema = @Schema(implementation = CartResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponse> removeFromCart(
            @Parameter(description = "ID of the cart item to remove", example = "5")
            @PathVariable Long cartItemId) {

        CartResponse response = cartService.removeFromCart(cartItemId);
        return ResponseEntity.ok(response);
    }



    @Operation(
            summary = "Get current user's cart",
            description = "Fetches the authenticated user's cart with all items and total amount."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cart retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CartResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping
    public ResponseEntity<CartResponse> getCart(){

        CartResponse response = cartService.getCart();
        return ResponseEntity.ok(response);
    }
}