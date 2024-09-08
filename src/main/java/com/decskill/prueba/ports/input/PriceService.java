package com.decskill.prueba.ports.input;

import com.decskill.prueba.Constants;
import com.decskill.prueba.domain.entity.Price;
import com.decskill.prueba.domain.event.RequestPriceCommand;
import com.decskill.prueba.domain.event.RequestPriceResponse;
import com.decskill.prueba.exception.PriceNotFoundException;
import com.decskill.prueba.ports.output.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.time.DateFormatUtils.format;

@Service
public class PriceService {
    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public RequestPriceResponse findFinalPrice(RequestPriceCommand command) {
        List<Price> inRange = priceRepository.findInRange(command.productId(), command.brandId(), command.applicationDate());
        Optional<Price> finalPriceOptional = inRange.stream().max(Comparator.comparingInt(Price::getPriority));
        return finalPriceOptional.map(price -> new RequestPriceResponse(
                price.getProduct().getId(),
                price.getBrandId(),
                price.getPriceList(),
                format(command.applicationDate(), Constants.DATE_FORMAT_PATTERN),
                price.getFinalPrice().doubleValue()
        )).orElseThrow(() -> new PriceNotFoundException("Price not found for criteria"));
    }
}
