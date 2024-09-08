package com.decskill.prueba.dataaccess.repository;

import com.decskill.prueba.domain.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceJpaRepository extends JpaRepository<Price, Integer>, JpaSpecificationExecutor<Price> {
}
