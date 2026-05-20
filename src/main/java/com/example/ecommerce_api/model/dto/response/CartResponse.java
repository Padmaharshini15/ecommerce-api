package com.example.ecommerce_api.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Represents the user's shopping cart")
public class CartResponse {

    @Schema(
            description = "Unique ID of the cart",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "List of items present in the cart"
    )
    private List<CartItemResponse> items;

    @Schema(
            description = "Total amount of all items in the cart",
            example = "2499.99"
    )
    private Double totalAmount;
}