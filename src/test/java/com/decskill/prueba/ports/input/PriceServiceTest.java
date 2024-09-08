package com.decskill.prueba.ports.input;

import com.decskill.prueba.domain.entity.Price;
import com.decskill.prueba.domain.entity.Product;
import com.decskill.prueba.domain.event.RequestPriceCommand;
import com.decskill.prueba.domain.event.RequestPriceResponse;
import com.decskill.prueba.domain.valueobject.Money;
import com.decskill.prueba.exception.PriceNotFoundException;
import com.decskill.prueba.ports.output.repository.PriceRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PriceServiceTest {

    private static final int PRODUCT_ID = 12345;
    private static final int BRAND_ID = 5;
    private static final int PRICE_LIST = 1;
    private static final String APPLICATION_DATE = "2023-12-22 10:15:30";
    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceService priceService;

    private Price price1;
    private Product product;
    private RequestPriceCommand command;

    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);
        product = new Product(PRODUCT_ID, "product1", Sets.newLinkedHashSet());
        price1 = Price.builder()
                .priceList(PRICE_LIST).priority(1).product(product).brandId(BRAND_ID)
                .price(new Money(new BigDecimal("25.45"), Currency.getInstance("EUR")))
                .startDate(Timestamp.from(Instant.parse("2023-12-01T00:00:00.00Z")))
                .endDate(Timestamp.from(Instant.parse("2023-12-31T23:59:59.00Z")))
                .build();
        command = new RequestPriceCommand(DateUtils.parseDate(APPLICATION_DATE, "yyyy-MM-dd HH:mm:ss"), PRODUCT_ID, BRAND_ID);
    }

    @Test
    void findFinalPrice_returnsPriceWithHighestPriority() {
        Price price2 = Price.builder()
                .priceList(2).priority(0).product(price1.getProduct()).brandId(BRAND_ID).price(new Money(new BigDecimal("30.50"), Currency.getInstance("EUR")))
                .startDate(Timestamp.from(Instant.parse("2023-12-21T00:00:00.00Z")))
                .endDate(Timestamp.from(Instant.parse("2023-12-23T23:59:59.00Z")))
                .build();
        product.getPrices().add(price2);

        List<Price> prices = Arrays.asList(price1, price2);

        when(priceRepository.findInRange(anyInt(), anyInt(), any(Date.class))).thenReturn(prices);

        RequestPriceResponse response = priceService.findFinalPrice(command);

        assertResponse(response);
    }

    @Test
    void findFinalPrice_handlesSinglePrice() {
        List<Price> prices = List.of(price1);

        when(priceRepository.findInRange(anyInt(), anyInt(), any(Date.class))).thenReturn(prices);

        RequestPriceResponse response = priceService.findFinalPrice(command);

        assertResponse(response);
    }

    @Test
    void findFinalPrice_throwsExceptionWhenNoPriceFound() {
        command = new RequestPriceCommand(new Date(), PRODUCT_ID, BRAND_ID);

        when(priceRepository.findInRange(anyInt(), anyInt(), any(Date.class))).thenReturn(List.of());

        assertThrows(PriceNotFoundException.class, () -> priceService.findFinalPrice(command));
    }

    private void assertResponse(RequestPriceResponse response) {
        verify(priceRepository).findInRange(PRODUCT_ID, BRAND_ID, command.applicationDate());
        assertEquals(price1.getFinalPrice().doubleValue(), response.finalPrice());
        assertEquals(price1.getProduct().getId(), response.productId());
        assertEquals(price1.getBrandId(), response.brandId());
        assertEquals(price1.getPriceList(), response.priceList());
        assertEquals(APPLICATION_DATE, response.applicationDate());
    }
}