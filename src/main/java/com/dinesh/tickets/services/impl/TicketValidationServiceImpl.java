package com.dinesh.tickets.services.impl;

import com.dinesh.tickets.Domain.entities.*;
import com.dinesh.tickets.exceptions.QrCodeNotFoundException;
import com.dinesh.tickets.exceptions.TicketNotFoundException;
import com.dinesh.tickets.repositories.QrCodeRepository;
import com.dinesh.tickets.repositories.TicketRepository;
import com.dinesh.tickets.repositories.TicketValidationRepository;
import com.dinesh.tickets.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketValidationServiceImpl implements TicketValidationService {
    private final TicketValidationRepository ticketValidationRepository;
    private final QrCodeRepository qrCodeRepository;
    private final TicketRepository ticketRepository;



    @Override
    public TicketValidations validateTicketByQrCode(UUID qrCodeId) {
        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QRcodeStatusEnum.ACTIVE)
                .orElseThrow(() -> new QrCodeNotFoundException(
                        String.format(
                                "QR Code with ID %s was not found", qrCodeId
                        )
                ));

        Ticket ticket = qrCode.getTickets();

        return validateTicket(ticket, TicketValidationMethodEnum.QR_SCAN);
    }

    private TicketValidations validateTicket(Ticket ticket,
                                            TicketValidationMethodEnum ticketValidationMethod) {
        TicketValidations ticketValidation = new TicketValidations();
        ticketValidation.setTicket(ticket);
        ticketValidation.setMethod(ticketValidationMethod);

        TicketValidationStatusEnum ticketValidationStatus = ticket.getTicketValidations().stream()
                .filter(v -> TicketValidationStatusEnum.VALID.equals(v.getStatus()))
                .findFirst()
                .map(v -> TicketValidationStatusEnum.INVALID)
                .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(ticketValidationStatus);

        return ticketValidationRepository.save(ticketValidation);
    }

    @Override
    public TicketValidations validateTicketByManually(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(TicketNotFoundException::new);
        return validateTicket(ticket, TicketValidationMethodEnum.MANUAL);
    }
}
