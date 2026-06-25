package org.pranay.creatorstore.repository;

import org.pranay.creatorstore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
