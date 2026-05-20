package com.example.ecommerce_api.service.inf;

import com.example.ecommerce_api.model.Product;
import com.example.ecommerce_api.model.dto.request.ProductRequest;
import com.example.ecommerce_api.model.dto.response.ProductResponse;
import lombok.Data;

import java.util.List;

public interface IProductService {

    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    List<ProductResponse> getProductByCategory(String category);
    List<ProductResponse>getProductsByPriceRange(Double minPrice,Double maxPrice);
    List<ProductResponse> searchProducts(String keyword);
    String createProduct(ProductRequest request);
    String updateProduct(Long id, ProductRequest request);
    String deleteProduct(Long id);


}
