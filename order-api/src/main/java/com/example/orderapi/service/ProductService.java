package com.example.orderapi.service;


import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.model.ProductItem;
import com.example.orderapi.domain.product.AddProductForm;
import com.example.orderapi.domain.product.UpdateProductForm;
import com.example.orderapi.domain.product.UpdateProductItemForm;
import com.example.orderapi.domain.repository.ProductRepository;
import com.example.orderapi.exception.CustomException;
import com.example.orderapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    // 상품 추가
    @Transactional
    public Product addProduct(Long sellerId, AddProductForm form){
        return productRepository.save(Product.of(sellerId, form));
    }


    @Transactional
    public Product updateProduct(Long sellerId, UpdateProductForm form){
        Product product = productRepository.findBySellerIdAndId(sellerId, form.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
        product.setName(form.getName());
        product.setDescription(form.getDescription());

        for (UpdateProductItemForm itemForm : form.getItems()){
            ProductItem item = product.getProductItems().stream()
                .filter(pi->pi.getId().equals(itemForm.getId()))
                .findFirst().orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_ITEM));
            item.setName(itemForm.getName());
            item.setPrice(itemForm.getPrice());
            item.setCount(itemForm.getCount());
        }

        return product;
    }

    @Transactional
    public void deleteProduct(Long sellerId, Long productId){
        Product product = productRepository.findBySellerIdAndId(sellerId, productId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

        productRepository.delete(product);
    }

}
