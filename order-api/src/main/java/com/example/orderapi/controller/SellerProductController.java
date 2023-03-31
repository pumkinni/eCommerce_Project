package com.example.orderapi.controller;

import com.example.domain.config.JwtAuthenticationProvider;
import com.example.orderapi.domain.model.ProductItem;
import com.example.orderapi.domain.product.AddProductForm;
import com.example.orderapi.domain.product.AddProductItemForm;
import com.example.orderapi.domain.product.ProductDto;
import com.example.orderapi.domain.product.ProductItemDto;
import com.example.orderapi.domain.product.UpdateProductForm;
import com.example.orderapi.domain.product.UpdateProductItemForm;
import com.example.orderapi.service.ProductItemService;
import com.example.orderapi.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @ApiOperation("상품 수정")
    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(
        @RequestHeader(name = "X-AUTH-TOKEN") String token,
        @RequestBody UpdateProductForm form) {
        return ResponseEntity.ok(ProductDto.from
            (productService.updateProduct(provider.getUserVo(token).getId(), form)));
    }

    @ApiOperation("상품 아이템 수정")
    @PutMapping("/item")
    public ResponseEntity<ProductItemDto> updateProductItem(
        @RequestHeader(name = "X-AUTH-TOKEN") String token,
        @RequestBody UpdateProductItemForm form) {
        return ResponseEntity.ok(ProductItemDto.from
            (productItemService.updateProductItem(provider.getUserVo(token).getId(), form)));
    }

    @ApiOperation("상품 삭제")
    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(
        @RequestHeader(name = "X-AUTH-TOKEN") String token,
        @RequestParam Long id) {

        productService.deleteProduct(provider.getUserVo(token).getId(), id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("상품 아이템 삭제")
    @DeleteMapping("/item")
    public ResponseEntity<Void> deleteProductItem(
        @RequestHeader(name = "X-AUTH-TOKEN") String token,
        @RequestParam Long id) {

        productItemService.deleteProductItem(provider.getUserVo(token).getId(), id);
        return ResponseEntity.ok().build();
    }
}
