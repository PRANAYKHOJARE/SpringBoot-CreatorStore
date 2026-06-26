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

        Customer customer = customerRepository.findByEmail(request.getCustomerEmail())
                .orElseGet(() -> {

                    Customer newCustomer = new Customer();

                    newCustomer.setName(request.getCustomerName());
                    newCustomer.setEmail(request.getCustomerEmail());
                    newCustomer.setPassword("TEMP_PASSWORD");
                    newCustomer.setPhone("");
                    newCustomer.setAddress("");

                    return customerRepository.save(newCustomer);
                });


        Order order = new Order();

        order.setCustomer(customer);
        order.setCustomerEmail(customer.getEmail());
        order.setStatus("CONFIRMED");


        List<OrderItem> items = new ArrayList<>();

        BigDecimal total = BigDecimal.ZERO;


        for (OrderRequestDTO.OrderItemDTO itemDTO : request.getItems()) {


            Product product = productService.getProductById(
                    itemDTO.getProductId()
            );


            if(product.getStockQuantity() < itemDTO.getQuantity()) {

                throw new RuntimeException(
                        "Insufficient stock for " + product.getName()
                );
            }


            // reduce stock
            product.setStockQuantity(
                    product.getStockQuantity() - itemDTO.getQuantity()
            );


            productService.updateProduct(
                    product.getId(),
                    product
            );


            OrderItem item = OrderItem.builder()
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .priceAtPurchase(product.getPrice())
                    .order(order)
                    .build();


            items.add(item);


            total = total.add(
                    product.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(itemDTO.getQuantity())
                            )
            );
        }


        for(OrderItem item : items){
            item.setOrder(order);
        }


        order.setOrderItems(items);
        order.setTotalPrice(total);

        System.out.println("========== BEFORE SAVING ORDER ==========");
        System.out.println("Customer ID: " + customer.getId());
        System.out.println("Customer Email: " + customer.getEmail());
        System.out.println("Items Count: " + items.size());
        System.out.println("Total Price: " + total);

        try {
            Order savedOrder = orderRepository.save(order);

            System.out.println("========== ORDER SAVED SUCCESSFULLY ==========");
            System.out.println("Order ID: " + savedOrder.getId());

            return savedOrder;

        } catch (Exception e) {

            System.out.println("========== ORDER SAVE FAILED ==========");
            e.printStackTrace();

            throw e;
        }
    }

    public Order updateStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);

        return orderRepository.save(order);
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}