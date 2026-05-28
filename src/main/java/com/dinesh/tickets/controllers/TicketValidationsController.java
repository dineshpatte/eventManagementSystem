package com.dinesh.tickets.controllers;


import com.dinesh.tickets.Domain.DTO.TicketValidationRequestDTO;
import com.dinesh.tickets.Domain.DTO.TicketValidationResponseDTO;
import com.dinesh.tickets.Domain.entities.TicketValidationMethodEnum;
import com.dinesh.tickets.Domain.entities.TicketValidations;
import com.dinesh.tickets.mappers.TicketValidationMappers;
import com.dinesh.tickets.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/ticket-validation")
@RequiredArgsConstructor
public class TicketValidationsController {

    private final TicketValidationService ticketValidationService;
    private final TicketValidationMappers ticketValidationMappers;

    @PostMapping
    public ResponseEntity<TicketValidationResponseDTO> validateTicket(@RequestBody TicketValidationRequestDTO ticketValidationRequestDTO) {
        TicketValidationMethodEnum method =  ticketValidationRequestDTO.getMethod();

        TicketValidations ticketValidation;

        if(TicketValidationMethodEnum.MANUAL.equals(method)){
           ticketValidation =  ticketValidationService.validateTicketByManually(ticketValidationRequestDTO.getId());
        }
        else{
            ticketValidation =  ticketValidationService.validateTicketByQrCode(ticketValidationRequestDTO.getId());
        }

        return ResponseEntity.ok(ticketValidationMappers.toTicketValidationResponseDto(ticketValidation));
    }
}
