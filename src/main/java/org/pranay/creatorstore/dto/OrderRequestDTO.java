package org.pranay.creatorstore.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {
    private String customerName;
    private String customerEmail;
    private List<OrderItemDTO> items;

    @Getter
    @Setter
    public static class OrderItemDTO {
        private Long productId;
        private Integer quantity;
    }
}