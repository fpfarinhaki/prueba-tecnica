package com.decskill.prueba.controller;

import com.decskill.prueba.Constants;
import com.decskill.prueba.domain.event.RequestPriceCommand;
import com.decskill.prueba.domain.event.RequestPriceResponse;
import com.decskill.prueba.ports.input.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/price")
public class PriceController {
    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<RequestPriceResponse> findFinalPrice(@RequestParam Integer productId,
                                                               @RequestParam Integer brandId,
                                                               @RequestParam @DateTimeFormat(pattern = Constants.DATE_FORMAT_PATTERN) Date applicationDate) {
        RequestPriceCommand command = new RequestPriceCommand(applicationDate, productId, brandId);

        RequestPriceResponse response = priceService.findFinalPrice(command);
        return ResponseEntity.ok(response);
    }
}
