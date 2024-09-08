package com.decskill.prueba.dataaccess.adapter;

import com.decskill.prueba.dataaccess.repository.PriceJpaRepository;
import com.decskill.prueba.dataaccess.repository.PriceSpecifications;
import com.decskill.prueba.domain.entity.Price;
import com.decskill.prueba.ports.output.repository.PriceRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class PriceRepositoryImpl implements PriceRepository {
    private final PriceJpaRepository priceJpaRepository;

    public PriceRepositoryImpl(PriceJpaRepository priceJpaRepository) {
        this.priceJpaRepository = priceJpaRepository;
    }

    @Override
    public List<Price> findInRange(Integer productId, Integer brandId, Date applicationDate) {

       return priceJpaRepository.findAll(PriceSpecifications.finalPriceInTheRange(productId, brandId, applicationDate));
    }
}
