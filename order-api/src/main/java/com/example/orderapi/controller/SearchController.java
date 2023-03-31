package com.example.orderapi.controller;


import com.example.domain.config.JwtAuthenticationProvider;
import com.example.orderapi.domain.product.ProductDto;
import com.example.orderapi.service.ProductSearchService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search/product")
@RequiredArgsConstructor
public class SearchController {
    private final ProductSearchService productSearchService;
    private final JwtAuthenticationProvider provider;

    @ApiOperation("상품 리스트 조회")
    @GetMapping
    public ResponseEntity<List<ProductDto>> searchByName(@RequestParam String name){
        return ResponseEntity.ok(productSearchService.searchByName(name)
            .stream().map(ProductDto::withOutItemsfrom).collect(Collectors.toList()));
    }

    @ApiOperation("상품 상세 검색")
    @GetMapping("/detail")
    public ResponseEntity<ProductDto> getDetail(@RequestParam Long productId){
        return ResponseEntity.ok(
            ProductDto.from(productSearchService.getByProductId(productId)));
    }

}
