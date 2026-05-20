package com.example.ecommerce_api.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response object representing product details")
public class ProductResponse {

    @Schema(description = "Unique ID of the product", example = "101")
    private Long id;

    @Schema(description = "Title of the product", example = "iPhone 15 Pro")
    private String title;

    @Schema(description = "Brand name of the product", example = "Apple")
    private String brand;

    @Schema(description = "Model name of the product", example = "A3101")
    private String model;

    @Schema(description = "Detailed description of the product", example = "Latest Apple iPhone with A17 chip")
    private String description;

    @Schema(description = "Available stock quantity", example = "50")
    private Integer stockQuantity;

    @Schema(description = "Price of the product", example = "129999.99")
    private Double price;

    @Schema(description = "Category of the product", example = "Electronics")
    private String category;

    @Schema(description = "Image URL of the product", example = "https://example.com/images/iphone15.jpg")
    private String imageUrl;

    @Schema(description = "Seller ID who listed the product", example = "10")
    private Long sellerId;

    @Schema(description = "Name of the seller", example = "TechStore Pvt Ltd")
    private String sellerName;
}