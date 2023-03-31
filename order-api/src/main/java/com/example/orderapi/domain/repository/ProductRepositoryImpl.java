package com.example.orderapi.domain.repository;


import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.model.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> searchByName(String name) {
        String search = "%" + name + "%";

        QProduct product = QProduct.product;
        return queryFactory.selectFrom(product)
            .where(product.name.like(search))
            .fetch();
    }
}
