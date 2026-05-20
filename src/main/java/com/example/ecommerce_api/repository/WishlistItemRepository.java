package com.example.ecommerce_api.repository;

import com.example.ecommerce_api.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem,Long> {
}
