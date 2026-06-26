package org.pranay.creatorstore.services;

import lombok.RequiredArgsConstructor;
import org.pranay.creatorstore.dto.CustomerDashboardResponse;
import org.pranay.creatorstore.entities.Customer;
import org.pranay.creatorstore.entities.Order;
import org.pranay.creatorstore.repository.CustomerRepository;
import org.pranay.creatorstore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerDashboardService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public CustomerDashboardResponse getDashboard(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<Order> orders = orderRepository.findByCustomer(customer);

        long pending = orders.stream()
                .filter(o -> "PENDING".equalsIgnoreCase(o.getStatus()))
                .count();

        long processing = orders.stream()
                .filter(o -> "PROCESSING".equalsIgnoreCase(o.getStatus()))
                .count();

        long delivered = orders.stream()
                .filter(o -> "DELIVERED".equalsIgnoreCase(o.getStatus()))
                .count();

        long cancelled = orders.stream()
                .filter(o -> "CANCELLED".equalsIgnoreCase(o.getStatus()))
                .count();

        BigDecimal totalSpent = orders.stream()
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CustomerDashboardResponse response = new CustomerDashboardResponse();

        response.setCustomerName(customer.getName());
        response.setTotalOrders(orders.size());
        response.setPendingOrders(pending);
        response.setProcessingOrders(processing);
        response.setDeliveredOrders(delivered);
        response.setCancelledOrders(cancelled);
        response.setTotalSpent(totalSpent);
        response.setRecentOrders(orders);

        return response;
    }
}