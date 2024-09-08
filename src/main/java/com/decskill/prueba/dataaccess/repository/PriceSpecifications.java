package com.decskill.prueba.dataaccess.repository;

import com.decskill.prueba.domain.entity.Price;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class PriceSpecifications {
    public static Specification<Price> byProductId(Integer productId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("product").get("id"), productId);
    }

    public static Specification<Price> byBrandId(Integer brandId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("brandId"), brandId);
    }

    public static Specification<Price> inRange(Date applicationDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(criteriaBuilder.literal(applicationDate), root.get("startDate"), root.get("endDate"));
    }

    public static Specification<Price> finalPriceInTheRange(Integer productId, Integer brandId, Date applicationDate) {
        return byProductId(productId).and(byBrandId(brandId)).and(inRange(applicationDate));
    }
}
