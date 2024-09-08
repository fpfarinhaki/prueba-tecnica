package com.decskill.prueba.domain.event;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record RequestPriceResponse(Integer productId, Integer brandId, Integer priceList, String applicationDate,
                                   Double finalPrice) {
}
