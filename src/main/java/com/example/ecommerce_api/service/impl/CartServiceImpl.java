package com.example.ecommerce_api.service.impl;

import com.example.ecommerce_api.exception.BadRequestException;
import com.example.ecommerce_api.exception.ResourceNotFoundException;
import com.example.ecommerce_api.model.Cart;
import com.example.ecommerce_api.model.CartItem;
import com.example.ecommerce_api.model.Product;
import com.example.ecommerce_api.model.User;
import com.example.ecommerce_api.model.dto.request.CartItemRequest;
import com.example.ecommerce_api.model.dto.response.CartItemResponse;
import com.example.ecommerce_api.model.dto.response.CartResponse;
import com.example.ecommerce_api.repository.CartItemRepository;
import com.example.ecommerce_api.repository.CartRepository;
import com.example.ecommerce_api.repository.ProductRepository;
import com.example.ecommerce_api.service.inf.ICartService;
import com.example.ecommerce_api.service.inf.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
    public class CartServiceImpl  implements ICartService {

    private final CartRepository cartRepository;
    private final IUserService userService;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

        @Override
        @Transactional
        public String addToCart(CartItemRequest request) {
            User currentUser = userService.getCurrentUser();
            Cart cart = cartRepository.findByUserId(currentUser.getId())
                    .orElseGet(()->createCartForUser(currentUser));

            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(()-> new ResourceNotFoundException("Product not found with ID: "+request.getProductId()));

            if(product.getStockQuantity() < request.getQuantity())
                throw new BadRequestException("No enough stocks available!");

            CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(),product.getId()).orElse(null);

            if(cartItem!=null){
                cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            }
            else{
                cartItem = CartItem.builder()
                        .cart(cart)
                        .product(product)
                        .quantity(request.getQuantity())
                        .build();
                cart.addItem(cartItem);
            }
            cartRepository.save(cart);
            return "Product Added to Cart Successfully!";
        }

        @Override
        public CartResponse updateCart(CartItemRequest request) {
            User currentUser = userService.getCurrentUser();

            Cart cart = cartRepository.findByUserId(currentUser.getId())
                    .orElseThrow(()-> new ResourceNotFoundException("Cart not Found!"));

            CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(),request.getProductId())
                    .orElseThrow(()-> new ResourceNotFoundException("Product not Found in Cart!"));

            if(request.getQuantity() == 0){
                cart.removeItem(cartItem);
                cartItemRepository.delete(cartItem);
            }
            else{
                if(cartItem.getProduct().getStockQuantity() < request.getQuantity())
                    throw new BadRequestException("No enough stocks available!");
                cartItem.setQuantity(request.getQuantity());
            }
            cartRepository.save(cart);
            return mapToCartResponse(cart);
        }

        @Override
        @Transactional
        public CartResponse removeFromCart(Long id) {
            User currentUser = userService.getCurrentUser();

            Cart cart = cartRepository.findByUserId(currentUser.getId())
                    .orElseThrow(()-> new ResourceNotFoundException("Cart not Found!"));

            CartItem cartItem = cartItemRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Product not Found in Cart!"));

            cart.removeItem(cartItem);
            cartItemRepository.delete(cartItem);
            cartRepository.save(cart);
            return mapToCartResponse(cart);
        }

        @Override
        public CartResponse getCart() {
            User currentUser = userService.getCurrentUser();
            Cart cart = cartRepository.findByUserId(currentUser.getId())
                    .orElseGet(()->createCartForUser(currentUser));
            return mapToCartResponse(cart);
        }

        private Cart createCartForUser(User currentUser){
            Cart cart = Cart.builder()
                    .user(currentUser)
                    .build();
            cartRepository.save(cart);
            return cart;
        }

        private CartResponse mapToCartResponse(Cart cart){
            List<CartItem> items = cart.getItems();
            List<CartItemResponse> responseItems = cart.getItems().stream()
                    .map(this::mapToCartItemResponse)
                    .toList();
            double total = 0.0;
            for(CartItem item: items){
                total = total + (item.getProduct().getPrice() * item.getQuantity());
            }
            return CartResponse.builder()
                    .id(cart.getId())
                    .items(responseItems)
                    .totalAmount(total)
                    .build();
        }

        private CartItemResponse mapToCartItemResponse(CartItem cartItem) {
            double subTotal = 0.0;
            subTotal = cartItem.getQuantity() * cartItem.getProduct().getPrice();

            return CartItemResponse.builder()
                    .id(cartItem.getId())
                    .quantity(cartItem.getQuantity())
                    .productTitle(cartItem.getProduct().getTitle())
                    .productBrand(cartItem.getProduct().getBrand())
                    .productModel(cartItem.getProduct().getModel())
                    .productPrice(cartItem.getProduct().getPrice())
                    .subTotal(subTotal)
                    .build();
        }
    }
