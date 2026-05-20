package com.example.ecommerce_api.model.dto.response;

import com.example.ecommerce_api.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response object representing an Order")
public class OrderResponse {

    @Schema(description = "Unique ID of the order", example = "101")
    private Long id;

    @Schema(description = "Date and time when order was placed",
            example = "2026-05-20T10:15:30")
    private LocalDateTime orderDate;

    @Schema(description = "Total amount of the order", example = "2500.75")
    private Double totalAmount;

    @Schema(description = "Current status of the order",
            example = "SHIPPED")
    private OrderStatus status;

    @Schema(description = "Shipping address for delivery",
            example = "12, Anna Nagar, Chennai")
    private String shippingAddress;

    @Schema(description = "ID of the user who placed the order",
            example = "5")
    private Long userId;

    @Schema(description = "List of items in this order")
    private List<OrderItemResponse> items;
}