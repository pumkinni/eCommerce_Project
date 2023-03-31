package com.example.orderapi.service;


import static com.example.orderapi.exception.ErrorCode.NOT_FOUND_PRODUCT;

import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.repository.ProductRepository;
import com.example.orderapi.exception.CustomException;
import com.example.orderapi.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSearchService {
    private final ProductRepository productRepository;

    public List<Product> searchByName(String name){
        return productRepository.searchByName(name);
    }

    // 아이템 아이디 입력 시 아이템 페이지 출력
    public Product getByProductId(Long productId){
        return productRepository.findWithProductItemsById(productId)
            .orElseThrow(()-> new CustomException(NOT_FOUND_PRODUCT));
    }

    public List<Product> getListByProductIds(List<Long> productIds){
        return productRepository.findAllById(productIds);
    }

}
