package com.example.ecommerce_api.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItemRequest {

    @NotNull(message = "Product ID is requires!")
    private Long productId;


}
