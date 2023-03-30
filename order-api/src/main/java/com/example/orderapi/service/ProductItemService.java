package com.example.orderapi.service;

import static com.example.orderapi.exception.ErrorCode.NOT_FOUND_PRODUCT;
import static com.example.orderapi.exception.ErrorCode.SAME_ITEM_NAME;

import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.model.ProductItem;
import com.example.orderapi.domain.product.AddProductItemForm;
import com.example.orderapi.domain.product.ProductItemDto;
import com.example.orderapi.domain.repository.ProductItemRepository;
import com.example.orderapi.domain.repository.ProductRepository;
import com.example.orderapi.exception.CustomException;
import com.example.orderapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductItemService {

    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;


    public Product addProductItem(Long sellerId, AddProductItemForm form){
        Product product = productRepository.findBySellerIdAndId(sellerId, form.getProductId())
            .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));

        if (product.getProductItems().stream().anyMatch(item -> item.getName().equals(form.getName()))){
            throw new CustomException(SAME_ITEM_NAME);
        }

        ProductItem productItem = ProductItem.of(sellerId, form);
        product.getProductItems().add(productItem);
        return product;

    }
}
