package com.decskill.prueba.ports.output.repository;

import com.decskill.prueba.domain.entity.Price;

import java.util.Date;
import java.util.List;

public interface PriceRepository {
    List<Price> findInRange(Integer productId, Integer brandId, Date applicationDate);
}
