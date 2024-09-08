package com.decskill.prueba.dataaccess.adapter;

import com.decskill.prueba.Constants;
import com.decskill.prueba.ports.output.repository.PriceRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;

@SpringBootTest
@AutoConfigureTestDatabase
class PriceRepositoryImplTest {
    @Autowired
    PriceRepository priceRepository;

    @Test
    void findInRange() throws ParseException {
        priceRepository.findInRange(35455, 1, DateUtils.parseDate("2020-06-14 10:00:00", Constants.DATE_FORMAT_PATTERN));
    }
}