package org.pranay.creatorstore.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.pranay.creatorstore.dto.OrderItemRequest;
import org.pranay.creatorstore.dto.OrderRequest;
import org.pranay.creatorstore.entities.Order;
import org.pranay.creatorstore.entities.OrderItem;
import org.pranay.creatorstore.entities.Product;
import org.pranay.creatorstore.repository.OrderRepository;
import org.pranay.creatorstore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order createOrder(OrderRequest orderRequest){
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        Order order = new Order();
        order.setCustomerName(orderRequest.getCustomerName());
        order.setCustomerEmail(orderRequest.getCustomerEmail());
        order.setStatus("CONFIRMED");

        for (OrderItemRequest itemRequest : orderRequest.getItems()){
            Product product = productRepository.findById(itemRequest.getProductId()).orElseThrow(() -> new RuntimeException("product not found with id" + itemRequest.getProductId()));

            // check the product stock
            if (product.getStockQuantity() < itemRequest.getQuantity()){
                throw new RuntimeException("Not enough stock for this product" + itemRequest.getProductId());
            }

            //calculate total price
            BigDecimal priceOfItem = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            totalPrice = totalPrice.add(priceOfItem);

            //update product table latest stock quantity
            product.setStockQuantity(
                    product.getStockQuantity() - itemRequest.getQuantity()
        );
            productRepository.save(product);

            //Builder pattern to make object
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .priceAtPurchase(product.getPrice())
                    .build();

            orderItems.add(orderItem);
        }

        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);

        return orderRepository.save(order);
    }

}
