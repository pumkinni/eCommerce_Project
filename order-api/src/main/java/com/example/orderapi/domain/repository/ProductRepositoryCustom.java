package com.example.orderapi.domain.repository;

import com.example.orderapi.domain.model.Product;
import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> searchByName(String name);

}
