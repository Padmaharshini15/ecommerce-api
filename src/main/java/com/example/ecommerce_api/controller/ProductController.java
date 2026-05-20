package com.example.ecommerce_api.controller;

import com.example.ecommerce_api.model.dto.request.ProductRequest;
import com.example.ecommerce_api.model.dto.response.ProductResponse;
import com.example.ecommerce_api.service.inf.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
@Tag(name = "Product", description = "APIs for managing products")
public class ProductController {

    private final IProductService productService;

    @Operation(summary = "Get all products")
    @ApiResponse(responseCode = "200", description = "List of all products retrieved successfully")
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Get product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductsById(
            @Parameter(description = "ID of the product") @PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Get products by category")
    @ApiResponse(responseCode = "200", description = "Products filtered by category")
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @Parameter(description = "Category name") @PathVariable String category) {
        return ResponseEntity.ok(productService.getProductByCategory(category));
    }

    @Operation(summary = "Get products within price range")
    @ApiResponse(responseCode = "200", description = "Products filtered by price range")
    @GetMapping("/price")
    public ResponseEntity<List<ProductResponse>> getProductsByPriceRange(
            @Parameter(description = "Minimum price") @RequestParam Double minPrice,
            @Parameter(description = "Maximum price") @RequestParam Double maxPrice) {
        return ResponseEntity.ok(productService.getProductsByPriceRange(minPrice, maxPrice));
    }

    @Operation(summary = "Search products by keyword")
    @ApiResponse(responseCode = "200", description = "Products matching the search keyword")
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @Parameter(description = "Search keyword") @RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

    @Operation(summary = "Create a new product (Seller only)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    @PostMapping()
    public ResponseEntity<String> createProduct(@Valid @RequestBody ProductRequest request) {
        return new ResponseEntity<>(productService.createProduct(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update existing product (Seller only)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping()
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<String> updateProduct(
            @Parameter(description = "ID of the product to update") @RequestParam Long id,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @Operation(summary = "Delete product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @Parameter(description = "ID of the product to delete") @PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}