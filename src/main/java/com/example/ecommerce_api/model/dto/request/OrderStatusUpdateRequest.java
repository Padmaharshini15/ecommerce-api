package com.example.ecommerce_api.model.dto.request;

import com.example.ecommerce_api.model.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusUpdateRequest {

    @NotNull(message = "ID is required!")
    private Long id;

    @NotNull(message = "Status is required!")
    private OrderStatus status;
}
