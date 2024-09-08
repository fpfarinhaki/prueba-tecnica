package com.decskill.prueba.dataaccess.repository;

import com.decskill.prueba.domain.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path = "prices")
public interface PriceJpaRepository extends JpaRepository<Price, Integer>, JpaSpecificationExecutor<Price> {
}
