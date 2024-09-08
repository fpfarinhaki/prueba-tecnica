package com.decskill.prueba.domain.event;

import java.util.Date;

public record RequestPriceCommand(Date applicationDate, Integer productId, Integer brandId) {

}
