package com.decskill.prueba.domain.entity;

import com.decskill.prueba.domain.valueobject.Money;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer priceList;
    private Integer priority;
    private Integer brandId;
    private Timestamp startDate;
    private Timestamp endDate;
    private Money price;
    @ManyToOne
    private Product product;

    public BigDecimal getFinalPrice() {
        return price.getAmount();
    }
}
