package com.codenear.butterfly.product.domain;

import com.codenear.butterfly.product.util.CategoryConverter;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    @Column(nullable = false)
    private String productName;

    private String productImage;

    @Lob
    private String description;

    @Column(nullable = false)
    private Integer originalPrice;

    @Column(nullable = false, precision = 4, scale = 1)
    private BigDecimal saleRate;

    @Convert(converter = CategoryConverter.class)
    @Column(nullable = false)
    private Category category;

    private Integer quantity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private List<Option> options;

    //공동구매 신청현황(인원)
    @Column(nullable = false)
    private Integer purchaseParticipantCount;

    @Column(nullable = false)
    private Integer maxPurchaseCount;

    //재고수량
    @Column(nullable = false)
    private Integer stockQuantity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private List<Keyword> keywords;
}
