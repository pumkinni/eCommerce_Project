package com.example.orderapi.domain.product;


import com.example.orderapi.domain.model.Product;
import com.example.orderapi.domain.model.ProductItem;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private List<ProductItemDto> items;


    public static ProductDto from(Product product){
        List<ProductItemDto> items = product.getProductItems()
            .stream().map(ProductItemDto::from).collect(Collectors.toList());

        return ProductDto.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .items(items)
            .build();
    }

    public static ProductDto withOutItemsfrom(Product product){
        return ProductDto.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .build();
    }
}
