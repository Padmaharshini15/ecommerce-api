package com.example.ecommerce_api.service.impl;

import com.example.ecommerce_api.exception.BadRequestException;
import com.example.ecommerce_api.exception.ResourceNotFoundException;
import com.example.ecommerce_api.model.Product;
import com.example.ecommerce_api.model.User;
import com.example.ecommerce_api.model.Wishlist;
import com.example.ecommerce_api.model.WishlistItem;
import com.example.ecommerce_api.model.dto.request.WishlistItemRequest;
import com.example.ecommerce_api.model.dto.response.ProductResponse;
import com.example.ecommerce_api.model.dto.response.WishlistItemResponse;
import com.example.ecommerce_api.model.dto.response.WishlistResponse;
import com.example.ecommerce_api.repository.ProductRepository;
import com.example.ecommerce_api.repository.WishlistItemRepository;
import com.example.ecommerce_api.repository.WishlistRepository;
import com.example.ecommerce_api.service.inf.IUserService;
import com.example.ecommerce_api.service.inf.IWishlistService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements IWishlistService {

    private final IUserService userService;
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final WishlistItemRepository wishlistItemRepository;

    @Override
    @Transactional
    public String addToWishlist(WishlistItemRequest request) {
        User currentUser = userService.getCurrentUser();

        Wishlist wishlist = wishlistRepository.findByUserId(currentUser.getId())
                .orElseGet(()-> createWishlistForUser(currentUser));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException("Product not found with ID: "+request.getProductId()));

        boolean isAlreadyExists = wishlist.getItems().stream()
                .anyMatch(item -> item.getProduct().getId().equals(request.getProductId()));

        if(isAlreadyExists){
            throw new BadRequestException("Product already in Wishlist!");
        }
        WishlistItem wishlistItem = WishlistItem.builder()
                .wishlist(wishlist)
                .product(product)
                .build();
        wishlist.getItems().add(wishlistItem);
        wishlistRepository.save(wishlist);
        return "Product added into Wishlist Successfully!";

    }

    @Override
    public WishlistResponse removeFromWishlist(Long id) {
        User currentUser = userService.getCurrentUser();

        Wishlist wishlist = wishlistRepository.findByUserId(currentUser.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Wishlist not Found"));

        WishlistItem wishlistItem = wishlistItemRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Wishlist item not Found"));

        wishlist.getItems().remove(wishlistItem);
        wishlistItemRepository.delete(wishlistItem);
        wishlistRepository.save(wishlist);
        return mapToWishlistResponse(wishlist);
    }

    @Override
    public WishlistResponse getWishlist() {
        User currentUser = userService.getCurrentUser();
        Wishlist wishlist = wishlistRepository.findByUserId(currentUser.getId())
                .orElseGet(()-> createWishlistForUser(currentUser));
        return mapToWishlistResponse(wishlist);
    }

    private Wishlist createWishlistForUser(User currentUser) {
        Wishlist wishlist = Wishlist.builder()
                .user(currentUser)
                .build();
        wishlistRepository.save(wishlist);
        return wishlist;
    }

    private WishlistResponse mapToWishlistResponse(Wishlist wishlist){
        List<WishlistItemResponse> wishlistItems = wishlist.getItems().stream()
                .map(this::mapToWishlistItemResponse)
                .toList();

        return WishlistResponse.builder()
                .items(wishlistItems)
                .build();
    }

    private WishlistItemResponse mapToWishlistItemResponse(WishlistItem wishlistItem){
        return WishlistItemResponse.builder()
                .id(wishlistItem.getId())
                .product(mapToProductResponse(wishlistItem.getProduct()))
                .build();
    }

    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .brand(product.getBrand())
                .model(product.getModel())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .imageUrl(product.getImageUrl())
                .sellerId(product.getSeller().getId())
                .sellerName(product.getSeller().getFirstName()+" "+product.getSeller().getId())
                .build();
    }
}
