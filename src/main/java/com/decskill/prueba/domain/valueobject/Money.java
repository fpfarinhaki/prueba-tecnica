package com.decskill.prueba.domain.valueobject;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;

@Data
@Embeddable
public class Money {
    private final BigDecimal amount;
    private final String currency;

    public Money() {
        this.amount = BigDecimal.ZERO;
        this.currency = Currency.getInstance("USD").getCurrencyCode();
    }

    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency.getCurrencyCode();
    }
}

