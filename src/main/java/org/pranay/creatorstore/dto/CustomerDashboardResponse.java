package org.pranay.creatorstore.dto;

import lombok.Data;
import org.pranay.creatorstore.entities.Order;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CustomerDashboardResponse {

    private String customerName;

    private int totalOrders;

    private long pendingOrders;

    private long processingOrders;

    private long deliveredOrders;

    private long cancelledOrders;

    private BigDecimal totalSpent;

    private List<Order> recentOrders;

}