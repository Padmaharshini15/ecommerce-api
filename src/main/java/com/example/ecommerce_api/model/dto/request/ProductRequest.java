package com.example.ecommerce_api.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Title is required!")
    @Size(min = 2,max = 100, message = "Title should be between 2 to 100 characters" )
    private String title;

    @NotBlank(message = "Brand is required!")
    @Size(min = 2,max = 100, message = "Brand should be between 2 to 100 characters" )
    private String brand;

    @NotBlank(message = "Model is required!")
    @Size(min = 2,max = 100, message = "Brand should be between 2 to 100 characters" )
    private String model;

    @NotBlank(message = "Description is required!")
    @Size(min = 20,max = 1000, message = "Brand should be between 20 to 1000 characters" )
    private String description;

    @NotNull(message = "Price is required!")
    @Min(value = 10,message = "Price must be greater than 10")
    private Double price;

    @NotNull(message = "Stock Quantity is required!")
    @Min(value = 1,message = "Stock Quantity should be atleast 1")
    private Integer stockQuantity;

    @NotNull(message = "Category is required!")
    @Size(min = 2,max = 100, message = "Title should be between 2 to 100 characters" )
    private String category;

    @NotBlank(message = "Image Url is required!")
    private String imageUrl;


}
