package org.pranay.creatorstore.repository;

import org.pranay.creatorstore.entities.Customer;
import org.pranay.creatorstore.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomer(Customer customer);

    // or
    // List<Order> findByCustomerId(Long customerId);
}