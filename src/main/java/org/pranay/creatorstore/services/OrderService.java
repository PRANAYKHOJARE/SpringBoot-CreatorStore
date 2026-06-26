package org.pranay.creatorstore.services;

import org.pranay.creatorstore.dto.OrderRequestDTO;
import org.pranay.creatorstore.entities.Order;
import org.pranay.creatorstore.entities.OrderItem;
import org.pranay.creatorstore.entities.Product;
import org.pranay.creatorstore.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.pranay.creatorstore.entities.Customer;
import org.pranay.creatorstore.repository.CustomerRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductService productService,
                        CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Order placeOrder(OrderRequestDTO request) {
        Order order = new Order();
        Customer customer = customerRepository.findByEmail(request.getCustomerEmail())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        order.setCustomer(customer);
        order.setStatus("CONFIRMED");

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderRequestDTO.OrderItemDTO itemDTO : request.getItems()) {
            Product product = productService.getProductById(itemDTO.getProductId());

            if (product.getStockQuantity() < itemDTO.getQuantity()) {
                throw new RuntimeException(
                        "Insufficient stock for \"" + product.getName() + "\". " +
                                "Available: " + product.getStockQuantity()
                );
            }

            // Deduct stock
            product.setStockQuantity(product.getStockQuantity() - itemDTO.getQuantity());
            productService.createProduct(product); // save updated stock

            OrderItem item = OrderItem.builder()
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .priceAtPurchase(product.getPrice())
                    .order(order)
                    .build();

            items.add(item);
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));
        }

        order.setOrderItems(items);
        order.setTotalPrice(total);
        return orderRepository.save(order);
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    @Transactional
    public Order updateStatus(Long id, String status) {
        List<String> allowed = List.of("CONFIRMED", "PROCESSING", "SHIPPED", "DELIVERED", "CANCELLED");
        if (!allowed.contains(status)) {
            throw new RuntimeException("Invalid status: " + status);
        }
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}