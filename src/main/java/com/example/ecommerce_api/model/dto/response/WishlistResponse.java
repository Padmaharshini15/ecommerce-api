package com.example.ecommerce_api.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Represents the complete wishlist of the current user")
public class WishlistResponse {

    @Schema(description = "List of wishlist items added by the user")
    private List<WishlistItemResponse> items;

}