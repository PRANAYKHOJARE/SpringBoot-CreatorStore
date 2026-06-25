package org.pranay.creatorstore.repository;

import org.pranay.creatorstore.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}