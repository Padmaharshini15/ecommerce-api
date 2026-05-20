package com.example.ecommerce_api.controller;

import com.example.ecommerce_api.model.dto.request.OrderRequest;
import com.example.ecommerce_api.model.dto.request.OrderStatusUpdateRequest;
import com.example.ecommerce_api.model.dto.response.OrderResponse;
import com.example.ecommerce_api.service.inf.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
@Tag(name = "Order APIs", description = "Operations related to Order Management")
public class OrderController {

    private final IOrderService orderService;

    @Operation(
            summary = "Create a new Order",
            description = "Creates a new order for the logged-in user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping()
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody OrderRequest orderRequest) {

        OrderResponse response = orderService.createOrder(orderRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get My Orders",
            description = "Fetch all orders of the currently logged-in user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getMyOrders() {

        List<OrderResponse> orders = orderService.getMyOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(
            summary = "Get Order By ID",
            description = "Fetch details of a specific order using its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {

        OrderResponse order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @Operation(
            summary = "Update Order Status",
            description = "Updates the status of an existing order (Admin only)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PutMapping()
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @Valid @RequestBody OrderStatusUpdateRequest request) {

        OrderResponse order = orderService.updateOrderStatus(request);
        return ResponseEntity.ok(order);
    }
}