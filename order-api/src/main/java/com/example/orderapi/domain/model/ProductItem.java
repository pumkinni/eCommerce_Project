package com.example.orderapi.domain.model;

import com.example.orderapi.domain.product.AddProductItemForm;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@AuditOverride(forClass = BaseEntity.class)
public class ProductItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sellerId;
    @Audited
    private String name;
    @Audited
    private Integer price;
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // 파라미터가 하나일 때 : from, 둘 이상일 때 : of
    public static ProductItem of(Long sellerId, AddProductItemForm form) {
        return ProductItem.builder()
            .sellerId(sellerId)
            .name(form.getName())
            .id(form.getProductId())
            .price(form.getPrice())
            .count(form.getCount())
            .build();
    }

}
