package com.example.orderapi.controller;

import com.example.domain.config.JwtAuthenticationProvider;
import com.example.orderapi.domain.model.ProductItem;
import com.example.orderapi.domain.product.AddProductForm;
import com.example.orderapi.domain.product.AddProductItemForm;
import com.example.orderapi.domain.product.ProductDto;
import com.example.orderapi.domain.product.ProductItemDto;
import com.example.orderapi.service.ProductItemService;
import com.example.orderapi.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller/product")
@RequiredArgsConstructor
public class SellerProductController {

    private final ProductService productService;

    private final ProductItemService productItemService;

    private final JwtAuthenticationProvider provider;


    @ApiOperation("상품 추가")
    @PostMapping
    public ResponseEntity<ProductDto> addProduct(
        @RequestHeader(name = "X-AUTH-TOKEN") String token,
        @RequestBody AddProductForm form) {
        return ResponseEntity.ok(ProductDto.from
            (productService.addProduct(provider.getUserVo(token).getId(), form)));
    }

    @ApiOperation("상품 아이템 추가")
    @PostMapping("/item")
    public ResponseEntity<ProductDto> addProductItem(
        @RequestHeader(name = "X-AUTH-TOKEN") String token,
        @RequestBody AddProductItemForm form) {
        return ResponseEntity.ok(ProductDto.from
            (productItemService.addProductItem(provider.getUserVo(token).getId(), form)));
    }
}
