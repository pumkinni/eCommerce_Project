package com.example.orderapi.domain.repository;

import com.example.orderapi.domain.model.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

}
