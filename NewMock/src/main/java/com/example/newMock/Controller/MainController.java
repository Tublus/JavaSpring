package com.example.newMock.Controller;

import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
public class MainController {
    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper= new ObjectMapper();

    @PostMapping (
            value = "info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO){
        try {
            String clientId = requestDTO.getClientId();
            char FirstDiget = clientId.charAt(0);
            BigDecimal minValue = BigDecimal.ZERO;

            BigDecimal maxLimit;
            BigDecimal Balance;
            String Currency  ;
            String rqUID = requestDTO.getRqUID();
            if (FirstDiget == '8'){
                maxLimit = new BigDecimal(2000);
                Currency = "US";

                Balance= minValue.add(new BigDecimal(Math.random()).multiply(maxLimit)).setScale(2, RoundingMode.HALF_UP);

            }
            else   if (FirstDiget == '9'){
                maxLimit = new BigDecimal(1000);
                Currency = "EU";

                Balance= minValue.add(new BigDecimal(Math.random()).multiply(maxLimit)).setScale(2, RoundingMode.HALF_UP);
            }
            else   {
                maxLimit = new BigDecimal(10000);
                Currency = "RUB";

                Balance= minValue.add(new BigDecimal(Math.random()).multiply(maxLimit)).setScale(2, RoundingMode.HALF_UP);
            }
            ResponsDTO responsDTO = new ResponsDTO();

            responsDTO.setRqUID(rqUID);
            responsDTO.setClientId(clientId);
            responsDTO.setAccount(requestDTO.getAccount());
            responsDTO.setCurrency(Currency);
            responsDTO.setBalance(Balance);
            responsDTO.setMaxLimit(maxLimit);

            log.error("******* RequestDTO********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("******* ResponseDTO********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responsDTO));

            return  responsDTO;
        }
        catch (Exception e ){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
